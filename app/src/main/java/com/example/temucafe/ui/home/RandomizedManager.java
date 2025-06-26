package com.example.temucafe.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.temucafe.R;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RandomizedManager {
    private static final String PREFS_NAME = "DailyRandomize";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUMBERS = "numbers";

    public static String getDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        if (dateFormat instanceof SimpleDateFormat) {
            ((SimpleDateFormat) dateFormat).applyPattern("dMMyyyy");
        }
        return dateFormat.format(new Date());
    }

    public static int[] getRandomizedIndex(Random random, int maxIndex) {
        int[] result = new int[5];
        for (int i = 0; i < 5; i++) {
            result[i] = random.nextInt(maxIndex + 1); // maxIndex is inclusive
        }
        return result;
    }

    private static void saveArrayToPrefs(SharedPreferences prefs, String key, int[] array) {
        Gson gson = new Gson();
        String json = gson.toJson(array);
        prefs.edit().putString(key, json).apply();
    }

    private static int[] getArrayFromPrefs(SharedPreferences prefs, String key) {
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<int[]>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static int[] getStoredRandomNumbers(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currentDate = getDate();

        int maxIndex = context.getResources().getStringArray(R.array.data_cafe).length - 1;

        String storedDate = prefs.getString(KEY_DATE, "");
        int[] storedNumbers = getArrayFromPrefs(prefs, KEY_NUMBERS);

        if (storedDate.equals(currentDate) && storedNumbers != null) {
            return storedNumbers;
        } else {
            // Use a fixed seed for reproducibility if needed, or truly random for production
            Random random = new Random(Long.parseLong(currentDate)); // Seed with current date for daily change
            int[] newRandomNumbers = getRandomizedIndex(random, maxIndex);
            prefs.edit().putString(KEY_DATE, currentDate).apply();
            saveArrayToPrefs(prefs, KEY_NUMBERS, newRandomNumbers);
            return newRandomNumbers;
        }
    }
}
