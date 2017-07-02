package com.day.emergencycontact;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/7/2.
 */

public class SmsManager {
    private String mMessage;
    private Context mContext;

    public SmsManager(Context context){
        mContext = context;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void sendMsm(String phone){
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(phone, null, mMessage, null, null);
    }

    public void sendMsm(String phone, String message){
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(phone, null, message, null, null);
    }
}
