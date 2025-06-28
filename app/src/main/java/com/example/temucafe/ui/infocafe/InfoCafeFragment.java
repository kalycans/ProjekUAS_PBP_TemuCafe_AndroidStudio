package com.example.temucafe.ui.infocafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.temucafe.R;
import com.example.temucafe.data.CafeDataSource;
import com.example.temucafe.models.Cafe;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfoCafeFragment extends Fragment implements OnMapReadyCallback {

    private Cafe selectedCafe;

    public InfoCafeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_cafe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ambil data Cafe dari Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedCafe = (Cafe) bundle.getSerializable("selectedCafe");
        } else {
            selectedCafe = CafeDataSource.getSampleCafes().get(0); // fallback default
        }

        // Set elemen UI
        TextView tvName = view.findViewById(R.id.tvCafeName);
        TextView tvAddress = view.findViewById(R.id.tvCafeAddress);
        TextView tvHours = view.findViewById(R.id.tvCafeOpenHours);
        TextView tvRating = view.findViewById(R.id.tvCafeRating);
        ImageView ivHero = view.findViewById(R.id.ivCafeHero);
        Button btnOpenMap = view.findViewById(R.id.btnOpenMap);
        ViewPager2 viewPager = view.findViewById(R.id.viewPagerGallery);

        tvName.setText(selectedCafe.getName());
        tvAddress.setText("Alamat: " + selectedCafe.getAddress());
        tvHours.setText("Jam buka: " + selectedCafe.getOpenHours());
        tvRating.setText("Rating: " + selectedCafe.getRating());

        // Set gambar hero
        ivHero.setImageResource(selectedCafe.getImageResId());

        // Set galeri
        ImageGalleryAdapter adapter = new ImageGalleryAdapter(selectedCafe.getImageResIds());
        viewPager.setAdapter(adapter);

        // Setup Map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Tombol buka Google Maps eksternal
        btnOpenMap.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:" + selectedCafe.getLatitude() + "," + selectedCafe.getLongitude()
                    + "?q=" + Uri.encode(selectedCafe.getName()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (selectedCafe != null) {
            LatLng cafeLoc = new LatLng(selectedCafe.getLatitude(), selectedCafe.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(cafeLoc).title(selectedCafe.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafeLoc, 15f));
        }
    }
}
