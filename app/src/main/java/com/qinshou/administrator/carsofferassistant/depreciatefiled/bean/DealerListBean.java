package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author 岩 7/10
 */
public class DealerListBean {
	private int total;			// 获取检索到的待售车辆的总条数
	private int currentPage;	// 当前的页数
	private int totalPage;		// 总页数
	private List<DealersBean> dealers = new LinkedList<DealersBean>();
								// 待售车辆集合
	public DealerListBean() {
		super();
	}
	
	public DealerListBean(int total,
			int currentPage, int totalPage,
			List<DealersBean> dealers) {
		super();
		this.total = total;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.dealers = dealers;
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

	public List<DealersBean> getDealers() {
		return dealers;
	}

	public void setDealers(List<DealersBean> dealers) {
		this.dealers = dealers;
	}
	
	


}
