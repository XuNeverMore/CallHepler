package com.zero.callhepler;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.zero.callhepler.service.CallService;
import com.zero.callhepler.windows.WindowUtil;

import static com.zero.callhepler.NotificationPermissionUtil.gotoNotificationAccessSetting;

/**
 * created by Lin on 2017/12/16
 */

public class MainActivity extends Activity {

    public static final int REQUEST_CODE_WINDOW_SETTING = 1;
    public static final int REQUEST_CODE_PHONE_PERMISSION = 2;
    private Button mTvNotiStatus;
    private Button mBtnWindowPermission;
    private Button mBtnPhonePermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, CallService.class));
        mTvNotiStatus = findViewById(R.id.tv_notification_status);

        mTvNotiStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNotificationAccessSetting(MainActivity.this);
            }
        });

        mBtnWindowPermission = findViewById(R.id.btn_window_permission);
        //悬浮窗权限
        if (!WindowUtil.hasFloatingWindowPermission(this)) {
            mBtnWindowPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WindowUtil.openWindowPermissionSettingPage(MainActivity.this, REQUEST_CODE_WINDOW_SETTING);
                }
            });
        }

        //电话权限
        mBtnPhonePermission = findViewById(R.id.btn_phone_permission);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mBtnPhonePermission.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PHONE_PERMISSION);
                }
            });
        }else {
            mBtnPhonePermission.setSelected(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PHONE_PERMISSION) {
            boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            mBtnPhonePermission.setSelected(granted);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvNotiStatus.setSelected(NotificationPermissionUtil.isNotificationListenersEnabled(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WINDOW_SETTING) {
            mBtnWindowPermission.setSelected(WindowUtil.hasFloatingWindowPermission(this) );
        }
    }
}
