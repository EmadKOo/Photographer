package com.example.photographer.ui.UserNormal.DisplayImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comix.rounded.RoundedCornerImageView;
import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Image;
import com.example.photographer.ui.UserNormal.DisplayCategoryImages.DisplayCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayImageActivity extends AppCompatActivity implements IDisplayImage{
    Intent intent;
    ImageView displayBack;
    TextView imageName;
    TextView imageType;
    TextView similarTV;
    RoundedCornerImageView imageDisplay;
    ImageView likeIcon;
    TextView numOfLikes;
    TextView photographerNameTV;
    TextView location;
    RecyclerView similarRecyclerView;

    Image currentImage;
    ArrayList<Image> restOfCategoryList = new ArrayList<>();

    Fonts fonts;
    SimilarImagesAdapter similarImagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        fonts = new Fonts(this);
        initViews();

        if (getIntent().getStringExtra("activity").equals("All Images")){
            imageType.setTextColor(getResources().getColor(R.color.black));
        }
        currentImage = (Image) getIntent().getSerializableExtra("currentImage");
        restOfCategoryList = (ArrayList<Image>) getIntent().getSerializableExtra("restOfCategory");

        initRecyclerView();
        handleViews(currentImage);
    }

    private void initViews(){
        displayBack = findViewById(R.id.displayBack);
        imageName = findViewById(R.id.imageName);
        imageType = findViewById(R.id.imageType);
        imageDisplay = findViewById(R.id.imageDisplay);
        likeIcon = findViewById(R.id.likeIcon);
        numOfLikes = findViewById(R.id.numOfLikes);
        photographerNameTV = findViewById(R.id.photographerNameTV);
        location = findViewById(R.id.location);
        similarTV = findViewById(R.id.similarTV);

        imageName.setTypeface(fonts.getComfortaaFont());
        numOfLikes.setTypeface(fonts.getJostFont());
        photographerNameTV.setTypeface(fonts.getComfortaaFont());
        location.setTypeface(fonts.getComfortaaFont());
        similarTV.setTypeface(fonts.getJostFont());
    }

    private void handleViews(Image mImage){
        displayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Picasso.get().load(mImage.getImgPath()).into(imageDisplay);
        imageName.setText(capitalizeFirstChar(mImage.getName()));
        imageType.setText("#"+mImage.getCategory().getCategory());
        if (!getIntent().getStringExtra("activity").equals("All Images")){
            imageType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(DisplayImageActivity.this, DisplayCategoryActivity.class);
                    intent.putExtra("images", restOfCategoryList);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }

        numOfLikes.setText(mImage.getNumOfLikes() + " Likes");
        photographerNameTV.setText(capitalizeFirstChar(mImage.getPhotographerName()));
        location.setText(capitalizeFirstChar(mImage.getLocation()));
        likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeIcon.setImageResource(R.drawable.red_heart);
            }
        });
    }
    private void initRecyclerView(){
        similarRecyclerView = findViewById(R.id.similarRecyclerView);
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarImagesAdapter = new SimilarImagesAdapter(this,restOfCategoryList, "DisplayImageActivity", this);
        similarRecyclerView.setAdapter(similarImagesAdapter);
    }

    private String capitalizeFirstChar(String imageTitle){

        String[] title = imageTitle.split(" ");
        String updatedText = "";
        for (int x = 0; x < title.length; x++) {
            updatedText += title[x].substring(0,1).toUpperCase() + title[x].substring(1,title[x].length()).toLowerCase() + " ";
        }
        return updatedText;
    }

    @Override
    public void changeImage(Image image) {
        handleViews(image);
    }
}
