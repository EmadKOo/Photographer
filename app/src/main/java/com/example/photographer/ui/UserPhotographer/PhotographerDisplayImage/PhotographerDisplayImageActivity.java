package com.example.photographer.ui.UserPhotographer.PhotographerDisplayImage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comix.rounded.RoundedCornerImageView;
import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Image;
import com.squareup.picasso.Picasso;

public class PhotographerDisplayImageActivity extends AppCompatActivity {

    ImageView displayBack;
    TextView imageName;
    TextView imageType;
    RoundedCornerImageView imageDisplay;
    ImageView likeIcon;
    TextView numOfLikes;
    TextView photographerNameTV;
    TextView location;

    Image currentImage;
    Fonts fonts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_display_image);

        currentImage = (Image) getIntent().getSerializableExtra("image");
        initViews();
        handleViews();
    }

    private void initViews(){
        fonts = new Fonts(this);
        displayBack = findViewById(R.id.displayBack);
        imageName = findViewById(R.id.imageName);
        imageType = findViewById(R.id.imageType);
        imageDisplay = findViewById(R.id.imageDisplay);
        likeIcon = findViewById(R.id.likeIcon);
        numOfLikes = findViewById(R.id.numOfLikes);
        photographerNameTV = findViewById(R.id.photographerNameTV);
        location = findViewById(R.id.location);
    }

    private void handleViews(){
        imageName.setTypeface(fonts.getComfortaaFont());
        numOfLikes.setTypeface(fonts.getJostFont());
        photographerNameTV.setTypeface(fonts.getComfortaaFont());
        location.setTypeface(fonts.getComfortaaFont());

        displayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageName.setText(currentImage.getName());
        imageType.setText("#" + currentImage.getCategory().getCategory() );
        Picasso.get().load(currentImage.getImgPath()).into(imageDisplay);
        numOfLikes.setText(currentImage.getNumOfLikes());
        photographerNameTV.setText(currentImage.getPhotographerName());
        location.setText(currentImage.getLocation());
    }
}
