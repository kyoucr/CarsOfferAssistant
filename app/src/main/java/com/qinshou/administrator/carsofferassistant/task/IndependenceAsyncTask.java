package com.qinshou.administrator.carsofferassistant.task;

import android.app.ProgressDialog;
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

public class IndependenceAsyncTask extends AsyncTask<String, Void, List<Series>> {
    private IndependenceDataCallback result;
    private ProgressDialog dialog;

    public IndependenceAsyncTask(IndependenceDataCallback result,ProgressDialog dialog) {
        this.result = result;
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected List<Series> doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                return XMLParserIndependenceUtil.parserIndependence(is);
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
    protected void onPostExecute(List<Series> seriesList) {
        result.callBack(seriesList);
        dialog.dismiss();
    }
}
