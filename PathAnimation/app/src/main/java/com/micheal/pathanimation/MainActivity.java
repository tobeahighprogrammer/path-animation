package com.micheal.pathanimation;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.micheal.pathanimation.wave.WaveView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    private WaveView waveView;
    private Button btn_charge,btn_discharge;
    private int capacity=0;
    private Handler chargeHandler;
    private Thread mThread;
    private HandlerThread handlerThread;
    private ChargeRunnable chargeRunnable;
    private boolean isCharging=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");

        setContentView(R.layout.activity_main);
        initViews();
        waveView.setChargingStatus(isCharging);

//        mThread = new Thread(r);
//        mThread.start();

//        handlerThread=new HandlerThread("chargeThread");
//        handlerThread.start();
//
//        handler.post(r);

//        chargeRunnable=new ChargeRunnable();
//        ChargeHandler=new Handler(Looper.getMainLooper());
//        ChargeHandler.postDelayed(chargeRunnable,250);
//        waveView.invalidate();
    }

    private void initViews(){
        waveView=(WaveView) this.findViewById(R.id.wv_percent);
        btn_charge=(Button) this.findViewById(R.id.btn_charge);
        btn_discharge=(Button) this.findViewById(R.id.btn_discharge);

        btn_charge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!isCharging){
                    isCharging=true;
                    waveView.setChargingStatus(true);
                }
            }
        });

        btn_discharge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isCharging){
                    isCharging=false;
                    waveView.setChargingStatus(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
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
        Log.i(TAG, "onDestroy: ");

        handler.removeCallbacks(r);
        try{
            if(mThread!=null){
                mThread.join();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        mThread=null;
        handler=null;
    }
}
