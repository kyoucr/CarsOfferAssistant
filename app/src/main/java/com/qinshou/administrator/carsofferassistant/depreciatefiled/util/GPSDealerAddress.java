package com.qinshou.administrator.carsofferassistant.depreciatefiled.util;

import android.content.Context;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 岩on 2016/7/11.
 * 定位国家、城市、地址的工具类
 */

public class GPSDealerAddress {
    private Context context;

    private TextView countryTextView;    // 国家名 显示的TextView
    private TextView cityTextView;       // 城市名 显示的TextView
    private TextView addressTextView;   // 地址 显示的TextView

    /**
     * @param context   上下文实例，请使用getApplicationContext();
     */
     public GPSDealerAddress(Context context){
         SDKInitializer.initialize(context);
        this.context = context;
         startLocationService();
    }

    /**
     * @param context           上下文实例，请使用getApplicationContext();
     * @param countryTextView   要显示定位国家的国家的TextView，不获取，请传null
     * @param cityTextView      要显示定位城市的城市的TextView，不获取，请传null
     * @param addressTextView   要显示定位的地址的TextView，不获取，请传null
     */
    public GPSDealerAddress(Context context, TextView countryTextView, TextView cityTextView, TextView addressTextView){
        SDKInitializer.initialize(context);
        this.context = context;
        this.countryTextView = countryTextView;
        this.cityTextView = cityTextView;
        this.addressTextView = addressTextView;
        startLocationService();
    }

    /**
     * 启动定位服务
     */
    private void startLocationService() {

        LocationClient client = null;
        BDLocationListener listener = null;

        // 定位客户端实例的构建（LocationClient）
        LocationClientOption option = new LocationClientOption();
        // 设置定位的属性值
        // 坐标系
        // option.setCoorType("bd09ll");//百度经纬度坐标系
        option.setCoorType("gcj02");// 国测局经纬度坐标系
        // 设置定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setIsNeedAddress(true);// 需要地址信息，默认是false

        // 设置扫描间隔(设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效)
        //option.setScanSpan(2000);// 确保地图是最新的状态，不是历史数据

        client = new LocationClient(context, option);

        // 启动定位服务
        client.start();

        // 注册定位监听函数
        listener = new MyBDLocationListener();
        client.registerLocationListener(listener);
    }


    /**
     * BDLocationListener接口的实现类
     */
    private final class MyBDLocationListener implements BDLocationListener {

        /*
         * 定位请求回调函数 参数：BDLocaiton - 定位结果
         * @see
         * com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu
         * .location.BDLocation)
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if(countryTextView!=null){
                String country = location.getCountry();
                if(country!=null){
                    countryTextView.setText(country);
                }
            }
            if(cityTextView!=null){
                String city = location.getCity();
                if(city!=null){
                    cityTextView.setText(city);
                }
            }
            if(addressTextView!=null){
                String address = location.getAddrStr();
                if(address!=null){
                    addressTextView.setText(address);
                }
            }
        }
    }
}
