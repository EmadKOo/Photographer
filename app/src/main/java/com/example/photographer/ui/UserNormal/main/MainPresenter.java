package com.example.photographer.ui.UserNormal.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Image;
import com.example.photographer.pojo.SerializedImage;
import com.example.photographer.ui.CategoriesRegistration.CategoriesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainPresenter {

    ArrayList<Image> imagesList = new ArrayList<>();
    ArrayList<SerializedImage> serializedImagesList = new ArrayList<>();
    DatabaseReference imgRef;
    DatabaseReference userRef;

    DatabaseReference userCategoriesRef;
    String[] userCategoriesID;
    ArrayList<String> userCategoriesIDList = new ArrayList<>();
    IMain iMain;
    Fonts fonts ;
    private static final String TAG = "MainPresenter";

    public MainPresenter(IMain iMain) {
        this.iMain = iMain;
        imgRef = FirebaseDatabase.getInstance().getReference();
        userCategoriesRef = FirebaseDatabase.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference();
    }


    public void getUserCategories(){
        userCategoriesRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("categories")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cats = snapshot.getValue(String.class);
                userCategoriesID = cats.split(",");

                userCategoriesIDList.addAll(Arrays.asList(userCategoriesID));

                Log.d(TAG, "onDataChange: CATS " + userCategoriesIDList.size() );

                 getAllImages();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getAllImages(){
        imgRef.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serializedImagesList = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        for (DataSnapshot snapshot3: snapshot2.getChildren()) {
                           //if (userCategoriesIDList.contains(String.valueOf(snapshot3.getValue(Image.class).getCategory().getId())))
                                 imagesList.add(snapshot3.getValue(Image.class));
                        }
                         Log.d(TAG, "onDataChange: SIZZER " + imagesList.size());
                    }
                    Log.d(TAG, "onDataChange: SIZER " + imagesList.size());
                    if (userCategoriesIDList.contains(String.valueOf(imagesList.get(0).getCategory().getId())))
                         serializedImagesList.add(new SerializedImage(imagesList.get(0).getCategory().getCategory(), imagesList));
                    imagesList = new ArrayList<>();
                }
                iMain.getAllImagesCategoriezed(serializedImagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUserName(){
        userRef = FirebaseDatabase.getInstance().getReference();
        userRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("fName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                iMain.getUserName(capitalizeFirstChar(snapshot.getValue(String.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String capitalizeFirstChar(String imageTitle){

        String[] title = imageTitle.split(" ");
        String updatedText = "";
        for (int x = 0; x < title.length; x++) {
            updatedText += title[x].substring(0,1).toUpperCase() + title[x].substring(1,title[x].length()).toLowerCase() + " ";
        }
        return updatedText;
    }

     void dialogAbout(Context context){
         fonts = new Fonts(context);
         final Dialog dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about);
         dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

         TextView title = dialog.findViewById(R.id.title);
         TextView info = dialog.findViewById(R.id.info);

         title.setTypeface(fonts.getComfortaaFont());
         info.setTypeface(fonts.getJostFont());
        dialog.show();
    }

    void dialogNoItems(final Context context){
        fonts = new Fonts(context);
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_items_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        TextView noItems = dialog.findViewById(R.id.noItems);
        TextView selectItems = dialog.findViewById(R.id.selectItems);
        Button btnBackCategory = dialog.findViewById(R.id.btnBackCategory);

        noItems.setTypeface(fonts.getJostFont());
        selectItems.setTypeface(fonts.getComfortaaFont());
        btnBackCategory.setTypeface(fonts.getComfortaaFont());

        btnBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CategoriesActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
