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

import org.w3c.dom.Text;

public class RegisterUserActivity extends Activity {

    private Spinner spinner;
    private UserType selectedType;
    private Button registerButton;
    private TextView userNicknameInput;
    private CharSequence userNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        userNicknameInput = findViewById(R.id.userTextBox);
        registerButton = findViewById(R.id.registerButton);

        //Construccion del dropdown para seleccionar tipo de usuario
        spinner = findViewById(R.id.userTypeSpinner);
        UserType[] userTypeOptions = UserType.values();
        ArrayAdapter<UserType> adapter = new ArrayAdapter<UserType>(this, android.R.layout.simple_spinner_dropdown_item, userTypeOptions);
        spinner.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtener nickname del input
                userNickname = userNicknameInput.getText();
                //Obtener tipo seleccionado
                selectedType = (UserType) spinner.getSelectedItem();
                //TODO: Acá pasarle al BE los datos del form para registrar al usuario nuevo (nickname del input y la opcion seleccionada del dropdown)
                if (!userNickname.toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "usuario: " + userNickname + " tipo: " + selectedType, Toast.LENGTH_LONG).show();

                    //TODO: Redirigir al main activity
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un usuario válido para continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
