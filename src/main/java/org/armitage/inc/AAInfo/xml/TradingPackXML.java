package org.armitage.inc.AAInfo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.armitage.inc.AAInfo.entity.TradingPack;

@XmlType(propOrder = { "title", "id", "desc", "sellingPrices" }, name = "tradingPack")
public class TradingPackXML {
    private Integer id;
    private String title;
    private String desc;
    private List<SellingPriceXML> sellingPrices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "packTitle")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlElement(name = "description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlElementWrapper(name = "sellingPriceList")
    @XmlElement(name = "sellingPrice")
    public List<SellingPriceXML> getSellingPrices() {
        return sellingPrices;
    }

    public void setSellingPrices(List<SellingPriceXML> sellingPrices) {
        this.sellingPrices = sellingPrices;
    }

    public TradingPackXML() {}

    public TradingPackXML(TradingPack entity) {
        id = entity.getPackId();
        title = entity.getPackName();
        desc = entity.getDesc();
        desc = desc.replaceAll("\\<.+?\\>", " ");
        desc = desc.replaceAll("\r\n", "");
    }

}
