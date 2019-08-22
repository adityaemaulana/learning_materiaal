package com.example.learnmaterial.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.BerandaSlideAdapter;
import com.example.learnmaterial.model.Slide;
import com.example.learnmaterial.viewmodel.BerandaViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaView extends Fragment implements View.OnClickListener {
    private WebView webView;
    private BerandaSlideAdapter adapter;
    public static final String EXTRA_JURUSAN = "extra_jurusan";

    private ValueEventListener slideListener;
    private DatabaseReference slideRef;
    private FirebaseDatabase firebaseDB;

    private BerandaViewModel viewModel;

    public BerandaView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.wv_materi_beranda);

        initSlideRecyclerList(view);
        initViewModel();
        initJurusanButton(view);
        initDB();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDBListener();
    }

    public void initSlideRecyclerList(View view) {
        adapter = new BerandaSlideAdapter(getContext());

        RecyclerView rvSlide = view.findViewById(R.id.rv_beranda_slide);
        rvSlide.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSlide.setNestedScrollingEnabled(false);
        rvSlide.setAdapter(adapter);
    }

    public void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BerandaViewModel.class);
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
                    String strDoc = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(strDoc);
                }
            });
        }
    };

    public void initJurusanButton(View view) {
        ImageButton btnIF = view.findViewById(R.id.btn_beranda_if);
        ImageButton btnMBTI = view.findViewById(R.id.btn_beranda_mbti);
        ImageButton btnDKV = view.findViewById(R.id.btn_beranda_dkv);
        ImageButton btnTE = view.findViewById(R.id.btn_beranda_te);
        ImageButton btnTF = view.findViewById(R.id.btn_beranda_tf);
        ImageButton btnSI = view.findViewById(R.id.btn_beranda_si);
        ImageButton btnTK = view.findViewById(R.id.btn_beranda_tk);
        ImageButton btnDI = view.findViewById(R.id.btn_beranda_di);

        btnIF.setOnClickListener(this);
        btnMBTI.setOnClickListener(this);
        btnDKV.setOnClickListener(this);
        btnTE.setOnClickListener(this);
        btnTF.setOnClickListener(this);
        btnSI.setOnClickListener(this);
        btnTK.setOnClickListener(this);
        btnDI.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DaftarPelajaranView.class);

        switch (v.getId()) {
            case R.id.btn_beranda_if:
                intent.putExtra(EXTRA_JURUSAN, "if");
                startActivity(intent);
                break;
            case R.id.btn_beranda_mbti:
                intent.putExtra(EXTRA_JURUSAN, "mbti");
                startActivity(intent);
                break;
            case R.id.btn_beranda_dkv:
                intent.putExtra(EXTRA_JURUSAN, "dkv");
                startActivity(intent);
                break;
            case R.id.btn_beranda_te:
                intent.putExtra(EXTRA_JURUSAN, "te");
                startActivity(intent);
                break;
            case R.id.btn_beranda_tf:
                intent.putExtra(EXTRA_JURUSAN, "tf");
                startActivity(intent);
                break;
            case R.id.btn_beranda_si:
                intent.putExtra(EXTRA_JURUSAN, "si");
                startActivity(intent);
                break;
            case R.id.btn_beranda_tk:
                intent.putExtra(EXTRA_JURUSAN, "tk");
                startActivity(intent);
                break;
            case R.id.btn_beranda_di:
                intent.putExtra(EXTRA_JURUSAN, "di");
                startActivity(intent);
                break;
        }
    }

    public void initDB() {
        firebaseDB = FirebaseDatabase.getInstance();
        slideRef = firebaseDB.getReference().child("materi").child("t1if0m0");
        attachDBListener();
    }

    public void attachDBListener() {
//        final ProgressBar pb = findViewById(R.id.pb_daftar_materi);
        if (slideListener == null) {
//            pb.setVisibility(View.VISIBLE);

            slideListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    viewModel.setSlides(dataSnapshot);
//                    pb.setVisibility(View.GONE);
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
