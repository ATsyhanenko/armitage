package org.armitage.inc.AAInfo.extra;

import java.text.DecimalFormat;

public class SalePrice {
    private int percent;
    private double price;
    private String formattedPrice;

    public SalePrice(double basePrice, int percent){
        calc(basePrice, percent);
    }
    
    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFormattedPrice() {
        formattedPrice = new DecimalFormat("##.##").format(price);
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public void calc(double basePrice, int percent){
        this.percent = percent;
        price = basePrice * ((double)percent / 100);
    }
}
