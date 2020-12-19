package com.example.photographer.ui.UserNormal.AllImages;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.Image;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllImagesPresenter {
    DatabaseReference imgRef;
    ArrayList<Image> imagesList = new ArrayList<>();

    IAllImages iAllImages;
    private static final String TAG = "AllImagesPresenter";
    public AllImagesPresenter(IAllImages iAllImages) {
        this.iAllImages = iAllImages;
        imgRef = FirebaseDatabase.getInstance().getReference();

    }

    public void getAllImages(final String type){
        imgRef.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        for (DataSnapshot snapshot3: snapshot2.getChildren()) {
                            imagesList.add(snapshot3.getValue(Image.class));
                        }
                    }
                }
                //sortTop(imagesList);
                if (type.equals("All Images")){
                    Collections.shuffle(imagesList);
                    iAllImages.getShuffeledList(imagesList);
                }else if (type.equals("Top Rated")){
                    sortTop(imagesList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sortTop(ArrayList<Image> randomedList){
        ArrayList<Image> topRatedList = new ArrayList<>();
        Collections.sort(randomedList, new Comparator<Image>(){
            @Override
            public int compare(Image o1, Image o2) {
                return Integer.valueOf(o1.getNumOfLikes()).compareTo(Integer.valueOf(o2.getNumOfLikes()));
            }
        });

        for (int x = 0; x < randomedList.size(); x++) {
            Log.d(TAG, "sortTop: " + randomedList.get(x).getName()  + " " + randomedList.get(x).getNumOfLikes());
        }
       // Collections.sort(randomedList, Collections.reverseOrder());

        for (int x = 1; x <= 10; x++) {
            topRatedList.add(randomedList.get(randomedList.size()-x));
        }
        iAllImages.getTopRated(topRatedList);
    }
}
