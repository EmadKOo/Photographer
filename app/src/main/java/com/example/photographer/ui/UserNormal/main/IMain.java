package com.example.photographer.ui.UserNormal.main;

import com.example.photographer.pojo.SerializedImage;

import java.util.ArrayList;

public interface IMain {
    void getAllImagesCategoriezed(ArrayList<SerializedImage> serializedImages);

    void getUserName(String userName);
}
