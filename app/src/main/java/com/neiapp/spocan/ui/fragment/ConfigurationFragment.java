package com.neiapp.spocan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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


public class ConfigurationFragment extends Fragment {

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

        changeAccountBtn.setOnClickListener(v -> {
            Backend backend = Backend.getInstance();
            backend.logOut(new CallbackVoid() {
                @Override
                public void onSuccess() {
                    getActivity().runOnUiThread(() -> {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();

                        // logout de google para volver a elegir cuenta
                        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), task -> {
                            Intent logOutIntent = new Intent(requireContext().getApplicationContext(), LoginActivity.class);
                            logOutIntent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(logOutIntent);
                        });
                    });
                }
            });
        });

        return view;
    }

    private void changeAccount() {
        // liberar currentUser para evitar autoLogin

    }
}