package com.neiapp.spocan.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.Models.UserType;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.rest.HTTPCodes;

public class ProfileFragment extends Fragment {

    LayoutInflater layoutInflater;
    LinearLayout mparent;
    ImageView imgView;
    TextView textViewNickname;
    TextView textViewTypeUser;
    TextView textViewCountPosts;
    TextView textEmail;
    ImageView imgProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        Backend backend = Backend.getInstance();
        backend.getUser(new CallbackInstance<User>() {
            @Override
            public void onSuccess(User instance) {
                // Inflate the layout for this fragment
                textViewNickname = view.findViewById(R.id.username_profile);
                textViewNickname.setText(instance.getNickname());
                textViewTypeUser = view.findViewById(R.id.type_of_user_profile);
                textViewTypeUser.setText(instance.getType().toString());
                textViewCountPosts = view.findViewById(R.id.count_posts);
                textViewCountPosts.setText(String.valueOf(instance.getAmountOfInitiatives()));
            }
            @Override
            public void onFailure(String message, Integer httpStatus) {
                if (httpStatus != null) {
                    if (httpStatus == HTTPCodes.NOT_ACCEPTABLE.getCode() || httpStatus == HTTPCodes.BAD_REQUEST_ERROR.getCode()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Comprobar la conexión a Internet", Toast.LENGTH_LONG).show();
                    } else if (httpStatus == HTTPCodes.SERVER_ERROR.getCode()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error del servidor, intente de nuevo mas tarde", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }
}