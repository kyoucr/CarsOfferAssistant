package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.adapter.SelectModelsAdapter;
import com.qinshou.administrator.carsofferassistant.bean.Brand;
import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class SelectModelsTask extends AsyncTask {
    private Context context;
    private ExpandableListView select_models_elv;
    private CallBack callBack;

    public SelectModelsTask(Context context, ExpandableListView select_models_elv, CallBack callBack) {
        this.context = context;
        this.select_models_elv = select_models_elv;
        this.callBack = callBack;
    }

    @Override
    protected Map<String, List<Brand>> doInBackground(Object[] params) {
        InputStream selectModelsXml = DownloadXml.downloadInputStream((String) params[0]);
        return ParseXml.parseSelectModels(selectModelsXml);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Map<String, List<Brand>> map = (Map<String, List<Brand>>) o;
        List<String> brandAcronyms = new ArrayList<String>(map.keySet());
        callBack.getSelectModels(map, brandAcronyms);
        SelectModelsAdapter selectModelsAdapter = new SelectModelsAdapter(context, map, brandAcronyms);
        select_models_elv.setAdapter(selectModelsAdapter);
        select_models_elv.setGroupIndicator(null);
        select_models_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < selectModelsAdapter.getGroupCount(); i++) {
            select_models_elv.expandGroup(i);
        }
    }

    public interface CallBack {
        public void getSelectModels(Map<String, List<Brand>> map, List<String> list);
    }
}
