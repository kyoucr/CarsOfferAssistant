package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.adapter.BrandDetailAdapter;
import com.qinshou.administrator.carsofferassistant.bean.ModelsSeries;
import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class BrandDetailTask extends AsyncTask {
    private Context context;
    private ExpandableListView brand_detail_elv;
    private CallBack callBack;

    public BrandDetailTask(Context context, ExpandableListView brand_detail_elv, CallBack callBack) {
        this.context = context;
        this.brand_detail_elv = brand_detail_elv;
        this.callBack = callBack;
    }

    @Override
    protected Map<String, List<ModelsSeries>> doInBackground(Object... params) {
        InputStream brandDetailXml = DownloadXml.downloadInputStream((String) params[0]);
        return ParseXml.parseBrandDetail(brandDetailXml);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Map<String, List<ModelsSeries>> map = (Map<String, List<ModelsSeries>>) o;
        List<String> seriesName = new ArrayList<String>(map.keySet());
        callBack.getBrandDetail(map, seriesName);
        BrandDetailAdapter brandDetailAdapter = new BrandDetailAdapter(context, map, seriesName);
        brand_detail_elv.setAdapter(brandDetailAdapter);
        brand_detail_elv.setGroupIndicator(null);
        brand_detail_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < brandDetailAdapter.getGroupCount(); i++) {
            brand_detail_elv.expandGroup(i);
        }
    }

    public interface CallBack {
        public void getBrandDetail(Map<String, List<ModelsSeries>> map, List<String> list);
    }
}
