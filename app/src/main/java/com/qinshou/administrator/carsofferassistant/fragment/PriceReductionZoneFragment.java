package com.qinshou.administrator.carsofferassistant.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.MainActivity;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.activity.SelectCityActivity;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.fragment.TabDetalFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yan on 2016/7/6.
 * 降价专区主页面的fragment
 */

public class PriceReductionZoneFragment extends Fragment {

    private ViewPager mViewPager;       // ViewPager控件
    private RadioGroup mRadioGroup;     // 顶部选项栏按钮group控件

    private List<TabDetalFragment> fragments;

    private String cityName;        // 城市名字
    private String carType;         // 车型
    private String carSeriesId;        // 车型编号

    private SharedPreferences.Editor cityNameEditor;    // 城市名字偏好写入编辑器
    private SharedPreferences.Editor carTypeEditor;     // 车型偏好写入编辑器

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);    // 设置fragment的onCreateOptionsMenu菜单生效
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_depreciatefiled_main, container, false);// 推荐使用这种方法加载布局文件，能够继承布局文件的参数信息
        mViewPager = (ViewPager) view.findViewById(R.id.vp_id);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_id);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.main_tb);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();

        getMySharedPreferences();
        actionBar.setTitle(R.string.quotes_reduce_price);
        aboutViewPager();
        aboutRadioGroup();
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 获取偏好设置，用来显示用户选择过的城市、车型
     */
    private void getMySharedPreferences() {
        SharedPreferences sharedCityName = getActivity().getSharedPreferences("SelectCity", Context.MODE_PRIVATE);
        SharedPreferences shareCarType = getActivity().getSharedPreferences("SelectCarType", Context.MODE_PRIVATE);

        cityNameEditor = sharedCityName.edit();
        carTypeEditor = shareCarType.edit();

        cityName = sharedCityName.getString("cityName", "北京");
        carSeriesId = shareCarType.getString("carSeriesId", "0");
        carType = shareCarType.getString("carType", "全部车型");
    }

    /**
     * g关于ViewPager的相关操作
     */
    private void aboutViewPager() {
        fragments = new LinkedList<TabDetalFragment>();
        fragments.clear();
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            TabDetalFragment fragment = new TabDetalFragment();

            Bundle argument = new Bundle();

            argument.putString("cityName", cityName);
            argument.putString("carSeriesId", carSeriesId);
            argument.putInt("selectType", i + 1);

            fragment.setArguments(argument);
            fragments.add(fragment);
        }
        mViewPager.setAdapter(new MyViewPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * ViewPager页面改变的监听器
     */
    private final class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            //((RadioButton)mRadioGroup.getChildAt(position)).setChecked(true);
            ((RadioButton) mRadioGroup.getChildAt(position % fragments.size())).setChecked(true);
        }
    }

    /**
     * ViewPager的适配器
     */
    private final class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
            //return Integer.MAX_VALUE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
            //  return fragments.get(position%fragments.size());
        }
    }

    /**
     * 关于单选按钮的一些操作
     */
    private void aboutRadioGroup() {
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.rb_jiangfuzuida_id:
                        position = 0;
                        break;
                    case R.id.rb_zuixinfabu_id:
                        position = 1;
                        break;
                    case R.id.rb_jiagezuigao_id:
                        position = 2;
                        break;
                    case R.id.rb_jiagezuidi_id:
                        position = 3;
                        break;
                }
                mViewPager.setCurrentItem(position);
            }
        });
    }

    /**
     * 获取从activity返回的用户选择的城市或者车型的信息
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 202) {
            String resultCityName = data.getStringExtra("cityName");
            //Toast.makeText(getContext(),"返回值是："+str,Toast.LENGTH_SHORT).show();
            if (cityName != null && cityName.length() > 0) {
                cityNameEditor.putString("cityName", resultCityName).commit();// 将用户选择的城市名保存到本地
                this.cityName = resultCityName;
            }
        }else if(requestCode == 503 && resultCode == 505){
            String serialId = data.getStringExtra("serialId");
            String csShowName = data.getStringExtra("csShowName");
           // Toast.makeText(getActivity(),"返回l  ："+serialId+csShowName,Toast.LENGTH_SHORT).show();
            carType = csShowName;
            carSeriesId = serialId;

            carTypeEditor.putString("carSeriesId",carSeriesId).putString("carType",carType).commit();

        }
        getActivity().invalidateOptionsMenu();      // 通知菜单刷新，回调onPrepareOptionsMenu()函数
        aboutViewPager();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化菜单
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();       // 清除掉以前的菜单
        inflater.inflate(R.menu.deprecritafiled_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 根据点击的菜单选项，跳转到不同的activity获取相应的信息后返回
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_city_select_id:  // 选择城市菜单选项
                Intent intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.menu_cartype_select_id:   // 选择车型选项
                Intent intent2 = new Intent(getActivity(),MainActivity.class);
                intent2.putExtra("requestCode",503);
                startActivityForResult(intent2,503);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 刷新菜单，每次选择完城市、车型后，刷新菜单
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            switch (item.getItemId()) {
                case R.id.menu_city_select_id:  // 城市菜单选项
                    item.setTitle(cityName);
                    break;
                case R.id.menu_cartype_select_id:   // 车型选项
                    item.setTitle(carType);
                    break;
            }
        }
        super.onPrepareOptionsMenu(menu);
    }
}