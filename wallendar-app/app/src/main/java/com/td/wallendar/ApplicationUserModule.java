package com.td.wallendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationUserModule {
    private final static String LOGGED_USER_ID = "LOGGED_USER_ID";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedUserId(Context context, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(LOGGED_USER_ID, value);
        editor.apply();
    }

    public static long getLoggedUserId(Context context) {
        return getSharedPreferences(context).getLong(LOGGED_USER_ID, 0);
    }

}
