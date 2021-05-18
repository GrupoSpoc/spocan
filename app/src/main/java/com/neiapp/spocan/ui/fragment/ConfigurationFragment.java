package com.neiapp.spocan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.internal.$Gson$Preconditions;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.backend.rest.ServerCallback;
import com.neiapp.spocan.ui.activity.LoginActivity;
import com.neiapp.spocan.ui.activity.MainActivity;

import java.util.Objects;
import java.util.concurrent.Executor;


public class ConfigurationFragment extends Fragment {

    private LoginActivity loginAct;
    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_, container, false);
        Button changeAccountBtn = view.findViewById(R.id.changeAccountBtn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

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
        return view;
    }

    private void changeAccount() {
        Log.d("CHANGE ACCOUNT", "Entró al metodo de cambio de cuenta");
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Cuenta cambiada con exito", Toast.LENGTH_LONG).show();
                Intent logOutIntent = new Intent(requireContext().getApplicationContext(), LoginActivity.class);
                logOutIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logOutIntent);
            }
        });
    }
}