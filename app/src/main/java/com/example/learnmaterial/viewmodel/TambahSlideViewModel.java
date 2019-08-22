package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Modul;
import com.example.learnmaterial.model.Pelajaran;
import com.example.learnmaterial.model.Slide;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class TambahSlideViewModel  extends ViewModel {
    private MutableLiveData<ArrayList<Pelajaran>> arrPelajaran = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Modul>> arrModul = new MutableLiveData<>();

    public void setMataKuliah(DataSnapshot dataSnapshot) {
        ArrayList<Pelajaran> arrMK = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Pelajaran p = data.getValue(Pelajaran.class);
                p.setId(data.getKey());
                arrMK.add(p);
            }
        }

        this.arrPelajaran.setValue(arrMK);
    }

    public LiveData<ArrayList<Pelajaran>> getMataKuliah() {
        return arrPelajaran;
    }

    public void setModul(DataSnapshot dataSnapshot) {
        ArrayList<Modul> arr = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Modul m = data.getValue(Modul.class);
                m.setId(data.getKey());
                arr.add(m);
            }
        }

        this.arrModul.setValue(arr);
    }

    public LiveData<ArrayList<Modul>> getModul() {
        return arrModul;
    }
}
