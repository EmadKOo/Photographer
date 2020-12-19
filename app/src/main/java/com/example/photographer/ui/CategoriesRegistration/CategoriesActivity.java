package com.example.photographer.ui.CategoriesRegistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.example.photographer.ui.UserNormal.main.MainActivity;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements ICategory {

    RecyclerView categoriesRecyclerView;

    Button btnSaveCategories;
    TextView imageName;
    ImageView displayBack;

    ArrayList<Category> categoryList = new ArrayList<>();
    CategoryPresenter categoryPresenter;
    CategoryAdapter categoryAdapter;
    ArrayList favouriteCatsBefore = new ArrayList();

    String previousActivity;
    Fonts fonts;
    private static final String TAG = "CategoriesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        fonts = new Fonts(this);
        previousActivity = getIntent().getStringExtra("activity");
        categoryPresenter = new CategoryPresenter(this, this);
        categoryPresenter.getFavList();
        initRecyclerView();
        initViews();
        categoryPresenter.getAllCategories(categoryAdapter);
    }

    public void initRecyclerView(){
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryAdapter = new CategoryAdapter(this,categoryList, favouriteCatsBefore);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void initViews(){

        displayBack = findViewById(R.id.displayBack);
        displayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageName = findViewById(R.id.imageName);
        imageName.setText("Select your favourite Sections");
        imageName.setTypeface(fonts.getJostFont());
        btnSaveCategories = findViewById(R.id.btnSaveCategories);
        btnSaveCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryPresenter.updateCategories(categoryAdapter.getFavouriteCategories());
                if (previousActivity==null) // after registeration
                    categoryPresenter.addNotification();
                startActivity(new Intent(CategoriesActivity.this, MainActivity.class));
                CategoriesActivity.this.finish();
            }
        });
        btnSaveCategories.setTypeface(fonts.getComfortaaFont());
    }
    @Override
    public void setCategory(ArrayList<Category> categories) {
        Log.d(TAG, "setCategory: " + categories.get(0).getCategory());
        categoryList = categories;
        categoryAdapter = new CategoryAdapter(this,categoryList,favouriteCatsBefore);
        categoriesRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void getFavourieCats(ArrayList favList) {
        favouriteCatsBefore = favList;
    }

    @Override
    public void updateUserCategories(boolean x, String exception) {
    }
}
