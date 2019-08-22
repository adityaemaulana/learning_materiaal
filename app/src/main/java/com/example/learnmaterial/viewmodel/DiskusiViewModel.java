package com.example.learnmaterial.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.learnmaterial.model.Diskusi;

import java.util.ArrayList;

public class DiskusiViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Diskusi>> arrDiskusi = new MutableLiveData<>();

    public void setDiskusi() {
        ArrayList<Diskusi> dummy = new ArrayList<>();
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 1));
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 0));
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 1));
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 0));
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 1));
        dummy.add(new Diskusi("Ada yang tau maksud penjelasan awal materi ini gak ?", 10, 50, 0));

        this.arrDiskusi.setValue(dummy);
    }

    public LiveData<ArrayList<Diskusi>> getDiskusi() {
        return arrDiskusi;
    }
}
