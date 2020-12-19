package com.example.photographer.ui.CategoriesRegistration;

import com.example.photographer.pojo.Category;

import java.util.ArrayList;

public interface ICategory {
    void setCategory(ArrayList<Category> categories);
    void getFavourieCats(ArrayList favList);
    void updateUserCategories(boolean x, String exception);
}
