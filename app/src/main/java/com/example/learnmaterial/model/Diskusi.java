package com.example.learnmaterial.model;

public class Diskusi {
    private String judul;
    private int jlhLike;
    private int jlhDiskusi;
    private int isLike;

    public Diskusi(){}

    public Diskusi(String judul, int jlhLike, int jlhDiskusi, int isLike) {
        this.judul = judul;
        this.jlhLike = jlhLike;
        this.jlhDiskusi = jlhDiskusi;
        this.isLike = isLike;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getJlhLike() {
        return jlhLike;
    }

    public void setJlhLike(int jlhLike) {
        this.jlhLike = jlhLike;
    }

    public int getJlhDiskusi() {
        return jlhDiskusi;
    }

    public void setJlhDiskusi(int jlhDiskusi) {
        this.jlhDiskusi = jlhDiskusi;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
