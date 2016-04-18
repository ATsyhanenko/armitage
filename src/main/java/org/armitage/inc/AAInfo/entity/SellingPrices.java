package org.armitage.inc.AAInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "selling_prices")
public class SellingPrices {
    @Id
    @GeneratedValue
    @Column(name = "s_id")
    private Integer priceId;

    @ManyToOne
    @JoinColumn(name = "s_tradepack")
    private TradingPack pack;

    @Column(name = "s_price")
    private Double packPrice = 0.00;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public TradingPack getPack() {
        return pack;
    }

    public void setPack(TradingPack pack) {
        this.pack = pack;
    }

    public Double getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(Double packPrice) {
        this.packPrice = packPrice;
    }

    @Override
    public String toString() {
        return "SellingPrices [priceId=" + priceId + ", pack=" + pack + ", packPrice=" + packPrice
                + "]";
    }

}
