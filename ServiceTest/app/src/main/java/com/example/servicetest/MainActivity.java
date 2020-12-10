package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyService mMyService = null;

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        private static final String LOG_TAG = "MY LOG TAG";

        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder)
        {
            // TODO Auto-generated method stub
            mMyService = ((MyService.LocalBinder)serviceBinder).getService();
        }

        public void onServiceDisconnected(ComponentName name)
        {
            // TODO Auto-generated method stub
            Log.d(LOG_TAG, "onServiceDisconnected()" + name.getClassName());
        }
    };

    private Button mBtnStartMyService,
            mBtnStopMyService,
            mBtnBindMyService,
            mBtnUnbindMyService,
            mBtnCallMyServiceMethod;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartMyService =  findViewById(R.id.mBtnStartMyService);
        mBtnStopMyService =  findViewById(R.id.mBtnStopMyService);
        mBtnBindMyService =  findViewById(R.id.mBtnBindMyService);
        mBtnUnbindMyService =  findViewById(R.id.mBtnUnBindMyService);
        mBtnCallMyServiceMethod =  findViewById(R.id.mBtnCallMyServiceMethod);

        mBtnStartMyService.setOnClickListener(btnStartMyServiceOnClkLis);
        mBtnStopMyService.setOnClickListener(btnStopMyServiceOnClkLis);
        mBtnBindMyService.setOnClickListener(btnBindMyServiceOnClkLis);
        mBtnUnbindMyService.setOnClickListener(btnUnbindMyServiceOnClkLis);
        mBtnCallMyServiceMethod.setOnClickListener(btnCallMyServiceMethodOnClkLis);
    }

    private View.OnClickListener btnStartMyServiceOnClkLis
            = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            startService(it); //開始Service
        }
    };

    private View.OnClickListener btnStopMyServiceOnClkLis
            = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            stopService(it); //結束Service
        }
    };

    private View.OnClickListener btnBindMyServiceOnClkLis
            = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            bindService(it, mServiceConnection, BIND_AUTO_CREATE); //綁定Service
        }
    };

    private View.OnClickListener btnUnbindMyServiceOnClkLis
            = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            mMyService = null;
            unbindService(mServiceConnection); //解除綁定Service
        }
    };

    private View.OnClickListener btnCallMyServiceMethodOnClkLis
            = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (mMyService != null)
                mMyService.myMethod(); //透過bindService()可以使用Service中的方法
        }
    };
}