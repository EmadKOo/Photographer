package com.example.photographer.ui.UserNormal.DisplayAllCategories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comix.rounded.RoundedCornerImageView;
import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.example.photographer.ui.UserNormal.DisplayCategoryImages.DisplayCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayAllCategoriesAdapter  extends RecyclerView.Adapter<DisplayAllCategoriesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Category> categoriesList;
    Fonts fonts;
    Intent intent;

    public DisplayAllCategoriesAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
        fonts = new Fonts(context);
        intent = new Intent(context, DisplayCategoryActivity.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.similarviewholder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Picasso.get().load("https://via.placeholder.com/300/09f/fff.png").into(holder.imageSimilarHolder);
        holder.imageNameSimilarHolder.setText(categoriesList.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", categoriesList.get(position).getCategory());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedCornerImageView imageSimilarHolder;
        TextView imageNameSimilarHolder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSimilarHolder = itemView.findViewById(R.id.imageSimilarHolder);
            imageNameSimilarHolder = itemView.findViewById(R.id.imageNameSimilarHolder);
        }
    }
}