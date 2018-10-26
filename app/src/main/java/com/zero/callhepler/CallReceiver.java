package com.zero.callhepler;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zero.callhepler.windows.FloatingWindow;
import com.zero.callhepler.windows.IFloatingWindow;

/**
 * created by Lin on 2017/12/16
 */

public class CallReceiver extends BroadcastReceiver {

    private Context mContext;
    private IFloatingWindow mWindow;
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://响铃
                    if (mWindow == null) {
                        mWindow = showCallWindow(mContext, phoneNumber);
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE://挂断
                    if (mWindow != null) {
                        mWindow.dismiss();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:

                    break;
            }

        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (!TextUtils.equals(intent.getAction(), Intent.ACTION_NEW_OUTGOING_CALL)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            assert tm != null;
            tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

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
