package com.example.thesystemofanimal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



    private Context mContext;

    public MyAdapter(Context context){
        mContext = context ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvpos ;
        ImageView img ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpos = itemView.findViewById(R.id.textView_pos) ;
            img  = itemView.findViewById(R.id.imageView) ;


        }
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvpos.setText(MainActivity.arrayList.get(position).get("Placename"));

        Glide.with(mContext).load(MainActivity.arrayList.get(position).get("PhotoUrl")).into(holder.img);
        //  Log.d("園區",MainActivity.arrayList.get(position).get("PlaceName")) ;
        holder.img.setOnClickListener((v)->{
            //Toast.makeText(v.getContext(),"hi",Toast.LENGTH_LONG).show();
            int where = position ;
            Intent intent = new Intent(mContext, MainActivity2.class) ;
            Bundle bundle = new Bundle() ;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            bundle.putString("position",String.valueOf(where));
            bundle.putString("mapcoordinate",MainActivity.arrayList.get(position).get("mapcoordinate"));
            bundle.putString("Placename",MainActivity.arrayList.get(position).get("Placename"));
            bundle.putString("Placenamedescribe",MainActivity.arrayList.get(position).get("Placenamedescribe"));
            intent.putExtras(bundle) ;
            mContext.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return MainActivity.arrayList.size();
    }




}
