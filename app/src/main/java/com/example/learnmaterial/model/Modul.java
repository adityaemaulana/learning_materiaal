package com.example.learnmaterial.model;

public class Modul {
    private String id;
    private String nama;
    private String pertemuan;
    private int jlhSlide;
    private int jlhDiskusi;

    public Modul(){
        this.id = null;
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

    public String getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(String pertemuan) {
        this.pertemuan = pertemuan;
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
