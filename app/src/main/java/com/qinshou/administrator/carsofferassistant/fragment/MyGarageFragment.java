package com.qinshou.administrator.carsofferassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.qinshou.administrator.carsofferassistant.R;

/**
 * Created by 禽兽先生 on 2016.07.05
 */

public class MyGarageFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        aboutActionBar();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 关于ActionBar的操作
     */
    private void aboutActionBar(){
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.main_tb);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("我的车库");
    }

}
