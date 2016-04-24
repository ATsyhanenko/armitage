package org.armitage.inc.AAInfo.xml;

import org.armitage.inc.AAInfo.entity.SellingPrice;

public class SellingPriceXML {
    private Double price;
    private String locationName;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public SellingPriceXML(){}
    
    public SellingPriceXML(SellingPrice entity){
        price = entity.getPackPrice();
        locationName = entity.getLocation().getLocationName();
    }

}
