package com.zero.callhepler;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.zero.callhepler.windows.FloatingWindow;
import com.zero.callhepler.windows.IFloatingWindow;

/**
 * created by Lin on 2017/12/16
 */

public class CallReceiver extends BroadcastReceiver {


    private IFloatingWindow mWindow;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            int currentCallState = telephonyManager.getCallState();
            switch (currentCallState) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if (mWindow == null) {
                        mWindow = showCallWindow(context, phoneNumber);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }

    private IFloatingWindow showCallWindow(final Context context, String phoneNumber) {
        final IFloatingWindow window = new FloatingWindow(context);
        window.setContentView(R.layout.calling_layout);
        TextView tvPhoneNum = window.findViewById(R.id.tv_phone_number);
        tvPhoneNum.setText(phoneNumber);
        window.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseCall(context, true);
                window.dismiss();
                mWindow = null;
            }
        });
        window.findViewById(R.id.btn_reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseCall(context, false);
                window.dismiss();
                mWindow = null;
            }
        });
        window.show();
        return window;
    }

    private void responseCall(Context context, boolean accept) {
        if (accept) {
            CallWorkActivity.sCall_op = CallWorkActivity.CALL_OP.CALL_ACCEPT;
        } else {
            CallWorkActivity.sCall_op = CallWorkActivity.CALL_OP.CALL_REJECT;
        }
        Intent workIntent = new Intent(context, CallWorkActivity.class);
        workIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(workIntent);
    }

}
