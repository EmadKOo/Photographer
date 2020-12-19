package com.example.photographer.ui.UserNormal.AllImages;

import com.example.photographer.pojo.Image;

import java.util.ArrayList;

public interface IAllImages {
    void getShuffeledList(ArrayList<Image> imagesList);

    void getTopRated(ArrayList<Image> topRatedList);
}
