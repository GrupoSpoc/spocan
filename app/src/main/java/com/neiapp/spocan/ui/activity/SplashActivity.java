package com.neiapp.spocan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;

import android.os.Handler;

import com.neiapp.spocan.BuildConfig;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;


public class SplashActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        Backend backend = Backend.getInstance();

        // ejemplo de get
        backend.getObject(new CallbackInstance<Object>() {
            @Override
            public void onSuccess(Object o) {
                System.out.println(o);
            }
        });

        // ejemplo de post
        backend.createObject(new Object(), new CallbackVoid() {
            @Override
            public void onSuccess() {
                System.out.println("ALL GOOOOD");
            }
        });
*/
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
