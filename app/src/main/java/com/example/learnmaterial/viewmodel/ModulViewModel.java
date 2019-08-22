package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Modul;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ModulViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Modul>> arrModul = new MutableLiveData<>();

    public void setModul(DataSnapshot dataSnapshot) {
        ArrayList<Modul> dataModul = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Modul m = data.getValue(Modul.class);
                m.setId(data.getKey());
                dataModul.add(m);
            }
        }

        this.arrModul.setValue(dataModul);
    }

    public LiveData<ArrayList<Modul>> getModul() {
        return arrModul;
    }
}
