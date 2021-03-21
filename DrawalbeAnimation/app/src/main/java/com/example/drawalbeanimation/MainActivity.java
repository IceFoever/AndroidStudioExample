package com.example.drawalbeanimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable thesunandnight ;
    ImageView imageView ;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);


         imageView = findViewById(R.id.image);
         imageView.setBackgroundResource(R.drawable.animation);
         thesunandnight = (AnimationDrawable) imageView.getBackground();
     }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        thesunandnight.start();
    }
}