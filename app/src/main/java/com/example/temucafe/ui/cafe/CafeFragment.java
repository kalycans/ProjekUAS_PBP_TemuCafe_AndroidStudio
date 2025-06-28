package com.example.temucafe.ui.cafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.R;
import com.example.temucafe.data.CafeDataSource;
import com.example.temucafe.models.Cafe;

import java.util.ArrayList;
import java.util.List;

public class CafeFragment extends Fragment {

    private RecyclerView rvCafe;
    private Spinner spinnerWilayah;
    private CafeListAdapter cafeAdapter;
    private List<Cafe> allCafes;
    private List<Cafe> filteredCafes;

    public CafeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cafe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCafe = view.findViewById(R.id.rvCafe);
        spinnerWilayah = view.findViewById(R.id.spinnerWilayah);

        // Ambil semua data cafe
        allCafes = CafeDataSource.getSampleCafes();
        filteredCafes = new ArrayList<>(allCafes);

        // Inisialisasi dan atur RecyclerView
        rvCafe.setLayoutManager(new LinearLayoutManager(requireContext()));
        // Pastikan requireContext() dan getParentFragmentManager() digunakan di sini
        cafeAdapter = new CafeListAdapter(filteredCafes, requireContext(), getParentFragmentManager(), false);
        rvCafe.setAdapter(cafeAdapter);

        // Atur Spinner Wilayah
        // Pastikan requireContext() digunakan di sini
        ArrayAdapter<CharSequence> adapterWilayah = ArrayAdapter.createFromResource(
                requireContext(), R.array.wilayah_array, android.R.layout.simple_spinner_item);
        adapterWilayah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWilayah.setAdapter(adapterWilayah);

        // Listener ketika wilayah dipilih
        spinnerWilayah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                filteredCafes.clear();

                if (selected.equals("Semua")) {
                    filteredCafes.addAll(allCafes);
                } else {
                    for (Cafe cafe : allCafes) {
                        if (cafe.getRegion().equalsIgnoreCase(selected)) {
                            filteredCafes.add(cafe);
                        }
                    }
                }
                cafeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
