package com.neiapp.spocan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.neiapp.spocan.models.InitiativeStatus;
import com.neiapp.spocan.models.User;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

public class ProfileFragment extends Fragment {

    TextView textViewNickname;
    TextView textViewTypeUser;
    TextView textViewCountPosts;
    TextView textViewApprovedPosts;
    TextView textViewPendingPosts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //spinner
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "Cargando perfil...");
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        Backend backend = Backend.getInstance();
        spinnerDialog.start();
        backend.getUser(new CallbackInstance<User>() {
            @Override
            public void onSuccess(User instance) {
                getActivity().runOnUiThread(() -> {
                    textViewNickname = view.findViewById(R.id.username_profile);
                    textViewNickname.setText(instance.getNickname());
                    textViewTypeUser = view.findViewById(R.id.type_of_user_profile);
                    textViewTypeUser.setText("Tipo de usuario:" + instance.getType().toString());
                    textViewCountPosts = view.findViewById(R.id.count_posts);
                    textViewCountPosts.setText(String.valueOf(instance.getAmountOfInitiatives()));
                    textViewApprovedPosts = view.findViewById(R.id.count_approved_posts);
                    textViewApprovedPosts.setText(String.valueOf(instance.getAmountOfInitiativesByStatus(InitiativeStatus.APPROVED.getId())));
                    textViewPendingPosts = view.findViewById(R.id.count_pending_posts);
                    textViewPendingPosts.setText(String.valueOf(instance.getAmountOfInitiativesByStatus(InitiativeStatus.PENDING.getId())));
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
        return view;
    }
}