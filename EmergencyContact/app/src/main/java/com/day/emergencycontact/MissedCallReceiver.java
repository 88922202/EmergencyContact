package com.day.emergencycontact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.day.emergencycontact.location.AmapLocationService;
import com.day.emergencycontact.utils.LogUtils;

/**
 * Created by Administrator on 2017/7/2.
 */

public class MissedCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int mMissCallCount = intent.getExtras().getInt("MissedCallNumber");
        LogUtils.d("missed call count " + mMissCallCount);
        AmapLocationService.getInstance().startLocation();
    }
}
