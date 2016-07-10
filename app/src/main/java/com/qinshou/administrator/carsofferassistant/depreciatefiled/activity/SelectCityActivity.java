package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        aboutActionBar();
        aboutAutoCompleteTextView();
        aboutExpandableListView();

        new DownLoadCitiesAsyncTask(this).execute(Urls.CITY_lIST_SELECT);   // 开启异步任务下载数据源

    }

    /**
     * 关于自动填充文本的操作
     */
    private void  aboutAutoCompleteTextView(){
        actv_search_city_id = (AutoCompleteTextView) findViewById(R.id.actv_search_city_id);
        cityNames = new LinkedList<>();  // 初始化数据源
        actvAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,cityNames);
        actv_search_city_id.setAdapter(actvAdapter);
        actv_search_city_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view instanceof TextView){
                    String cityName = ((TextView) view).getText().toString();
                    backTheCallActivity(cityName);
                }else{
                    backTheCallActivity("");
                }
            }
        });
    }

    /**
     * 更新AutoCompleteTextView的数据源
     */
    private void updateAutoCompleteTextView(){
        cityNames.clear();
        if(datas!=null && datas.getProvices().size()>0){
            for(ProvinceBean provinces:datas.getProvices()){
                if(provinces!=null && provinces.getCities().size()>0){
                    for(CityBean cityBean:provinces.getCities()){
                        if(cityBean!=null){
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
    private void aboutExpandableListView(){
        datas = new CitiesListInfoBean();    // 初始化数据源
        elv_cities_id = (ExpandableListView) findViewById(R.id.elv_cities_id);
        LinearLayout elv_empty_id = (LinearLayout) findViewById(R.id.elv_empty_id);
        elv_cities_id.setEmptyView(elv_empty_id);

        adapter = new ExpandableListAdapterForCitiesList(datas,this);
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
    private void aboutActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.select_city_tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);  // 决定左上角图标的右侧是否有向左的小箭头, true
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){    // 左上角的箭头
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 展开ExpandableListView的每一项
     */
    private void expandEveryItem(){
        if(datas!=null && datas.getProvices().size()>0){
            for(int i=0;i<datas.getProvices().size();i++){
                elv_cities_id.expandGroup(i);
            }
        }
    }

    /**
     * 从网络上下载城市列表后更新页面
     * @param citiesListInfoBean
     */
    @Override
    public void hasDownLoadCities(CitiesListInfoBean citiesListInfoBean) {
        if(citiesListInfoBean==null){   // 从网络获取数据异常的时候，就从本地资产目录加载。
            try {
                InputStream is = getAssets().open("wz_rule.xml");
                if(is!=null){
                    citiesListInfoBean = ParseXml.parseCities(is);
                }else{
                    Toast.makeText(this,"资产目录不存在",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.datas = citiesListInfoBean;
        adapter = new ExpandableListAdapterForCitiesList(datas,this);
        elv_cities_id.setAdapter(adapter);  // ExpandableListView控件的notifyDataSetChanged()方法不能使用，重新绑定适配器
        expandEveryItem();                  // 展开每一个选项
        updateAutoCompleteTextView();       //  更新自动填充框的数据源
    }

    /**
     * 返回启动该Activity的页面，并返回所选中的城市名
     * @param cityName  城市名
     */
    public void backTheCallActivity(String cityName){
        Intent intent = new Intent();
        intent.putExtra("cityName",cityName);
        setResult(202,intent);
        finish();
    }

}
