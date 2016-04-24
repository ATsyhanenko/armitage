package org.armitage.inc.AAInfo.service;

import java.util.List;

import org.armitage.inc.AAInfo.dto.SellingPriceDto;
import org.armitage.inc.AAInfo.extra.SalePrice;

public interface SellingPriceService {

    void saveNewSellingPrice(SellingPriceDto sellingPriceDto);

    SellingPriceDto getPriceDto(Integer priceId);

    void saveEditedPrice(SellingPriceDto sellingPriceDto);

    List<SalePrice> getDetailedPrice(Double basePrice);

    void deletePriceByPriceId(Integer priceId);

    void deletePricesByPackId(Integer packId);

}
