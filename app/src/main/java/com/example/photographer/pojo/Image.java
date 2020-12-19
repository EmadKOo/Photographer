package com.example.photographer.pojo;

import java.io.Serializable;

public class Image implements Serializable {

    Category category;
    String name;
    String location;
    String imgPath;
    String numOfLikes;
    String photographerName;
    String photographerUID;

    public Image() {
    }

    public Image(Category category, String name, String location, String imgPath, String numOfLikes, String photographerName, String photographerUID) {
        this.category = category;
        this.name = name;
        this.location = location;
        this.imgPath = imgPath;
        this.numOfLikes = numOfLikes;
        this.photographerName = photographerName;
        this.photographerUID = photographerUID;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(String numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public String getPhotographerUID() {
        return photographerUID;
    }

    public void setPhotographerUID(String photographerUID) {
        this.photographerUID = photographerUID;
    }
}
