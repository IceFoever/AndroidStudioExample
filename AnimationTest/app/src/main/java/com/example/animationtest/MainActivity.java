package com.example.animationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    Button alpha, scale, translate, rotate ;
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alpha = findViewById(R.id.Alpha) ;
        scale = findViewById(R.id.Scale) ;
        translate = findViewById(R.id.Translate) ;
        rotate = findViewById(R.id.Rotate) ;
        imageView = findViewById(R.id.imageView2) ;

        alpha.setOnClickListener(v -> {
            imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha));
        });
        scale.setOnClickListener(v -> {
            imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scale));
        });
        translate.setOnClickListener(v ->  {
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
        });
        rotate.setOnClickListener(v -> {
            imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

        });

    }
}