package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.io.Serializable;

/**
 * Created by zyj on 2016/7/11.
 */

public class ReduceCar implements Serializable{
    private String id;
    private String carId;
    private String name;
    private String carYear;
    private String vendorPrice;
    private String promotePrice;
    private String reduce;
    private String csName;
    private String pic;
    private String csPic;

    public ReduceCar() {
    }

    public ReduceCar(String id, String carId, String name, String carYear, String vendorPrice, String promotePrice, String reduce, String csName, String pic, String csPic) {
        this.id = id;
        this.carId = carId;
        this.name = name;
        this.carYear = carYear;
        this.vendorPrice = vendorPrice;
        this.promotePrice = promotePrice;
        this.reduce = reduce;
        this.csName = csName;
        this.pic = pic;
        this.csPic = csPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(String vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCsPic() {
        return csPic;
    }

    public void setCsPic(String csPic) {
        this.csPic = csPic;
    }

    @Override
    public String toString() {
        return "ReduceCar{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", name='" + name + '\'' +
                ", carYear='" + carYear + '\'' +
                ", vendorPrice='" + vendorPrice + '\'' +
                ", promotePrice='" + promotePrice + '\'' +
                ", reduce='" + reduce + '\'' +
                ", csName='" + csName + '\'' +
                ", pic='" + pic + '\'' +
                ", csPic='" + csPic + '\'' +
                '}';
    }
}
