package com.example.okhttpexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button query = findViewById(R.id.button_GET) ;

        query.setOnClickListener( v ->{
            sendGet();
        }) ;
    }

    private void sendGet() {
        TextView tvRes = findViewById(R.id.text_Respond) ;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build() ;
        Call call = client.newCall(request) ;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                tvRes.setText("查詢失敗");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string() ;
                try {
                    JSONArray data = new JSONArray(result);
                    Log.d("userid", data.getJSONObject(0).getString("userId")) ;
                    tvRes.setText( data.getJSONObject(0).getString("userId")
                            +"\n"+data.getJSONObject(0).getString("id")
                            +"\n"+data.getJSONObject(0).getString("title")
                            +"\n"+data.getJSONObject(0).getString("body"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("找尋失敗");
                }


                 Log.d("json", result) ;

            }
        });
    }


}


