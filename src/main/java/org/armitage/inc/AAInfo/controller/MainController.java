package org.armitage.inc.AAInfo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.armitage.inc.AAInfo.dao.LocationRepository;
import org.armitage.inc.AAInfo.dao.SellingPriceRepository;
import org.armitage.inc.AAInfo.dao.TradingPackRepository;
import org.armitage.inc.AAInfo.dto.SellingPriceDto;
import org.armitage.inc.AAInfo.dto.TradingPackDto;
import org.armitage.inc.AAInfo.entity.Location;
import org.armitage.inc.AAInfo.entity.SellingPrice;
import org.armitage.inc.AAInfo.entity.TradingPack;
import org.armitage.inc.AAInfo.extra.SalePrice;
import org.armitage.inc.AAInfo.service.LocationService;
import org.armitage.inc.AAInfo.service.SellingPriceService;
import org.armitage.inc.AAInfo.service.TradingPackService;
import org.armitage.inc.AAInfo.service.UserService;
import org.armitage.inc.AAInfo.service.XmlDataService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {
	@Autowired
	private Logger logger;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TradingPackRepository tradingPackRepository;
	
	@Autowired
	private TradingPackService tradingPackService;
	
	@Autowired
	private SellingPriceRepository sellingPriceRepository;
	
	@Autowired
	private SellingPriceService sellingPriceService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private XmlDataService xmlDataService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showIndex(Model model){
		Sort listSort = new Sort(Sort.Direction.ASC,"locationName");
		List<Location> locationList = locationRepository.findAll(listSort);
		model.addAttribute("locationList", locationList);
		
		return "base";
	}
	
	@RequestMapping(value="/getPackList", method = RequestMethod.GET)
	public String showPackageList(@RequestParam("locationId") Integer locationId, Model model){
	    logger.debug("recieved value: "+locationId);
	    Location location = locationRepository.findOne(locationId);
	    logger.debug("found location: "+location.getLocationName()+" ("+location.getLocationId()+")");
	    List<TradingPack> tradingPackList = tradingPackRepository.findTradingPacksByLocation(location);
	    logger.debug("found packs: "+tradingPackList.size());
	    
	    model.addAttribute("location", location);
	    model.addAttribute("packageList", tradingPackList);
	    
	    logger.debug(tradingPackList.toString());
	    
	    return "productList";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addNewPack", method = RequestMethod.GET)
	public String showAddNewPackForm(@RequestParam("locationId") String locationId, Model model){
        model.addAttribute("locationId", locationId);
        model.addAttribute("tradingPackDto", new TradingPackDto());
        model.addAttribute("formAction", "create");
	    return "packageForm";
	}
    
    @PreAuthorize("hasRole('ADMIN')")
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
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/editPack", method = RequestMethod.POST)
    public ResponseEntity<String> saveEdittedPack(@Valid @ModelAttribute TradingPackDto tradingPackDto, BindingResult errors){
        logger.debug("recieved: "+tradingPackDto);
        logger.debug("errors? "+errors.getErrorCount());
        ResponseEntity<String> response = null;
        if(errors.hasErrors()){
            response = new ResponseEntity<String>(errors.getFieldError("title").getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }else{
            tradingPackService.savePackage(tradingPackDto);
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        
        return response;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addNewPack", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addNewPack(@Valid @ModelAttribute TradingPackDto tradingPackDto, BindingResult errors){
        logger.debug("recieved: "+tradingPackDto);
        logger.debug("errors? "+errors.getErrorCount());
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
    @ResponseBody
    @RequestMapping(value = "/deletePack", method = RequestMethod.GET)
    public String deletePack(@RequestParam("packId") Integer packId){
        tradingPackService.deletePackage(packId);
        return "OK";
    }
	
    @RequestMapping(value = "/getPrices", method = RequestMethod.GET)
    public String showPrices(@RequestParam("packId") Integer packId, Model model){
        TradingPack pack = tradingPackRepository.findOne(packId);
        logger.debug("found pack: "+pack);
        
        List<SellingPrice> priceList = sellingPriceRepository.findByPack(pack);
        model.addAttribute("priceList", priceList);
        
        return "sellingPricesList";
    }
    
    @RequestMapping(value = "/priceDetail", method = RequestMethod.GET)
    public String getPricesDetail(@RequestParam("priceId") Integer priceId, Model model){
        logger.debug("calculating details");
        SellingPrice price = sellingPriceRepository.findOne(priceId);
        List<SalePrice> priceList = sellingPriceService.getDetailedPrice(price.getPackPrice());
        model.addAttribute("priceList", priceList);
        return "sellingPriceDetail";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value ="/addNewPrice", method = RequestMethod.GET)
    public String showAddNewPriceForm(@RequestParam("packId") Integer packId, Model model){
        logger.debug("begin");
        SellingPriceDto priceDto = new SellingPriceDto();
        List<Location> locations = locationService.getMerchantsForTradingPackByPackId(packId);
        TradingPack pack = tradingPackRepository.findOne(packId);
        
        priceDto.setPack(pack.getPackId());
        model.addAttribute("sellingPrice", priceDto);
        model.addAttribute("formAction", "create");
        model.addAttribute("merchantList", locations);
        logger.debug("end");
        return "sellingPriceForm";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addNewPrice", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> addNewPrice(@Valid @ModelAttribute("sellingPriceDto") SellingPriceDto sellingPriceDto, BindingResult errors){
        logger.debug(sellingPriceDto.toString());
        logger.debug("errors? "+errors.getErrorCount());
        ResponseEntity<String> response = null;
        if(errors.hasErrors()){
            response = new ResponseEntity<String>(errors.getFieldError().getCode(), HttpStatus.BAD_REQUEST);
        }else{
            sellingPriceService.saveNewSellingPrice(sellingPriceDto);
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        logger.debug(sellingPriceDto.toString());
        return response;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/editPrice", method = RequestMethod.GET)
    public String editPriceForm(@RequestParam("priceId") Integer priceId, Model model){
        logger.debug("begin");
        SellingPriceDto priceDto = sellingPriceService.getPriceDto(priceId);
        model.addAttribute("sellingPrice", priceDto);
        model.addAttribute("formAction", "edit");
        
        List<Location> locations = locationService.getMerchantsForTradingPackByDto(priceDto);
        model.addAttribute("merchantList", locations);
        logger.debug("end");
        return "sellingPriceForm";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/editPrice", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> saveEditedPrice(@Valid @ModelAttribute("sellingPrice") SellingPriceDto sellingPriceDto, BindingResult errors){
        logger.debug(sellingPriceDto.toString());
        logger.debug("errors? "+errors.getErrorCount());
        ResponseEntity<String> response = null;
        if(errors.hasErrors()){
            response = new ResponseEntity<String>(errors.getFieldError().getCode(), HttpStatus.BAD_REQUEST);
        }else{
            sellingPriceService.saveEditedPrice(sellingPriceDto);
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        logger.debug(sellingPriceDto.toString());
        return response;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/deletePrice", method = RequestMethod.GET)
    public String deletePrice(@RequestParam("priceId") Integer priceId){
        sellingPriceService.deletePriceByPriceId(priceId);
        return "OK";
    }
    
	@RequestMapping(value="/formXmlStructure", method = RequestMethod.GET)
	public void getXmlData(HttpServletResponse response){
	    ByteArrayOutputStream stream = xmlDataService.formXmlStructure();
	    
	    response.setContentType("application/xml");
	    response.setHeader("Content-Disposition", "attachment;filename=xmlDataStructure.xml");
	    
	    byte[] byteOutput = stream.toByteArray();
	    
	    try {
            response.getOutputStream().write(byteOutput);
            
            try {
                response.flushBuffer();
            } catch (IOException e) {
                logger.error("Couldn't flush data to output stream: "+e.getMessage());
            }
        } catch (IOException e) {
            logger.error("Couldn't write data to output stream: "+e.getMessage());
        } finally{
            try{
                stream.close();
            }catch(IOException e){
                logger.error("Error closing input stream");
            }
        }
	}

	@RequestMapping(value="/preAuth", method=RequestMethod.POST)
	public String preAuth(Model model, @RequestParam("userLogin") String userLogin, @RequestParam("password") String password){
	    boolean credentialsAccepted = userService.preAuthUserCheck(userLogin, password);
	    if(credentialsAccepted){
    	    model.addAttribute("login", userLogin);
            model.addAttribute("password", password);
            return "passAuth";
	    }
	    String errorMessage = "AbstractUserDetailsAuthenticationProvider.badCredentials";
	    model.addAttribute("error", messageSource.getMessage(errorMessage, null, Locale.getDefault()));
	    return "loginForm";
	}
	
	@ExceptionHandler(Exception.class)
	public String logException(Exception exception){
	    logger.warn("Caught an exception. \n\tStackTrace: "+exception+" ("+exception.fillInStackTrace() +")\n\tDetails: "+exception.getMessage());
	    return "redirect:/";
	}
}
