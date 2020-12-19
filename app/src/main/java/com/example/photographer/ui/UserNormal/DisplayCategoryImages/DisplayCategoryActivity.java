package com.example.photographer.ui.UserNormal.DisplayCategoryImages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Image;
import com.example.photographer.ui.UserNormal.DisplayImage.IDisplayImage;
import com.example.photographer.ui.UserNormal.DisplayImage.SimilarImagesAdapter;

import java.util.ArrayList;

public class DisplayCategoryActivity extends AppCompatActivity implements IDisplayImage, IDisplayCategory {

    ImageView displayBack;
    TextView imageName;
    RecyclerView displayCategoryRecyclerView;

    DisplayCategoryPresenter displayCategoryPresenter;
    Fonts fonts;

    SimilarImagesAdapter similarImagesAdapter;
    ArrayList<Image> images = new ArrayList<>();
    String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);

        fonts = new Fonts(this);
        categoryName = (String) getIntent().getSerializableExtra("category");
        if (categoryName != null){
            // from DisplayCategoriesActivity
            /**
             * so we will get data from our presenter
             */
            displayCategoryPresenter = new DisplayCategoryPresenter(categoryName, this);
            displayCategoryPresenter.getImagesOfCurrentCategory();
        }else {
            // from main activity (Main External Adapter)
            images = (ArrayList<Image>) getIntent().getSerializableExtra("images");
        }
        initViews();
        imageName.setTextColor(getResources().getColor(R.color.cyan));

    }

    private void initViews(){
        displayBack = findViewById(R.id.displayBack);
        imageName = findViewById(R.id.imageName);
        imageName.setTypeface(fonts.getJostFont());
        if (categoryName ==null)
            imageName.setText("#" +images.get(0).getCategory().getCategory());
        displayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        displayCategoryRecyclerView = findViewById(R.id.displayCategoryRecyclerView);
        displayCategoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        similarImagesAdapter = new SimilarImagesAdapter(this, images,"DisplayCategoryActivity", this);
        displayCategoryRecyclerView.setAdapter(similarImagesAdapter);
    }

    @Override
    public void changeImage(Image image) {

    }

    @Override
    public void getImagesOfCategory(ArrayList<Image> images) {
        this.images = images;
        similarImagesAdapter = new SimilarImagesAdapter(this, images,"DisplayCategoryActivity", this);
        displayCategoryRecyclerView.setAdapter(similarImagesAdapter);
        imageName.setText("#" +images.get(0).getCategory().getCategory());
    }
}
