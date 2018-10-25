package com.zero.callhepler.windows;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

/**
 * @author XuChuanting
 * on 2018/10/25-16:47
 */
public class WindowUtil {

    public static boolean hasFloatingWindowPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }


    public static void openWindowPermissionSettingPage(Activity context,int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName())), requestCode);
        }
    }
}
