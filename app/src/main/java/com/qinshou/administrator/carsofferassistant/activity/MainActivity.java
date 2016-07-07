package com.qinshou.administrator.carsofferassistant.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.fragment.IndependentCarFragment;
import com.qinshou.administrator.carsofferassistant.fragment.MyGarageFragment;
import com.qinshou.administrator.carsofferassistant.fragment.PriceReductionZoneFragment;
import com.qinshou.administrator.carsofferassistant.fragment.SelectModelsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments;
    private SelectModelsFragment selectModelsFragment;
    private IndependentCarFragment independentCarFragment;
    private PriceReductionZoneFragment priceReductionZoneFragment;
    private MyGarageFragment myGarageFragment;
    private Toolbar main_tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        fragments = new ArrayList<Fragment>();
        selectModelsFragment = new SelectModelsFragment();
        independentCarFragment = new IndependentCarFragment();
        priceReductionZoneFragment = new PriceReductionZoneFragment();
        myGarageFragment = new MyGarageFragment();
        main_tb = (Toolbar) findViewById(R.id.main_tb);
        setSupportActionBar(main_tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void chooseFragment(View view) {
        switch (view.getId()) {
            case R.id.select_models_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, selectModelsFragment).commit();
                main_tb.setTitle("选择车型");
                break;
            case R.id.independent_car_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, independentCarFragment).commit();
                main_tb.setTitle("自主选车");
                break;
            case R.id.price_reduction_zone_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, priceReductionZoneFragment).commit();
                main_tb.setTitle("降价专区");
                break;
            case R.id.my_garage_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, myGarageFragment).commit();
                main_tb.setTitle("我的车库");
                break;
        }
    }
}
