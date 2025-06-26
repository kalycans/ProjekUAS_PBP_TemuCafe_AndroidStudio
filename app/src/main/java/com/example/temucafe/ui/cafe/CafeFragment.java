package com.example.temucafe.ui.cafe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.temucafe.Cafe;
import com.example.temucafe.R;
import com.example.temucafe.databinding.FragmentCafeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CafeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentCafeBinding binding;
    private ListCafeAdapter listAdapter;
    private GridCafeAdapter gridAdapter;
    private SharedPreferences sharedPreferences;
    private ArrayList<Cafe> listCafe;
    private boolean sortByRatingOnRecommendation = false;
    private boolean isGridLayoutManager = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCafeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        listCafe = generateCafeList();

        sortCafesByRating();

        listAdapter = new ListCafeAdapter(listCafe, requireContext());
        gridAdapter = new GridCafeAdapter(listCafe, requireContext());

        binding.rvCafe.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCafe.setAdapter(listAdapter);
        isGridLayoutManager = false;

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        binding.btnCategory.setOnClickListener(v -> showCategoryDialog());

        binding.btnRecommendation.setOnClickListener(v -> {
            sortByRatingOnRecommendation = true;
            sortCafesByRating();
            updateAdapter();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        updateCafeRatings();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sortByRatingOnRecommendation) {
            sortCafesByRating();
            updateAdapter();
        }
    }

    private void updateAdapter() {
        if (isGridLayoutManager) {
            gridAdapter.sortListByRating();
        } else {
            listAdapter.sortListByRating();
        }
    }

    private void sortCafesByRating() {
        if (sortByRatingOnRecommendation) {
            for (Cafe cafe : listCafe) {
                cafe.setRating(sharedPreferences.getFloat(cafe.getName(), 0f));
            }
            Collections.sort(listCafe, (c1, c2) -> Float.compare(c2.getRating(), c1.getRating()));
        }
    }

    private void filterList(String query) {
        if (query != null) {
            ArrayList<Cafe> filteredList = new ArrayList<>();
            for (Cafe cafe : listCafe) {
                if (cafe.getName().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                    filteredList.add(cafe);
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show();
            } else {
                if (isGridLayoutManager) {
                    gridAdapter.setFilteredList(filteredList);
                } else {
                    listAdapter.setFilteredList(filteredList);
                }
            }
        }
    }

    private void filterByCategory(List<String> selectedCategories) {
        ArrayList<Cafe> filteredList = new ArrayList<>();
        for (Cafe cafe : listCafe) {
            if (selectedCategories.contains(cafe.getCategory())) {
                filteredList.add(cafe);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            listAdapter.setFilteredList(filteredList);
            gridAdapter.setFilteredList(filteredList);
        }
    }

    private void showCategoryDialog() {
        String[] categories = getResources().getStringArray(R.array.data_categories);
        boolean[] selectedCategories = new boolean[categories.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Categories");
        builder.setMultiChoiceItems(categories, selectedCategories, (dialog, which, isChecked) -> {
            selectedCategories[which] = isChecked;
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            List<String> selectedCategoryList = new ArrayList<>();
            for (int i = 0; i < selectedCategories.length; i++) {
                if (selectedCategories[i]) {
                    selectedCategoryList.add(categories[i]);
                }
            }
            filterByCategory(selectedCategoryList);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCafeRatings();
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

    private ArrayList<Cafe> generateCafeList() {
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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CafeFinderPrefs", Context.MODE_PRIVATE);

        ArrayList<Cafe> cafeList = new ArrayList<>();
        for (int i = 0; i < cafeNames.length; i++) {
            float rating = sharedPreferences.getFloat(cafeNames[i], 0f);
            Cafe cafe = new Cafe(
                    cafeNames[i],
                    cafeDescriptions[i],
                    cafeImages[i],
                    categories[i],
                    recommended[i],
                    rating
            );
            cafeList.add(cafe);
        }
        return cafeList;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_list) {
            if (isGridLayoutManager) {
                binding.rvCafe.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvCafe.setAdapter(listAdapter);
                isGridLayoutManager = false;
                return true;
            }
        } else if (id == R.id.action_grid) {
            if (!isGridLayoutManager) {
                binding.rvCafe.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.rvCafe.setAdapter(gridAdapter);
                isGridLayoutManager = true;
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        binding = null;
    }
}
