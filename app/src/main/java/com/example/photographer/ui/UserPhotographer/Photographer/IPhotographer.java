package com.example.photographer.ui.UserPhotographer.Photographer;

import com.example.photographer.pojo.Category;
import com.example.photographer.pojo.Image;

import java.util.ArrayList;

public interface IPhotographer {

    void imgUploadToStorage(boolean a, String ex);
    void addImageToFirebaseDatabase(boolean a, String ex);

    void configureMainAdapter(ArrayList<Category> userCategories, ArrayList<Image> userImages);
    void getAllCategories(ArrayList<Category> categories);
  //  void getSelectedCategoryFromSpinner(Category category);
    void getFirstName(String name);
}
