package com.example.learnmaterial.model;

public class Slide {
    private String id;
    private String mataKuliah;
    private String judul;
    private String jurusan;
    private String url;

    public Slide() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlSlide) {
        this.url = urlSlide;
    }
}
