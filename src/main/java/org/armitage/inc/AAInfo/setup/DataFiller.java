package org.armitage.inc.AAInfo.setup;

import java.util.ArrayList;
import java.util.List;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.UserRepository;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.MapReference;
import org.armitage.inc.AAInfo.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataFiller {
    @Autowired
    private Logger logger;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Bean
    public Void fillInData(){
    	User user = userRepository.getByLogin("admin");
    	if(user == null){
    		logger.info("admin account not found. Creating... ");
    		user = new User();
    	}
        logger.info("Setting admin account");
        user.setUserName("admin");
        user.setPassword("$2a$10$GCO0J2jn4nIfaPO8WzcHuuUrnaV.U/vDlUGOzdfpdrq1mB/3vV/mK");
        user.setEmail("sunchase1989@gmail.com");
        user.setPushToken("udcfeievo6pfypkkv62oiegqj3wu3k");
        user.setEnabled(1);
        logger.info("saving");
        userRepository.save(user);
        logger.info("done");

        
        if(locationRepository.count() < 1){
            logger.info("location repository is empty. Filling with data");
            List<Location> locations = new ArrayList<Location>();
            
            Location location = new Location("Мерианхольд",1);
            MapReference map = new MapReference(273,186);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Лес Гвинедар", 1);
            map = new MapReference(250,100);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Заболоченные низины", 0);
            map = new MapReference(165,300);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Золотые равнины", 0);
            map = new MapReference(206,230);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Две Короны", 1);
            map = new MapReference(326,211);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Земля Говорящих Камней", 0);
            map = new MapReference(298,146);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Полуосторов Падающих Звезд", 1);
            map = new MapReference(380,186);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Полуостров Солрид", 1);
            map = new MapReference(377,119);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Белый лес", 1);
            map = new MapReference(230,145);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Холмы Лилиот", 1);
            map = new MapReference(310,90);
            location.setMapReference(map);
            locations.add(location);
            
            location = new Location("Долгая Коса", 1);
            map = new MapReference(233,266);
            location.setMapReference(map);
            locations.add(location);
            
            logger.info("saving");
            locationRepository.save(locations);
            logger.info("done");
        }
        
        
        return null;
    }
}
