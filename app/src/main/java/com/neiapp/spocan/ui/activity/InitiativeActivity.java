package com.neiapp.spocan.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.neiapp.spocan.models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

import java.util.Objects;

public class InitiativeActivity extends SpocanActivity {

    final static int IMAGE_MAX_SIZE = 160000; // 400px * 400px
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
        //spinner
        final SpinnerDialog spinnerDialog = new SpinnerDialog(this, "Enviando iniciativa...");
        mAddPhotoBtn = findViewById(R.id.addPhotoBtn);
        mCancelPublishBtn = findViewById(R.id.cancel_button);
        mPublishInitiativeBtn = findViewById(R.id.publish_button);
        textDescription = findViewById(R.id.editTextDescription);
        imageView = findViewById((R.id.imageView));

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mPublishInitiativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateField()) {
                    spinnerDialog.start();
                    Initiative intitiative = new Initiative(textDescription.getText().toString(), bitmap);
                    Backend backend = Backend.getInstance();
                    backend.createInitiative(intitiative, new CallbackVoid() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Iniciativa enviada para evaluar su aprobación", Toast.LENGTH_LONG).show();
                                spinnerDialog.stop();
                            });
                        }

                        @Override
                        public void onFailure(String message, int httpStatus) {
                            InitiativeActivity.super.handleError(message, httpStatus);
                            runOnUiThread(() -> {
                                spinnerDialog.stop();
                            });
                        }
                    });
                }
            }
        });
    }

    // valida que los atributos para la creación de la iniciativa sean válidos.
    private boolean validateField() {
        Boolean result = true;
        if (bitmap == null) {
            result = false;
            Toast.makeText(getApplicationContext(), "Debe agregar una foto", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(textDescription.getText())) {
            result = false;
            Toast.makeText(getApplicationContext(), "Debe agregar una descripción", Toast.LENGTH_LONG).show();
        }
        if (validateFiwareCharacter(textDescription.getText().toString())){
            result = false;
            Toast.makeText(getApplicationContext(), "No se pueden ingresar los siguientes caracteres : ( ) > < ; = ' \" ", Toast.LENGTH_LONG).show();
        }
        return result;
    }

    private boolean validateFiwareCharacter(String description){
            char[] fiwareRestrictedChars = {'<','>','"','=','(',')',';', '\''};
            boolean containRestricted = false;

            for(char i : description.toCharArray()){
                for(char j : fiwareRestrictedChars){
                    if(i == j){
                        containRestricted = true;
                        break;
                    }
                }
            }
            return containRestricted;
    }
     static public Bitmap controlImageSize(Bitmap bitmap){
        Bitmap bitmapResult = bitmap;

        if((bitmap.getWidth()*bitmap.getHeight()) > IMAGE_MAX_SIZE) {
           bitmapResult = Bitmap.createBitmap((bitmapResult.getWidth() * 1/2), bitmapResult.getHeight() * 1/2, Bitmap.Config.ARGB_8888);
        }
         return bitmapResult;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(resultCode, requestCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                final Bitmap bitmapPreview = (Bitmap) Objects.requireNonNull(data.getExtras().get("data"));
                final Bitmap validateBitmap = controlImageSize(bitmapPreview);
                imageView.setImageBitmap(validateBitmap);
                this.bitmap = validateBitmap;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1001);
                setResult(RESULT_OK);
            } else {
                Toast.makeText(getApplicationContext(), "Es necesario aprobar los permisos de la cámara para tomar la foto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected View getViewForAutoHiddingKeyboard() {
        return findViewById(R.id.initiativeView);
    }

}
