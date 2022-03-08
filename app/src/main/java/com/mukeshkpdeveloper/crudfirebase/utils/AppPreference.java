package com.mukeshkpdeveloper.crudfirebase.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    public static final String APP_PREFERENCE = "CRUD-Firebase";

    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static void setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloatPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static void setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearPreferences(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
