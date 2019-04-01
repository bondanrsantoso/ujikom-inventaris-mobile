package com.example.inventaris.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EnvironmentVariables {

    public static final String NULL = "NULL" ;

    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserToken(Context context, String apiToken){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("API_TOKEN", apiToken);
        editor.apply();
    }

    public static String getUserToken(Context context){
        return getPreferences(context).getString("API_TOKEN", NULL);
    }

    public static void setBaseURL(Context context, String url){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("BASE_URL", url);
        editor.apply();
    }

    public static String getBaseURL(Context context){
        return getPreferences(context).getString("BASE_URL", NULL);
    }
}
