package com.mrk.bindertest.mybinderlocal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;

// From Guide: Intent の送信元に対して、結果などのデータを直接返すことはできないため、
// Broadcast など別の方法を組み合わせて実現する必要がある
// AndroidManifest.xmlの<service>タグにでこのサービスを定義する必要あり
public class MyLocalStartedService extends Service {
    static final String tag = "MyLocalStartedService";

    static int DEFAULT_SLEEP_TIME = 2000;

    public MyLocalStartedService() {
    }

    public void onCreate() {
        super.onCreate();
        Log.d(tag, "onCreate:" + MyUtil.getProessInfo());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag, "onStartCommand:" + MyUtil.getProessInfo());
        int val = intent.getIntExtra("sleep_time", -1);
        val = val < 0 ? DEFAULT_SLEEP_TIME : val;
        try{
            Log.d(tag, "onStartCommand:  sleep " + val + " msecond");
            Thread.sleep(val);
        } catch(InterruptedException e){
            Log.e(tag, "sleep throw exception");
        }
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "onDestroy:" + MyUtil.getProessInfo());
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
