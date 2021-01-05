package com.example.photographer.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter {
    FirebaseAuth mAuth;
    ILogin iLogin;
    DatabaseReference databaseUsers;
    User user;

    private static final String TAG = "LoginPresenter";
    public LoginPresenter(ILogin iLogin){
        this.iLogin = iLogin;
        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void login(String mail, String password){
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   getUser();
                }else {
                    iLogin.login(null, null);
                }
            }
        });
    }

    private void getUser(){
        databaseUsers.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                Log.d(TAG, "onDataChange: " + user.getMail());
                Log.d(TAG, "onDataChange: " + user.getUserType());
                iLogin.login(mAuth.getCurrentUser(), user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}