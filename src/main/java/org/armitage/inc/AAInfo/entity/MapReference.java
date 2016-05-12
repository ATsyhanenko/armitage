package org.armitage.inc.AAInfo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class MapReference {
    @Column(name="l_posX")
    @ColumnDefault("0")
    private Integer posX = 0;
    
    @Column(name="l_posY")
    @ColumnDefault("0")
    private Integer posY = 0;
    
    public MapReference() {
    }

    public MapReference(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
}
