package com.neiapp.spocan.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.neiapp.spocan.R;

public class InitiativeActivity extends AppCompatActivity {

    Button mAddPhotoBtn;
    Button mPublishInitiativeBtn;
    Button mCancelPublishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiative);

        mAddPhotoBtn = findViewById(R.id.addPhotoBtn);
        mCancelPublishBtn = findViewById(R.id.cancel_button);
        mPublishInitiativeBtn = findViewById(R.id.publish_button);


        mAddPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "addPhoto", "Aca se deberia pasar a la pantalla que agrega la foto");
            }
        });

        mCancelPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "cancel", "Aca se deberia volver al home o desde donde se hubiera llamado a agregar iniciativa");
            }
        });

        mPublishInitiativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "publish", "Aca se deberia mostrar un toast con _operacion completada_ y redirigirse al home/muro");
            }
        });
    }

}
