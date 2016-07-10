package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 岩 on 2016/7/7.
 * 城市总列表信息实体类，里面包含省份。
 */

public class CitiesListInfoBean {
    private String care_city;   // 信息类型
    private String detal;       // 详情
    private int hasUpdate;      // 更新版本
    private String timeserivse; // 获取延时
    private String timestamp;   // 时间标记
    private List<ProvinceBean> provices = new LinkedList<>();    // 省份集合

    public CitiesListInfoBean() {
    }

    public CitiesListInfoBean(String care_city, String detal, int hasUpdate, String timeserivse, String timestamp, List<ProvinceBean> provices) {
        this.care_city = care_city;
        this.detal = detal;
        this.hasUpdate = hasUpdate;
        this.timeserivse = timeserivse;
        this.timestamp = timestamp;
        this.provices = provices;
    }

    public String getCare_city() {
        return care_city;
    }

    public void setCare_city(String care_city) {
        this.care_city = care_city;
    }

    public String getDetal() {
        return detal;
    }

    public void setDetal(String detal) {
        this.detal = detal;
    }

    public int getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(int hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public String getTimeserivse() {
        return timeserivse;
    }

    public void setTimeserivse(String timeserivse) {
        this.timeserivse = timeserivse;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<ProvinceBean> getProvices() {
        return provices;
    }

    public void setProvices(List<ProvinceBean> provices) {
        this.provices = provices;
    }
}
