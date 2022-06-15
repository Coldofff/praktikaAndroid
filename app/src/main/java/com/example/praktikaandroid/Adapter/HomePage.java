package com.example.praktikaandroid.Adapter;

import java.io.Serializable;

public class HomePage implements Serializable {
    int image;
    String title, info;

    public HomePage(int image, String title, String info) {
        this.image = image;
        this.title = title;
        this.info = info;
    }

    public HomePage() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
