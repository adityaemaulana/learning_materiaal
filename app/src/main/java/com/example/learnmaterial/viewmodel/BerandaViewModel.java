package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Slide;

import java.util.ArrayList;

public class BerandaViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Slide>> slides = new MutableLiveData<>();

    public void setSlides() {
        ArrayList<Slide> dummy = new ArrayList<>();
        dummy.add(new Slide("DAP", "Variable, Record, I/O, Asssignment", "Informatika", ""));
        dummy.add(new Slide("DAP", "Variable, Record, I/O, Asssignment", "Informatika", ""));
        dummy.add(new Slide("DAP", "Variable, Record, I/O, Asssignment", "Informatika", ""));
        dummy.add(new Slide("DAP", "Variable, Record, I/O, Asssignment", "Informatika", ""));
        dummy.add(new Slide("DAP", "Variable, Record, I/O, Asssignment", "Informatika", ""));

        this.slides.setValue(dummy);
    }

    public LiveData<ArrayList<Slide>> getSlides() {
        return slides;
    }
}
