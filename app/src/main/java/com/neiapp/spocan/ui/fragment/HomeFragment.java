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

import org.w3c.dom.Text;

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

        // Inflate the layout for this fragment
        View eso = inflater.inflate(R.layout.fragment_home_, container, false);
        //publicaciones
        mparent = eso.findViewById(R.id.mParent);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //backend
        Backend backend = Backend.getInstance();
        backend.getAll(new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> collection) {
                for(int i = 0; i < collection.size(); i++){
                    View myView = layoutInflater.inflate(R.layout.post_item, null, false);
                    TextView user;
                    ImageView img;
                    TextView desc;
                    user = myView.findViewById(R.id.username);
                    user.setText(collection.get(i).getNickname());
                    img = myView.findViewById(R.id.post_image);
                    img.setImageBitmap(collection.get(i).getImage());
                    desc = myView.findViewById(R.id.description);
                    desc.setText(collection.get(i).getDescription());
                    mparent.addView(myView);
                }
            }
            @Override
            public void onFailure(String message, Integer httpStatus){
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

