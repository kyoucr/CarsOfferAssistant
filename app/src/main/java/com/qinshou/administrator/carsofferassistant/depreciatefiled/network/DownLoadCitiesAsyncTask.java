package com.qinshou.administrator.carsofferassistant.depreciatefiled.network;

import android.os.AsyncTask;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.DownloadCitiesCallbackInter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ParseXml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 岩 on 2016/7/8.
 * 下载城市列表信息的异步任务
 */

public class DownLoadCitiesAsyncTask extends AsyncTask<String,Void,CitiesListInfoBean> {
    private DownloadCitiesCallbackInter callbackInter;

    public DownLoadCitiesAsyncTask(DownloadCitiesCallbackInter callbackInter){
        this.callbackInter = callbackInter;
    }

    @Override
    protected CitiesListInfoBean doInBackground(String... params) {
        CitiesListInfoBean citiesListInfoBean = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = conn.getInputStream();
                return ParseXml.parseCities(is);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(CitiesListInfoBean citiesListInfoBean) {
        callbackInter.hasDownLoadCities(citiesListInfoBean);
    }
}
