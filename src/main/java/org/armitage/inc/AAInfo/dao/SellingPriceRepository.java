package org.armitage.inc.AAInfo.dao;

import java.util.List;

import org.armitage.inc.AAInfo.entity.SellingPrice;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellingPriceRepository extends JpaRepository<SellingPrice,Integer>{
    @Query("Select s from SellingPrice as s where s.pack= :pack order by s.packPrice desc")
    public List<SellingPrice> findByPack(@Param("pack") TradingPack pack);
}
