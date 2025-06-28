package com.example.temucafe.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.temucafe.Cafe;
import com.example.temucafe.CafeDetailActivity;
import com.example.temucafe.R;

import java.util.ArrayList;

public class GridFavoriteAdapter extends RecyclerView.Adapter<GridFavoriteAdapter.GridViewHolder> {

    private ArrayList<Cafe> listFavorite;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public GridFavoriteAdapter(ArrayList<Cafe> listFavorite, Context context) {
        this.listFavorite = listFavorite;
        this.context = context;
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

    public class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoriteImage;
        public TextView cafeName;
        public TextView ratingCafe;
        public Button deleteButton;
        public ImageButton menuButton;

        public GridViewHolder(View itemView) {
            super(itemView);
            favoriteImage = itemView.findViewById(R.id.favoriteImg);
            cafeName = itemView.findViewById(R.id.cafeName);
            ratingCafe = itemView.findViewById(R.id.ratingCafe);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            menuButton = itemView.findViewById(R.id.menuButton);

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(listFavorite.get(position));
                }
            });

            menuButton.setOnClickListener(v -> showBottomSheetMenu());
        }

        private void showBottomSheetMenu() {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null);
            bottomSheetDialog.setContentView(view);

            ImageButton closeButton = view.findViewById(R.id.closeButton);
            TextView seeCafe = view.findViewById(R.id.seeCafeBottomDialog);
            TextView seeLocation = view.findViewById(R.id.seeLocationBottomDialog);
            TextView shareCafe = view.findViewById(R.id.shareBottomDialog);
            TextView delete = view.findViewById(R.id.deleteBottomDialog);

            int position = getAdapterPosition();
            Cafe cafe = listFavorite.get(position);

            closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

            seeCafe.setOnClickListener(v -> {
                Intent intent = new Intent(context, CafeDetailActivity.class);
                intent.putExtra("SONG_NAME", cafe.getName());
                intent.putExtra("SONG_DESC", cafe.getDescription());
                intent.putExtra("SONG_IMG_RES_ID", cafe.getImageId());
                // You might need to pass CAFE_POSITION and CAFE_MENU here if CafeDetailActivity relies on them
                // For simplicity, assuming it can fetch details by name
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            });

            seeLocation.setOnClickListener(v -> {
                if (position != RecyclerView.NO_POSITION) {
                    String url = context.getResources().getStringArray(R.array.data_link_alamat)[position];
                    navigateToURL(url);
                }
                bottomSheetDialog.dismiss();
            });

            shareCafe.setOnClickListener(v -> {
                if (position != RecyclerView.NO_POSITION) {
                    String name = cafe.getName();
                    String address = context.getResources().getStringArray(R.array.data_alamat)[position];
                    String link = context.getResources().getStringArray(R.array.data_link_alamat)[position];
                    shareCafeInfo(name, address, link);
                }
                bottomSheetDialog.dismiss();
            });

            delete.setOnClickListener(v -> {
                int deletePosition = getAdapterPosition();
                if (deletePosition != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(listFavorite.get(deletePosition));
                }
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.show();
        }

        private void shareCafeInfo(String name, String address, String link) {
            String shareText = "Check out this café:\n\nName: " + name + "\nAddress: " + address + "\nGoogle Maps: " + link;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
        }

        private void navigateToURL(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridfavorite_cafe, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Cafe cafe = listFavorite.get(position);
        holder.cafeName.setText(cafe.getName().length() > 9 ? cafe.getName().substring(0, 9) + ".." : cafe.getName());
        holder.favoriteImage.setImageResource(cafe.getImageId());
        holder.ratingCafe.setText(String.valueOf(cafe.getRating()));
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
