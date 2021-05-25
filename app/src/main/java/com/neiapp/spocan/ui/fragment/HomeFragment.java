package com.neiapp.spocan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.ui.activity.InitiativeActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;

import java.util.List;

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

        View root = inflater.inflate(R.layout.fragment_home_, container, false);
        //publicaciones
        mparent = root.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        fetchInitiatives();

        //publicar
        post = root.findViewById(R.id.CrearPost);
        post.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InitiativeActivity.class);
            startActivity(intent);
        });
        return root;
    }

    private void fetchInitiatives() {
        Backend backend = Backend.getInstance();
        backend.getAllInitiatives(new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> collection) {
                getActivity().runOnUiThread(() -> {
                    for (int i = 0; i < collection.size(); i++) {
                        final View myView = layoutInflater.inflate(R.layout.post_item, null, false);
                        final Initiative initiative = collection.get(i);

                        final TextView user = myView.findViewById(R.id.username);
                        user.setText(initiative.getNickname());

                        final ImageView img = myView.findViewById(R.id.post_image);
                        img.setImageBitmap(initiative.getImage());

                        final TextView desc = myView.findViewById(R.id.description);
                        desc.setText(initiative.getDescription());

                        final String formattedDate = getFormattedDate(initiative);

                        final TextView horario = myView.findViewById(R.id.horario);
                        horario.setText(formattedDate);

                        mparent.addView(myView);
                    }
                });
            }

            @Override
            public void onFailure(String message, int httpStatus) {
                SpocanActivity spocanActivity = (SpocanActivity) getActivity();
                spocanActivity.handleError(message, httpStatus);
            }

            private String getFormattedDate(Initiative initiative) {
                int minuto = initiative.getDateLocal().getMinute();
                String minutoConCero = String.format("%02d", minuto);
                int hora = initiative.getDateLocal().getHour();
                String horaConCero = String.format("%02d", hora);
                String dia = String.valueOf(initiative.getDateLocal().getDayOfMonth());
                String mes = String.valueOf(initiative.getDateLocal().getMonthValue());
                String año = String.valueOf(initiative.getDateLocal().getYear());

                return horaConCero +":"+ minutoConCero + " " + dia + "/" + mes + "/" + año;
            }
        });

    }
}

