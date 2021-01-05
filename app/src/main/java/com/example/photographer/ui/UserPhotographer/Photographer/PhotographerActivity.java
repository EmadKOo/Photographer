package com.example.photographer.ui.UserPhotographer.Photographer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.example.photographer.pojo.Image;
import com.example.photographer.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;

public class PhotographerActivity extends AppCompatActivity implements IPhotographer{

    PhotographerPresenter photographerPresenter;
    ArrayList<Category> categories = new ArrayList<>();
    Dialog dialog;
    ArrayList<String> categoriesNames = new ArrayList<>();
    Image imageInfo;
    Image currentImage;
    Category currentCategory;
    String fName;
    String selectedCategory = "Art";
    TextView noItems;
    ProgressBar progress;
    PhotographerMainAdapter photographerMainAdapter;
    RecyclerView photographerHomeRecyclerView;

    final static private int IMAGE_PICK_GALLERY_CODE = 1001;
    private static final String TAG = "PhotographerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer);

        photographerPresenter = new PhotographerPresenter(this);
        photographerPresenter.getAllCategoriesOfCurrentUser();
        photographerPresenter.getAllCategories();
        imageInfo = new Image();

        noItems = findViewById(R.id.noItems);
        progress = findViewById(R.id.progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photographer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnAddImage) {
            pickGallry();
            return true;
        } else if (id == R.id.btnSignout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickGallry() {
        showAddImageDialog();
    }

    private void getCategoriesNamesOnly(){
        for (int x = 0; x <categories.size() ; x++) {
            categoriesNames.add(categories.get(x).getCategory());
        }
    }
    public void initRecyclerView(){
        photographerHomeRecyclerView = findViewById(R.id.photographerHomeRecyclerView);
        photographerHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_GALLERY_CODE){
            try {
                dialog.dismiss();
                progress.setVisibility(View.VISIBLE);
                Bitmap bitmapImageUpload = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
              //  imageInfo();
                 photographerPresenter.addImageToFirebase(bitmapImageUpload, currentImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void imgUploadToStorage(boolean a, String ex) {

    }

    @Override
    public void addImageToFirebaseDatabase(boolean a, String ex) {
        if (a){
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void configureMainAdapter(ArrayList<Category> userCategories, ArrayList<Image> userImages) {
        initRecyclerView();
        photographerMainAdapter = new PhotographerMainAdapter(PhotographerActivity.this, userImages, userCategories);
        photographerHomeRecyclerView.setAdapter(photographerMainAdapter);

        if (userCategories.size() == 0){
            Log.d(TAG, "configureMainAdapter: EMPTY");
            noItems.setVisibility(View.VISIBLE);
        }else noItems.setVisibility(View.GONE);
    }

    @Override
    public void getAllCategories(ArrayList<Category> categories) {
        this.categories  = categories;
        getCategoriesNamesOnly();
    }

//    @Override
//    public void getSelectedCategoryFromSpinner(Category category) {
//        currentCategory = category;
//    }

    @Override
    public void getFirstName(String name) {
        fName = name;
    }


    void showAddImageDialog(){

        Fonts fonts = new Fonts(this);
        dialog = new Dialog(this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_image);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        TextView categoryTV = dialog.findViewById(R.id.categoryTV);
        ImageButton btnPickImage = dialog.findViewById(R.id.btnPickImage);
        final TextInputEditText imgLocation = dialog.findViewById(R.id.imgLocation);
        final TextInputEditText imgName = dialog.findViewById(R.id.imgName);
        final Spinner spinnerCategory = dialog.findViewById(R.id.spinnerCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item,categoriesNames);

        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photographerPresenter.getUserName(spinnerCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categoryTV.setTypeface(fonts.getComfortaaFont());

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImage = new Image(photographerPresenter.getSelectedCategory(categories,spinnerCategory.getSelectedItem().toString()), imgName.getEditableText().toString(),
                        imgLocation.getEditableText().toString(),"", "0", fName,
                        FirebaseAuth.getInstance().getCurrentUser().getUid());

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
            }
        });
        dialog.show();
    }
}