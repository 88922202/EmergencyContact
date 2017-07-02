package com.day.emergencycontact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.day.emergencycontact.utils.LogUtils;

/**
 * Created by Administrator on 2017/7/2.
 */

public class CallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        LogUtils.i("CallReceiver Start...");
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        CallListener customPhoneListener = new CallListener(context);
        telephony.listen(customPhoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);
        Bundle bundle = intent.getExtras();
        String phoneNr = bundle.getString("incoming_number");
        LogUtils.i("CallReceiver Phone Number : " + phoneNr);
    }
}
