package org.armitage.inc.AAInfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.TradingPackDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.service.TradingPackService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {
	@Autowired
	private Logger log;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TradingPackRepository tradingPackRepository;
	
	@Autowired
	private TradingPackService tradingPackService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showIndex(Model model){
		model.addAttribute("msg1","Hello, human");
		
		Sort listSort = new Sort(Sort.Direction.ASC,"locationName");
		List<Location> locationList = locationRepository.findAll(listSort);
		
		model.addAttribute("locationList", locationList);
		log.info("YUP, LOGGER WORKS");
		return "base";
	}
	
	@RequestMapping(value="/getPackList", method = RequestMethod.GET)
	public String showPackageList(@RequestParam("locationId") Integer locationId, Model model){
	    log.info("recieved value: "+locationId);
	    Location location = locationRepository.findOne(locationId);
	    log.info("found location: "+location.getLocationName()+" ("+location.getLocationId()+")");
	    List<TradingPack> tradingPackList = tradingPackRepository.findTradingPacksByLocation(location);
	    log.info("found packs: "+tradingPackList.size());
	    
	    model.addAttribute("location", location);
	    model.addAttribute("packageList", tradingPackList);
	    
	    log.info(tradingPackList.toString());
	    
	    return "productList";
	}
	
    @RequestMapping(value = "/addNewPack", method = RequestMethod.GET)
	public String showAddNewPackForm(@RequestParam("locationId") String locationId, Model model){
        model.addAttribute("locationId", locationId);
        model.addAttribute("tradingPackDto", new TradingPackDto());
        model.addAttribute("formAction", "create");
	    return "packageForm";
	}
    
    @RequestMapping(value = "/editPack", method = RequestMethod.GET)
    public String showEditPackForm(@RequestParam("packId") Integer packId, Model model){
        TradingPack pack = tradingPackRepository.findOne(packId);
        TradingPackDto packDto = new TradingPackDto();
        packDto.setDesc(pack.getDesc());
        model.addAttribute("locationId", pack.getLocation().getLocationId());
        packDto.setPackId(pack.getPackId());
        packDto.setTitle(pack.getPackName());
        model.addAttribute("tradingPackDto", packDto);
        model.addAttribute("formAction", "edit");
        return "packageForm";
    }
    
    @RequestMapping(value = "/editPack", method = RequestMethod.POST)
    public ResponseEntity<String> saveEdittedPack(@Valid @ModelAttribute TradingPackDto tradingPackDto, BindingResult errors){
        log.info("recieved: "+tradingPackDto);
        log.info("errors? "+errors.getErrorCount());
        ResponseEntity<String> response = null;
        if(errors.hasErrors()){
            response = new ResponseEntity<String>(errors.getFieldError("title").getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }else{
            tradingPackService.savePackage(tradingPackDto);
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        
        return response;
    }
    
    @RequestMapping(value = "/addNewPack", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addNewPack(@Valid @ModelAttribute TradingPackDto tradingPackDto, BindingResult errors){
        log.info("recieved: "+tradingPackDto);
        log.info("errors? "+errors.getErrorCount());
        ResponseEntity<String> response = null;
        if(errors.hasErrors()){
            response = new ResponseEntity<String>(errors.getFieldError("title").getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }else{
            tradingPackService.addNewPackage(tradingPackDto);
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        
        return response;
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String getAdminPage(Model model){
		return "admin";
	}
}
