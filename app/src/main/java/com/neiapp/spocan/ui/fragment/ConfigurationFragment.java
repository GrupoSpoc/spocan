package com.neiapp.spocan.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.ui.activity.LoginActivity;
import com.neiapp.spocan.ui.activity.MainActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;


public class ConfigurationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_, container, false);
        Button changeAccountBtn = view.findViewById(R.id.changeAccountBtn);
        //spinner
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "Cerrando sesión...");

        changeAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.lelon_logo3)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Esta seguro que desea cerrar sesión de CollaborAR?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                spinnerDialog.start();
                                Backend backend = Backend.getInstance();
                                backend.logOut(new CallbackVoid() {
                                    @Override
                                    public void onSuccess() {
                                        SpocanActivity spocanActivity = (SpocanActivity) getActivity();
                                        spocanActivity.runOnUiThread(spocanActivity::logOut);
                                        spocanActivity.runOnUiThread(() -> {
                                            spinnerDialog.stop();
                                        });
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        return view;
    }
}