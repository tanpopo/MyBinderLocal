package com.mrk.bindertest.mybinderlocal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

// From Guide:
// Serviceは呼出側と同じプロセスで動く
// Serviceの公開ができない

public class MyLocalBoundService extends Service {
    static final String tag = "MyLocalBoundService";
    private final Random mGenerator = new Random();

    public MyLocalBoundService() {
    }

    public class LocalBinder extends Binder {
        MyLocalBoundService getService() {
            return MyLocalBoundService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(tag, "onBind:" + MyUtil.getProessInfo() + ", intent=" + intent);
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent){
        Log.d(tag, "onReBind:" + MyUtil.getProessInfo() + ", intent=" + intent);
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.d(tag, "onUnbind:" + MyUtil.getProessInfo() + ", intent=" + intent);
        return true;
    }

    public void onStart(){

        Log.d(tag, "onStart:" + MyUtil.getProessInfo());
    }

    public void onDestroy(){
        Log.d(tag, "onDestroy:" + MyUtil.getProessInfo());
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
