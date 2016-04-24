package org.armitage.inc.AAInfo.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.SellingPriceRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.SellingPrice;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.service.XmlDataService;
import org.armitage.inc.AAInfo.xml.DataRoot;
import org.armitage.inc.AAInfo.xml.LocationXML;
import org.armitage.inc.AAInfo.xml.SellingPriceXML;
import org.armitage.inc.AAInfo.xml.TradingPackXML;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class XmlDataServiceImpl implements XmlDataService{
    @Autowired
    private Logger logger;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private TradingPackRepository tradingPackRepository;
    
    @Autowired
    private SellingPriceRepository sellingPriceRepository;
    
    @Override
    @Transactional
    public ByteArrayOutputStream formXmlStructure(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataRoot dataRoot = new DataRoot();
        
        Sort locationSort = new Sort(Sort.Direction.ASC,"locationName");
        List<Location> locations = locationRepository.findAll(locationSort);
        Sort packSort = new Sort(Sort.Direction.ASC,"packName");
        List<TradingPack> tradingPacks = tradingPackRepository.findAll(packSort);
        Sort priceSort = new Sort(Sort.Direction.DESC,"packPrice");
        List<SellingPrice> sellingPrices = sellingPriceRepository.findAll(priceSort);
        
        List<LocationXML> locationsXML = new ArrayList<LocationXML>();
        for(Location location : locations){
            LocationXML locationXML = new LocationXML(location);
            locationsXML.add(locationXML);
            
            List<TradingPackXML> tradingPacksXML = new ArrayList<TradingPackXML>();
            
            for(TradingPack pack : tradingPacks){
                if(pack.getLocation().equals(location)){
                    TradingPackXML tradingPackXML = new TradingPackXML(pack);
                    tradingPacksXML.add(tradingPackXML);
                    
                    List<SellingPriceXML> sellingPricesXML = new ArrayList<SellingPriceXML>();
                    for(SellingPrice price: sellingPrices){
                        if(price.getPack().equals(pack)){
                            SellingPriceXML sellingPriceXML = new SellingPriceXML(price);
                            sellingPricesXML.add(sellingPriceXML);
                        }
                    }
                    tradingPackXML.setSellingPrices(sellingPricesXML);
                }
            }
            locationXML.setTradingPacks(tradingPacksXML);
        }
        
        dataRoot.setLocations(locationsXML);
        
        try {
            JAXBContext context = JAXBContext.newInstance(DataRoot.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.setProperty(Marshaller.JAXB_ENCODING, Boolean.TRUE);
            marshaller.marshal(dataRoot, output);
            //marshaller.marshal(, System.out);
           } catch (JAXBException exception) {
               logger.error(exception.getMessage());
           }
        return output;
    }
}
