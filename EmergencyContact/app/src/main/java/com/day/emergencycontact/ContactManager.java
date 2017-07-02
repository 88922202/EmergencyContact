package com.day.emergencycontact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ContactManager {
    private static ContactManager INSTANCE;

    private List<Contact> contacts = new ArrayList<>();

    private ContactManager(){

    }

    synchronized public static ContactManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ContactManager();
        }
        return INSTANCE;
    }

    public List<Contact> getContacts(){
        return contacts;
    }
}
