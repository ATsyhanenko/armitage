package org.armitage.inc.AAInfo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TradingPackDto {
    private Integer packId;
    @NotNull
    @Pattern(regexp = "^[а-яА-Я -:]{5,50}$",message="Only cyrillic symbols allowed. Min. length - 5 symbols")
    private String title;
    @NotNull
    private Integer locationId;
    
    private String desc;
    
    public Integer getPackId() {
        return packId;
    }
    public void setPackId(Integer packId) {
        this.packId = packId;
    }
    
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getLocationId() {
        return locationId;
    }
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
    
    @Override
    public String toString() {
        return "TradingPackDto [title=" + title + ", locationId=" + locationId + "]";
    }
}
