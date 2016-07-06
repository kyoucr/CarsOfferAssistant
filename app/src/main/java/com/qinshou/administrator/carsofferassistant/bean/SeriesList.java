package com.qinshou.administrator.carsofferassistant.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyj on 2016/7/6.
 */

public class SeriesList {
    private int total;
    private int currentPage;
    private int totalPage;
    private List<Series> seriesList = new LinkedList<>();

    public SeriesList() {
    }

    public SeriesList(int total, int currentPage, int totalPage, List<Series> seriesList) {
        this.total = total;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.seriesList = seriesList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @Override
    public String toString() {
        return "SeriesList{" +
                "total=" + total +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", seriesList=" + seriesList +
                '}';
    }
}
