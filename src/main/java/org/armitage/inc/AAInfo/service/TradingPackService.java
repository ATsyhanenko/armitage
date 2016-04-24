package org.armitage.inc.AAInfo.service;

import org.armitage.inc.AAInfo.dto.TradingPackDto;

public interface TradingPackService {
    void addNewPackage(TradingPackDto tradingPack);

    void savePackage(TradingPackDto tradingPackDto);

    void deletePackage(Integer packId);
}
