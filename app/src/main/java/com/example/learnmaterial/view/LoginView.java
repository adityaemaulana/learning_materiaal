package com.example.learnmaterial.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.learnmaterial.R;

public class LoginView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegis = findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegis.setOnClickListener(this);

        initEditText();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            Intent intent = new Intent(LoginView.this, MainView.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(LoginView.this, RegisterView.class);
            startActivity(intent);
        }
    }

    public void initEditText() {
        final EditText etEmail = findViewById(R.id.et_login_email);
        final EditText etPassword = findViewById(R.id.et_login_password);
    }
}
