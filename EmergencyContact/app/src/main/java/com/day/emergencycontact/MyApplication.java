package com.day.emergencycontact;

import android.app.Application;

/**
 * Created by Administrator on 2017/7/2.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate(){
        super.onCreate();

//        SophixManager.getInstance().setContext(this)
//                .setAppVersion("1.0")
//                .setAesKey(null)
//                .setEnableDebug(false)
//                .setPatchLoadStatusStub(new MyPatchLoadStatusListener())
//                .initialize();

        INSTANCE = this;
//        registerActivityLifecycleCallbacks(new AiQGLifecycleHandler());
//        MobclickAgent.openActivityDurationTrack(false);
//        Bmob.initialize(this, "6f134c6cb27845a294e3130de0a93dad");
//        Bugtags.start("55899236f25cd2136ab02bb9654f7e26", this, Bugtags.BTGInvocationEventNone);
    }

    public static MyApplication getInstance(){
        return INSTANCE;
    }
}
