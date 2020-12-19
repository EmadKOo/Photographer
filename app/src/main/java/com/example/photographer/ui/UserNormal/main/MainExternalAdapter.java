package com.example.photographer.ui.UserNormal.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.SerializedImage;
import com.example.photographer.ui.UserNormal.DisplayCategoryImages.DisplayCategoryActivity;

import java.util.ArrayList;

public class MainExternalAdapter extends RecyclerView.Adapter<MainExternalAdapter.MyViewHolder>{
    Context context;
    ArrayList<SerializedImage> allCategories;
    MainInternalAdapter internalAdapter;
    Fonts fonts;
    Intent intent;

    private static final String TAG = "PhotographerMainAdapter";

    public MainExternalAdapter(Context context, ArrayList<SerializedImage> allCategories) {
        this.context = context;
        this.allCategories = allCategories;
        fonts = new Fonts(context);
        intent = new Intent(context, DisplayCategoryActivity.class);
    }

    @NonNull
    @Override
    public MainExternalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.photographer_view_holder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.recyclerType.setText("#" +allCategories.get(position).getImgCategory());
        holder.recyclerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("images", allCategories.get(position).getImageList());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        initInsideRecyclerView(holder.photographerInsideRecyclerview, allCategories.get(position));
    }

    private void initInsideRecyclerView(RecyclerView categoriesRecyclerView, SerializedImage imgs){
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        internalAdapter = new MainInternalAdapter(context,imgs);
        categoriesRecyclerView.setAdapter(internalAdapter);
    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView photographerInsideRecyclerview;
        TextView recyclerType;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerType = itemView.findViewById(R.id.recyclerType);
            recyclerType.setTypeface(fonts.getComfortaaFont());
            photographerInsideRecyclerview = itemView.findViewById(R.id.photographerInsideRecyclerview);
        }
    }

}
