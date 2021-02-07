package com.example.floatingervicetest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * 类描述
 *
 * @author HB.yangzuozhe
 * @date 2020-11-12
 */
public class MyView extends RelativeLayout {
    public MyView(Context context) {
        this(context,null);

    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.my_view, null);
        addView(view);
    }
}
