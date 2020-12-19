package com.example.photographer.ui.UserNormal.DisplayAllCategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;

import java.util.ArrayList;

public class DisplayCategoriesActivity extends AppCompatActivity implements IDisplayCategories{

    DisplayCategoriesPresenter displayCategoriesPresenter;
    DisplayAllCategoriesAdapter categoriesAdapter;

    RecyclerView displayAllCategoriesRecyclerView;

    Fonts fonts;
    ImageView displayBack;
    TextView imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_categories);

        fonts = new Fonts(this);
        displayCategoriesPresenter = new DisplayCategoriesPresenter(this);
        displayCategoriesPresenter.getAllCategories();

        handleToolbar();
    }

    @Override
    public void getAllCategories(ArrayList<Category> categories) {
        displayAllCategoriesRecyclerView = findViewById(R.id.displayAllCategoriesRecyclerView);
        displayAllCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        categoriesAdapter = new DisplayAllCategoriesAdapter(this, categories);
        displayAllCategoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    private void handleToolbar(){
        displayBack = findViewById(R.id.displayBack);
        imageName = findViewById(R.id.imageName);
        imageName.setTypeface(fonts.getJostFont());

        displayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageName.setText("Categories");

    }
}