package com.iutbm.example.iutbm.couchot.healthybody.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.iutbm.example.iutbm.couchot.healthybody.R;


public class HeartBeatView extends AppCompatImageView {

    private Drawable heartDrawable;

    public HeartBeatView(Context context) {
        super(context);
        init();
    }

    public HeartBeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartBeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        heartDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_red_24dp);
        setImageDrawable(heartDrawable);
    }
}