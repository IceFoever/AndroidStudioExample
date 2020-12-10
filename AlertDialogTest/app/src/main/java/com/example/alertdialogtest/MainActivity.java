package com.example.alertdialogtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int DIALOG_ID =  1;
    String[] s_list = {"Item01", "Item02", "Item03"} ;
    boolean[] s_list2 = {false, false, false} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button s1,s2,s3,s4,s5 ;
        s1 = findViewById(R.id.button) ;
        s2 = findViewById(R.id.button2) ;
        s3 = findViewById(R.id.button3) ;
        s4 = findViewById(R.id.button4) ;
        s5 = findViewById(R.id.button5) ;

        s1.setOnClickListener(v ->{
            showDialog(1);
        });
        s2.setOnClickListener(v ->{
            showDialog(2);
        });
        s3.setOnClickListener(v ->{
            showDialog(3);
        });
        s4.setOnClickListener(v ->{
            showDialog(4);
        });
        s5.setOnClickListener(v ->{
            showDialog(5);
        });
    }

    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null ;

        switch (id)
        {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
                builder.setTitle("基本的Dialog")
                       .setMessage("第一個基本Dialog")
                       .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(MainActivity.this,"我是確定鍵", Toast.LENGTH_LONG ).show(); ;
                           }
                       }).setNeutralButton("中間鍵", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"我是中間鍵", Toast.LENGTH_LONG ).show(); ;
                    }
                       }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(MainActivity.this,"我是取消鍵", Toast.LENGTH_LONG ).show(); ;
                           }
                       });
                dialog = builder.create();
                break;

            case 2 :

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this) ;
                builder2.setTitle("列表清單的AlertDialog")
                        .setItems(s_list, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, s_list[which], Toast.LENGTH_LONG).show();
                            }
                        });
                dialog = builder2.create();
                break;

            case 3:
                String s[] ;
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("可選式單選dialog") //設定標題文字
                        .setSingleChoiceItems(s_list, 0,  new DialogInterface.OnClickListener()
                        { //設定單選清單
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //whitch 參數代表點擊了哪一個 Item
                                Toast.makeText(MainActivity.this,s_list[which], Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });

                dialog = builder3.create(); //建立對話方塊並存成 dialog
                break;

            case 4:

                AlertDialog.Builder builder4 = new AlertDialog.Builder(this) ;
                builder4.setTitle("Chechkbox的dialog")
                        .setMultiChoiceItems(s_list, s_list2, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                s_list2[which] = isChecked ;
                            }
                        }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String temp = "選擇了" ;
                        for (int i = 0; i< s_list2.length; i++){
                            if(s_list2[i]==true) {
                                temp = temp + s_list[i];
                            }
                        }
                        Toast.makeText(MainActivity.this,temp,Toast.LENGTH_LONG).show();
                    }
                });
                dialog = builder4.create() ;
                break;

            case 5 :

                final View dialog_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null) ;
                AlertDialog.Builder builder5 = new AlertDialog.Builder(this) ;
                builder5.setTitle("客製化自己的diertdialog")
                        .setView(dialog_layout)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView textView = dialog_layout.findViewById(R.id.textView);
                                EditText editText = dialog_layout.findViewById(R.id.editTextTextPersonName) ;
                                Toast.makeText(MainActivity.this,editText.getText(),Toast.LENGTH_LONG).show();
                            }
                        });

                dialog = builder5.create() ;
                break;



            default:
                break;
        }
        return dialog ;

    }


}