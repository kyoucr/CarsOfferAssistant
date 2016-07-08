package com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;

/**
 * Created by 岩 on 2016/7/8.
 * 关于显示城市列表的ExpandableListView的适配器
 */

public class ExpandableListAdapterForCitiesList extends BaseExpandableListAdapter {
    private CitiesListInfoBean infos;
    private Context context;

    public ExpandableListAdapterForCitiesList(CitiesListInfoBean infos,Context context){
        this.infos = infos;
        this.context = context;
    }


    @Override
    public int getGroupCount() {
        return infos.getProvices().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return infos.getProvices().get(groupPosition).getCities().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return infos.getProvices().get(groupPosition).getProvinceName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return infos.getProvices().get(groupPosition).getCities().get(childPosition);
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
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gvh = null;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.brand_province_item,null);
            gvh = new GroupViewHolder();
            gvh.elv_tv_group_id = (TextView) convertView.findViewById(R.id.elv_tv_group_id);
            convertView.setTag(gvh);
        }else{
            gvh = (GroupViewHolder) convertView.getTag();
        }
        gvh.elv_tv_group_id.setText(infos.getProvices().get(groupPosition).getProvinceName());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SonViewHolder svh = null;
        if(convertView==null){
            convertView = View.inflate(context,R.layout.brand_citiesname_item,null);
            svh = new SonViewHolder();
            svh.elv_tv_son_id = (TextView) convertView.findViewById(R.id.elv_tv_son_id);
            convertView.setTag(svh);
        }else{
            svh = (SonViewHolder) convertView.getTag();
        }
        svh.elv_tv_son_id.setText(infos.getProvices().get(groupPosition).getCities().get(childPosition).getcityName());

        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private final class GroupViewHolder{
        private TextView elv_tv_group_id;
    }
    private final class SonViewHolder{
        private TextView elv_tv_son_id;
    }
}
