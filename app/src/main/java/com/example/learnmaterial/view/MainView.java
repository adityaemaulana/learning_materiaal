package com.example.learnmaterial.view;

import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learnmaterial.R;

public class MainView extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnBeranda, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_appbar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainView.this, "Clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        setNavigationButton();

        setDefaultFragment();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_main_beranda) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                BerandaView berandaFragment = new BerandaView();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, berandaFragment, BerandaView.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commit();
            }

            berandaSelected();
        } else if (v.getId() == R.id.btn_main_profile) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                ProfileView profileFragment = new ProfileView();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, profileFragment, ProfileView.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commit();
            }

            profileSelected();
        }
    }

    public void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BerandaView berandaFragment = new BerandaView();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(BerandaView.class.getSimpleName());
        if (!(fragment instanceof BerandaView)) {
            fragmentTransaction.add(R.id.main_container, berandaFragment, BerandaView.class.getSimpleName());
            fragmentTransaction.commit();
        }
    }

    public void setNavigationButton() {
        btnBeranda = findViewById(R.id.btn_main_beranda);
        btnProfile = findViewById(R.id.btn_main_profile);

        Glide.with(this).load(R.drawable.ic_beranda).into(btnBeranda);
        Glide.with(this).load(R.drawable.ic_profile).into(btnProfile);

        btnBeranda.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        berandaSelected();
    }

    public void berandaSelected() {
        btnBeranda.setColorFilter(getResources().getColor(R.color.state_pressed));
        btnProfile.setColorFilter(getResources().getColor(R.color.state_released));
    }

    public void profileSelected() {
        btnBeranda.setColorFilter(getResources().getColor(R.color.state_released));
        btnProfile.setColorFilter(getResources().getColor(R.color.state_pressed));
    }
}
