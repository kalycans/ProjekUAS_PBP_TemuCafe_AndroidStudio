package com.example.temucafe.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.R;
import com.example.temucafe.data.CafeDataSource;
import com.example.temucafe.models.Cafe;
import com.example.temucafe.ui.cafe.CafeListAdapter;
import com.example.temucafe.utils.FavoriteManager;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private RecyclerView rvFavorite;

    public FavoriteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rvFavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Cafe> allCafes = CafeDataSource.getSampleCafes();

        ArrayList<Cafe> favoriteCafes = new ArrayList<>();
        for (Cafe cafe : allCafes) {
            boolean isFav = FavoriteManager.isFavorite(requireContext(), cafe.getId());
            if (isFav) {
                cafe.setFavorite(true);
                favoriteCafes.add(cafe);
            }
        }

        // ✅ Gunakan constructor terbaru
        CafeListAdapter adapter = new CafeListAdapter(favoriteCafes, requireContext(), getParentFragmentManager(), true);
        rvFavorite.setAdapter(adapter);

        return view;
    }
}
