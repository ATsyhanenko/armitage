package org.armitage.inc.AAInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "trading_packs")
public class TradingPack {
    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Integer packId;
    
    @Column(name="p_name", nullable = false, length=50)
    private String packName;
    
    @ManyToOne
    @JoinColumn(name="p_location", nullable=false)
    private Location location;
    
    @Column(name="p_desc", length=256)
    @Type(type="text")
    private String p_desc;


    public String getDesc() {
        return p_desc;
    }


    public void setDesc(String p_desc) {
        this.p_desc = p_desc;
    }


    public Integer getPackId() {
        return packId;
    }


    public void setPackId(Integer packId) {
        this.packId = packId;
    }


    public String getPackName() {
        return packName;
    }


    public void setPackName(String packName) {
        this.packName = packName;
    }


    public Location getLocation() {
        return location;
    }


    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object arg){
        if(arg instanceof TradingPack){
            TradingPack comp = (TradingPack)arg;
            return (comp.packId == this.packId);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "TradingPack [packId=" + packId + ", packName=" + packName + ", location=" + location
                + "]";
    }
}
