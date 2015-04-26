package cn.edu.hstc.foodserver.controller.mobile;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hstc.foodserver.domain.Store;
import cn.edu.hstc.foodserver.service.StoreService;

@RestController
@RequestMapping(value = "/mobile")
public class StoreForMobile {
	
	@Autowired
	private StoreService storeService;
	private static final Integer SIZE = 5;
	
	@RequestMapping(value = "/stores",method = RequestMethod.GET)
	private List<Store> stores(@RequestParam(value = "page", required = false) Integer page) {
		if (page == null) {
			page = 1;
		}
		List<Store> stores = storeService.getStores( page - 1, SIZE);
		
		return stores;
		
		
	}
	
	@RequestMapping(value = "/stores_date",method = RequestMethod.GET)
	private List<Store> getStoresByMaxId(@RequestParam(value = "date", required = true) Long date) {
		
		List<Store> stores = storeService.getStores(new Date(date));
		
		return stores;
		
		
	}
	


}
