package com.neiapp.spocan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.Models.UserType;
import com.neiapp.spocan.R;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.rest.HTTPCodes;

public class RegisterUserActivity extends Activity {

    private Spinner spinner;
    private UserType selectedType;
    private TextView userNicknameInput;
    private String userNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        userNicknameInput = findViewById(R.id.nicknameTextBox);
        Button registerButton = findViewById(R.id.registerButton);

        //Construccion del dropdown para seleccionar tipo de usuario
        spinner = findViewById(R.id.userTypeSpinner);
        UserType[] userTypeOptions = UserType.values();
        ArrayAdapter<UserType> adapter = new ArrayAdapter<UserType>(this, android.R.layout.simple_spinner_dropdown_item, userTypeOptions);
        spinner.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedType = (UserType) spinner.getSelectedItem();
                if (!validateEmptyTextView(userNicknameInput)) {
                    userNickname = userNicknameInput.getText().toString();
                    User newUser = new User(userNickname, selectedType);
                    Backend.getInstance().createUser(newUser, new CallbackInstance<User>() {
                        @Override
                        public void onSuccess(User user) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Usuario registrado con éxito!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra(MainActivity.USER, user);
                                startActivity(intent);
                            });
                        }

                        @Override

                        public void onFailure(String message, int httpStatus) {
                            if (httpStatus == HTTPCodes.NICKNAME_ALREADY_TAKEN.getCode()) {
                                Toast.makeText(getApplicationContext(), "Ya existe el nickname de usuario", Toast.LENGTH_LONG).show();
                                userNicknameInput.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un nickname válido para continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateEmptyTextView(TextView userNicknameInput) {
        return TextUtils.isEmpty(userNicknameInput.getText());
    }
}
