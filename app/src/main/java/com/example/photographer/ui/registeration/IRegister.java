package com.example.photographer.ui.registeration;

import com.example.photographer.pojo.User;
import com.google.firebase.auth.FirebaseUser;

public interface IRegister {

    void onSuccess(FirebaseUser firebaseUser);
    void onFailed(String msg);

    void addUserToDatabase(boolean status);
}
