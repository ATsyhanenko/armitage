package org.armitage.inc.AAInfo.service.impl;

import java.util.List;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.SellingPriceDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.service.LocationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService{
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private TradingPackRepository tradingPackRepository;
    
    @Autowired
    private Logger logger;
    
    @Override
    public List<Location> getMerchantsForTradingPackByPackId(Integer tradingPackId){
        TradingPack pack = tradingPackRepository.findOne(tradingPackId);
        List<Location> merchants = locationRepository.findAllMerchantsForTradePack(pack);
        
        return merchants;
    }
    
    @Override
    public List<Location> getMerchantsForTradingPackByDto(SellingPriceDto priceDto){
        TradingPack pack = tradingPackRepository.findOne(priceDto.getPack());
        Location selectedLocation = locationRepository.findOne(priceDto.getLocation());
        List<Location> merchants = locationRepository.findAllMerchantsForTradePack(pack);
        merchants.add(selectedLocation);
        
        return merchants;
    }
}
