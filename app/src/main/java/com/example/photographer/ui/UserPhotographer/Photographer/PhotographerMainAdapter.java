package com.example.photographer.ui.UserPhotographer.Photographer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photographer.R;
import com.example.photographer.pojo.Category;
import com.example.photographer.pojo.Image;

import java.util.ArrayList;

public class PhotographerMainAdapter extends RecyclerView.Adapter<PhotographerMainAdapter.MyViewHolder>{
    Context context;
    ArrayList<Image> imagesList;
    ArrayList<Category> allCategories;
    PhotographerInternalAdapter internalAdapter;

    private static final String TAG = "PhotographerMainAdapter";
    public PhotographerMainAdapter(Context context, ArrayList<Image> imagesList, ArrayList<Category> allCategories) {
        this.context = context;
        this.imagesList = imagesList;
        this.allCategories = allCategories;
    }

    @NonNull
    @Override
    public PhotographerMainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.photographer_view_holder, parent, false);
        return new PhotographerMainAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographerMainAdapter.MyViewHolder holder, final int position) {
        ArrayList<Image> filteredImages = new ArrayList<>();
        holder.recyclerType.setText(allCategories.get(position).getCategory());

        for (int y = 0; y <imagesList.size() ; y++) {
            if (allCategories.get(position).getId() == imagesList.get(y).getCategory().getId()){
                filteredImages.add(imagesList.get(y));
            }
        }
        // initRecyclerView
        initInsideRecyclerView(holder.photographerInsideRecyclerview, filteredImages);
    }

    private void initInsideRecyclerView(RecyclerView categoriesRecyclerView, ArrayList<Image> imgs){
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        internalAdapter = new PhotographerInternalAdapter(context,imgs);
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
            photographerInsideRecyclerview = itemView.findViewById(R.id.photographerInsideRecyclerview);
        }
    }
}