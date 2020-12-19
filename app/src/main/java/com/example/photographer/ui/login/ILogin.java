package com.example.photographer.ui.login;

import com.example.photographer.pojo.User;
import com.google.firebase.auth.FirebaseUser;

public interface ILogin {
    void login(FirebaseUser firebaseUser, User user);
}
