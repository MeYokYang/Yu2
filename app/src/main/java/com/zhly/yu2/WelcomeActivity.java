package com.zhly.yu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView tvCountdown;
    Button btnSkip;

    boolean skipAlready = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
        initEvent();

    }



    private void initView() {
        tvCountdown = findViewById(R.id.countdown);
        btnSkip = findViewById(R.id.skip);
    }

    private void initEvent() {

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                tvCountdown.setText("       "+(l/1000));
            }

            @Override
            public void onFinish() {
                if(!skipAlready){
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();

        btnSkip.setOnClickListener(view -> {
            skipAlready = true;
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}