package com.qinshou.administrator.carsofferassistant.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<Fragment>();
        selectModelsFragment = new SelectModelsFragment();
        independentCarFragment = new IndependentCarFragment();
        priceReductionZoneFragment = new PriceReductionZoneFragment();
        myGarageFragment = new MyGarageFragment();
    }

    public void chooseFragment(View view) {
        switch (view.getId()) {
            case R.id.select_models_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, selectModelsFragment).commit();
                break;
            case R.id.independent_car_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, independentCarFragment).commit();
                break;
            case R.id.price_reduction_zone_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, priceReductionZoneFragment).commit();
                break;
            case R.id.my_garage_bt:
                getFragmentManager().beginTransaction().replace(R.id.content_fm, myGarageFragment).commit();
                break;
        }
    }
}
