package com.day.emergencycontact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class SettingsManager {

    private static SettingsManager INSTANCE;

    private String mCustomMessage;                          //短信内容
    private List<Contact> contacts = new ArrayList<>();     //紧急联系人

    private SettingsManager(){

    }

    synchronized public static SettingsManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SettingsManager();
            INSTANCE.initTestData();
        }
        return INSTANCE;
    }

    public String getCustomMessage() {
        return mCustomMessage;
    }

    public void setCustomMessage(String mCustomMessage) {
        this.mCustomMessage = mCustomMessage;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    private void getLocalSettings(){

    }

    private void initTestData(){
        Contact contact = new Contact("18516266534");
        contacts.add(contact);
    }
}
