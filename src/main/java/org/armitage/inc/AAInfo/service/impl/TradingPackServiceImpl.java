package org.armitage.inc.AAInfo.service.impl;

import javax.transaction.Transactional;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.TradingPackDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.service.SellingPriceService;
import org.armitage.inc.AAInfo.service.TradingPackService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingPackServiceImpl implements TradingPackService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TradingPackRepository tradingPackRepository;
    @Autowired
    private SellingPriceService sellingPriceService;
    @Autowired
    private Logger logger;

    @Override
    @Transactional
    public void addNewPackage(TradingPackDto tradingPackDto) {
        logger.debug("begin");
        Location location = locationRepository.findOne(tradingPackDto.getLocationId());
        TradingPack tradingPack = new TradingPack();
        tradingPack.setLocation(location);
        tradingPack.setPackName(tradingPackDto.getTitle());
        tradingPack.setDesc(tradingPackDto.getDesc());
        logger.debug("saving");
        tradingPackRepository.save(tradingPack);
        logger.debug("end");
    }

    @Override
    @Transactional
    public void savePackage(TradingPackDto tradingPackDto) {
        logger.debug("begin");
        TradingPack tradingPack = tradingPackRepository.findOne(tradingPackDto.getPackId());
        tradingPack.setDesc(tradingPackDto.getDesc());
        tradingPack.setPackName(tradingPackDto.getTitle());
        logger.debug("saving");
        tradingPackRepository.save(tradingPack);
        logger.debug("end");
    }

    @Override
    @Transactional
    public void deletePackage(Integer packId) {
        logger.debug("begin");
        sellingPriceService.deletePricesByPackId(packId);
        logger.debug("deleting package with id: " + packId);
        tradingPackRepository.delete(packId);
        logger.debug("end");
    }

}
