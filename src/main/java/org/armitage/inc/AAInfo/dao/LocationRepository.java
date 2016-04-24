package org.armitage.inc.AAInfo.dao;

import java.util.List;

import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location,Integer> {
    @Query(value = "SELECT * FROM locations as l left join selling_prices as s on s.s_location = l.l_id  and s_tradepack = :pack where s_location is null and l_is_merchant = 1", nativeQuery = true)
    public List<Location> findAllMerchantsForTradePack(@Param("pack") TradingPack pack);
    
    @Query("Select l from Location as l where l.isMerchant = 1")
    public List<Location> findAllMerchants();
}
