package com.neiapp.spocan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.internal.$Gson$Preconditions;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.ui.activity.LoginActivity;
import com.neiapp.spocan.ui.activity.MainActivity;

import java.util.Objects;
import java.util.concurrent.Executor;


public class ConfigurationFragment extends Fragment {

    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_, container, false);
        Button changeAccountBtn = view.findViewById(R.id.changeAccountBtn);
        Button logOutBtn = view.findViewById(R.id.logOutBtn);

        changeAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Backend.test()) {
                    Toast.makeText(requireContext().getApplicationContext(), "Este entorno es de prueba, CHANGE no disponible. Se redirige al home", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(requireContext().getApplicationContext(), MainActivity.class));
                } else {
                    changeAccount();
                }


            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Backend.test()) {
                    Toast.makeText(requireContext().getApplicationContext(), "Este entorno es de prueba, LOG OUT no disponible. Se redirige al home", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(requireContext().getApplicationContext(), MainActivity.class));
                } else {
                    signOut();
                }

            }
        });

        return view;
    }

    private void changeAccount() {
        //Se necesita el logOut para cambiar de cuenta
        //TODO google sign in es null en este punto, hay que inicializarlo
        Log.d("CHANGE", "ENTRO AL CHANGE ACCOUNT");
        mGoogleSignInClient.signOut();
        Intent changeAccountIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(changeAccountIntent, RC_SIGN_IN);

        //TODO quizas se podria llamar al updateUI pasandole un firebaseUser para que muestre la app para el nuevo user
    }

    private void signOut() {
        Log.d("OUT", "ENTRO AL SIGN OUT");
        //TODO google sign in es null en este punto, hay que inicializarlo
        mGoogleSignInClient.signOut();
        Intent logOutIntent = new Intent(getContext(), LoginActivity.class);
        startActivity(logOutIntent);
    }
}
