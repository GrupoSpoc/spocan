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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.rest.HTTPCodes;
import com.neiapp.spocan.ui.activity.InitiativeActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

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
        //spinner
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "Cargando iniciativas...");
        spinnerDialog.start();

        // Inflate the layout for this fragment
        View eso = inflater.inflate(R.layout.fragment_home_, container, false);
        //publicaciones
        mparent = eso.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //backend
        Backend backend = Backend.getInstance();
        backend.getAllInitiatives(new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> collection) {
                getActivity().runOnUiThread(() -> {
                    for (int i = 0; i < collection.size(); i++) {
                        View myView = layoutInflater.inflate(R.layout.post_item, null, false);
                        TextView user;
                        ImageView img;
                        TextView desc;
                        TextView horario;
                        String horaChica;
                        int minuto;
                        String dia;
                        String mes;
                        String año;
                        int hora;
                        String horaConCero;
                        String minutoConCero;
                        Initiative initiative = collection.get(i);
                        user = myView.findViewById(R.id.username);
                        user.setText(initiative.getNickname());
                        img = myView.findViewById(R.id.post_image);
                        img.setImageBitmap(initiative.getImage());
                        desc = myView.findViewById(R.id.description);
                        desc.setText(initiative.getDescription());
                        horario = myView.findViewById(R.id.horario);
                        minuto = initiative.getDateLocal().getMinute();
                        minutoConCero = String.format("%02d", minuto);
                        hora = initiative.getDateLocal().getHour();
                        horaConCero = String.format("%02d", hora);
                        dia = String.valueOf(initiative.getDateLocal().getDayOfMonth());
                        mes = String.valueOf(initiative.getDateLocal().getMonthValue());
                        año = String.valueOf(initiative.getDateLocal().getYear());
                        horaChica = horaConCero +":"+ minutoConCero + " " + dia + "/" + mes + "/" + año;
                        horario.setText(horaChica);
                        mparent.addView(myView);
                    }
                    spinnerDialog.stop();
                });
            }

            @Override
            public void onFailure(String message, int httpStatus) {
                SpocanActivity spocanActivity = (SpocanActivity) getActivity();
                spocanActivity.handleError(message, httpStatus);
                spocanActivity.runOnUiThread(() -> {
                    spinnerDialog.stop();
                });
            }
        });

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

