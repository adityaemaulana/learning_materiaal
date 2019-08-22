package com.example.learnmaterial.model;

public class Pelajaran {
    private String nama;
    private String id;
    private int jlhSlide, jlhDiskusi;

    public Pelajaran(){
        this.id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJlhSlide() {
        return jlhSlide;
    }

    public void setJlhSlide(int jlhSlide) {
        this.jlhSlide = jlhSlide;
    }

    public int getJlhDiskusi() {
        return jlhDiskusi;
    }

    public void setJlhDiskusi(int jlhDiskusi) {
        this.jlhDiskusi = jlhDiskusi;
    }
}
