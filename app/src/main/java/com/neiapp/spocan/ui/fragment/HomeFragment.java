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

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neiapp.spocan.models.Initiative;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.ui.activity.InitiativeActivity;
import com.neiapp.spocan.ui.activity.SpocanActivity;
import com.neiapp.spocan.ui.extra.SpinnerDialog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class HomeFragment extends Fragment {
    FloatingActionButton post;
    LinearLayout mparent;
    LayoutInflater layoutInflater;
    List<Initiative> initiatives;
    TextView ownAllSwitch_desc;
    Switch ownAllSwitch;
    NestedScrollView nestedScrollView;
    private static final String POST_ITEM_TAG = "post-item";

    boolean fromCurrentUser = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_, container, false);


        mparent = root.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initiatives = new ArrayList<>();

        // scroll
        nestedScrollView = root.findViewById(R.id.scrollView);
        nestedScrollView.setOnScrollChangeListener(scrollListener);

        // publicar
        post = root.findViewById(R.id.CrearPost);
        post.setOnClickListener(goToCreationListener);

        // filtro propias/todas
        ownAllSwitch = root.findViewById(R.id.own_all_switch);
        ownAllSwitch.setOnClickListener(ownAllFilterListener);
        ownAllSwitch_desc = root.findViewById(R.id.switch_text);
        ownAllSwitch_desc.setText("Todas");

        fetchInitiatives();
        scrollToTop();

        return root;
    }

    private void fetchInitiatives() {
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), "", true);
        spinnerDialog.start();

        final LocalDateTime dateTop = getLastInitiativeDateUTC();

        Backend backend = Backend.getInstance();
        backend.getAllInitiatives(dateTop, fromCurrentUser, new CallbackCollection<Initiative>() {
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
                spocanActivity.runOnUiThread(spinnerDialog::stop);
            }
        });
    }

    private void populatePostItems() {
        // skipeamos las que ya están renderizadas
        initiatives.stream().skip(countPostItems()).forEach(initiative ->  {
            final View myView = layoutInflater.inflate(R.layout.post_item, null, false);

            final TextView user = myView.findViewById(R.id.username);
            user.setText(initiative.getNickname());

            final ImageView img = myView.findViewById(R.id.post_image);
            img.setImageBitmap(initiative.getImage());

            final TextView desc = myView.findViewById(R.id.description);
            desc.setText(initiative.getDescription());

            final String formattedDate = getFormattedDate(initiative.getDateLocal());

            final TextView date = myView.findViewById(R.id.horario);
            date.setText(formattedDate);

            myView.setTag(POST_ITEM_TAG);
            mparent.addView(myView);
        });
    }


    // Esto sirve para saber cuantas publicaciones ya están renderizadas en el muro.
    // Entonces al momento de popular skipeamos ese número para no tener que volver a renderizarlas
    private long countPostItems() {
        int count = 0;

        for (int i = 0; i < mparent.getChildCount(); i ++){
            View view = mparent.getChildAt(i);
            if (POST_ITEM_TAG.equals(view.getTag())) {
                count++;
            }
        }

        return count;
    }

    private String getFormattedDate(LocalDateTime date) {
        Function<Integer, String> addZeros = s -> String.format(Locale.US, "%02d", s);

        int minute = date.getMinute();
        String minuteWithZero = addZeros.apply(minute);

        int hour = date.getHour();
        String hourWithZero = addZeros.apply(hour);

        int day = date.getDayOfMonth();
        String dayWithZero = addZeros.apply(day);

        int monthValue = date.getMonthValue();
        String monthWithZero = addZeros.apply(monthValue);

        String year = String.valueOf(date.getYear());

        return hourWithZero + ":" + minuteWithZero + " " + dayWithZero + "/" + monthWithZero + "/" + year;
    }

    private void scrollToTop() {
        nestedScrollView.scrollTo(0, 0);
    }

    private LocalDateTime getLastInitiativeDateUTC() {
        return initiatives.isEmpty() ? null : initiatives.get(initiatives.size() - 1).getDateUTC();
    }


    // ---- LISTENERS ----

    private final NestedScrollView.OnScrollChangeListener scrollListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView scrollView, int scrollX, int actualY, int oldScrollX, int oldScrollY) {
            if (bottomWasReached(scrollView)) {
                fetchInitiatives();
            }
        }

        private boolean bottomWasReached(NestedScrollView scrollView) {
            View childView = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
            int diff = (childView.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

            return diff == 0;
        }
    };

    private final View.OnClickListener goToCreationListener = v -> {
        Intent intent = new Intent(getContext(), InitiativeActivity.class);
        startActivity(intent);
    };

    private final View.OnClickListener ownAllFilterListener = v -> {
            Switch aSwitch = (Switch) v;

            if (aSwitch.isChecked()) {
                fromCurrentUser = true;
                ownAllSwitch_desc.setText("Propias");
            } else {
                fromCurrentUser = false;
                ownAllSwitch_desc.setText("Todas");

            }

            // Limpiamos el muro para volver a empezar
            scrollToTop();
            mparent.removeAllViews();
            HomeFragment.this.initiatives.clear();

            fetchInitiatives();
    };
}

