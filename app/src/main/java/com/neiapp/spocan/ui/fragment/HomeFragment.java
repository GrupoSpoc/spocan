package com.neiapp.spocan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.ui.activity.InitiativeActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HomeFragment extends Fragment {

    FloatingActionButton post;
    LinearLayout mparent;
    LayoutInflater layoutInflater;
    List<Initiative> initiatives;
    TextView switch_desc;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_, container, false);
        //publicaciones
        switch_desc = root.findViewById(R.id.switch_text);
        switch_desc.setText("Todas");
        mparent = root.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initiatives = new ArrayList<>();

        fetchInitiatives();

        //publicar
        post = root.findViewById(R.id.CrearPost);
        post.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InitiativeActivity.class);
            startActivity(intent);
        });

        Switch switchButton = root.findViewById(R.id.switch1);

        switchButton.setOnClickListener(new FilterByCurrentUserListener());
        return root;
    }

    private void fetchInitiatives() {
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "Cargando iniciativas...");
        spinnerDialog.start();
        Backend backend = Backend.getInstance();
        backend.getAllInitiatives(new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> initiatives) {

                getActivity().runOnUiThread(() -> {
                    HomeFragment.this.initiatives = initiatives;
                    populatePostItemsWithEveryInitiative();
                    spinnerDialog.stop();
                });
            }

            @Override
            public void onFailure(String message, int httpStatus) {
                SpocanActivity spocanActivity = (SpocanActivity) getActivity();
                spocanActivity.handleError(message, httpStatus);
                spocanActivity.runOnUiThread(() -> spinnerDialog.stop());
            }
        });
    }

    private void populatePostItemsWithEveryInitiative() {
        populatePostItems(initiative -> true);
    }


    private void populatePostItems(Predicate<Initiative> predicate) {
        mparent.removeAllViews();

        initiatives.stream().filter(predicate).forEach(initiative ->  {
            final View myView = layoutInflater.inflate(R.layout.post_item, null, false);

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
        });
    }

    private String getFormattedDate(Initiative initiative) {
        int minuto = initiative.getDateLocal().getMinute();
        String minutoConCero = String.format("%02d", minuto);
        int hora = initiative.getDateLocal().getHour();
        String horaConCero = String.format("%02d", hora);
        int diaInt = initiative.getDateLocal().getDayOfMonth();
        String dia = String.format("%02d", diaInt);
        int mesInt = initiative.getDateLocal().getMonthValue();
        String mes = String.format("%02d", mesInt);
        String año = String.valueOf(initiative.getDateLocal().getYear());

        return horaConCero +":"+ minutoConCero + " " + dia + "/" + mes + "/" + año;
    }

    private class FilterByCurrentUserListener implements View.OnClickListener {
        public void onClick(View v) {
            Switch aSwitch = (Switch) v;
            if (aSwitch.isChecked()) {
                populatePostItems(Initiative::isFromCurrentUser);
                switch_desc.setText("Propias");
            } else {
                populatePostItemsWithEveryInitiative();
                switch_desc.setText("Todas");
            }
        }
    }
}

