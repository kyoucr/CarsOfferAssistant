package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;

import com.qinshou.administrator.carsofferassistant.bean.Series;
import com.qinshou.administrator.carsofferassistant.inter.IndependenceDataCallback;
import com.qinshou.administrator.carsofferassistant.utils.XMLParserIndependenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by zyj on 2016/7/6.
 */

public class IndependenceAsyncTask extends AsyncTask<String, Void, InputStream> {
    private IndependenceDataCallback result;

    public IndependenceAsyncTask(IndependenceDataCallback result) {
        this.result = result;
    }

    @Override
    protected InputStream doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(InputStream is) {
        List<Series> seriesList = XMLParserIndependenceUtil.parserIndependence(is);
        result.callBack(seriesList);
    }
}
