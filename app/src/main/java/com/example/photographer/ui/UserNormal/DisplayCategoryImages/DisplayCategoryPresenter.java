package com.example.photographer.ui.UserNormal.DisplayCategoryImages;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.Image;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoryPresenter {
    private DatabaseReference categoryRef;
    private String categoryName;
    private ArrayList<Image> imageList;
    private IDisplayCategory iDisplayCategory;
    private Image currentImage;
    public DisplayCategoryPresenter(String categoryName, IDisplayCategory iDisplayCategory) {
        this.categoryName = categoryName;
        this.iDisplayCategory = iDisplayCategory;
        categoryRef = FirebaseDatabase.getInstance().getReference();
        imageList = new ArrayList<>();
    }


     void getImagesOfCurrentCategory(){
        categoryRef.child("images").child(categoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        imageList.add(dataSnapshot1.getValue(Image.class));
                    }
                }

                iDisplayCategory.getImagesOfCategory(imageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
