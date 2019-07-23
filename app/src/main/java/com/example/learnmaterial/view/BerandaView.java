package com.example.learnmaterial.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.learnmaterial.R;
import com.example.learnmaterial.adapter.BerandaSlideAdapter;
import com.example.learnmaterial.model.Slide;
import com.example.learnmaterial.viewmodel.BerandaViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaView extends Fragment {
    private BerandaSlideAdapter adapter;

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

        initSlideRecyclerList(view);

        BerandaViewModel viewModel = ViewModelProviders.of(this).get(BerandaViewModel.class);
        viewModel.setSlides();
        viewModel.getSlides().observe(this, observerSlide);
    }

    public void initSlideRecyclerList(View view) {
        adapter = new BerandaSlideAdapter(getContext());

        RecyclerView rvSlide = view.findViewById(R.id.rv_beranda_slide);
        rvSlide.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSlide.setNestedScrollingEnabled(false);
        rvSlide.setAdapter(adapter);
    }

    public Observer<ArrayList<Slide>> observerSlide = new Observer<ArrayList<Slide>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Slide> slides) {
            adapter.setSlides(slides);
            adapter.setOnItemClickListener(new BerandaSlideAdapter.OnItemSlideClick() {
                @Override
                public void onItemClicked(int position) {
                    Toast.makeText(getContext(), slides.get(position).getJudul() + " dipilih",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
