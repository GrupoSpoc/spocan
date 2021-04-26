package com.neiapp.spocan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class LogInActivity extends Activity {

    Button mRegularlogInBtn, mSignInWithGoogleBtn, mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        mRegularlogInBtn.findViewById(R.id.regular_login_btn);
        mSignInWithGoogleBtn.findViewById(R.id.google_login_btn);
        mRegisterBtn.findViewById(R.id.register_btn);


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent =  new Intent(getApplicationContext(), RegisterUserActivity.class);
                //startActivity(intent);
            }
        });

    }
}
