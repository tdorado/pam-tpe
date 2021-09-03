package com.td.wallendar.home.profile.ui;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.td.wallendar.R;

public class ProfileViewImpl extends LinearLayout implements ProfileView{
    public ProfileViewImpl(Context context) {
        this(context, null);
    }

    public ProfileViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_profile, this);
        setGravity(CENTER);
        setOrientation(VERTICAL);

    }

    @Override
    public void bind() {

    }
}
