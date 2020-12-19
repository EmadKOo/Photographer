package com.example.photographer.ui.UserNormal.AllImages;

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
import com.example.photographer.pojo.Image;
import com.example.photographer.ui.UserNormal.DisplayImage.DisplayImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllImagesAdapter extends RecyclerView.Adapter<AllImagesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Image> allImages;
    Fonts fonts;
    Intent intent;
    public AllImagesAdapter(Context context, ArrayList<Image> allImages) {
        this.context = context;
        fonts = new Fonts(context);
        this.allImages = allImages;
    }

    @NonNull
    @Override
    public AllImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.images_internal_view_holder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllImagesAdapter.MyViewHolder holder, final int position) {
        Picasso.get().load(allImages.get(position).getImgPath()).into(holder.imageInternalHolder);
        holder.imageTitle.setText(allImages.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, DisplayImageActivity.class);
                intent.putExtra("activity", "All Images");
                intent.putExtra("currentImage", allImages.get(position));
                intent.putExtra("restOfCategory", allImages);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void myNotify(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return allImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedCornerImageView imageInternalHolder;
        TextView imageTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.imageTitle);
            imageInternalHolder = itemView.findViewById(R.id.imageInternalHolder);
            imageTitle.setTypeface(fonts.getComfortaaFont());

        }
    }
}
