package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Pelajaran;
import com.example.learnmaterial.model.Slide;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MateriViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Slide>> slides = new MutableLiveData<>();

    public void setSlides(DataSnapshot dataSnapshot) {
        ArrayList<Slide> dataSlide = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Slide s = data.getValue(Slide.class);
                s.setId(data.getKey());
                dataSlide.add(s);
            }
        }

        this.slides.setValue(dataSlide);
    }

    public LiveData<ArrayList<Slide>> getSlides() {
        return slides;
    }
}
