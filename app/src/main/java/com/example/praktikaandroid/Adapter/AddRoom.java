package com.example.praktikaandroid.Adapter;

import java.io.Serializable;

public class AddRoom implements Serializable {
    int image;
    String title;


    public AddRoom(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public AddRoom() {
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
}
