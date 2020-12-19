package com.example.photographer.ui.CategoriesRegistration;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comix.rounded.RoundedCornerImageView;
import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {


    Context context;
    ArrayList<Category> categoriesList;
    ArrayList favouriteCategories = new ArrayList();
    Fonts fonts;
    ArrayList favouriteCatsBefore = new ArrayList();
    public CategoryAdapter(Context context, ArrayList<Category> categoriesList, ArrayList favouriteCatsBefore) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.favouriteCatsBefore = favouriteCatsBefore;
        fonts = new Fonts(context);
        favouriteCategories.addAll(favouriteCatsBefore);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.category_view_holder, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Log.d("TAG", "onBindViewHolder: ");
        holder.imageNameCategoryHolder.setText(categoriesList.get(position).getCategory());
        Picasso.get().load("https://via.placeholder.com/300/09f/fff.png").into(holder.imageCategoryHolder);
        if (favouriteCatsBefore.contains(String.valueOf(categoriesList.get(position).getId()))){
            holder.checkboxItem.setChecked(true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkboxItem.isChecked()){
                    holder.checkboxItem.setChecked(false);
                    holder.imageCategoryHolder2.setVisibility(View.GONE);
                    favouriteCategories.remove(String.valueOf(categoriesList.get(position).getId()));
                   // favouriteCategories.remove(categoriesList.remove(categoriesList.get(position)));
                }else {
                    holder.imageCategoryHolder2.setVisibility(View.VISIBLE);
                    holder.checkboxItem.setChecked(true);
                   favouriteCategories.add(categoriesList.get(position).getId());
                }
//                holder.checkboxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            favouriteCategories.add(categoriesList.get(position).getId());
//                        }else {
//                            favouriteCategories.remove(categoriesList.remove(categoriesList.get(position)));
//                        }
//                    }
//                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public String getFavouriteCategories(){
        String favs = "";
        for (int x = 0; x <favouriteCategories.size() ; x++) {
            favs+=favouriteCategories.get(x)+",";
        }
        return favs;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkboxItem;
        TextView imageNameCategoryHolder;
        RoundedCornerImageView imageCategoryHolder;
        RoundedCornerImageView imageCategoryHolder2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkboxItem = itemView.findViewById(R.id.checkboxItem);
            imageNameCategoryHolder = itemView.findViewById(R.id.imageNameCategoryHolder);
            imageCategoryHolder = itemView.findViewById(R.id.imageCategoryHolder);
            imageCategoryHolder2 = itemView.findViewById(R.id.imageCategoryHolder2);
            checkboxItem.setTypeface(fonts.getComfortaaFont());
        }
    }
}
