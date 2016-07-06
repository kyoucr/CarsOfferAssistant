package com.qinshou.administrator.carsofferassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.bean.Car;

import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */
public class SelectModelsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<Car>> map;
    private List<String> brandAcronyms;

    public SelectModelsAdapter(Context context, Map<String, List<Car>> map, List<String> brandAcronyms) {
        this.context = context;
        this.map = map;
        this.brandAcronyms = brandAcronyms;
    }

    @Override
    public int getGroupCount() {
        return brandAcronyms.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(brandAcronyms.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return brandAcronyms.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return map.get(brandAcronyms.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brand_acronym_layout, null);
        TextView group_tv = (TextView) view.findViewById(R.id.groupclassify_tv);
        group_tv.setText(brandAcronyms.get(groupPosition));
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brand_layout, null);
        ImageView car_brand_iv = (ImageView) view.findViewById(R.id.car_brand_iv);
        TextView car_brand_tv = (TextView) view.findViewById(R.id.car_brand_tv);
        Glide.with(context).load(map.get(brandAcronyms.get(groupPosition)).get(childPosition).getPic()).into(car_brand_iv);
        car_brand_tv.setText(map.get(brandAcronyms.get(groupPosition)).get(childPosition).getBsName());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
