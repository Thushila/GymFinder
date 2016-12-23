package com.example.shiwantha.testone.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by shiwantha on 12/22/16.
 */

public class StatusCheck {

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
