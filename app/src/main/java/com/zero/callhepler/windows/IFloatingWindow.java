package com.zero.callhepler.windows;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.WindowManager;

/**
 * @author XuChuanting
 * on 2018/10/25-16:28
 */
public interface IFloatingWindow {

    Context getContext();

    View getContentView();

    void setContentView(@LayoutRes int layoutRes);

    void setContentView(View view);

    <T extends View> T findViewById(@IdRes int viewId);

    void setParams(WindowManager.LayoutParams params);

    void show();

    void dismiss();

    boolean isShowing();

    WindowManager.LayoutParams  getDefaultLayoutParams();

    WindowManager.LayoutParams  getLayoutParams();

}
