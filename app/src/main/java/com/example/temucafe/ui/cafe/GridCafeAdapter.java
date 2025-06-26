package com.example.temucafe.ui.cafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.Cafe;
import com.example.temucafe.CafeDetailActivity;
import com.example.temucafe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GridCafeAdapter extends RecyclerView.Adapter<GridCafeAdapter.GridViewHolder> {
    private ArrayList<Cafe> listCafe;
    private Context context;
    private OnItemClickCallback onItemClickCallback;
    private SharedPreferences sharedPreferences;

    private boolean isGridMode = true; // Default to grid mode for this adapter

    public GridCafeAdapter(ArrayList<Cafe> listCafe, Context context) {
        this.listCafe = listCafe;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPhoto;
        public TextView tvName;
        public TextView tvDescription;
        public TextView ratingCafe;
        public TextView category;

        public GridViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_song_name);
            tvDescription = itemView.findViewById(R.id.tv_song_description);
            ratingCafe = itemView.findViewById(R.id.rating);
            category = itemView.findViewById(R.id.category);
        }
    }

    public void setFilteredList(ArrayList<Cafe> filteredList) {
        this.listCafe = filteredList;
        notifyDataSetChanged();
    }

    public void sortListByRating() {
        Collections.sort(listCafe, new Comparator<Cafe>() {
            @Override
            public int compare(Cafe c1, Cafe c2) {
                float rating1 = sharedPreferences.getFloat(c1.getName(), 0f);
                float rating2 = sharedPreferences.getFloat(c2.getName(), 0f);
                return Float.compare(rating2, rating1); // Sort descending
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_cafe, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Cafe cafe = listCafe.get(position);
        holder.imgPhoto.setImageResource(cafe.getImageId());
        holder.tvName.setText(cafe.getName());
        holder.tvDescription.setText(cafe.getDescription());
        holder.ratingCafe.setText(String.valueOf(cafe.getRating()));
        holder.category.setText(cafe.getCategory());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), CafeDetailActivity.class);
            intent.putExtra("SONG_NAME", cafe.getName());
            intent.putExtra("SONG_DESC", cafe.getDescription());
            intent.putExtra("SONG_IMG_RES_ID", cafe.getImageId());
            intent.putExtra("CAFE_POSITION", position); // Pass position for address/time
            intent.putExtra("CAFE_MENU", position); // Pass position for menu link
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listCafe.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Cafe data);
    }
}
