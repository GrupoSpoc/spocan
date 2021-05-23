package com.neiapp.spocan.ui.activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.rest.HTTPCodes;

public abstract class SpocanActivity extends AppCompatActivity {

    public void handleError(String message, int httpStatus) {
        runOnUiThread(() -> {
            if (httpStatus == HTTPCodes.NOT_ACCEPTABLE.getCode()) {
                Toast.makeText(getApplicationContext(), "Su sesión ha caducado, incie sesión nuevamente", Toast.LENGTH_LONG).show();
                logOut();
            } else if (httpStatus == HTTPCodes.SERVER_ERROR.getCode()) {
                Toast.makeText(getApplicationContext(), "Error del servidor, intente de nuevo mas tarde", Toast.LENGTH_LONG).show();
            } else if (httpStatus == HTTPCodes.TIMEOUT.getCode()) {
                Toast.makeText(getApplicationContext(), "No se pudo procesar la operación, reintentelo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logOut() {
        // liberar currentUser para evitar login automatico
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // logout de google para volver a elegir cuenta
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent logOutIntent = new Intent(this, LoginActivity.class);
            logOutIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logOutIntent);
        });
    }

}
