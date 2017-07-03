package com.day.emergencycontact.location;

import android.content.Intent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.day.emergencycontact.MyApplication;
import com.day.emergencycontact.event.LocationEvent;
import com.day.emergencycontact.utils.LogUtils;

import de.greenrobot.event.EventBus;


/**
 * <font color='#9B77B2'>该类的主要用途:</font><br/><font color='#36FC2C'><b>
 * <p></p>
 * <b/></font><br/><hr/>
 * <b><font color='#05B8FD'>作者: C&C</font></b><br/><br/>
 * <b><font color='#05B8FD'>创建时间：2017/2/16</font></b><br/><br/>
 * <b><font color='#05B8FD'>联系方式：862530304@qq.com</font></b>
 */

public class AmapLocationService {

   // public static final String TAG = "AmapLocationService";
    public static final String LOCATION = "location";
    public static final String INCOME_CALL = "income_call";

    private static int count;

    private static AmapLocationService mInstance;
    private AMapLocationClient locationClient;
    private String mIncomePhoneNumber;

    private AmapLocationService(){

    }

    synchronized public static AmapLocationService getInstance(){
        if (mInstance == null){
            mInstance = new AmapLocationService();
            mInstance.initLocation();
        }

        return mInstance;
    }

    public void setIncomePhoneNumber(String number){
        mIncomePhoneNumber = number;
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void initLocation(){
        LogUtils.d("initLocation.");
        //初始化client
        locationClient = new AMapLocationClient(MyApplication.getInstance());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(true);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        return mOption;
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void startLocation(){
        locationClient.startLocation();
        LogUtils.d( "startLocation.");
    }

//    /**
//     * 停止定位
//     *
//     * @since 2.8.0
//     * @author hongming.wang
//     *
//     */
//    public void stopLocation(){
//        // 停止定位
//        locationClient.stopLocation();
//        LogUtils.d(TAG, "stopLocation.");
//    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    public void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            //locationOption = null;
            LogUtils.d( "destroyLocation.");
        }
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            LogUtils.d( "onLocationChanged. location." + loc.toString());
            //sendLocationSuccessEvent(loc);
            sendLocationSuccessBroadcast(loc);
            count++;
            if (count >= 5){
                //持续定位10秒后，关闭定位
                locationClient.stopLocation();
                count = 0;
            }
        }
    };

//    private void sendLocationSuccessEvent(AMapLocation location){
//        if (location != null) {
//            final LocationEvent successEvent = new LocationEvent();
//            successEvent.setLatitude( location.getLatitude());
//            successEvent.setLongitude(location.getLongitude());
//            //successEvent.radius = location.getBearing();
//            successEvent.setAddress(location.getAddress());
//            successEvent.setCity(location.getCity());
//            successEvent.setCityCode(location.getCityCode());
//            LogUtils.d(TAG, "sendLocationSuccessEvent.");
//            EventBus.getDefault().post(successEvent);
//            //locationClient.stopLocation();
//        }else {
//            LogUtils.d(TAG, "sendLocationFailedEvent.");
//            LocationEvent failEvent = new LocationEvent();
//            EventBus.getDefault().post(failEvent);
//            //locationClient.stopLocation();
//        }
//    }

    private void sendLocationSuccessBroadcast(AMapLocation location){
        Intent intent = new Intent("com.day.emergencycontact.LOCATION");
        intent.putExtra(LOCATION, location);
        intent.putExtra(INCOME_CALL, mIncomePhoneNumber);
        MyApplication.getInstance().sendBroadcast(intent);
        LogUtils.d( "send location broadcast.");
    }

}
