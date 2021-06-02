package com.neiapp.spocan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.ui.activity.InitiativeActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HomeFragment extends Fragment {

    FloatingActionButton post;
    LinearLayout mparent;
    LayoutInflater layoutInflater;
    List<Initiative> initiatives;
    NestedScrollView nestedScrollView;
    int offset;
    boolean fromCurrentUser = false;


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
        initiatives = new ArrayList<>();

        nestedScrollView = root.findViewById(R.id.scrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
           @Override
           public void onScrollChange(NestedScrollView view, int scrollX, int actualY, int oldScrollX, int oldScrollY) {
               if (bottomWasReached(view, actualY)) {
                   if (fromCurrentUser) {
                       offset = initiatives.size();
                   }
                   fetchInitiatives();

               }
           }

           private boolean bottomWasReached(NestedScrollView view, int actualY) {
               final int bottomY = Math.abs(view.getMeasuredHeight() - view.getChildAt(0).getMeasuredHeight());
               return  bottomY - actualY == 0;
           }

       });

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
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "", true);
        spinnerDialog.start();
        Backend backend = Backend.getInstance();
        final LocalDateTime dateTop = getLastInitiativeDateUTC();
        backend.getAllInitiatives(dateTop, fromCurrentUser, offset, new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> initiatives) {

                getActivity().runOnUiThread(() -> {
                    HomeFragment.this.initiatives.addAll(initiatives);
                    populatePostItems();
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

    private void populatePostItems() {
        mparent.removeAllViews();
        initiatives.forEach(initiative ->  {
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
        String dia = String.valueOf(initiative.getDateLocal().getDayOfMonth());
        String mes = String.valueOf(initiative.getDateLocal().getMonthValue());
        String año = String.valueOf(initiative.getDateLocal().getYear());

        return horaConCero +":"+ minutoConCero + " " + dia + "/" + mes + "/" + año;
    }

    private class FilterByCurrentUserListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Switch aSwitch = (Switch) v;

            if (aSwitch.isChecked()) {
                fromCurrentUser = true;
            } else {
                fromCurrentUser = false;
            }

            offset = 0;
            HomeFragment.this.initiatives.clear();
            fetchInitiatives();
        }
    }

    private LocalDateTime getLastInitiativeDateUTC() {
        return initiatives.isEmpty() ? null : initiatives.get(initiatives.size() - 1).getDateUTC();
    }
}

