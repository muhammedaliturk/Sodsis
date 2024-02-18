package com.example.sodsis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class internet {
    public static boolean internetBaglantisiVarMi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
}
