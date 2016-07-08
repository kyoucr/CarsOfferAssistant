package com.qinshou.administrator.carsofferassistant.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.fragment.IndependentCarFragment;
import com.qinshou.administrator.carsofferassistant.fragment.MyGarageFragment;
import com.qinshou.administrator.carsofferassistant.fragment.PriceReductionZoneFragment;
import com.qinshou.administrator.carsofferassistant.fragment.SelectModelsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments;
    private SelectModelsFragment selectModelsFragment;
    private IndependentCarFragment independentCarFragment;
    private PriceReductionZoneFragment priceReductionZoneFragment;
    private MyGarageFragment myGarageFragment;
    private Button select_models_bt, independent_car_bt, price_reduction_zone_bt, my_garage_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<Fragment>();
        selectModelsFragment = new SelectModelsFragment();
        independentCarFragment = new IndependentCarFragment();
        priceReductionZoneFragment = new PriceReductionZoneFragment();
        myGarageFragment = new MyGarageFragment();
        select_models_bt = (Button) findViewById(R.id.select_models_bt);//选择车型按钮
        independent_car_bt = (Button) findViewById(R.id.independent_car_bt);//自主选车按钮
        price_reduction_zone_bt = (Button) findViewById(R.id.price_reduction_zone_bt);//降价专区按钮
        my_garage_bt = (Button) findViewById(R.id.my_garage_bt);//我的车库按钮
        getFragmentManager().beginTransaction().replace(R.id.content_fm, selectModelsFragment).commit();
    }

    public void chooseFragment(View view) {
        switch (view.getId()) {
            case R.id.select_models_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, selectModelsFragment).commit();
                select_models_bt.setEnabled(false);
                independent_car_bt.setEnabled(true);
                price_reduction_zone_bt.setEnabled(true);
                my_garage_bt.setEnabled(true);
                break;
            case R.id.independent_car_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, independentCarFragment).commit();
                select_models_bt.setEnabled(true);
                independent_car_bt.setEnabled(false);
                price_reduction_zone_bt.setEnabled(true);
                my_garage_bt.setEnabled(true);
                break;
            case R.id.price_reduction_zone_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, priceReductionZoneFragment).commit();
                select_models_bt.setEnabled(true);
                independent_car_bt.setEnabled(true);
                price_reduction_zone_bt.setEnabled(false);
                my_garage_bt.setEnabled(true);
                break;
            case R.id.my_garage_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, myGarageFragment).commit();
                select_models_bt.setEnabled(true);
                independent_car_bt.setEnabled(true);
                price_reduction_zone_bt.setEnabled(true);
                my_garage_bt.setEnabled(false);
                break;
        }
    }
}
