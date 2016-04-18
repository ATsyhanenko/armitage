package org.armitage.inc.AAInfo.dao;

import java.util.List;

import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradingPackRepository extends JpaRepository<TradingPack,Integer>{
    @Query("Select p from TradingPack as p where p.location = :location")
    public List<TradingPack> findTradingPacksByLocation(@Param("location") Location location);
}
