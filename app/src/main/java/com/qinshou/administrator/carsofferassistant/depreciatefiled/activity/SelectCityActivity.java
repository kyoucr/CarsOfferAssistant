package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter.ExpandableListAdapterForCitiesList;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CityBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ProvinceBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.DownloadCitiesCallbackInter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.network.DownLoadCitiesAsyncTask;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ParseXml;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity implements DownloadCitiesCallbackInter {

    private ExpandableListView elv_cities_id;
    private CitiesListInfoBean datas;             // 城市列表数据源
    private ExpandableListAdapterForCitiesList adapter; // 城市列表适配器
    private AutoCompleteTextView actv_search_city_id;   // 自动填充输入框
    private ArrayAdapter<String> actvAdapter; // 自动填充数据框数据源
    private List<String> cityNames;     // 自动填充数据框数据源


    // 关于百度定位要用到的属性
    private BroadcastReceiver receiver;
    private LocationClient client;
    private BDLocationListener listener;


    private Button btn_gps_city_id;    // 重试按钮
    private TextView tv_gps_city_id;    // 定位城市显示文本

//    public SelectCityActivity() {
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化
        SDKInitializer.initialize(getApplicationContext());// 此处，参数建议获取全局的Context
        // 检查百度地图是否可用（广播接收者）
        //checkBaiduMapUsage();

        setContentView(R.layout.activity_select_city);

        btn_gps_city_id = (Button) findViewById(R.id.btn_gps_city_id);    // 重试按钮
        tv_gps_city_id = (TextView) findViewById(R.id.tv_gps_city_id);    // 定位城市显示文本


        aboutActionBar();
        aboutAutoCompleteTextView();
        aboutExpandableListView();

        new DownLoadCitiesAsyncTask(this).execute(Urls.CITY_lIST_SELECT);   // 开启异步任务下载数据源

        startLocationService();      // 启动定位服务
    }

    /**
     * 关于自动填充文本的操作
     */
    private void aboutAutoCompleteTextView() {
        actv_search_city_id = (AutoCompleteTextView) findViewById(R.id.actv_search_city_id);
        cityNames = new LinkedList<>();  // 初始化数据源
        actvAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityNames);
        actv_search_city_id.setAdapter(actvAdapter);
        actv_search_city_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    String cityName = ((TextView) view).getText().toString();
                    backTheCallActivity(cityName);
                } else {
                    backTheCallActivity("");
                }
            }
        });
    }

    /**
     * 更新AutoCompleteTextView的数据源
     */
    private void updateAutoCompleteTextView() {
        cityNames.clear();
        if (datas != null && datas.getProvices().size() > 0) {
            for (ProvinceBean provinces : datas.getProvices()) {
                if (provinces != null && provinces.getCities().size() > 0) {
                    for (CityBean cityBean : provinces.getCities()) {
                        if (cityBean != null) {
                            cityNames.add(cityBean.getcityName());
                        }
                    }
                }
            }
        }
        actvAdapter.notifyDataSetChanged();
    }


    /**
     * 关于ExpandableListView的操作
     */
    private void aboutExpandableListView() {
        datas = new CitiesListInfoBean();    // 初始化数据源
        elv_cities_id = (ExpandableListView) findViewById(R.id.elv_cities_id);
        LinearLayout elv_empty_id = (LinearLayout) findViewById(R.id.elv_empty_id);
        elv_cities_id.setEmptyView(elv_empty_id);

        adapter = new ExpandableListAdapterForCitiesList(datas, this);
        elv_cities_id.setAdapter(adapter);
        elv_cities_id.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {       // 使group点击不可展开
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        elv_cities_id.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                String cityName = datas.getProvices().get(groupPosition).getCities().get(childPosition).getcityName();
                backTheCallActivity(cityName);
                return false;
            }
        });

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
     * 展开ExpandableListView的每一项
     */
    private void expandEveryItem() {
        if (datas != null && datas.getProvices().size() > 0) {
            for (int i = 0; i < datas.getProvices().size(); i++) {
                elv_cities_id.expandGroup(i);
            }
        }
    }

    /**
     * 从网络上下载城市列表后更新页面
     *
     * @param citiesListInfoBean
     */
    @Override
    public void hasDownLoadCities(CitiesListInfoBean citiesListInfoBean) {
        if (citiesListInfoBean == null) {   // 从网络获取数据异常的时候，就从本地资产目录加载。
            try {
                InputStream is = getAssets().open("wz_rule.xml");
                if (is != null) {
                    citiesListInfoBean = ParseXml.parseCities(is);
                } else {
                    Toast.makeText(this, "资产目录不存在", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.datas = citiesListInfoBean;
        adapter = new ExpandableListAdapterForCitiesList(datas, this);
        elv_cities_id.setAdapter(adapter);  // ExpandableListView控件的notifyDataSetChanged()方法不能使用，重新绑定适配器
        expandEveryItem();                  // 展开每一个选项
        updateAutoCompleteTextView();       //  更新自动填充框的数据源
    }

    /**
     * 返回启动该Activity的页面，并返回所选中的城市名
     *
     * @param cityName 城市名
     */
    public void backTheCallActivity(String cityName) {
        Intent intent = new Intent();
        intent.putExtra("cityName", cityName);
        setResult(202, intent);
        finish();
    }


    public void gpsCity(View view) {
        switch (view.getId()) {
            case R.id.btn_gps_city_id:      // 重试按钮

                view.setVisibility(View.GONE); // 让按钮消失
                tv_gps_city_id.setText("正在定位城市...");     // 改变TextView上的文字
                startLocationService();     // 启动定位服务
                break;
            case R.id.ll_gps_city_id:
            case R.id.tv_gps_city_id:
                String cityName = tv_gps_city_id.getText().toString();
                if(!TextUtils.isEmpty(cityName)){
                    backTheCallActivity(cityName);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 启动定位服务
     */
    private void startLocationService() {
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
         * @see
         * com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu
         * .location.BDLocation)
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            // 标题
            //  String title = location.getCountry() + location.getCity()
            //              + location.getAddrStr();

            // 经纬度
            // double latitude = location.getLatitude();
            // double longitude = location.getLongitude();
            String city = location.getCity();
            if (city !=null && city.length() > 0) {
                tv_gps_city_id.setText(city);
            }else{
                tv_gps_city_id.setText("定位失败，请检查您的网络");
                btn_gps_city_id.setVisibility(View.VISIBLE);
            }
        }
    }

}
