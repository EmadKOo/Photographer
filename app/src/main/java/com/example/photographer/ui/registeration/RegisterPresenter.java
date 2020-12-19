package com.example.photographer.ui.registeration;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterPresenter {
    private FirebaseAuth mAuth;
    private  DatabaseReference database;
    private Context context;

    private IRegister iRegister;
    private User user;

    private static final String TAG = "RegisterPresenter";
    public RegisterPresenter(IRegister iRegister){

        this.iRegister = iRegister;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    public void createUser(Context context, final User user){
        this.user = user;
        this.context = context;
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(user.getMail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    iRegister.onSuccess(mAuth.getCurrentUser());
                    user.setFid(mAuth.getCurrentUser().getUid());
                    addUserToFirebaseDatabase(mAuth.getCurrentUser().getUid());
                }else {
                    iRegister.onFailed(task.getException().toString());
                }
            }
        });
    }


    private void addUserToFirebaseDatabase(String uid){
        database.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iRegister.addUserToDatabase(true);
                }else {
                    iRegister.addUserToDatabase(false);
                }
            }
        });
    }




}
