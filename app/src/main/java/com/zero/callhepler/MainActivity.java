package com.zero.callhepler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zero.callhepler.service.CallService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.zero.callhepler.NotificationPermissionUtil.gotoNotificationAccessSetting;

/**
 * created by Lin on 2017/12/16
 */

public class MainActivity extends Activity {

    private TextView mTvNotiStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallWorkActivity.sCall_op = CallWorkActivity.CALL_OP.CALL_ACCEPT;
            }
        });
        findViewById(R.id.button_reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallWorkActivity.sCall_op = CallWorkActivity.CALL_OP.CALL_REJECT;
            }
        });
        findViewById(R.id.button_none).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallWorkActivity.sCall_op = CallWorkActivity.CALL_OP.NONE;
            }
        });
        startService(new Intent(this, CallService.class));
        mTvNotiStatus = (TextView) findViewById(R.id.tv_notification_status);
        findViewById(R.id.tv_notification_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNotificationAccessSetting(MainActivity.this);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTvNotiStatus.setText(NotificationPermissionUtil.isNotificationListenersEnabled(this)?"通知访问权限已打开":"请打开通知访问权限");
    }
}
