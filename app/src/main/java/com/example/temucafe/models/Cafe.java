package com.example.temucafe.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cafe implements Serializable {
    private String id;
    private String name;
    private String address;
    private String openHours;
    private float rating;
    private int imageResId; // Gambar utama (hero)
    private double latitude;
    private double longitude;
    private ArrayList<Integer> imageResIds; // Gambar galeri
    private boolean isFavorite;
    private String region;

    public Cafe(String id, String name, String address, String openHours, float rating, int imageResId,
                double latitude, double longitude, ArrayList<Integer> imageResIds, String region) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openHours = openHours;
        this.rating = rating;
        this.imageResId = imageResId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageResIds = imageResIds;
        this.isFavorite = false;
        this.region = region;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getOpenHours() { return openHours; }
    public float getRating() { return rating; }
    public int getImageResId() { return imageResId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public ArrayList<Integer> getImageResIds() { return imageResIds; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
