package com.example.temucafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {
    private static final String PREFS_NAME = "favorites";
    private static final String KEY_FAVORITES = "favorite_ids";

    public static void saveFavorite(Context context, String cafeId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favs = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        Set<String> newFavs = new HashSet<>(favs);
        newFavs.add(cafeId);
        prefs.edit().putStringSet(KEY_FAVORITES, newFavs).apply();
    }

    public static void removeFavorite(Context context, String cafeId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favs = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        Set<String> newFavs = new HashSet<>(favs);
        newFavs.remove(cafeId);
        prefs.edit().putStringSet(KEY_FAVORITES, newFavs).apply();
    }

    public static boolean isFavorite(Context context, String cafeId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favs = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        return favs.contains(cafeId);
    }

    public static Set<String> getFavoriteIds(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
    }
}
