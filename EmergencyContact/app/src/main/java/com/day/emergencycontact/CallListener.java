package com.day.emergencycontact;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.day.emergencycontact.location.AmapLocationService;
import com.day.emergencycontact.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class CallListener extends PhoneStateListener {
    private static final String TAG = "sms";
    private static int lastetState = TelephonyManager.CALL_STATE_IDLE; //最后的状态
    private Context context;
    private String mIncomingNumber;
    public CallListener(Context context) {
        super();
        this.context = context;
    }
    public void onCallStateChanged(int state, String incomingNumber) {
        LogUtils.d( "CallListener call state changed : " + incomingNumber);
        if (!TextUtils.isEmpty(incomingNumber)){
            mIncomingNumber = incomingNumber;
        }
        String m = null;
// 如果当前状态为空闲,上次状态为响铃中的话,则认为是未接来电
        if(lastetState == TelephonyManager.CALL_STATE_RINGING
                && state == TelephonyManager.CALL_STATE_IDLE){
            List<Contact> contacts = SettingsManager.getInstance().getContacts();
            for (Contact eachContact : contacts){
                //if (eachContact.getPhoneNumber().equals(mIncomingNumber)){
                    //如果来电在紧急联系人列表中，启动定位
                    startLocation(mIncomingNumber);
                //}
            }
        }
//最后改变当前值
        lastetState = state;
    }
    private void startLocation(String incomingNumber) {
        //未接来电处理,启动定位
        LogUtils.d( "get missed call : " + incomingNumber + "start location.");
        AmapLocationService.getInstance().setIncomePhoneNumber(incomingNumber);
        AmapLocationService.getInstance().startLocation();
    }
}