package com.example.thesystemofanimal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {
    ProgressBar pgSpinner;
     /*廣播相關變數*/
    private static final String FILTER_ACTION="broadcast";
    Reciever reciever=new Reciever();
    Intent intent =new Intent(FILTER_ACTION);
    /*元件*/
    Button get ;
    /*json資料陣列*/
    public static ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    /*coodinate座標*/
    String s[] = new String[1000] ;
    int k = 0 ;

    RecyclerView recyclerView;
    MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            /////Broadreciever
            IntentFilter filter = new IntentFilter();
            filter.addAction(FILTER_ACTION);
            registerReceiver(reciever, filter);
            /////Get資料
            sendGet();


    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
//land
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
//port
        }
    }


    /*廣播部分*/
    @Override
    protected void onResume() {
        super.onResume();
        //   廣播
        sendBroadcast(intent);
        Log.d("Debug","傳送訊息");
    }
    @Override
    protected void onPause() {
        super.onPause();

        //      取消註冊廣播
        unregisterReceiver(reciever);
        Log.d("Debug","取消註冊");
    }

    private void sendGet() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Request request = new Request.Builder()
                .url("https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();

                Log.d("json", result);
                new Thread(() -> {
                    try {
                        JSONObject animal = new JSONObject(result);
                        JSONObject animal2 = animal.getJSONObject("result");
                        JSONArray animal3 = animal2.getJSONArray("results");
                        for (int i = 0; i < animal3.length(); i++) {
                      //      Log.i("園區", animal3.getJSONObject(i).getString("E_Name"));
                      //      Log.i("PhotoUrl", animal3.getJSONObject(i).getString("E_Pic_URL"));
                      //      Log.i("Placenamedescribe", animal3.getJSONObject(i).getString("E_Info"));
                            String Placename = animal3.getJSONObject(i).getString("E_Name");
                            String PhotoUrl = animal3.getJSONObject(i).getString("E_Pic_URL");
                            String Placenamedescribe = animal3.getJSONObject(i).getString("E_Info");
                            String mapcoordinate = animal3.getJSONObject(i).getString("E_Geo") ;
                            Log.i("coodinate", animal3.getJSONObject(i).getString("E_Geo"));



                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("mapcoordinate",mapcoordinate) ;
                            hashMap.put("Placename", Placename);
                            hashMap.put("PhotoUrl", PhotoUrl);
                            hashMap.put("Placenamedescribe", Placenamedescribe);
                            arrayList.add(hashMap);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("找尋失敗");
                    }
                        /**有要在UI上面顯示的內容的話，就必須使用runOnUiThread!*/
                        runOnUiThread(() -> {



                            recyclerView = findViewById(R.id.recyclerview);
                            myAdapter = new MyAdapter(getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                            recyclerView.setAdapter(myAdapter);

                          
                        });


                }).start();




            }

        });






    }



}