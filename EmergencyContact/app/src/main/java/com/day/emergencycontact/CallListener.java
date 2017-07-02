package com.day.emergencycontact;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.day.emergencycontact.location.AmapLocationService;
import com.day.emergencycontact.utils.LogUtils;

/**
 * Created by Administrator on 2017/7/2.
 */

public class CallListener extends PhoneStateListener {
    private static final String TAG = "sms";
    private static int lastetState = TelephonyManager.CALL_STATE_IDLE; //最后的状态
    private Context context;
    public CallListener(Context context) {
        super();
        this.context = context;
    }
    public void onCallStateChanged(int state, String incomingNumber) {
        LogUtils.d( "CallListener call state changed : " + incomingNumber);
        String m = null;
// 如果当前状态为空闲,上次状态为响铃中的话,则认为是未接来电
        if(lastetState == TelephonyManager.CALL_STATE_RINGING
                && state == TelephonyManager.CALL_STATE_IDLE){
            sendSmgWhenMissedCall(incomingNumber);
        }
//最后改变当前值
        lastetState = state;
    }
    private void sendSmgWhenMissedCall(String incomingNumber) {
        //未接来电处理,启动定位
        AmapLocationService.getInstance().startLocation();
    }
}