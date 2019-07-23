package com.example.learnmaterial.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.learnmaterial.R;

public class RegisterView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        Button btnLogin = findViewById(R.id.btn_login2);
        Button btnRegis = findViewById(R.id.btn_register2);
        btnLogin.setOnClickListener(this);
        btnRegis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login2) {
            Intent intent = new Intent(RegisterView.this, LoginView.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_register2) {
            Intent intent = new Intent(RegisterView.this, LoginView.class);
            startActivity(intent);
        }
    }
}
