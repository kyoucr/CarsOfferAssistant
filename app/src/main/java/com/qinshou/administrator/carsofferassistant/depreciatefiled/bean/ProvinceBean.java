package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 岩 on 2016/7/7.
 * 省份列表实体类
 */

public class ProvinceBean {
    private String provinceName;    // 省份名字
    private List<CityBean> cities = new LinkedList<>();  // 省份所包含的城市

    public ProvinceBean() {
    }

    public ProvinceBean(String provinceName, List<CityBean> cities) {
        this.provinceName = provinceName;
        this.cities = cities;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<CityBean> getCities() {
        return cities;
    }

    public void setCities(List<CityBean> cities) {
        this.cities = cities;
    }
}
