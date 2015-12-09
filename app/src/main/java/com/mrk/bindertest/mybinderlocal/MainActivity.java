package com.mrk.bindertest.mybinderlocal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//ref: http://inon29.hateblo.jp/entry/2014/03/31/213229
public class MainActivity extends AppCompatActivity {
    static final String tag = "MainActivity";

    ///////////////////////////////////
    //bound service
    MyLocalBoundService mBindService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Serviceとの接続確立時に呼び出される。
            // service引数には、Onbind()で返却したBinderが渡される
            mBindService = ((MyLocalBoundService.LocalBinder) service).getService();

            Log.d(tag, "onServiceConnected: callingPid=" + Integer.toString(((MyLocalBoundService.LocalBinder) service).getCallingPid()));
            Log.d(tag, "onServiceConnected: callingUid=" + Integer.toString(((MyLocalBoundService.LocalBinder) service).getCallingUid()));
            //必要であればmBoundServiceを使ってバインドしたServiceへの制御を行う
        }

        public void onServiceDisconnected(ComponentName className) {
            // Serviceとの切断時に呼び出される。
            mBindService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "onCreate:" + MyUtil.getProessInfo());


        ///////////////////////////////////
        //started service
        Button startButton = (Button) findViewById(R.id.startService);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Serviceの起動
                Log.d(tag, "startService#onClick:" + MyUtil.getProessInfo());

                EditText editText = (EditText)findViewById(R.id.sleepTime);
                int sleep_time = Integer.getInteger(editText.getText().toString(), -1);
                Log.d(tag, "startService sleep_time=" + editText.getText());

                Intent intent = new Intent(MainActivity.this, MyLocalStartedService.class);
                intent.putExtra("sleep_time", sleep_time);
                startService(intent);
            }
        });

        Button stopButton = (Button) findViewById(R.id.stopService);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Serviceの停止
                Log.d(tag, "stopService#onClick:" + MyUtil.getProessInfo());
                Intent intent = new Intent(MainActivity.this, MyLocalStartedService.class);
                stopService(intent);
            }
        });
        ///////////////////////////////////
        //bound service
        Button bindButton = (Button) findViewById(R.id.bindService);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Serviceの起動
                Log.d(tag, "bindService#onClick:" + MyUtil.getProessInfo());
                Intent intent = new Intent(MainActivity.this, MyLocalBoundService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
        });
        Button unbindButton = (Button) findViewById(R.id.unbindService);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Serviceの起動
                Log.d(tag, "unbindService#onClick:" + MyUtil.getProessInfo());
                Intent intent = new Intent(MainActivity.this, MyLocalBoundService.class);
                unbindService(mConnection);
            }
        });
        Button callServiceButton = (Button) findViewById(R.id.callService);
        callServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBindService != null) {
                    int num = mBindService.getRandomNumber();
                    try{
                        Thread.sleep(3000); //3000ミリ秒Sleepする
                    } catch(InterruptedException e){
                        Log.e(tag, "sleep throw exception");
                    }

                    Toast.makeText(MainActivity.this, "number:" + num, Toast.LENGTH_LONG).show();
                }
            }
        });
    }//end onCreate()
}



