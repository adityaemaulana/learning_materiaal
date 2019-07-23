package com.example.learnmaterial.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.learnmaterial.R;

public class SplashView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_view);

        ImageView ivBackground = findViewById(R.id.iv_splash);
        Glide.with(this).load(R.drawable.splash_bg)
                .into(ivBackground);

        ImageView ivLogo = findViewById(R.id.iv_logo);
        Glide.with(this).load(R.drawable.ic_logo)
                .into(ivLogo);

        // Loading splash screen selama 3 detik
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(SplashView.this, LoginView.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
