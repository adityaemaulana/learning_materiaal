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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.BerandaSlideAdapter;
import com.example.learnmaterial.model.Slide;
import com.example.learnmaterial.viewmodel.MateriViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarMateriView extends AppCompatActivity {
    private BerandaSlideAdapter adapter;

    private ValueEventListener slideListener;
    private DatabaseReference slideRef;
    private FirebaseDatabase firebaseDB;

    private MateriViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_materi_view);

        Toolbar toolbar = findViewById(R.id.toolbar_daftar_materi);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        ImageButton btnDiskusi = findViewById(R.id.btn_diskusi_daftar_materi);
        btnDiskusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarMateriView.this, DaftarDiskusiView.class);
                startActivity(intent);
            }
        });

        initViewModel();
        initRecyclerView();
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
        adapter = new BerandaSlideAdapter(this);

        RecyclerView rvSlide = findViewById(R.id.rv_daftar_materi);
        rvSlide.setLayoutManager(new LinearLayoutManager(this));
        rvSlide.setAdapter(adapter);
    }

    public void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MateriViewModel.class);
        viewModel.setSlides(null);
        viewModel.getSlides().observe(this, observerSlide);
    }

    public Observer<ArrayList<Slide>> observerSlide = new Observer<ArrayList<Slide>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Slide> slides) {
            adapter.setSlides(slides);
            adapter.setOnItemClickListener(new BerandaSlideAdapter.OnItemSlideClick() {
                @Override
                public void onItemClicked(int position) {
                    String url = slides.get(position).getUrl();
                    WebView webView = findViewById(R.id.wv_materi);
                    String strDoc = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(strDoc);
                }
            });
        }
    };

    public void initDB() {
        firebaseDB = FirebaseDatabase.getInstance();
        slideRef = firebaseDB.getReference().child("materi").child(getIntent()
                .getStringExtra(DaftarModulView.EXTRA_ID_MODUL));
        attachDBListener();
    }

    public void attachDBListener() {
        final ProgressBar pb = findViewById(R.id.pb_daftar_materi);
        if (slideListener == null) {
            pb.setVisibility(View.VISIBLE);

            slideListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setSlides(dataSnapshot);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            slideRef.addListenerForSingleValueEvent(slideListener);
        }
    }

    public void detachDBListener() {
        if (slideListener != null) {
            slideRef.removeEventListener(slideListener);
            slideRef = null;
            slideListener = null;
        }
    }
}
