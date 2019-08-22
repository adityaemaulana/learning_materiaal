package com.example.learnmaterial.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.learnmaterial.R;
import com.example.learnmaterial.model.Modul;
import com.example.learnmaterial.model.Pelajaran;
import com.example.learnmaterial.model.Slide;
import com.example.learnmaterial.viewmodel.TambahSlideViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TambahSlideView extends AppCompatActivity {
    private EditText etNama;
    private RelativeLayout btnSlide;
    private Button btnSubmit;
    private Spinner spJurusan, spTingkat, spMatkul, spMateri;
    private final ArrayList<String> arrJurusan = new ArrayList<String>() {{
        add("Informatika");
        add("MBTI");
        add("Desain Komunikasi Visual");
        add("Teknik Elektro");
        add("Teknik Fisika");
        add("Sistem Informasi");
        add("Teknik Komputer");
        add("Desain Interior");
    }};

    private final ArrayList<String> arrTingkat = new ArrayList<String>() {{
        add("Tingkat 1");
        add("Tingkat 2");
        add("Tingkat 3");
        add("Tingkat 4");
    }};

    private final ArrayList<String> arrMatakuliah = new ArrayList<>();
    private final ArrayList<String> arrMateri = new ArrayList<>();
    private final ArrayList<Pelajaran> arrDataPelajaran = new ArrayList<>();
    private final ArrayList<Modul> arrDataModul = new ArrayList<>();

    private Slide slide = new Slide();

    private String jurusanKey, tingkatKey, materiKey;

    private ValueEventListener mkValueListener, materiValueListener;
    private DatabaseReference mkRef, materiRef;
    private FirebaseDatabase firebaseDB;

    private ArrayAdapter<String> adapter;
    private TambahSlideViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_slide_view);

        // Hide keyboard on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambahkan Materi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        firebaseDB = FirebaseDatabase.getInstance();

        initUI();
        initViewModel();

        prepareSpinner(spJurusan, arrJurusan, jurusanListener);
        prepareSpinner(spTingkat, arrTingkat, tingkatListener);
        prepareSpinner(spMatkul, arrMatakuliah, mkListener);
        prepareSpinner(spMateri, arrMateri, materiListener);
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

    public void initUI() {
        etNama = findViewById(R.id.et_nama_materi_tambah_slide);
        spJurusan = findViewById(R.id.sp_jurusan_tambah_slide);
        spTingkat = findViewById(R.id.sp_tingkat_tambah_slide);
        spMatkul = findViewById(R.id.sp_matakuliah_tambah_slide);
        spMateri = findViewById(R.id.sp_materi_tambah_slide);
        btnSlide = findViewById(R.id.btn_tambah_slide);
        btnSubmit = findViewById(R.id.btn_submit_tambah_slide);

        btnSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNama.getText().toString().isEmpty()) {
                    slide.setJudul(etNama.getText().toString().trim());
                    slide.setUrl("http://dedetarwidi.staff.telkomuniversity.ac.id/files/2017/01/Minggu_7_Prosedur.pdf");

                    materiRef = firebaseDB.getReference().child("materi").child(materiKey);
                    DatabaseReference newRef = materiRef.push();
                    newRef.setValue(slide);

                    final Snackbar snackbar = Snackbar.make(findViewById(R.id.container_tambah_slide), "Berhasil Menambahkan Slide", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Tutup", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();

                } else {
                    Toast.makeText(TambahSlideView.this, "Isi Nama Materi terlebih dahulu", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TambahSlideViewModel.class);
        viewModel.setMataKuliah(null);
        viewModel.getMataKuliah().observe(this, observerMK);

        viewModel.setModul(null);
        viewModel.getModul().observe(this, observerMateri);
    }

    public Observer<ArrayList<Pelajaran>> observerMK = new Observer<ArrayList<Pelajaran>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Pelajaran> arrPelajaran) {
            arrDataPelajaran.clear();
            arrDataPelajaran.addAll(arrPelajaran);

            arrMatakuliah.clear();
            for (Pelajaran p : arrPelajaran) {
                arrMatakuliah.add(p.getNama());
            }

            prepareSpinner(spMatkul, arrMatakuliah, mkListener);
        }
    };

    public Observer<ArrayList<Modul>> observerMateri = new Observer<ArrayList<Modul>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Modul> arrModul) {
            arrDataModul.clear();
            arrDataModul.addAll(arrModul);

            arrMateri.clear();
            for (Modul m : arrModul) {
                arrMateri.add(m.getNama());
            }

            prepareSpinner(spMateri, arrMateri, materiListener);
        }
    };

    public void prepareSpinner(Spinner sp, ArrayList<String> arr, AdapterView.OnItemSelectedListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(listener);
    }

    private AdapterView.OnItemSelectedListener jurusanListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            detachDBListener();
            switch (position) {
                case 0:
                    jurusanKey = "if";
                    break;
                case 1:
                    jurusanKey = "mbti";
                    break;
                case 2:
                    jurusanKey = "dkv";
                    break;
                case 3:
                    jurusanKey = "te";
                    break;
                case 4:
                    jurusanKey = "tf";
                    break;
                case 5:
                    jurusanKey = "si";
                    break;
                case 6:
                    jurusanKey = "tk";
                    break;
                case 7:
                    jurusanKey = "di";
                    break;
            }

            slide.setJurusan(arrJurusan.get(position));
            generateSpinnerData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener tingkatListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            detachDBListener();
            switch (position) {
                case 0:
                    tingkatKey = "tingkat1";
                    break;
                case 1:
                    tingkatKey = "tingkat2";
                    break;
                case 2:
                    tingkatKey = "tingkat3";
                    break;
                case 3:
                    tingkatKey = "tingkat4";
                    break;
            }

            generateSpinnerData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mkListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (!arrDataPelajaran.isEmpty()) {
                if (materiValueListener != null) {
                    materiRef.removeEventListener(materiValueListener);
                    materiRef = null;
                    materiValueListener = null;
                }

                initMateriDB(arrDataPelajaran.get(position).getId());
                prepareSpinner(spMateri, arrMateri, materiListener);
            }


            slide.setMataKuliah(arrMatakuliah.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener materiListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            materiKey = arrDataModul.get(position).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void generateSpinnerData() {
        if (jurusanKey != null && tingkatKey != null) {
            Log.d("generate", jurusanKey);
            initMataKuliahDB(jurusanKey, tingkatKey);
        }
    }

    public void initMataKuliahDB(String jurusan, String tingkat) {
        mkRef = firebaseDB.getReference().child("pelajaran").child(jurusan).child(tingkat);

        if (mkValueListener == null) {

            mkValueListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setMataKuliah(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            mkRef.addListenerForSingleValueEvent(mkValueListener);
        }
    }

    public void initMateriDB(String id) {
        materiRef = firebaseDB.getReference().child("modul").child(id);

        if (materiValueListener == null) {

            materiValueListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setModul(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            materiRef.addListenerForSingleValueEvent(materiValueListener);
        }
    }

    public void detachDBListener() {
        if (mkValueListener != null) {
            mkRef.removeEventListener(mkValueListener);
            mkRef = null;
            mkValueListener = null;
        }

        if (materiValueListener != null) {
            materiRef.removeEventListener(materiValueListener);
            materiRef = null;
            materiValueListener = null;
        }
    }


}