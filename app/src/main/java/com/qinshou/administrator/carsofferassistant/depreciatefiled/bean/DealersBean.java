package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.io.Serializable;

/**
 * 待售车辆类
 * @author 岩 7/10
 *
 */
public class DealersBean implements Serializable{
	
	private String dealerType;		// 销售类型
	private String csName;			// 车型名字
	private String id;				// Id编号
	private String dealerId;		// 销售编号
	private String carId;			// 车辆编号
	private String csPic;			// 车辆图片url
	private String name;			// 车名字
	private String vendorPrice;		// 售价
	private String dealerName;		// 销售店名字
	private String carYear;			// 生产年份
	private String pic;				// 车辆图片url
	private String reduce;			// 所降金额
	private String saleRange;		// 销售范围
	private String promotePrice;	// 促销价
	
	public DealersBean() {
		super();
	}

	public DealersBean(String dealerType, String csName, String id,
			String dealerId, String carId, String csPic, String name,
			String vendorPrice, String dealerName, String carYear, String pic,
			String reduce, String saleRange, String promotePrice) {
		super();
		this.dealerType = dealerType;
		this.csName = csName;
		this.id = id;
		this.dealerId = dealerId;
		this.carId = carId;
		this.csPic = csPic;
		this.name = name;
		this.vendorPrice = vendorPrice;
		this.dealerName = dealerName;
		this.carYear = carYear;
		this.pic = pic;
		this.reduce = reduce;
		this.saleRange = saleRange;
		this.promotePrice = promotePrice;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
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

	public String getCsPic() {
		return csPic;
	}

	public void setCsPic(String csPic) {
		this.csPic = csPic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(String vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getReduce() {
		return reduce;
	}

	public void setReduce(String reduce) {
		this.reduce = reduce;
	}

	public String getSaleRange() {
		return saleRange;
	}

	public void setSaleRange(String saleRange) {
		this.saleRange = saleRange;
	}

	public String getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(String promotePrice) {
		this.promotePrice = promotePrice;
	}
	
	
	
	
	
	
	
	
	

}
