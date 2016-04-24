package org.armitage.inc.AAInfo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.armitage.inc.AAInfo.entity.Location;

@XmlType(propOrder = { "name", "id", "isManufacturer", "isMerchant", "tradingPacks" }, name = "location")
public class LocationXML {
    private Integer id;
    private String name;
    private Boolean isMerchant;
    private Boolean isManufacturer;

    private List<TradingPackXML> tradingPacks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(Boolean isMerchant) {
        this.isMerchant = isMerchant;
    }

    public Boolean getIsManufacturer() {
        return isManufacturer;
    }

    public void setIsManufacturer(Boolean isManufacturer) {
        this.isManufacturer = isManufacturer;
    }
    @XmlElementWrapper(name="tradingPackList")
    @XmlElement(name="tradingPack")
    public List<TradingPackXML> getTradingPacks() {
        return tradingPacks;
    }

    public void setTradingPacks(List<TradingPackXML> tradingPacks) {
        this.tradingPacks = tradingPacks;
    }

    public LocationXML() {
    }
    
    public LocationXML(Location entity){
        id = entity.getLocationId();
        name = entity.getLocationName();
        isManufacturer = (entity.getIsManufacturer() == 1) ? true : false;
        isMerchant = (entity.getIsMerchant() == 1) ? true : false;
    }
}
