package com.neiapp.spocan.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import java.util.Objects;

public class InitiativeActivity extends AppCompatActivity {

    Button mAddPhotoBtn;
    Button mPublishInitiativeBtn;
    Button mCancelPublishBtn;
    ImageView imageView;
    EditText textDescription;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiative);

        mAddPhotoBtn = findViewById(R.id.addPhotoBtn);
        mCancelPublishBtn = findViewById(R.id.cancel_button);
        mPublishInitiativeBtn = findViewById(R.id.publish_button);
        textDescription = findViewById(R.id.editTextDescription);
        imageView = findViewById((R.id.imageView));
        Toast toast_photoError = Toast.makeText(this, "DEBE AGREGAR UNA FOTO", Toast.LENGTH_LONG);
        Toast toast_descriptionError = Toast.makeText(this, "DEBE AGREGAR UNA DESCRIPCION", Toast.LENGTH_LONG);
        Toast toast_succsess = Toast.makeText(this, "EXITO", Toast.LENGTH_LONG);

        mAddPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    final String[] permission = {Manifest.permission.CAMERA};
                    requestPermissions(permission, 1001);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1001);
                    setResult(RESULT_OK);
                }
            }
        });
        mCancelPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitiativeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mPublishInitiativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    if (!TextUtils.isEmpty(textDescription.getText())) {
                        Initiative intitiative = new Initiative(textDescription.getText().toString(), bitmap, true);
                        Backend backend = Backend.getInstance();
                        backend.createInitiative(intitiative, new CallbackVoid() {
                            @Override
                            public void onSuccess() {
                                toast_succsess.show();
                                Intent intent = new Intent(InitiativeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String message, Integer httpStatus){
                                Toast.makeText(InitiativeActivity.this, " No se pudo procesar la operación", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        toast_descriptionError.show();
                    }
                } else {
                    toast_photoError.show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(resultCode, requestCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                final Bitmap bitmapPreview = (Bitmap) Objects.requireNonNull(data.getExtras().get("data"));
                imageView.setImageBitmap(bitmapPreview);
                this.bitmap = bitmapPreview;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1001);
                setResult(RESULT_OK);
            } else {
                Toast.makeText(InitiativeActivity.this, " DAME LOS PERMISOS PARA LA CAMARA AMEGO", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
