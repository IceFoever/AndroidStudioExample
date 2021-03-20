package com.example.thesystemofanimal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity2 extends AppCompatActivity {
    TextView area ;////hashmap area
    TextView areaintrduce ;///hashmap areaintroduce
    String areaname,areadescribe ;
    String coordinate, capturecoordinate; ///hashmap coodinate of google map
    String xcoordinate, ycoodinate ;
    Button button,mapbutton ;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mapbutton = findViewById(R.id.button2) ;
        button = findViewById(R.id.button) ;

        area = findViewById(R.id.textView) ;
        areaintrduce = findViewById(R.id.textView2) ;
        /**/
        Bundle bundle = getIntent().getExtras();
        String where = bundle.getString("position") ;
        /*coordinate */
        coordinate = bundle.getString("mapcoordinate") ;
        String regex = "[^0-9.]" ;
        Pattern p = Pattern.compile(regex) ;
        Matcher m = p.matcher(coordinate) ;
        capturecoordinate = m.replaceAll("");
        xcoordinate = capturecoordinate.substring(0, 11) ;
        ycoodinate = capturecoordinate.substring(11,21) ;


        /* settext hashmap information */
        areaname = bundle.getString("Placename" );
        areadescribe = bundle.getString("Placenamedescribe") ;
        area.setText(areaname);
        areaintrduce.setText(areadescribe);


        /* button onclicklistner */
        button.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);

        });

        mapbutton.setOnClickListener(v ->{
            Intent intent = new Intent();
           // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle1 = new Bundle() ;
            bundle1.putDouble("xcoordinate",Double.valueOf(xcoordinate));
            bundle1.putDouble("ycoordinate",Double.valueOf(ycoodinate));
            bundle1.putString("areaname",areaname);
            intent.setClass(this, MapsActivity.class);
            intent.putExtras(bundle1) ;
            startActivity(intent);
        });
    }
}