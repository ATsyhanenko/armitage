package org.armitage.inc.AAInfo.service;

import java.util.List;

import org.armitage.inc.AAInfo.dto.SellingPriceDto;
import org.armitage.inc.AAInfo.entity.Location;

public interface LocationService {
    List<Location> getMerchantsForTradingPackByPackId(Integer tradingPackId);

    List<Location> getMerchantsForTradingPackByDto(SellingPriceDto priceDto);

}
