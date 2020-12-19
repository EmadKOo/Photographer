package com.example.photographer.pojo;

import java.util.ArrayList;

public class SerializedImage {

    String imgCategory;
    ArrayList<Image> imageList;

    public SerializedImage() {
    }

    public SerializedImage(String imgCategory, ArrayList<Image> imageList) {
        this.imgCategory = imgCategory;
        this.imageList = imageList;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public ArrayList<Image> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Image> imageList) {
        this.imageList = imageList;
    }
}
