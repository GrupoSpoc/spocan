package com.neiapp.spocan.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neiapp.spocan.R;

public class ProfileFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  /*      Backend backend = Backend.getInstance();
          backend.ping();

        ejemplo de get
        backend.getUser(new CallbackInstance<User>() {
            @Override
            public void onSuccess(User u) {
                System.out.println(u);
            }
        });

        // ejemplo de post
        backend.createUser(new User(), new CallbackVoid() {
            @Override
            public void onSuccess() {
                System.out.println("userrrrr");
            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_, container, false);
    }
}