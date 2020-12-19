package com.example.photographer.ui.UserNormal.DisplayImage;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SimilarImagesAdapter extends RecyclerView.Adapter<SimilarImagesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Image> imgs;
    String ActivityName;
    Fonts fonts;
    Intent intent;
    IDisplayImage iDisplayImage;
    public SimilarImagesAdapter(Context context,  ArrayList<Image> imgs, String ActivityName, IDisplayImage iDisplayImage) {
        this.context = context;
        this.imgs = imgs;
        this.ActivityName = ActivityName;
        fonts = new Fonts(context);
        this.iDisplayImage = iDisplayImage;
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
        Picasso.get().load(imgs.get(position).getImgPath()).into(holder.imageSimilarHolder);
        holder.imageNameSimilarHolder.setText(capitalizeFirstChar(imgs.get(position).getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemView(imgs.get(position));
            }
        });

    }

    private void handleItemView(Image currentImage){
        if (ActivityName.equals("DisplayCategoryActivity")){
            intent = new Intent(context, DisplayImageActivity.class);
            intent.putExtra("activity", "Similar Adapter");
            intent.putExtra("currentImage" ,currentImage);
            intent.putExtra("restOfCategory" ,imgs);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else if (ActivityName.equals("DisplayImageActivity")){
            // change the image call Interface
            iDisplayImage.changeImage(currentImage);
        }
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
    public int getItemCount() {
        return imgs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedCornerImageView imageSimilarHolder;
        TextView imageNameSimilarHolder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSimilarHolder = itemView.findViewById(R.id.imageSimilarHolder);
            imageNameSimilarHolder = itemView.findViewById(R.id.imageNameSimilarHolder);
            imageNameSimilarHolder.setTypeface(fonts.getComfortaaFont());
        }
    }
}