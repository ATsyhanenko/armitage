package org.armitage.inc.AAInfo.service.impl;

import javax.transaction.Transactional;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.TradingPackDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.service.SellingPriceService;
import org.armitage.inc.AAInfo.service.TradingPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TradingPackServiceImpl implements TradingPackService{
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TradingPackRepository tradingPackRepository;
    @Autowired
    private SellingPriceService sellingPriceService;
    
    @Override
    @Transactional
    public void addNewPackage(TradingPackDto tradingPackDto) {
        Location location = locationRepository.findOne(tradingPackDto.getLocationId());
        TradingPack tradingPack = new TradingPack();
        tradingPack.setLocation(location);
        tradingPack.setPackName(tradingPackDto.getTitle());
        tradingPack.setDesc(tradingPackDto.getDesc());
        
        tradingPackRepository.save(tradingPack);
    }
    
    @Override
    @Transactional
    public void savePackage(TradingPackDto tradingPackDto){
        TradingPack tradingPack = tradingPackRepository.findOne(tradingPackDto.getPackId());
        if(tradingPack == null){
            throw new NullPointerException("incorrect data");
        }
        tradingPack.setDesc(tradingPackDto.getDesc());
        tradingPack.setPackName(tradingPackDto.getTitle());
        tradingPackRepository.save(tradingPack);
    }
    
    @Override
    @Transactional
    public void deletePackage(Integer packId){
        sellingPriceService.deletePricesByPackId(packId);
        tradingPackRepository.delete(packId);
    }

}
