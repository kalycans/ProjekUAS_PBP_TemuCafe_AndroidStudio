package com.example.temucafe.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.Cafe;
import com.example.temucafe.FavoriteManager;
import com.example.temucafe.R;
import com.example.temucafe.databinding.FragmentHomeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RandomizedCafeAdapter adapter;
    private ArrayList<Cafe> listCafe;
    private int[] randomizedIndex;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        if (dateFormat instanceof SimpleDateFormat) {
            ((SimpleDateFormat) dateFormat).applyPattern("EEEE, dd MMMM yyyy");
        }
        String date = dateFormat.format(new Date());
        binding.date.setText(date);
        updateFavoriteCount();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        randomizedIndex = RandomizedManager.getStoredRandomNumbers(requireContext());
        Log.d("Debug", java.util.Arrays.toString(randomizedIndex));

        RecyclerView recyclerView = binding.recycleView; // Use binding to access the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        listCafe = generateCafeList(randomizedIndex);
        adapter = new RandomizedCafeAdapter(listCafe);
        recyclerView.setAdapter(adapter);
        updateCafeRatings();
    }

    public ArrayList<Cafe> generateCafeList(int[] indexes) {
        String[] cafeNames = getResources().getStringArray(R.array.data_cafe);
        String[] cafeDescriptions = getResources().getStringArray(R.array.data_desc_cafe);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.data_img_cafe);
        int[] cafeImages = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            cafeImages[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        String[] categories = getResources().getStringArray(R.array.data_cash);
        String[] recommended = getResources().getStringArray(R.array.data_recommended);

        ArrayList<Cafe> cafeList = new ArrayList<>();
        for (int i : indexes) {
            Cafe cafe = new Cafe(
                    cafeNames[i],
                    cafeDescriptions[i],
                    cafeImages[i],
                    categories[i],
                    recommended[i],
                    0f // Default rating, it will be updated later
            );
            cafeList.add(cafe);
        }
        return cafeList;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCafeRatings();
        updateFavoriteCount();
    }

    private void updateFavoriteCount() {
        int favoriteCount = FavoriteManager.getFavoriteCount(requireContext());
        binding.favCount.setText(String.valueOf(favoriteCount));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCafeRatings() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);
        for (Cafe cafe : listCafe) {
            float rating = sharedPreferences.getFloat(cafe.getName(), 0f);
            cafe.setRating(rating);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear binding reference
    }
}
