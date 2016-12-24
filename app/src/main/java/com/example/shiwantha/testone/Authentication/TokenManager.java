package com.example.shiwantha.testone.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nipun on 12/21/2016.
 */

public class TokenManager {
    static final String TOKEN = null;

    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context context){
        return getSharedPreferences(context).getString(TOKEN,"");
    }
}
