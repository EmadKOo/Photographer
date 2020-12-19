package com.example.photographer.pojo;

import java.io.Serializable;

public class Category implements Serializable {
    int id;
    String userID;
    String category;
    String categoryImage;

    public Category() {
    }

    public Category(int id, String userID, String category, String categoryImage) {
        this.id = id;
        this.userID = userID;
        this.category = category;
        this.categoryImage = categoryImage;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
