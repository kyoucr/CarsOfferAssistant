package com.qinshou.administrator.carsofferassistant.depreciatefiled.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.activity.SelectCityActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yan on 2016/7/6.
 * 降价专区主页面的fragment
 */

public class DepreciatefiledMainFragment extends Fragment {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;

    private List<TabDetalFragment> fragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);    // 设置fragment的onCreateOptionsMenu菜单生效
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View view = inflater.inflate( R.layout.fragment_depreciatefiled_main,null);
        // 上述方法不好，不推荐使用

        View view = inflater.inflate(R.layout.fragment_depreciatefiled_main, container, false);// 推荐使用这种方法加载布局文件，能够继承布局文件的参数信息

        mViewPager = (ViewPager) view.findViewById(R.id.vp_id);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_id);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //actionBar.setTitle("降价专区");
        actionBar.setTitle(R.string.quotes_reduce_price);
        aboutViewPager();
        aboutRadioGroup();

        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);

        super.onActivityCreated(savedInstanceState);

    }

    /**
     * g关于ViewPager的相关操作
     */
    private void aboutViewPager() {
        fragments = new LinkedList<TabDetalFragment>();
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            TabDetalFragment fragment = new TabDetalFragment();

            Bundle argument = new Bundle();
            argument.putString("tabName", ((RadioButton) mRadioGroup.getChildAt(i)).getText().toString());
            fragment.setArguments(argument);
            fragments.add(fragment);

        }

       // mViewPager.setAdapter(new MyViewPagerAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * ViewPager页面改变的监听器
     */
    private final class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            //((RadioButton)mRadioGroup.getChildAt(position)).setChecked(true);
            ((RadioButton)mRadioGroup.getChildAt(position%fragments.size())).setChecked(true);
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
           // return fragments.get(position);
            return fragments.get(position%fragments.size());
        }
    }

    /**
     * 关于单选按钮的一些操作
     */
    private void aboutRadioGroup() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch(checkedId){
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.deprecritafiled_main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 根据点击的菜单选项，跳转到不同的activity获取相应的信息后返回
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menu_city_select_id:  // 选择城市菜单选项
                Intent intent = new Intent(getActivity(),SelectCityActivity.class);
                startActivityForResult(intent,101);
               // Toast.makeText(getContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_cartype_select_id:   // 选择车型选项
                Toast.makeText(getContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取从activity返回的用户选择的城市或者车型的信息
     * @param requestCode   请求码
     * @param resultCode    返回码
     * @param data          返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101 && resultCode == 202){
            String str = data.getStringExtra("values");
            Toast.makeText(getContext(),"返回值是："+str,Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
