package com.example.temucafe.ui.favorite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.temucafe.Cafe;
import com.example.temucafe.FavoriteManager;
import com.example.temucafe.R;
import com.example.temucafe.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;
import java.util.Set;

public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private ListFavoriteAdapter listAdapter;
    private GridFavoriteAdapter gridAdapter;
    private ArrayList<Cafe> listCafe;
    private boolean isGridLayout = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listCafe = generateFavoriteCafeList();
        setupRecyclerView();
        updateFavoriteCount();
        updateCafeRatings();

        binding.layoutButton.setOnClickListener(v -> toggleLayout());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCafeRatings();
        // Re-generate favorite list in case it changed from CafeDetailActivity
        listCafe = generateFavoriteCafeList();
        if (isGridLayout) {
            gridAdapter.setListFavorite(listCafe);
        } else {
            listAdapter.setListFavorite(listCafe);
        }
        updateFavoriteCount();
    }

    private void setupRecyclerView() {
        listAdapter = new ListFavoriteAdapter(listCafe, requireContext());
        listAdapter.setOnDeleteClickListener(new ListFavoriteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Cafe cafe) {
                showDeleteConfirmationDialog(cafe);
            }
        });

        gridAdapter = new GridFavoriteAdapter(listCafe, requireContext());
        gridAdapter.setOnDeleteClickListener(new GridFavoriteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Cafe cafe) {
                showDeleteConfirmationDialog(cafe);
            }
        });

        binding.rvFavorite.setAdapter(listAdapter);
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void toggleLayout() {
        isGridLayout = !isGridLayout;
        if (isGridLayout) {
            binding.rvFavorite.setAdapter(gridAdapter);
            binding.rvFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.layoutButton.setImageResource(R.drawable.gridicon);
        } else {
            binding.rvFavorite.setAdapter(listAdapter);
            binding.rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.layoutButton.setImageResource(R.drawable.listicon);
        }
    }

    private void updateFavoriteCount() {
        int favoriteCount = FavoriteManager.getFavoriteCount(requireContext());
        String oldcount = binding.cafeFavoriteCount.getText().toString();
        String newcount = oldcount.replaceAll("^\\d+", String.valueOf(favoriteCount));
        binding.cafeFavoriteCount.setText(newcount);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCafeRatings() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);
        for (Cafe cafe : listCafe) {
            float rating = sharedPreferences.getFloat(cafe.getName(), 0f);
            cafe.setRating(rating);
        }
        this.listAdapter.notifyDataSetChanged();
        this.gridAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteFavoriteItem(Cafe deletedCafe) {
        listCafe.remove(deletedCafe);
        this.listAdapter.notifyDataSetChanged();
        this.gridAdapter.notifyDataSetChanged();
        updateFavoriteCount();
        FavoriteManager.removeFavorite(requireContext(), deletedCafe);
    }

    private void showDeleteConfirmationDialog(Cafe deletedCafe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah Anda yakin ingin menghapus cafe ini dari favorit?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteFavoriteItem(deletedCafe);
            updateFavoriteCount();
        });
        builder.setNegativeButton("No", (dialog, which) -> { });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ArrayList<Cafe> generateFavoriteCafeList() {
        Set<String> favoriteNames = FavoriteManager.getFavorites(requireContext());
        ArrayList<Cafe> allCafes = generateAllCafeList();
        ArrayList<Cafe> favoriteCafes = new ArrayList<>();
        for (Cafe cafe : allCafes) {
            if (favoriteNames.contains(cafe.getName())) {
                favoriteCafes.add(cafe);
            }
        }
        return favoriteCafes;
    }

    private ArrayList<Cafe> generateAllCafeList() {
        String[] cafeNames = getResources().getStringArray(R.array.data_cafe);
        String[] cafeDescriptions = getResources().getStringArray(R.array.data_desc_cafe);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.data_img_cafe);
        int[] cafeImages = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            cafeImages[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        String[] category = getResources().getStringArray(R.array.data_cash);
        String[] recommended = getResources().getStringArray(R.array.data_recommended);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);

        ArrayList<Cafe> cafeList = new ArrayList<>();
        for (int i = 0; i < cafeNames.length; i++) {
            float rating = sharedPreferences.getFloat(cafeNames[i], 0f);
            Cafe cafe = new Cafe(
                    cafeNames[i],
                    cafeDescriptions[i],
                    cafeImages[i],
                    category[i],
                    recommended[i],
                    rating
            );
            cafeList.add(cafe);
        }
        return cafeList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
