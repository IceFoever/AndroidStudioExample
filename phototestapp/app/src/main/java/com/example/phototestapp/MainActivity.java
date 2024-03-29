package com.example.phototestapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.security.Provider;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName()+"My";

    private String mPath = "";//設置高畫質的照片位址
    public static final int CAMERA_PERMISSION = 100;//檢測相機權限用
    public static final int REQUEST_HIGH_IMAGE = 101;//檢測高畫質相機回傳
    public static final int REQUEST_LOW_IMAGE = 102;//檢測低畫質相機回傳
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btHigh = findViewById(R.id.Button_High);
        Button btLow = findViewById(R.id.Button_Low);
        /**取得相機權限*/
        if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION);
        /**按下低畫質照相之拍攝按鈕*/
        btLow.setOnClickListener(v -> {
            Intent lowIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //檢查是否已取得權限
            if (lowIntent.resolveActivity(getPackageManager()) == null) return;
            startActivityForResult(lowIntent,REQUEST_LOW_IMAGE);
        });

        btHigh.setOnClickListener(v ->{
            Intent highIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //檢查是否已取得權限
            if (highIntent.resolveActivity(getPackageManager()) == null) return;
            //取得相片檔案的URI位址及設定檔案名稱
            File imageFile = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                imageFile = getImageFile();
            }
            if (imageFile == null) return;
            //取得相片檔案的URI位址
            Uri imageUri = FileProvider.getUriForFile(
                    this,
                    "com.example.phototestapp.CameraEx",//記得要跟AndroidManifest.xml中的authorities 一致
                    imageFile
            );
            highIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(highIntent,REQUEST_HIGH_IMAGE);//開啟相機
        });
       

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private File getImageFile() {
        String time = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = time+"_";
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            //給予檔案命名及檔案格式
            File imageFile = File.createTempFile(fileName,".jpg",dir);
            //給予全域變數中的照片檔案位置，方便後面取得
            mPath = imageFile.getAbsolutePath();
            return imageFile;
        } catch (IOException e) {
            return null;
        }
    }

    /**取得照片回傳*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**可在此檢視回傳為哪個相片，requestCode為上述自定義，resultCode為-1就是有拍照，0則是使用者沒拍照*/
        Log.d(TAG, "onActivityResult: requestCode: "+requestCode+", resultCode "+resultCode);
        /**如果是低畫質的相片回傳*/
        if (requestCode == REQUEST_LOW_IMAGE && resultCode == -1) {
            ImageView imageLow = findViewById(R.id.imageView2_Low);
            Bundle getImage = data.getExtras();
            Bitmap getLowImage = (Bitmap) getImage.get("data");
            Matrix matrix = new Matrix();
            matrix.setRotate(90f);

            //以Glide設置圖片
            Glide.with(this)
                    .load(getLowImage)
                    .centerCrop()
                    .into(imageLow);
        }else if (requestCode == REQUEST_HIGH_IMAGE && resultCode == -1){
                 ImageView imageView_High = findViewById(R.id.imageView_High) ;
                 new Thread(() -> {
                     AtomicReference<Bitmap> High_imagine = new AtomicReference<>(BitmapFactory.decodeFile(mPath)) ;
                     Matrix matrix = new Matrix();
                     matrix.setRotate(90f);//轉90度
                     High_imagine.set(Bitmap.createBitmap(High_imagine.get()
                             ,0,0
                             ,High_imagine.get().getWidth()
                             ,High_imagine.get().getHeight()
                             ,matrix,true));
                     runOnUiThread(()->{
                         //以Glide設置圖片(因為旋轉圖片屬於耗時處理，故會LAG一下，且必須使用Thread執行緒)
                         Glide.with(this)
                                 .load(High_imagine.get())
                                 .centerCrop()
                                 .into(imageView_High);
                     });
                 }).start();
        }else{
            Toast.makeText(this, "未作任何拍攝", Toast.LENGTH_SHORT).show();
        }
    }

}