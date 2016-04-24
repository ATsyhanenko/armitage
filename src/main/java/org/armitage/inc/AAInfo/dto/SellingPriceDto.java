package org.armitage.inc.AAInfo.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SellingPriceDto {
    @NotNull
    private Integer location;
    @NotNull
    @Valid
    private Double price;
    @NotNull
    private Integer pack;
    private Integer priceId;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPack() {
        return pack;
    }

    public void setPack(Integer pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "SellingPriceDto [location=" + location + ", price=" + price + ", pack=" + pack
                + ", priceId=" + priceId + "]";
    }

   


}
