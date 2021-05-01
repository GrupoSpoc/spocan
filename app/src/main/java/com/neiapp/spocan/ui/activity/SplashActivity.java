package com.neiapp.spocan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;

import android.os.Handler;

import com.neiapp.spocan.BuildConfig;
import com.neiapp.spocan.R;


public class SplashActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);

        handler = new Handler();
        handler.postDelayed(() -> {
            final Intent intent;

            if (Boolean.parseBoolean(BuildConfig.test)) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, 5000);
    }
}
