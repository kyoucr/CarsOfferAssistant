package com.qinshou.administrator.carsofferassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.CarDetailActivity;
import com.qinshou.administrator.carsofferassistant.bean.Brand;
import com.qinshou.administrator.carsofferassistant.bean.ModelsSeries;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.task.BrandDetailTask;
import com.qinshou.administrator.carsofferassistant.task.SelectModelsTask;
import com.qinshou.administrator.carsofferassistant.view.IndexOnRight;

import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.05
 */

public class SelectModelsFragment extends Fragment {
    private String selectModelsUrl = Urls.SELECT_MODELS;
    private String brandDetailUrl = Urls.BEFORE_MASTERID;
    private ExpandableListView select_models_brand_elv;
    private ExpandableListView brand_detail_elv;
    private DrawerLayout select_models_content_dl;
    private Map<String, List<Brand>> selectModelsMap;
    private Map<String, List<ModelsSeries>> brandDetailMap;
    private List<String> selectModelsList, brandDetailList;
    private IndexOnRight index_on_right_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_models, null);
        select_models_brand_elv = (ExpandableListView) view.findViewById(R.id.select_models_brand_elv);
        brand_detail_elv = (ExpandableListView) view.findViewById(R.id.brand_detail_elv);
        select_models_content_dl = (DrawerLayout) view.findViewById(R.id.select_models_content_dl);
        index_on_right_view = (IndexOnRight) view.findViewById(R.id.index_on_right_view);
        new SelectModelsTask(getActivity(), select_models_brand_elv, new SelectModelsTask.CallBack() {

            @Override
            public void getSelectModels(Map<String, List<Brand>> map, List<String> list) {
                selectModelsMap = map;
                selectModelsList = list;
            }
        }).execute(selectModelsUrl);
        select_models_brand_elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String masterId = selectModelsMap.get(selectModelsList.get(groupPosition)).get(childPosition).getBsID();
                new BrandDetailTask(getActivity(), brand_detail_elv, new BrandDetailTask.CallBack() {
                    @Override
                    public void getBrandDetail(Map<String, List<ModelsSeries>> map, List<String> list) {
                        brandDetailMap = map;
                        brandDetailList = list;
                    }

                }).execute(brandDetailUrl + masterId);
                select_models_content_dl.openDrawer(GravityCompat.START);
                return true;
            }
        });
        brand_detail_elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String serialId = brandDetailMap.get(brandDetailList.get(groupPosition)).get(childPosition).getCsID();
                Intent carDetailIntent = new Intent(getActivity(), CarDetailActivity.class);
                carDetailIntent.putExtra("serialId", serialId);
                startActivity(carDetailIntent);
                return true;
            }
        });
        index_on_right_view.setOnTouchingLetterChangedListener(new IndexOnRight.OnTouchingLetterChangedListener() {

            @Override
            public void OnTouchingLetterChanged(String string) {
                select_models_content_dl.closeDrawer(GravityCompat.START);
                int position = -1;
                if ("热".equals(string)) {
                    select_models_brand_elv.setSelectedGroup(0);
                } else {
                    for (String s : selectModelsList) {
                        if (s.equals(string)) {
                            position = selectModelsList.indexOf(s);
                            select_models_brand_elv.setSelectedGroup(position);
                        }
                    }
                }
            }
        });
        return view;
    }
}
