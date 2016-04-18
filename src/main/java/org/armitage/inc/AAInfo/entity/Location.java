package org.armitage.inc.AAInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "l_id")
    private Integer locationId;
    
    @Column(name="l_name", nullable=false, unique = true)
    private String locationName;
    
    public Location(String locationName, Integer isMerchant) {
        this.locationName = locationName;
        this.isMerchant = isMerchant;
    }

    public Location() {
    }

    @Column(name="l_is_manufacturer", nullable = false)
    private Integer isManufacturer = 1;
    
    @Column(name="l_is_merchant", nullable = false)
    private Integer isMerchant;//is Merchant

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getIsManufacturer() {
        return isManufacturer;
    }

    public void setIsManufacturer(Integer isManufacturer) {
        this.isManufacturer = isManufacturer;
    }

    public Integer getisMerchant() {
        return isMerchant;
    }

    public void setisMerchant(Integer isMerchant) {
        this.isMerchant = isMerchant;
    }

    @Override
    public String toString() {
        return "Location [locationId=" + locationId + ", locationName=" + locationName
                + ", isManufacturer=" + isManufacturer + ", isKnacker=" + isMerchant + "]";
    }
}
