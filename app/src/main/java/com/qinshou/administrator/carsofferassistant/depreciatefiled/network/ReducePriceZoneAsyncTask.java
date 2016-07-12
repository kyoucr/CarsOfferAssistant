package com.qinshou.administrator.carsofferassistant.depreciatefiled.network;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter.ReduceZoneAdapter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.TestInterface;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ParseXml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zyj on 2016/7/10.
 */

public class ReducePriceZoneAsyncTask extends AsyncTask<String, Void, DealerListBean> {
    private TestInterface data;
    private ProgressDialog dialog;

    public ReducePriceZoneAsyncTask(TestInterface data, ProgressDialog dialog) {
        this.data = data;
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected DealerListBean doInBackground(String... params) {

       /* try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(params[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            InputStream result = response.body().byteStream();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return ParseXml.parseDealers(inputStream);
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
    protected void onPostExecute(DealerListBean dealerListBean) {
        data.textCallBack(dealerListBean);
        dialog.dismiss();
        super.onPostExecute(dealerListBean);
    }
}
