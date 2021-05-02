package com.neiapp.spocan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.neiapp.spocan.Models.UserType;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.callback.CallbackVoid;

public class RegisterUserActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private UserType type;
    private Button registerButton;
    private TextView userNicknameInput;
    private String userNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        //Construccion del dropdown para seleccionar tipo de usuario
        spinner = findViewById(R.id.userTypeSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Obtener nickname del input
        userNicknameInput = findViewById(R.id.userTextBox);
        userNickname = (String) userNicknameInput.getText();

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Acá pasarle al BE los datos del form para registrar al usuario nuevo (nickname del input y la opcion seleccionada del dropdown)
                try{


                }catch (RuntimeException e){
                    e.getMessage();
                    e.getStackTrace();

                }

                //TODO: Despues tiene que redirigir al main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.type = (UserType) parent.getItemAtPosition(position);
        Log.d("El tipo usuario seleccionado es: ", type.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "Tiene que seleccionar una opcion", Toast.LENGTH_SHORT).show();
        throw new RuntimeException("No se elegió ninguna opcion");
    }
}
