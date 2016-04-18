package org.armitage.inc.AAInfo.dao;

import java.util.List;

import org.armitage.inc.AAInfo.entity.SellingPrices;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellingPricesRepository extends JpaRepository<SellingPrices,Integer>{
    @Query("Select s from SellingPrices as s where s.pack= :pack")
    public List<SellingPrices> findByPack(@Param("pack") TradingPack pack);
}
