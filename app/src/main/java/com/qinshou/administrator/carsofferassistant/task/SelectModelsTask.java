package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

import java.io.InputStream;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class SelectModelsTask extends AsyncTask {
    private Context context;
    private ListView listView;

    public SelectModelsTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        InputStream selectModelsXml = DownloadXml.downloadSelectModels((String) params[0]);
        return ParseXml.parseSelectModels(selectModelsXml);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
