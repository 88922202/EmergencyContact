package com.day.emergencycontact.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.day.emergencycontact.Contact;
import com.day.emergencycontact.ContactManager;
import com.day.emergencycontact.SettingsManager;
import com.day.emergencycontact.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(AmapLocationService.INCOME_CALL);
        Location location = intent.getParcelableExtra(AmapLocationService.LOCATION);
        //if (!TextUtils.isEmpty(phoneNumber) && location != null){
            LogUtils.d("send msm");
            String message = SettingsManager.getInstance().getCustomMessage();
            message += "我现在的位置是,经度：" + location.getLongitude() + ",纬度： " + location.getLongitude();
            List<Contact> contacts = SettingsManager.getInstance().getContacts();
            for (Contact eachContact : contacts){
                android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
                sms.sendTextMessage(eachContact.getPhoneNumber(), null, message, null, null);
            }
       // }
    }
}
