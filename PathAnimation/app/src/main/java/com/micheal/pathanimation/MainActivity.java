package com.micheal.pathanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.micheal.pathanimation.wave.WaveView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WaveView waveView=(WaveView) this.findViewById(R.id.wv_percent);
        waveView.setProgress(50);
    }
}
