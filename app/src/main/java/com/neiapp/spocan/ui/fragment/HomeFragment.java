package com.neiapp.spocan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.R;
import com.neiapp.spocan.ui.activity.InitiativeActivity;

public class HomeFragment extends Fragment {

    FloatingActionButton post;
    LinearLayout mparent;
    LayoutInflater layoutInflater;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View eso = inflater.inflate(R.layout.fragment_home_, container, false);

        //publicaciones
        mparent = eso.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View myView = layoutInflater.inflate(R.layout.post_item, null, false);
        //mparent.addView(myView);
        for(int i = 0; i < 10; i++){
            View myView = layoutInflater.inflate(R.layout.post_item, null, false);
            TextView user;
            user = myView.findViewById(R.id.username);
            user.setText("nicooooo");
            mparent.addView(myView);
        }

        //publicar
        post = eso.findViewById(R.id.CrearPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InitiativeActivity.class);
                startActivity(intent);
            }
        });
        return eso;
    }
}

