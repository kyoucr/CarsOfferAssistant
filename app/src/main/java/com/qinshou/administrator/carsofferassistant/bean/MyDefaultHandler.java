/**
 *
 */
package com.qinshou.administrator.carsofferassistant.bean;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyDefaultHandler extends DefaultHandler {
    private List<Series> seriesList;
    private Series series;
    private String tmpQName;// 便签名
    private Series p;// 数据项

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @Override
    public void startDocument() throws SAXException {
        seriesList = new LinkedList<>();// 初始化容器
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        tmpQName = qName;

        if ("series".equals(qName)) {
            // 解析节点的属性
            series = new Series();
            parseNodeAttrbuites(attributes);
        }

    }

    private void parseNodeAttrbuites(Attributes attributes) {
        // 解析p节点的属性p_id
        for (int index = 0; index < attributes.getLength(); index++) {
            String attrName = attributes.getQName(index);
            String attrValue = attributes.getValue(index);

            if ("id".equals(attrName)) {
                series.setId(Integer.parseInt(attrValue));
            } else if ("price_range".equals(attrName)) {
                series.setPrice_range(attrValue);
            } else if ("name".equals(attrName)) {
                series.setName(attrValue);
            } else if ("pic".equals(attrName)) {
                series.setPic(attrValue);
            }
            seriesList.add(series);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tmpQName = null;// 将临时变量清空
    }
}
