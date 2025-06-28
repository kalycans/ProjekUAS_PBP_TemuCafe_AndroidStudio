<<<<<<< HEAD
//HomeFragment.java
package com.example.temucafe.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView; // Import ImageView

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
=======
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
>>>>>>> b34972fc911a1d3df98f781ced2699b80afee650
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import com.example.temucafe.R;
import com.example.temucafe.data.CafeDataSource;
import com.example.temucafe.models.Cafe;
import com.example.temucafe.ui.home.RecommendationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvLocation;
    private TextView tvWelcomeMessage;
    private TextView tvSubWelcomeMessage;
    private ImageView ivHeroImage; // New: Hero Image
    private FusedLocationProviderClient fusedLocationClient;
    private View rootView;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tvLocation = rootView.findViewById(R.id.tvLocation);
        tvWelcomeMessage = rootView.findViewById(R.id.tvWelcomeMessage);
        tvSubWelcomeMessage = rootView.findViewById(R.id.tvSubWelcomeMessage);
        ivHeroImage = rootView.findViewById(R.id.ivHeroImage); // Initialize hero image
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        setHeroSectionContent(); // Set content for hero section
        getLastLocation();

        return rootView;
    }

    private void setHeroSectionContent() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String welcomeText;
        String subWelcomeText = "Temukan cafe terbaik di sekitarmu.";
        int heroImageResId;

        if (hourOfDay >= 0 && hourOfDay < 12) {
            welcomeText = "Selamat Pagi, Pecinta Kopi!";
            heroImageResId = R.drawable.hero_morning; // New image for morning
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            welcomeText = "Selamat Siang, Pecinta Kopi!";
            heroImageResId = R.drawable.hero_afternoon; // New image for afternoon
        } else {
            welcomeText = "Selamat Malam, Pecinta Kopi!";
            heroImageResId = R.drawable.hero_night; // New image for night
        }

        tvWelcomeMessage.setText(welcomeText);
        tvSubWelcomeMessage.setText(subWelcomeText);
        ivHeroImage.setImageResource(heroImageResId);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            tvLocation.setText(String.format("Lat: %.4f, Lon: %.4f", lat, lon));

                            RecyclerView rv = rootView.findViewById(R.id.rvRecommendation);
                            rv.setLayoutManager(new LinearLayoutManager(getContext()));

                            ArrayList<Cafe> cafes = CafeDataSource.getSampleCafes();
                            ArrayList<Cafe> sortedCafes = new ArrayList<>(cafes);
                            ArrayList<Double> jarakList = new ArrayList<>();

                            Collections.sort(sortedCafes, (c1, c2) -> {
                                double jarak1 = hitungJarak(lat, lon, c1.getLatitude(), c1.getLongitude());
                                double jarak2 = hitungJarak(lat, lon, c2.getLatitude(), c2.getLongitude());
                                return Double.compare(jarak1, jarak2);
                            });

                            List<Cafe> top3 = sortedCafes.subList(0, Math.min(3, sortedCafes.size()));
                            for (Cafe cafe : top3) {
                                double jarak = hitungJarak(lat, lon, cafe.getLatitude(), cafe.getLongitude());
                                jarakList.add(jarak);
                            }

                            RecommendationAdapter adapter = new RecommendationAdapter(top3, jarakList);
                            rv.setAdapter(adapter);

                        } else {
                            tvLocation.setText(R.string.location_error);
                        }
                    }
                });
    }

    private double hitungJarak(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
=======
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
>>>>>>> b34972fc911a1d3df98f781ced2699b80afee650
    }
}
