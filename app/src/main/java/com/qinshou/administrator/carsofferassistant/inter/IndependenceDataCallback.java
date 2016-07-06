package com.qinshou.administrator.carsofferassistant.inter;

import com.qinshou.administrator.carsofferassistant.bean.Series;

import java.util.List;

/**
 * Created by zyj on 2016/7/6.
 */

public interface IndependenceDataCallback {
    void callBack(List<Series> seriesList);
}
