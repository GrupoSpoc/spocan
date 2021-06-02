package com.neiapp.spocan.ui.extra;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.neiapp.spocan.R;

import org.jetbrains.annotations.NotNull;

public class SpinnerDialog {
    private final AlertDialog dialog;

    public SpinnerDialog(@NotNull final Activity activity) {
        this(activity, "Cargando", false); // default message
    }

    public SpinnerDialog(@NotNull final Activity activity, @NotNull final String loadingMessage) {
        this(activity, loadingMessage, false); // default message
    }

    public SpinnerDialog(@NotNull final Activity activity, @NotNull final String loadingMessage, boolean transparent) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View inflatedView = inflater.inflate(R.layout.spinner_dialog, null);
        final TextView loadingTextView = inflatedView.findViewById(R.id.loadingTextView);
        loadingTextView.setText(loadingMessage);
        builder.setView(inflatedView);
        builder.setCancelable(false);
        dialog = builder.create();

        if (transparent) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void start() {
        dialog.show();
    }

    public void stop() {
        dialog.dismiss();
    }

}
