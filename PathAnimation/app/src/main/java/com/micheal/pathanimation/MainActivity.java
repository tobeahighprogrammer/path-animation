package com.micheal.pathanimation;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.micheal.pathanimation.wave.WaveView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    private WaveView waveView;
    private int capacity=0;
    private Handler ChargeHandler;
    private Thread mThread;
    private ChargeRunnable chargeRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waveView=(WaveView) this.findViewById(R.id.wv_percent);

        waveView.setCharging(true);

        mThread = new Thread(r);
        mThread.start();

//        chargeRunnable=new ChargeRunnable();
//        ChargeHandler=new Handler(Looper.getMainLooper());
//        ChargeHandler.postDelayed(chargeRunnable,250);
//        waveView.invalidate();
    }

    private class ChargeRunnable implements Runnable{
        @Override
        public void run() {
            while(true){
                capacity+=25;
                if(capacity>100){
                    capacity=0;
                }
                waveView.setProgress(capacity);
                waveView.invalidate();
            }

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            capacity=(int)msg.obj;
            waveView.setProgress(capacity);
            waveView.invalidate();
        }
    };

    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (true) {
                capacity += 25;
                if (capacity > 100) {
                    capacity = 0;
                }
                Message msg=handler.obtainMessage();
                msg.obj=capacity;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChargeHandler.removeCallbacks(chargeRunnable);
        ChargeHandler=null;

        handler.removeCallbacks(r);
        try{
            mThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        handler=null;
    }
}
