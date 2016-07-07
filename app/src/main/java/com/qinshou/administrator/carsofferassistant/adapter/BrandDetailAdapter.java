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
import com.qinshou.administrator.carsofferassistant.bean.ModelsSeries;

import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */
public class BrandDetailAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<ModelsSeries>> map;
    private List<String> seriesName;

    public BrandDetailAdapter(Context context, Map<String, List<ModelsSeries>> map, List<String> seriesName) {
        this.context = context;
        this.map = map;
        this.seriesName = seriesName;
    }

    @Override
    public int getGroupCount() {
        return seriesName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(seriesName.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return seriesName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return map.get(seriesName.get(groupPosition)).get(childPosition);
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
        View view = inflater.inflate(R.layout.series_name_layout, null);
        TextView group_tv = (TextView) view.findViewById(R.id.series_name_tv);
        group_tv.setText(seriesName.get(groupPosition));
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.series_layout, null);
        ImageView series_iv = (ImageView) view.findViewById(R.id.series_iv);
        TextView series_name_tv = (TextView) view.findViewById(R.id.series_name_tv);
        TextView series_prices_tv = (TextView) view.findViewById(R.id.series_prices_tv);
        Glide.with(context).load(map.get(seriesName.get(groupPosition)).get(childPosition).getPic()).into(series_iv);
        series_name_tv.setText(map.get(seriesName.get(groupPosition)).get(childPosition).getCsShowName());
        series_prices_tv.setText(map.get(seriesName.get(groupPosition)).get(childPosition).getPrice_range() + "万");
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
