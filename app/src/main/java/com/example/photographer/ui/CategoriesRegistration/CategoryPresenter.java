package com.example.photographer.ui.CategoriesRegistration;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.photographer.R;
import com.example.photographer.pojo.Category;
import com.example.photographer.ui.splash.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryPresenter {

    ICategory iCategory;

    DatabaseReference categoriesReference, favReference, usersReference;
    ArrayList<Category> categoriesList = new ArrayList<>();
    String[] userCategoriesID;
    Context context;
    ArrayList userCategoriesIDList = new ArrayList();
    private static final String TAG = "CategoryPresenter";
    public CategoryPresenter(ICategory iCategory, Context context) {
        this.iCategory = iCategory;
        this.context = context;
        categoriesReference = FirebaseDatabase.getInstance().getReference().child("categories");
    }

    public void getAllCategories(final CategoryAdapter categoryAdapter){
        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Category mCategory;
                categoriesList.clear();
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                   categoriesList.add(datasnapshot.getValue(Category.class));
                }


                Log.d(TAG, "onDataChange: " + categoriesList.get(0).getCategory());
                iCategory.setCategory(categoriesList);
                categoryAdapter.notifyDataSetChanged();
              //  adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateCategories(String favCategories){
        usersReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("categories");

        usersReference.setValue(favCategories).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iCategory.updateUserCategories(true, "");
                }else {
                    iCategory.updateUserCategories(false, task.getException().toString());
                }
            }
        });
    }


     void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.mlogo)
                        .setContentTitle("Welcome to photography family")
                        .setContentText("You`ve arrived to the world of food.Explore now ");

        Intent notificationIntent = new Intent(context, SplashActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    void getFavList(){
        favReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("categories");

        favReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cats = snapshot.getValue(String.class);
                if (!cats.trim().equals("")){
                    userCategoriesID = cats.split(",");

                    userCategoriesIDList.addAll(Arrays.asList(userCategoriesID));
                    iCategory.getFavourieCats(userCategoriesIDList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
