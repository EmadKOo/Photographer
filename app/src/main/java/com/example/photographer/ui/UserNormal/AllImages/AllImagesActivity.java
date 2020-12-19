package com.example.photographer.ui.UserNormal.AllImages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Image;

import java.util.ArrayList;

public class AllImagesActivity extends AppCompatActivity implements IAllImages{

    AllImagesPresenter allImagesPresenter;
    ArrayList<Image> imageArrayList = new ArrayList<>();
    RecyclerView displayAllImagesRecyclerView;
    AllImagesAdapter adapter;
    Fonts fonts;
    ImageView displayBack;
    TextView imageName;
    String activityType ; // shuffle => all images shuffeled ,,, rated // get the first 10
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_images);

        activityType = getIntent().getStringExtra("type");
        fonts = new Fonts(this);
        allImagesPresenter = new AllImagesPresenter(this);


        handleToolbar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        allImagesPresenter.getAllImages(activityType);
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

        if (activityType.equals(getResources().getString(R.string.shuffle)))
            imageName.setText("All Images");
        else if (activityType.equals(getResources().getString(R.string.rated)))
            imageName.setText("Top Rated");
    }

    private void initRecyclerView(){
        displayAllImagesRecyclerView = findViewById(R.id.displayAllImagesRecyclerView);
        displayAllImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllImagesAdapter(AllImagesActivity.this, imageArrayList);
        displayAllImagesRecyclerView.setAdapter(adapter);


    }

    @Override
    public void getShuffeledList(ArrayList<Image> imagesList) {
        imageArrayList.addAll(imagesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getTopRated(ArrayList<Image> topRatedList) {
        imageArrayList.addAll(topRatedList);
        adapter.notifyDataSetChanged();
    }
}
