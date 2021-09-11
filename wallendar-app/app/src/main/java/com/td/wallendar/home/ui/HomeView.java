package com.td.wallendar.home.ui;

import android.content.Context;
import android.content.Intent;

public interface HomeView {
    void startActivity(Intent intent);

    Context getApplicationContext();
}
