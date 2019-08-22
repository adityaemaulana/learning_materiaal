package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Pelajaran;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class PelajaranViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Pelajaran>> arrPelajaran = new MutableLiveData<>();

    public void setPelajaran(DataSnapshot dataSnapshot) {
        ArrayList<Pelajaran> dataPelajaran = new ArrayList<>();
        if (dataSnapshot != null) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Pelajaran p = data.getValue(Pelajaran.class);
                p.setId(data.getKey());
                dataPelajaran.add(p);
            }
        }

        this.arrPelajaran.setValue(dataPelajaran);
    }

    public LiveData<ArrayList<Pelajaran>> getPelajaran() {
        return arrPelajaran;
    }
}
