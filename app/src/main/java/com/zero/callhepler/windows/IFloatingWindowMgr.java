package com.zero.callhepler.windows;

import android.support.annotation.LayoutRes;

/**
 * @author XuChuanting
 * on 2018/10/25-16:23
 */
public interface IFloatingWindowMgr {

    boolean showWindow(IFloatingWindow window);

    void hideWindow(IFloatingWindow window);

    void removeAllWindow();

}
