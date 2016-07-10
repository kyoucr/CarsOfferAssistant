package com.qinshou.administrator.carsofferassistant.depreciatefiled.util;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CityBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ProvinceBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * @author 岩 by 2016/7/8
 * 解析xml数据的工具类
 */

public class ParseXml {
	/**
	 * 解析城市
	 * @param is	从网络获取城市的信息流
	 * @return		返回城市信息列表CitiesListInfoBean类
     */
	public static CitiesListInfoBean parseCities(InputStream is){
		CitiesListInfoBean infos = null;
		InputStreamReader isr = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();// 解析器工厂实例的构建
			XmlPullParser parser = factory.newPullParser();	// 解析器实例的构建
			
			isr = new InputStreamReader(is);
			parser.setInput(isr);

			ProvinceBean province = null;
			CityBean city = null;
			
			String tagName = null;
			int eventType = parser.getEventType();
			
			EXIT_FLG:while(true){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					infos = new CitiesListInfoBean();
					break;
				case XmlPullParser.END_DOCUMENT:
					break EXIT_FLG;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if("info".equals(tagName)){
						infos.setProvices(new LinkedList<ProvinceBean>());
					}else if("province".equals(tagName)){
						province = new ProvinceBean();
						province.setCities(new LinkedList<CityBean>());
						int count = parser.getAttributeCount();
						for(int i=0;i<count;i++){
							String attributeName = parser.getAttributeName(i);
							String attributeValue = parser.getAttributeValue(i);
							if("name".equals(attributeName)){
								province.setProvinceName(attributeValue);
							}
						}
						
					}else if("city".equals(tagName)){
						city = new CityBean();
						int count = parser.getAttributeCount();
						for(int i=0;i<count;i++){
							String attributeName = parser.getAttributeName(i);
							String attributeValue = parser.getAttributeValue(i);
							if("name".equals(attributeName)){
								city.setcityName(attributeValue);
							}else if("code".equals(attributeName)){
								city.setCityCode(attributeValue);
							}else if("py".equals(attributeName)){
								city.setCityPY(attributeValue);
							}
						}
					}
					
					break;
				case XmlPullParser.END_TAG:
					String tagEndName = parser.getName();
					if("info".equals(tagEndName)){
						
					}else if("province".equals(tagEndName)){
						infos.getProvices().add(province);
					}else if("city".equals(tagEndName)){
						province.getCities().add(city);
					}
					tagName = null;
					break;
				case XmlPullParser.TEXT:
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return infos;
	}


	/**
	 * 解析城市
	 * @param is	从网络获取销售汽车的信息流
	 * @return		返回DealerListBean类
	 */
	public static DealerListBean parseDealers(InputStream is){
		DealerListBean dealerListBean = null;
		InputStreamReader isr = null;

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();// 解析器工厂实例的构建
			XmlPullParser parser = factory.newPullParser();	// 解析器实例的构建

			isr = new InputStreamReader(is);
			parser.setInput(isr);

			DealersBean dealerBean = null;

			String tagName = null;
			int eventType = parser.getEventType();

			EXIT_FLG:while(true){
				switch(eventType){
					case XmlPullParser.START_DOCUMENT:
						dealerListBean = new DealerListBean();
						break;
					case XmlPullParser.END_DOCUMENT:
						break EXIT_FLG;
					case XmlPullParser.START_TAG:
						tagName = parser.getName();
						if("dealer_list".equals(tagName)){
							int count = parser.getAttributeCount();
							for(int i=0;i<count;i++){
								String attributeName = parser.getAttributeName(i);
								String attributeValue = parser.getAttributeValue(i).trim();
								int number = 0;
								if(attributeValue!=null && attributeValue.length()>0){
									number = Integer.parseInt(attributeValue);
								}
								if("total".equals(attributeName)){
									dealerListBean.setTotal(number);
								}else if("currentPage".equals(attributeName)){
									dealerListBean.setCurrentPage(number);
								}else if("totalPage".equals(attributeName)){
									dealerListBean.setTotalPage(number);
								}

							}
							dealerListBean.setDealers(new LinkedList<DealersBean>());
						}else if("dealers".equals(tagName)){
							dealerBean = new DealersBean();
							int count = parser.getAttributeCount();
							for(int i=0;i<count;i++){
								String attributeName = parser.getAttributeName(i);
								String attributeValue = parser.getAttributeValue(i);
								if("dealerType".equals(attributeName)){
									dealerBean.setDealerType(attributeValue);
								}else if("csName".equals(attributeName)){
									dealerBean.setCsName(attributeValue);
								}else if("id".equals(attributeName)){
									dealerBean.setId(attributeValue);
								}else if("dealerId".equals(attributeName)){
									dealerBean.setDealerId(attributeValue);
								}else if("carId".equals(attributeName)){
									dealerBean.setCarId(attributeValue);
								}else if("csPic".equals(attributeName)){
									dealerBean.setCsPic(attributeValue);
								}else if("name".equals(attributeName)){
									dealerBean.setName(attributeValue);
								}else if("vendorPrice".equals(attributeName)){
									dealerBean.setVendorPrice(attributeValue);
								}else if("dealerName".equals(attributeName)){
									dealerBean.setDealerName(attributeValue);
								}else if("carYear".equals(attributeName)){
									dealerBean.setCarYear(attributeValue);
								}else if("pic".equals(attributeName)){
									dealerBean.setPic(attributeValue);
								}else if("reduce".equals(attributeName)){
									dealerBean.setReduce(attributeValue);
								}else if("saleRange".equals(attributeName)){
									dealerBean.setSaleRange(attributeValue);
								}else if("promotePrice".equals(attributeName)){
									dealerBean.setPromotePrice(attributeValue);
								}
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String tagEndName = parser.getName();
						if("dealer_list".equals(tagEndName)){

						}else if("dealers".equals(tagEndName)){
							dealerListBean.getDealers().add(dealerBean);
						}
						tagName = null;
						break;
					case XmlPullParser.TEXT:
						break;
					default:
						break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dealerListBean;
	}




}
