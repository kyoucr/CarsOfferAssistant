package com.qinshou.administrator.carsofferassistant.depreciatefiled.util;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CityBean;
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
}
