package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.BroadcastReceiver;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.qinshou.administrator.carsofferassistant.R;

public class GPSDealerAddressActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;
    private LocationClient client;
    private BDLocationListener listener;

    private MapView mv_id;// 百度地图展示用的自定义控件
    private BaiduMap map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化
        SDKInitializer.initialize(getApplicationContext());// 此处，参数建议获取全局的Context
        // 检查百度地图是否可用（广播接收者）
        //checkBaiduMapUsage();

        setContentView(R.layout.activity_gpsdealer_address);

        mv_id = (MapView) findViewById(R.id.mv_id);
        map = mv_id.getMap();
        aboutActionBar();

        // ②给Baidumap添加OnMarkerClickListener监听器
        addMarkerListenerBaidumap();

        addMarkerToMap("天安门", new LatLng(39.913202, 116.404804));     //在屏幕上添加一个默认的覆盖物（不是点击之后添加的，而是默认的）

        startLocationService(); // 启动定位服务

    }

    /**
     * 关于ActionBar的相关操作
     */
    private void aboutActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.select_city_tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);  // 决定左上角图标的右侧是否有向左的小箭头, true
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {    // 左上角的箭头
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 在屏幕上添加一个默认的覆盖物
     */
    private void addMarkerToMap(String title, LatLng position) {

        // 1、 准备Overlay的实例
        MarkerOptions overlay = new MarkerOptions();

        // 2、 设置相应的属性
        // ① 设置图片
        BitmapDescriptor descriptor = BitmapDescriptorFactory
                .fromBitmap(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.dealer));
        overlay.icon(descriptor);

        // ②设置文字
        overlay.title(title);

        // ③设置经纬度
        overlay.position(position);

        // 3、 在百度地图上添加覆盖物
        map.addOverlay(overlay);

        // 4、修改百度地图的状态
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(position);
        map.setMapStatus(status);
    }





    /**
     * 给Baidumap添加OnMarkerClickListener监听器
     *
     */
    private void addMarkerListenerBaidumap() {
        //map.setOnMarkerClickListener(new OnMarkerClickListener() {
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            /**
             * 地图 Marker 覆盖物点击事件监听函数,开发者注意根据参数Marker来判断响应某个对象的点击事件
             */
            @Override
            public boolean onMarkerClick(Marker marker) {

                // 构建InfoWindow实例
                // InfoWindow(BitmapDescriptor bd, LatLng position, int yOffset,
                // InfoWindow.OnInfoWindowClickListener listener)
                Button btn = new Button(getApplicationContext());
                btn.setText(marker.getTitle());
                btn.setTextColor(Color.RED);
                // btn.setBackgroundResource(R.drawable.popup);
                btn.setBackgroundResource(R.mipmap.popup);
                BitmapDescriptor bd = BitmapDescriptorFactory.fromView(btn);

                LatLng position = marker.getPosition();

                int yOffset = -20;// 向上移动20各个单位

                // OnInfoWindowClickListener infoWindowListener = new InfoWindow.OnInfoWindowClickListener() {
                InfoWindow.OnInfoWindowClickListener infoWindowListener = new InfoWindow.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick() {
                        // 信息框消失
                        map.hideInfoWindow();
                    }
                };

                InfoWindow infoWindow = new InfoWindow(bd, position, yOffset,
                        infoWindowListener);

                // 在地图上显示InfoWindow
                map.showInfoWindow(infoWindow);

                return false;
            }
        });
    }



    /**
     * 启动定位服务
     */
    private void startLocationService() {
        // 思路：
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

        client = new LocationClient(getApplicationContext(), option);

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
         *
         * @see
         * com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu
         * .location.BDLocation)
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            // 标题
            String title = location.getCountry() + location.getCity()
                    + location.getAddrStr();


            // 经纬度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String counrty = location.getCountry();
            String city = location.getCity();
            String address = location.getAddrStr();

//            TextView county_id = (TextView) findViewById(R.id.county_id);
//            TextView city_id = (TextView) findViewById(R.id.city_id);
//            TextView address_id = (TextView) findViewById(R.id.address_id);
//
//            county_id.setText("counrty："+counrty);
//            city_id.setText("city："+city);
//            address_id.setText("address_id:"+address);

            // 在用户点击的地方，添加覆盖物
            // 根据定位的信息动态在屏幕上添加覆盖物
            map.clear();
            addMarkerToMap(title, new LatLng(latitude, longitude));
        }
    }
}

