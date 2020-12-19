package com.example.photographer.ui.UserAdmin.Admin;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.photographer.pojo.Category;
import com.example.photographer.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPresenter {
    FirebaseAuth mAuth;
    DatabaseReference categoriesReference;
    DatabaseReference usersReference;
    DatabaseReference usersReference2;
    Context context;
    Category category;
    IAdmin iAdmin;
    User user;
    String uid;

    public AdminPresenter(Context context, IAdmin iAdmin) {
        this.iAdmin = iAdmin;
        mAuth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        usersReference2 = FirebaseDatabase.getInstance().getReference().child("users");
        categoriesReference = FirebaseDatabase.getInstance().getReference().child("categories");
        this.context = context;
    }

    public void addCategoryToDatabase(Category category){
        categoriesReference.push().setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iAdmin.addCategory(true, "");
                }else {
                    iAdmin.addCategory(false, task.getException().toString());
                }
            }
        });
    }

    public void grantPermission(final String userType, final String mail){

        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    user = dataSnapshot.getValue(User.class);
                    if (user.getMail().equals(mail)){
                        // update user
                        uid = user.getFid();
                    }
                }

                usersReference2.child(uid).child("userType").setValue(userType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
