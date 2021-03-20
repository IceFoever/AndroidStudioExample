package com.example.thesystemofanimal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"歡迎來到動物園", Toast.LENGTH_LONG).show();
    }
}
