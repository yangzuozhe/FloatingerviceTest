package com.example.floatingervicetest;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

/**
 * 悬浮窗服务类
 *
 * @author HB.yangzuozhe
 * @date 2020-11-12
 */
public class FloatingService extends Service implements View.OnTouchListener {
    WindowManager windowManager;
    WindowManager.LayoutParams wmParams;
    Button button;
    private int mBtnX;
    private int mBtnY;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloating();
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 设置悬浮窗到手机屏幕
     */
    public void showFloating() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                //获取windowManger服务
                windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                button = new Button(getApplicationContext());
                //设置触摸事件为了，可以让悬浮窗滑动
                button.setOnTouchListener(this);
                button.setText("Floating Button");
                button.setBackgroundColor(Color.BLUE);
                //获取LayoutParam
                wmParams = new WindowManager.LayoutParams();
                //设置悬浮窗样式，根据手机系统来适配
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                } else {
                    wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                }
                //设置外部屏幕是否可以点击，不设置这个无法点击、滑动外部屏幕（很重要）
                wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
                //设置悬浮窗格式
                wmParams.format = PixelFormat.RGBA_8888;
                //设置悬浮窗宽度，高度
                wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                //设置x和y点
                wmParams.x = 300;
                wmParams.y = 300;
                //将button加入悬浮窗
                windowManager.addView(button, wmParams);
            }
        }
    }

    /**
     * 使悬浮窗可以拖动
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mBtnX = (int) event.getRawX();
                mBtnY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                int moveX = nowX - mBtnX;
                int moveY = nowY - mBtnY;
                mBtnX = nowX;
                mBtnY = nowY;
                wmParams.x = wmParams.x + moveX;
                wmParams.y = wmParams.y + moveY;
                windowManager.updateViewLayout(view, wmParams);
                break;

            default:
                break;
        }
        return false;
    }
}
