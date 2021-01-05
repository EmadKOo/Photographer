package com.example.photographer.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photographer.ui.UserAdmin.Admin.AdminActivity;
import com.example.photographer.ui.UserPhotographer.Photographer.PhotographerActivity;
import com.example.photographer.ui.login.LoginActivity;
import com.example.photographer.ui.UserNormal.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashPresenter {

    FirebaseAuth mAuth;
    Context context;
    DatabaseReference databaseReference;
    Intent intent;
    private static final String TAG = "SplashPresenter";

    public SplashPresenter(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public Intent detectType(final Activity activity){
        if (mAuth.getCurrentUser() != null){
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("userType").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange: " + mAuth.getCurrentUser().getUid());
                    Log.d(TAG, "onDataChange: " + snapshot.toString());
                    if (snapshot.getValue().equals("admin")){
                        intent = new Intent(context, AdminActivity.class);
                        Log.d(TAG, "onDataChange: ADMIN");
                    }else if (snapshot.getValue().equals("photographer")){
                        Log.d(TAG, "onDataChange: photographer");
                        intent = new Intent(context, PhotographerActivity.class);
                    }else if (snapshot.getValue().equals("normal")){
                        intent = new Intent(context, MainActivity.class);
                        Log.d(TAG, "onDataChange: normal");
                    }
                    context.startActivity(intent);
                    activity.finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            intent =  new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            activity.finish();
        }

        return intent;
    }
}
