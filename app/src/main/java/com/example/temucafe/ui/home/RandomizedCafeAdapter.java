package com.example.temucafe.ui.home;

import android.content.Intent;
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

public class RandomizedCafeAdapter extends RecyclerView.Adapter<RandomizedCafeAdapter.ListViewHolder> {

    private ArrayList<Cafe> listCafe;
    private OnDeleteClickListener onDeleteClickListener; // Not used in this adapter, but kept for consistency

    public RandomizedCafeAdapter(ArrayList<Cafe> listCafe) {
        this.listCafe = listCafe;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Cafe cafe);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoriteImage;
        public TextView cafeName;
        public TextView ratingCafe;

        public ListViewHolder(View itemView) {
            super(itemView);
            favoriteImage = itemView.findViewById(R.id.favoriteImg);
            cafeName = itemView.findViewById(R.id.cafeName);
            ratingCafe = itemView.findViewById(R.id.ratingCafe);
        }
    }

    @Override
    public int getItemCount() {
        return listCafe.size();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_randomizedcafe_home, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Cafe cafe = listCafe.get(position);
        holder.cafeName.setText(cafe.getName().length() > 12 ? cafe.getName().substring(0, 12) + "..." : cafe.getName());
        holder.favoriteImage.setImageResource(cafe.getImageId());
        holder.ratingCafe.setText(String.valueOf(cafe.getRating()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), CafeDetailActivity.class);
            intent.putExtra("SONG_NAME", cafe.getName());
            intent.putExtra("SONG_DESC", cafe.getDescription());
            intent.putExtra("SONG_IMG_RES_ID", cafe.getImageId());
            // You might need to pass CAFE_POSITION and CAFE_MENU here if CafeDetailActivity relies on them
            // For simplicity, assuming it can fetch details by name
            holder.itemView.getContext().startActivity(intent);
        });
    }
}
