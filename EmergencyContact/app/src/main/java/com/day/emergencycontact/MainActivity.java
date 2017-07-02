package com.day.emergencycontact;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.day.emergencycontact.event.LocationEvent;
import com.day.emergencycontact.utils.LogUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private Button btnChooseContact;
    private LinearLayout llContact;

    private MissedCallReceiver receiver;
    private CallReceiver callReceiver;
    private com.day.emergencycontact.SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsManager = new com.day.emergencycontact.SmsManager(this);

        EventBus.getDefault().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.phone.NotificationMgr.MissedCall_intent");
        receiver = new MissedCallReceiver();
        registerReceiver(receiver, intentFilter);

        IntentFilter callIntentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        callReceiver = new CallReceiver();
        registerReceiver(callReceiver, callIntentFilter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);
        unregisterReceiver(callReceiver);
    }

    public void onEvent(LocationEvent event){
        if (event != null){
            LogUtils.d(event.toString());
            List<Contact> contacts = ContactManager.getInstance().getContacts();
            for (Contact eachContact : contacts){
                smsManager.sendMsm(eachContact.getPhoneNumber());
            }
        }
    }

    private void initViews(){
        llContact = (LinearLayout) findViewById(R.id.ll_contact);
        btnChooseContact = (Button) findViewById(R.id.btn_choose_contact);
        btnChooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调出选择联系人界面
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取已选择的联系人
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor cursor = getContentResolver()
                        .query(uri,
                                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String number = cursor.getString(0);
                        String name = cursor.getString(1);
                        ContactView contactView = new ContactView(this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        llContact.addView(contactView, params);
                    }
                }
            }
         }
     }

}
