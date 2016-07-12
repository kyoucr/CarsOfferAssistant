package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyj on 2016/7/11.
 */

public class ListReduceCar implements Serializable {
    private String csName;
    private String id;
    private String dealerId;
    private String carId;
    private String carName;
    private String dealerName;
    private String original_price;
    private String new_price;
    private String carYear;
    private String reduce;
    private String range;
    private String pic;
    private String last_day;
    private String acivityDate;
    private String shopAddress;
    private String shopLat;
    private String shopLng;
    private String csPic;
    List<ReduceCar> listCars = new LinkedList<>();

    public ListReduceCar() {
    }

    public ListReduceCar(String csName, String id, String dealerId, String carId, String carName, String dealerName, String original_price, String new_price, String carYear, String reduce, String range, String pic, String last_day, String acivityDate, String shopAddress, String shopLat, String shopLng, String csPic, List<ReduceCar> listCars) {
        this.csName = csName;
        this.id = id;
        this.dealerId = dealerId;
        this.carId = carId;
        this.carName = carName;
        this.dealerName = dealerName;
        this.original_price = original_price;
        this.new_price = new_price;
        this.carYear = carYear;
        this.reduce = reduce;
        this.range = range;
        this.pic = pic;
        this.last_day = last_day;
        this.acivityDate = acivityDate;
        this.shopAddress = shopAddress;
        this.shopLat = shopLat;
        this.shopLng = shopLng;
        this.csPic = csPic;
        this.listCars = listCars;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLast_day() {
        return last_day;
    }

    public void setLast_day(String last_day) {
        this.last_day = last_day;
    }

    public String getAcivityDate() {
        return acivityDate;
    }

    public void setAcivityDate(String acivityDate) {
        this.acivityDate = acivityDate;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng;
    }

    public String getCsPic() {
        return csPic;
    }

    public void setCsPic(String csPic) {
        this.csPic = csPic;
    }

    public List<ReduceCar> getListCars() {
        return listCars;
    }

    public void setListCars(List<ReduceCar> listCars) {
        this.listCars = listCars;
    }
}
