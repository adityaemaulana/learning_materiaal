package com.example.learnmaterial.model;

public class Slide {
    private String mataKuliah;
    private String judul;
    private String jurusan;
    private String image;

    public Slide(String mataKuliah, String judul, String jurusan, String image) {
        this.mataKuliah = mataKuliah;
        this.judul = judul;
        this.jurusan = jurusan;
        this.image = image;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
