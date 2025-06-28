package com.example.temucafe;

public class Cafe {
    private String name;
    private String description;
    private int imageId;
    private String category;
    private String recommendation;
    private float rating;

    public Cafe(String name, String description, int imageId, String category, String recommendation, float rating) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.category = category;
        this.recommendation = recommendation;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
