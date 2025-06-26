package com.example.temucafe.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.Cafe;
import com.example.temucafe.CafeDetailActivity;
import com.example.temucafe.R;

import java.util.ArrayList;

public class ListFavoriteAdapter extends RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder> {

    private ArrayList<Cafe> listFavorite;
    private Context context;
    private SharedPreferences sharedPreferences;
    private OnDeleteClickListener onDeleteClickListener;

    public ListFavoriteAdapter(ArrayList<Cafe> listFavorite, Context context) {
        this.listFavorite = listFavorite;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Cafe cafe);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public void setListFavorite(ArrayList<Cafe> listFavorite) {
        this.listFavorite = listFavorite;
        notifyDataSetChanged();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoriteImage;
        public TextView cafeName;
        public TextView ratingCafe;
        public Button deleteButton;
        public Button gotoCafeButton;
        public Button locationCafeButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            favoriteImage = itemView.findViewById(R.id.favoriteImg);
            cafeName = itemView.findViewById(R.id.cafeName);
            ratingCafe = itemView.findViewById(R.id.ratingCafe);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            gotoCafeButton = itemView.findViewById(R.id.cafeButton);
            locationCafeButton = itemView.findViewById(R.id.locationButton);

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(listFavorite.get(position));
                }
            });

            locationCafeButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String url = context.getResources().getStringArray(R.array.data_link_alamat)[position];
                    navigateToURL(url);
                }
            });
        }

        private void navigateToURL(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rowfavorite_cafe, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Cafe cafe = listFavorite.get(position);
        holder.cafeName.setText(cafe.getName().length() > 12 ? cafe.getName().substring(0, 12) + "..." : cafe.getName());
        holder.favoriteImage.setImageResource(cafe.getImageId());
        holder.ratingCafe.setText(String.valueOf(cafe.getRating()));
        holder.gotoCafeButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), CafeDetailActivity.class);
            intent.putExtra("SONG_NAME", cafe.getName());
            intent.putExtra("SONG_DESC", cafe.getDescription());
            intent.putExtra("SONG_IMG_RES_ID", cafe.getImageId());
            // You might need to pass CAFE_POSITION and CAFE_MENU here if CafeDetailActivity relies on them
            // For simplicity, assuming it can fetch details by name
            holder.itemView.getContext().startActivity(intent);
        });
        holder.favoriteImage.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), CafeDetailActivity.class);
            intent.putExtra("SONG_NAME", cafe.getName());
            intent.putExtra("SONG_DESC", cafe.getDescription());
            intent.putExtra("SONG_IMG_RES_ID", cafe.getImageId());
            // You might need to pass CAFE_POSITION and CAFE_MENU here if CafeDetailActivity relies on them
            // For simplicity, assuming it can fetch details by name
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }
}
