package com.example.learnmaterial.view;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learnmaterial.R;

public class MainView extends AppCompatActivity implements View.OnClickListener {
    ImageView btnBeranda, btnProfile;
    View btnHeaderBeranda, btnHeaderProfile;
    RelativeLayout btnContainerBeranda, btnContainerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_appbar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, TambahSlideView.class);
                startActivity(intent);
            }
        });

        setNavigationButton();

        setDefaultFragment();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_container_main_beranda) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                BerandaView berandaFragment = new BerandaView();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, berandaFragment, BerandaView.class.getSimpleName());
                transaction.commit();
                setTitle(getResources().getString(R.string.title_beranda));
            }

            berandaSelected();
        } else if (v.getId() == R.id.btn_container_main_profile) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                ProfileView profileFragment = new ProfileView();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, profileFragment, ProfileView.class.getSimpleName());
                transaction.commit();
                setTitle(getResources().getString(R.string.title_profil));
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
            setTitle(getResources().getString(R.string.title_beranda));
        }
    }

    public void setNavigationButton() {
        btnBeranda = findViewById(R.id.btn_main_beranda);
        btnProfile = findViewById(R.id.btn_main_profile);
        btnHeaderBeranda = findViewById(R.id.btn_header_main_beranda);
        btnHeaderProfile = findViewById(R.id.btn_header_main_profile);
        btnContainerBeranda = findViewById(R.id.btn_container_main_beranda);
        btnContainerProfile = findViewById(R.id.btn_container_main_profile);

        Glide.with(this).load(R.drawable.ic_beranda).into(btnBeranda);
        Glide.with(this).load(R.drawable.ic_profile).into(btnProfile);

        btnContainerBeranda.setOnClickListener(this);
        btnContainerProfile.setOnClickListener(this);
        berandaSelected();
    }

    public void berandaSelected() {
        btnBeranda.setColorFilter(getResources().getColor(R.color.state_pressed));
        btnProfile.setColorFilter(getResources().getColor(R.color.state_released));
        btnHeaderBeranda.setVisibility(View.VISIBLE);
        btnHeaderProfile.setVisibility(View.INVISIBLE);
    }

    public void profileSelected() {
        btnBeranda.setColorFilter(getResources().getColor(R.color.state_released));
        btnProfile.setColorFilter(getResources().getColor(R.color.state_pressed));
        btnHeaderBeranda.setVisibility(View.INVISIBLE);
        btnHeaderProfile.setVisibility(View.VISIBLE);
    }
}
