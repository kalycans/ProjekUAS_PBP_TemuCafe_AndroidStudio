package com.example.temucafe;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String FAVORITES_KEY = "favorites";
    private static final String FAVORITE_COUNT_KEY = "favorite_count";

    public static void addFavorite(Context context, Cafe cafe) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(getFavorites(context));
        favorites.add(cafe.getName());
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply();

        updateFavoriteCount(context, favorites.size());
        Log.d("FavoriteManager", "Added favorite: " + cafe.getName() + ", new count: " + favorites.size());
    }

    public static void removeFavorite(Context context, Cafe cafe) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(getFavorites(context));
        favorites.remove(cafe.getName());
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply();

        updateFavoriteCount(context, favorites.size());
        Log.d("FavoriteManager", "Removed favorite: " + cafe.getName() + ", new count: " + favorites.size());
    }

    public static boolean isFavorite(Context context, Cafe cafe) {
        return getFavorites(context).contains(cafe.getName());
    }

    public static Set<String> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(FAVORITES_KEY, new HashSet<>());
    }

    public static int getFavoriteCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int count = prefs.getInt(FAVORITE_COUNT_KEY, 0);
        Log.d("FavoriteManager", "Retrieved favorite count: " + count);
        return count;
    }

    public static void updateFavoriteCount(Context context, int count) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(FAVORITE_COUNT_KEY, count).apply();
        Log.d("FavoriteManager", "Updated favorite count: " + count);
    }
}
