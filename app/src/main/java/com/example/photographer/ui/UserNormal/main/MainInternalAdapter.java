package com.example.photographer.ui.UserNormal.main;

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
import com.example.photographer.pojo.SerializedImage;
import com.example.photographer.ui.UserNormal.DisplayImage.DisplayImageActivity;
import com.squareup.picasso.Picasso;

public class MainInternalAdapter extends RecyclerView.Adapter<MainInternalAdapter.MyViewHolder>{

    Context context;
    SerializedImage imgs;
    Intent intent;
    Fonts fonts;
    public MainInternalAdapter(Context context, SerializedImage imgs) {
        this.context = context;
        this.imgs = imgs;
        fonts = new Fonts(context);
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
        Picasso.get().load(imgs.getImageList().get(position).getImgPath()).into(holder.imageInternalHolder);
        holder.imageTitle.setText(capitalizeFirstChar(imgs.getImageList().get(position).getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, DisplayImageActivity.class);
                intent.putExtra("activity", "Main");
                intent.putExtra("currentImage", imgs.getImageList().get(position));
                intent.putExtra("restOfCategory", imgs.getImageList());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs.getImageList().size();
    }

    private String capitalizeFirstChar(String imageTitle){

        String[] title = imageTitle.split(" ");
        String updatedText = "";
        for (int x = 0; x < title.length; x++) {
            updatedText += title[x].substring(0,1).toUpperCase() + title[x].substring(1,title[x].length()).toLowerCase() + " ";
        }
        return updatedText;
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
