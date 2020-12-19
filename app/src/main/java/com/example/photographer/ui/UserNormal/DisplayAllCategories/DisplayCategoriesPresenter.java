package com.example.photographer.ui.UserNormal.DisplayAllCategories;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoriesPresenter {

    IDisplayCategories iDisplayCategories;
    DatabaseReference categoriesReference;
    ArrayList<Category> categoriesList = new ArrayList<>();


    public DisplayCategoriesPresenter(IDisplayCategories iDisplayCategories) {
        this.iDisplayCategories = iDisplayCategories;
        categoriesReference = FirebaseDatabase.getInstance().getReference().child("categories");

    }

    public void getAllCategories(){
        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriesList.clear();
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    categoriesList.add(datasnapshot.getValue(Category.class));
                }
                iDisplayCategories.getAllCategories(categoriesList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
