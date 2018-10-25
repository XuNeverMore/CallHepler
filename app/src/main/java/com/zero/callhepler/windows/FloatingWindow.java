package com.zero.callhepler.windows;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author XuChuanting
 * on 2018/10/25-16:29
 */
public class FloatingWindow implements IFloatingWindow {

    private Context mContext;
    private View mContentView;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean mIsShowing = false;

    public FloatingWindow(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public View getContentView() {
        return mContentView;
    }

    @Override
    public void setContentView(@NonNull View view) {
        this.mContentView = view;
    }

    @Override
    public void setContentView(int layoutRes) {
        this.mContentView = LayoutInflater.from(mContext).inflate(layoutRes, null);
    }

    @Override
    public <T extends View> T findViewById(int viewId) {
        if (mContentView == null) {
            throw new NullPointerException("please invoke setContentView method first");
        }
        return mContentView.findViewById(viewId);
    }

    @Override
    public void setParams(WindowManager.LayoutParams params) {
        this.mLayoutParams = params;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IFloatingWindow){
            View contentView = ((IFloatingWindow) obj).getContentView();
            return contentView == getContentView();
        }
        return super.equals(obj);
    }

    @Override
    public void show() {
        if (mIsShowing) {
            return;
        }
        if (mLayoutParams == null) {
            mLayoutParams = getDefaultLayoutParams();
        }
        mIsShowing = FWManager.getFWManager().showWindow(this);
    }


    @Override
    public void dismiss() {
        FWManager.getFWManager().hideWindow(this);
        mIsShowing = false;
    }

    @Override
    public boolean isShowing() {
        return mIsShowing;
    }

    @Override
    public WindowManager.LayoutParams getDefaultLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM|
                WindowManager.LayoutParams.FLAG_FULLSCREEN|
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }

    @Override
    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }
}
