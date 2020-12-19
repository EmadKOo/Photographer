package com.example.photographer.ui.UserPhotographer.Photographer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photographer.R;
import com.example.photographer.pojo.Image;
import com.example.photographer.ui.UserPhotographer.PhotographerDisplayImage.PhotographerDisplayImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotographerInternalAdapter extends RecyclerView.Adapter<PhotographerInternalAdapter.MyViewHolder> {

    Context context;
    ArrayList<Image> imagesList;
    Intent intent;

    private static final String TAG = "PhotographerInternalAda";
    public PhotographerInternalAdapter(Context context, ArrayList<Image> imagesList) {
        this.context = context;
        this.imagesList = imagesList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.images_internal_view_holder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Picasso.get().load(imagesList.get(position).getImgPath()).placeholder(R.drawable.photo).into(holder.imageInternalHolder);
        holder.imageTitle.setText(imagesList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, PhotographerDisplayImageActivity.class);
                intent.putExtra("image", imagesList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageInternalHolder;
        TextView imageTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageInternalHolder = itemView.findViewById(R.id.imageInternalHolder);
            imageTitle = itemView.findViewById(R.id.imageTitle);
        }
    }
}
