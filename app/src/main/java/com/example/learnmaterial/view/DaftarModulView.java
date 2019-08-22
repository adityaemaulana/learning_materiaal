package com.example.learnmaterial.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.DaftarModulAdapter;
import com.example.learnmaterial.model.Modul;
import com.example.learnmaterial.viewmodel.ModulViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarModulView extends AppCompatActivity {
    private DaftarModulAdapter adapter;

    private ValueEventListener modulListener;
    private DatabaseReference modulRef;
    private FirebaseDatabase firebaseDB;

    private ModulViewModel viewModel;

    public static final String EXTRA_ID_MODUL = "extra_id_modul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_modul_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Modul");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        initRecyclerView();
        initViewModel();
        initDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDBListener();
    }

    public void initRecyclerView() {
        adapter = new DaftarModulAdapter(this);

        RecyclerView rvModul = findViewById(R.id.rv_daftar_modul);
        rvModul.setLayoutManager(new LinearLayoutManager(this));
        rvModul.setAdapter(adapter);
    }

    public void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ModulViewModel.class);
        viewModel.setModul(null);
        viewModel.getModul().observe(this, observerModul);
    }

    public Observer<ArrayList<Modul>> observerModul = new Observer<ArrayList<Modul>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Modul> arrModul) {
            adapter.setModul(arrModul);
            adapter.setOnItemClickListener(new DaftarModulAdapter.OnItemClick() {
                @Override
                public void onItemClicked(int position) {
                    Intent intent = new Intent(DaftarModulView.this, DaftarMateriView.class);
                    intent.putExtra(EXTRA_ID_MODUL, arrModul.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    };

    public void initDB() {
        firebaseDB = FirebaseDatabase.getInstance();
        modulRef = firebaseDB.getReference().child("modul").child(getIntent()
                .getStringExtra(DaftarPelajaranView.EXTRA_MODUL));
        attachDBListener();
    }

    public void attachDBListener() {
        final ProgressBar pb = findViewById(R.id.pb_daftar_modul);
        if (modulListener == null) {
            pb.setVisibility(View.VISIBLE);

            modulListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setModul(dataSnapshot);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            modulRef.addListenerForSingleValueEvent(modulListener);
        }
    }

    public void detachDBListener() {
        if (modulListener != null) {
            modulRef.removeEventListener(modulListener);
            modulRef = null;
            modulListener = null;
        }
    }
}
