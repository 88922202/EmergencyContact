package com.day.emergencycontact;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ContactView extends LinearLayout {

    private Context mContext;

    public ContactView(Context context) {
        super(context);
        mContext = context;
    }

    public ContactView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void createView(String name, String phone){
        final View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_contact, null);
        TextView tvContact = (TextView) rootView.findViewById(R.id.tv_contact);
        tvContact.setText(name + phone);
        ImageView ivDelete = (ImageView) rootView.findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.setVisibility(GONE);
            }
        });
    }
}
