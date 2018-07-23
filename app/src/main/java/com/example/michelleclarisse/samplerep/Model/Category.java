package com.example.michelleclarisse.samplerep.Model;

public class Category {
    private String Image, Name;

    public Category(String image, String name) {
        Image = image;
        Name = name;
    }
    public Category(){

    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}

