package com.example.photographer.ui.UserNormal.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.SerializedImage;
import com.example.photographer.ui.UserNormal.AllImages.AllImagesActivity;
import com.example.photographer.ui.CategoriesRegistration.CategoriesActivity;
import com.example.photographer.ui.UserNormal.DisplayAllCategories.DisplayCategoriesActivity;
import com.example.photographer.ui.splash.SplashActivity;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMain {

    MainPresenter mainPresenter ;
    MainExternalAdapter mainAdapter;
    RecyclerView mainRecyclerView;

    Fonts fonts ;
    Intent intent;

    RelativeLayout categoriesLayout, allImagesLayout, topRatedLayout, settingsLayout, aboutUsLayout;
    TextView title1, title2, title3, title4, title5, toolbarTitleTV;

    TextView userNameMenu;
    DrawerLayout drawer;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        fonts = new Fonts(this);
        mainPresenter = new MainPresenter(this);

        mainPresenter.getUserName();
        drawer = findViewById(R.id.drawer_layout);
        handleNavBar();
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_main_menu);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.getUserCategories();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.aboutLayout) {
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getAllImagesCategoriezed(ArrayList<SerializedImage> serializedImages) {
        initRecyclerView();
        mainAdapter = new MainExternalAdapter(getApplicationContext(), serializedImages);
        mainRecyclerView.setAdapter(mainAdapter);
        if (serializedImages.size() == 0){
            mainPresenter.dialogNoItems(this);
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUserName(String userName) {
        userNameMenu.setText("Hello " + userName);

    }

    public void initRecyclerView(){
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void handleNavBar(){
        categoriesLayout = findViewById(R.id.categoriesLayout);
        allImagesLayout = findViewById(R.id.allImagesLayout);
        topRatedLayout = findViewById(R.id.topRatedLayout);
        settingsLayout = findViewById(R.id.settingsLayout);
        aboutUsLayout = findViewById(R.id.aboutLayout);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);

        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);
        title4 = findViewById(R.id.title4);
        title5 = findViewById(R.id.title5);

        userNameMenu = findViewById(R.id.userNameMenu);
        userNameMenu.setTextColor(getResources().getColor(R.color.colorAccent));
        title1.setTypeface(fonts.getComfortaaFont());
        title2.setTypeface(fonts.getComfortaaFont());
        title3.setTypeface(fonts.getComfortaaFont());
        title4.setTypeface(fonts.getComfortaaFont());
        title5.setTypeface(fonts.getComfortaaFont());

        toolbarTitleTV.setTypeface(fonts.getComfortaaFont());

        userNameMenu.setTypeface(fonts.getComfortaaFont());
        categoriesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayCategoriesActivity.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        allImagesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AllImagesActivity.class);
                intent.putExtra("type", getResources().getString(R.string.shuffle));
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        topRatedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AllImagesActivity.class);
                intent.putExtra("type", getResources().getString(R.string.rated));
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CategoriesActivity.class);
                intent.putExtra("activity", "main");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.dialogAbout(MainActivity.this);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }

}