package com.neiapp.spocan.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.rest.HTTPCodes;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        mAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public  GoogleSignInClient getSignInClient(){
        return this.mGoogleSignInClient;
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this, "Logueado Exitosamente! ", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(acc.getIdToken());
        } catch (ApiException ae) {
            Toast.makeText(LoginActivity.this, "Hubo un error al loguearse ", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sign in with credential: Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Hacer algo con el usuario si hace falta
                            updateUI(user);
                        } else {
                            Log.w(TAG, "Fallo en el sign in", task.getException());
                            //Hacer algo para el usuario cuando falla si hace falta
                            updateUI(null);

                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser firebaseUser) {
        firebaseUser.getIdToken(true).addOnCompleteListener(task -> {
            GetTokenResult result = task.getResult();
            String token = result.getToken();

            Backend.authenticate(token, new CallbackInstance<User>() {
                @Override
                public void onFailure(String message, int httpStatus) {
                    if (httpStatus != 0) {
                        if (httpStatus == HTTPCodes.NOT_ACCEPTABLE.getCode() || httpStatus == HTTPCodes.BAD_REQUEST_ERROR.getCode()) {
                            Toast.makeText(getApplicationContext(), "Token invalido o no autorizado", Toast.LENGTH_LONG).show();
                        } else if (httpStatus == HTTPCodes.SERVER_ERROR.getCode()) {
                            Toast.makeText(getApplicationContext(), "Error del servidor, intente de nuevo mas tarde", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onSuccess(User instance) {
                    final Intent intent;
                    if (instance != null) {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(MainActivity.USER, instance);
                    } else {
                        intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                    }
                    startActivity(intent);
                }
            });
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}