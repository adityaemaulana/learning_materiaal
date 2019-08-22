package com.example.learnmaterial.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.DaftarPelajaranAdapter;
import com.example.learnmaterial.model.Pelajaran;
import com.example.learnmaterial.viewmodel.PelajaranViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarPelajaranView extends AppCompatActivity {
    private DaftarPelajaranAdapter adapter;
    private TabLayout tabLayout;

    private ValueEventListener pelajaranListener;
    private DatabaseReference pelajaranRef;
    private FirebaseDatabase firebaseDB;

    private PelajaranViewModel viewModel;

    public static final String EXTRA_MODUL = "extra_modul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pelajaran_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Pelajaran");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        initTabLayout();
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
        adapter = new DaftarPelajaranAdapter(this);

        RecyclerView rvPelajaran = findViewById(R.id.rv_daftar_pelajaran);
        rvPelajaran.setLayoutManager(new LinearLayoutManager(this));
        rvPelajaran.setAdapter(adapter);
    }

    public void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(PelajaranViewModel.class);
        viewModel.setPelajaran(null);
        viewModel.getPelajaran().observe(this, observerPelajaran);
    }

    public Observer<ArrayList<Pelajaran>> observerPelajaran = new Observer<ArrayList<Pelajaran>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Pelajaran> arrPelajaran) {
            adapter.setPelajaran(arrPelajaran);
            adapter.setOnItemClickListener(new DaftarPelajaranAdapter.OnItemClick() {
                @Override
                public void onItemClicked(int position) {
                    Intent intent = new Intent(DaftarPelajaranView.this, DaftarModulView.class);
                    intent.putExtra(EXTRA_MODUL, arrPelajaran.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    };

    public void initTabLayout() {
        tabLayout = findViewById(R.id.tl_daftar_pelajaran);
        tabLayout.addTab(tabLayout.newTab().setText("Tingkat 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tingkat 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tingkat 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tingkat 4"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String jurusan = getIntent().getStringExtra(BerandaView.EXTRA_JURUSAN);
                detachDBListener();

                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        pelajaranRef = getPelajaranRef(jurusan, "tingkat1");
                        break;
                    case 1:
                        pelajaranRef = getPelajaranRef(jurusan, "tingkat2");
                        break;
                    case 2:
                        pelajaranRef = getPelajaranRef(jurusan, "tingkat3");
                        break;
                    case 3:
                        pelajaranRef = getPelajaranRef(jurusan, "tingkat4");
                        break;
                    default:
                        break;
                }

                attachDBListener();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initDB() {
        firebaseDB = FirebaseDatabase.getInstance();
        pelajaranRef = getPelajaranRef(getIntent().getStringExtra(BerandaView.EXTRA_JURUSAN), "tingkat1");
        attachDBListener();
    }

    public DatabaseReference getPelajaranRef(String jurusan, String tingkat) {
        return firebaseDB.getReference().child("pelajaran").child(jurusan).child(tingkat);
    }

    public void attachDBListener() {
        final ProgressBar pb = findViewById(R.id.pb_daftar_pelajaran);
        if (pelajaranListener == null) {
            pb.setVisibility(View.VISIBLE);

            pelajaranListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setPelajaran(dataSnapshot);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            pelajaranRef.addListenerForSingleValueEvent(pelajaranListener);
        }
    }

    public void detachDBListener() {
        if (pelajaranListener != null) {
            pelajaranRef.removeEventListener(pelajaranListener);
            pelajaranRef = null;
            pelajaranListener = null;
        }
    }
}
