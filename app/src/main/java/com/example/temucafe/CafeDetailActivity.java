package com.example.temucafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CafeDetailActivity extends AppCompatActivity {
    private Cafe cafe;
    private ImageButton btnFavorite;
    private RatingBar ratingBarDisplay;
    private RatingBar ratingBarInput;
    private Button btnSubmitRating;
    private SharedPreferences sharedPreferences;
    private String linkAlamat;
    private String linkMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_detail);

        sharedPreferences = getSharedPreferences("CafeFinderPrefs", MODE_PRIVATE);

        String cafeName = getIntent().getStringExtra("SONG_NAME");
        String cafeDesc = getIntent().getStringExtra("SONG_DESC");
        int cafeImage = getIntent().getIntExtra("SONG_IMG_RES_ID", R.id.img_item_photo);
        int cafePosition = getIntent().getIntExtra("CAFE_POSITION", -1);
        int cafeMenuIndex = getIntent().getIntExtra("CAFE_MENU", -1);

        TextView tvCafeName = findViewById(R.id.tv_song_name);
        TextView tvCafeDesc = findViewById(R.id.tv_song_description);
        TextView tvAlamatDesc = findViewById(R.id.tv_alamat_text);
        TextView tvWaktuDesc = findViewById(R.id.tv_waktu_text);
        ImageView imgPhoto = findViewById(R.id.img_item_photo);
        btnFavorite = findViewById(R.id.btn_favorite);
        ratingBarDisplay = findViewById(R.id.ratingBarDisplay);
        ratingBarInput = findViewById(R.id.ratingBarInput);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        tvCafeName.setText(cafeName);
        tvCafeDesc.setText(cafeDesc);
        imgPhoto.setImageResource(cafeImage);

        if (cafePosition != -1) {
            String[] dataAlamat = getResources().getStringArray(R.array.data_alamat);
            tvAlamatDesc.setText(dataAlamat[cafePosition]);
            String[] dataWaktu = getResources().getStringArray(R.array.data_waktu);
            tvWaktuDesc.setText(dataWaktu[cafePosition]);
            String[] dataLinkAlamat = getResources().getStringArray(R.array.data_link_alamat);
            linkAlamat = dataLinkAlamat[cafePosition];
        }

        if (cafeMenuIndex != -1) {
            String[] dataLinkMenu = getResources().getStringArray(R.array.data_link_menu);
            linkMenu = dataLinkMenu[cafeMenuIndex];
        }

        float initialRating = sharedPreferences.getFloat(cafeName, 0f);
        cafe = new Cafe(cafeName, cafeDesc, cafeImage, "", "", initialRating);

        updateFavoriteIcon();

        ImageButton btnLocation = findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation(v);
            }
        });

        ImageButton btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu(v);
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating();
            }
        });

        // Display the current rating
        displayCurrentRating();
    }

    public void openLocation(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkAlamat));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openMenu(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkMenu));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void displayCurrentRating() {
        float defaultRating = 4.0f; // Default rating when none is set

        // Check if a rating exists in SharedPreferences
        float storedRating = sharedPreferences.getFloat(cafe.getName(), defaultRating);

        if (storedRating == defaultRating) {
            // Generate a random initial rating if none exists
            Random random = new Random();
            float randomRating = (float) (random.nextInt(5) + 1); // Generate random rating between 1 and 5
            ratingBarDisplay.setRating(randomRating);

            // Save the random rating to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(cafe.getName(), randomRating);
            editor.apply();
        } else {
            // Display the stored rating
            ratingBarDisplay.setRating(storedRating);
        }
    }

    private void submitRating() {
        float newRating = ratingBarInput.getRating();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(cafe.getName(), newRating);
        editor.apply();
        // For demonstration, we just log it
        Log.d("CafeDetailActivity", "New rating submitted: " + newRating);
        // Update the display rating after submission
        displayCurrentRating();
    }

    private void toggleFavorite() {
        if (FavoriteManager.isFavorite(this, cafe)) {
            FavoriteManager.removeFavorite(this, cafe);
        } else {
            FavoriteManager.addFavorite(this, cafe);
        }
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        if (FavoriteManager.isFavorite(this, cafe)) {
            btnFavorite.setImageResource(R.drawable.baseline_favorite_red_24); // icon untuk sudah favorite
        } else {
            btnFavorite.setImageResource(R.drawable.favorite_24dp_fill_white); // icon untuk belum favorite
        }
    }

    public void gotoMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SELECTED_TAB", R.id.navigation_cafe);
        startActivity(intent);
    }
}
