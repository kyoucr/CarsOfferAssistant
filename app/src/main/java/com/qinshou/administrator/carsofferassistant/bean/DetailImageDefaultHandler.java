/**
 * 解析器实例
 */
package com.qinshou.administrator.carsofferassistant.bean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;


public class DetailImageDefaultHandler extends DefaultHandler {
    private List<String> imagesUrls = new LinkedList<>();

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ("image".equals(qName)) {
            // 解析节点的属性
            for (int index = 0; index < attributes.getLength(); index++) {
                String attrName = attributes.getQName(index);
                String attrValue = attributes.getValue(index);

                if ("url".equals(attrName)) {
                    imagesUrls.add(attrValue);
                }
            }
        }
    }
}
