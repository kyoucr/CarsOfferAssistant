package com.qinshou.administrator.carsofferassistant.depreciatefiled.network;

import android.os.AsyncTask;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;

/**
 * Created by 岩 on 2016/7/8.
 * 下载城市列表信息的异步任务
 */

public class DownLoadCitiesAsyncTask extends AsyncTask<String,Void,CitiesListInfoBean> {

    @Override
    protected CitiesListInfoBean doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(CitiesListInfoBean citiesListInfoBean) {
        super.onPostExecute(citiesListInfoBean);
    }
}
