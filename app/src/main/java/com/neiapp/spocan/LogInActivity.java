package com.neiapp.spocan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class LogInActivity extends Activity {

    Button mSignInWithGoogleBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        mSignInWithGoogleBtn.findViewById(R.id.sign_in_button);
    }



}
