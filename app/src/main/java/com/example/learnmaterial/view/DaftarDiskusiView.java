package com.example.learnmaterial.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.DaftarDiskusiAdapter;
import com.example.learnmaterial.model.Diskusi;
import com.example.learnmaterial.viewmodel.DiskusiViewModel;

import java.util.ArrayList;

public class DaftarDiskusiView extends AppCompatActivity {
    private DaftarDiskusiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_diskusi_view);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Daftar Diskusi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        initViewModel();
        initRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void initRecyclerView() {
        adapter = new DaftarDiskusiAdapter(this);

        RecyclerView rvDiskusi = findViewById(R.id.rv_daftar_diskusi);
        rvDiskusi.setLayoutManager(new LinearLayoutManager(this));
        rvDiskusi.setAdapter(adapter);
    }

    public void initViewModel() {
        DiskusiViewModel viewModel = ViewModelProviders.of(this).get(DiskusiViewModel.class);
        viewModel.setDiskusi();
        viewModel.getDiskusi().observe(this, observerDiskusi);
    }

    public Observer<ArrayList<Diskusi>> observerDiskusi = new Observer<ArrayList<Diskusi>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Diskusi> arrDiskusi) {
            adapter.setDiskusi(arrDiskusi);
            adapter.setOnItemClickListener(new DaftarDiskusiAdapter.OnItemClick() {
                @Override
                public void onItemClicked(int position) {
                    Toast.makeText(DaftarDiskusiView.this, arrDiskusi.get(position).getJudul() + " dipilih",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
