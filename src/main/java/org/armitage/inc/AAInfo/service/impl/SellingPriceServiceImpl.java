package org.armitage.inc.AAInfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.SellingPriceRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.SellingPriceDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.SellingPrice;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.extra.SalePrice;
import org.armitage.inc.AAInfo.service.SellingPriceService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SellingPriceServiceImpl implements SellingPriceService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TradingPackRepository tradingPackRepository;
    @Autowired
    private SellingPriceRepository sellingPriceRepository;
    @Autowired
    private Logger log;
    
    @Override
    @Transactional
    public void saveNewSellingPrice(SellingPriceDto sellingPriceDto) {
        SellingPrice sellingPrice = new SellingPrice();
        Location location = locationRepository.findOne(sellingPriceDto.getLocation());
        log.info("found location: "+location);
        TradingPack pack = tradingPackRepository.findOne(sellingPriceDto.getPack());
        log.info("found pack: "+pack);
        
        sellingPrice.setLocation(location);
        sellingPrice.setPack(pack);
        sellingPrice.setPackPrice(sellingPriceDto.getPrice());
        
        log.info("saving");
        sellingPriceRepository.save(sellingPrice);
    }
    
    @Override
    public SellingPriceDto getPriceDto(Integer priceId){
        SellingPrice price = sellingPriceRepository.findOne(priceId);
        SellingPriceDto priceDto = new SellingPriceDto();
        priceDto.setPriceId(price.getPriceId());
        priceDto.setLocation(price.getLocation().getLocationId());
        priceDto.setPack(price.getPack().getPackId());
        priceDto.setPrice(price.getPackPrice());
        
        return priceDto;
    }
    
    @Override
    @Transactional
    public void saveEditedPrice(SellingPriceDto sellingPriceDto){
        SellingPrice sellingPrice = sellingPriceRepository.findOne(sellingPriceDto.getPriceId());
        Location location = locationRepository.findOne(sellingPriceDto.getLocation());
        sellingPrice.setLocation(location);
        sellingPrice.setPackPrice(sellingPriceDto.getPrice());
        
        sellingPriceRepository.save(sellingPrice);
    }
    
    @Override
    public List<SalePrice> getDetailedPrice(Double basePrice){
        List<SalePrice> result = new ArrayList<SalePrice>(15);
        SalePrice crit = new SalePrice(basePrice, 135);
        result.add(crit);
        for(int i = 130; i>110; i = i-2){
            SalePrice price = new SalePrice(basePrice, i);
            result.add(price);
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public void deletePriceByPriceId(Integer priceId){
        log.info("deleting price with id: "+priceId);
        sellingPriceRepository.delete(priceId);
    }
    
    @Override
    @Transactional
    public void deletePricesByPackId(Integer packId){
        TradingPack pack = tradingPackRepository.findOne(packId);
        List<SellingPrice> prices = sellingPriceRepository.findByPack(pack);
        sellingPriceRepository.delete(prices);
    }
}
