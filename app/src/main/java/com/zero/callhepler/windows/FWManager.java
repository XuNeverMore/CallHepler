package com.zero.callhepler.windows;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuChuanting
 * on 2018/10/25-16:59
 */
public class FWManager implements IFloatingWindowMgr {
    private static FWManager sFWManager;
    private List<IFloatingWindow> mWindows = new ArrayList<>();

    private FWManager() {

    }

    public static FWManager getFWManager() {
        if (sFWManager == null) {
            sFWManager = new FWManager();
        }
        return sFWManager;
    }

    @Override
    public boolean showWindow(IFloatingWindow window) {
        if (window != null) {
            Context context = window.getContext();
            WindowManager wgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            View contentView = window.getContentView();
            WindowManager.LayoutParams layoutParams = window.getLayoutParams();
            if (wgr != null && !window.isShowing() && contentView != null && layoutParams != null) {
                contentView.setFitsSystemWindows(true);
                wgr.addView(contentView, layoutParams);
                mWindows.add(window);
                return true;
            }
        }
        return false;
    }

    @Override
    public void hideWindow(IFloatingWindow window) {
        if (window != null && window.isShowing()) {
            Context context = window.getContext();
            WindowManager wgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            View contentView = window.getContentView();
            if (wgr != null) {
                wgr.removeView(contentView);
            }
            if (mWindows.contains(window)) {
                mWindows.remove(window);
            }
        }

    }

    @Override
    public void removeAllWindow() {
        for (IFloatingWindow window : mWindows) {
            hideWindow(window);
        }
    }
}
