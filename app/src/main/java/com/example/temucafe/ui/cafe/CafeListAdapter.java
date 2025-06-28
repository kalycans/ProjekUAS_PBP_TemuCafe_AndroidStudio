package com.example.temucafe.ui.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.R;
import com.example.temucafe.models.Cafe;
import com.example.temucafe.ui.infocafe.InfoCafeFragment;
import com.example.temucafe.utils.FavoriteManager;

import java.util.List;

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder> {

    private List<Cafe> cafeList;
    private Context context;
    private FragmentManager fragmentManager;
    private boolean isFromFavorite;

    // Constructor dengan flag isFromFavorite
    public CafeListAdapter(List<Cafe> cafeList, Context context, FragmentManager fragmentManager, boolean isFromFavorite) {
        this.cafeList = cafeList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.isFromFavorite = isFromFavorite;
    }

    @NonNull
    @Override
    public CafeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cafe_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeListAdapter.ViewHolder holder, int position) {
        Cafe cafe = cafeList.get(position);

        holder.tvName.setText(cafe.getName());
        holder.tvAddress.setText(cafe.getAddress());
        holder.tvRating.setText("⭐ " + cafe.getRating());
        holder.ivImage.setImageResource(cafe.getImageResId());

        // Sembunyikan tombol favorite jika dari halaman Favorite
        if (isFromFavorite) {
            holder.btnFavorite.setVisibility(View.GONE);
        } else {
            // Pastikan context yang valid digunakan di sini
            boolean isFav = FavoriteManager.isFavorite(context, cafe.getId());
            cafe.setFavorite(isFav);
            holder.btnFavorite.setImageResource(isFav ? R.drawable.ic_favorite : R.drawable.ic_favorite_outline);

            holder.btnFavorite.setOnClickListener(v -> {
                boolean nowFav = !cafe.isFavorite();
                cafe.setFavorite(nowFav);
                if (nowFav) {
                    FavoriteManager.saveFavorite(context, cafe.getId());
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
                } else {
                    FavoriteManager.removeFavorite(context, cafe.getId());
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_outline);
                }
            });
        }

        // Navigasi ke InfoCafeFragment saat diklik
        holder.itemView.setOnClickListener(v -> {
            InfoCafeFragment detailFragment = new InfoCafeFragment();
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putSerializable("selectedCafe", cafe);
            detailFragment.setArguments(bundle);

            // Pastikan fragmentManager tidak null sebelum melakukan transaksi
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null) // Ini penting agar bisa kembali ke daftar cafe
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cafeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvRating;
        ImageView ivImage, btnFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCafeName);
            tvAddress = itemView.findViewById(R.id.tvCafeAddress);
            tvRating = itemView.findViewById(R.id.tvCafeRating);
            ivImage = itemView.findViewById(R.id.ivCafeImage);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
