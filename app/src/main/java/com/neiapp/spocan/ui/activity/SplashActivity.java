package com.neiapp.spocan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.neiapp.spocan.R;


public class SplashActivity extends Activity {

    //variables
    Handler handler;
    Animation top_animation;
    Animation bottom_animation;
    ImageView logo;
    TextView company;
    TextView slogan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);

        //animations
        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //hooks
        logo = findViewById(R.id.imageView3);
        company = findViewById(R.id.textView3);
        slogan = findViewById(R.id.textView4);
        logo.setAnimation(top_animation);
        company.setAnimation(bottom_animation);
        slogan.setAnimation(bottom_animation);


        handler = new Handler();
        handler.postDelayed(() -> {
            final Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}
