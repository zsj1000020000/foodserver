package cn.edu.hstc.foodserver.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.hstc.foodserver.domain.Food;
import cn.edu.hstc.foodserver.domain.Store;
import cn.edu.hstc.foodserver.service.FoodService;
import cn.edu.hstc.foodserver.service.StoreService;

@Controller
@RequestMapping(value = "/food")
public class FoodController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private FoodService foodService;
	
	private static Logger log = Logger.getLogger(FoodController.class);
	

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, @RequestParam(value = "sid") Integer sid) {

		model.addAttribute("sid", sid);
		return "food/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			Model model,
			Food food,
			@RequestParam(value = "sid") Integer sid,
			@RequestParam(value = "picture", required = false) MultipartFile picture) {
		
		food.setStore_id(sid);
		String picDir = request.getServletContext().getRealPath("/")+ "/images/foods";
		try {
			File pic = new File(picDir);
			if (!pic.exists()) {
				pic.mkdirs();
			}
			
			if (!picture.isEmpty()) {
				String picURL = UUID.randomUUID().toString().replace("-", "")
						+ picture.getOriginalFilename().substring(
								picture.getOriginalFilename().lastIndexOf("."));
				log.info("url:" + picURL);
				byte[] bytes = picture.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(pic, picURL)));
				stream.write(bytes);
				stream.close();
				food.setPic("/images/foods" + "/" + picURL);
			} else {
				log.info("the food image is empty.");
			}
		} catch (Exception e) {
			log.info("the food image upload failure");
		}
		foodService.save(food);
		model.addAttribute("message", "save success.");
		return "common/message";
		
		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "fid") int fid, Model model) {

		Food food = foodService.getFoodById(fid);
		String picPath = food.getPic();
		File picFile = new File(picPath);
		if (picFile.exists()) {
			picFile.delete();
		}
		foodService.delete(fid);
		model.addAttribute("message", "delete success.");
		return "common/message";
	}
	
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public String edit(@RequestParam(value = "fid") int fid,Model model) {
		
		Food food = foodService.getFoodById(fid);	
		model.addAttribute("food", food);		
		return "food/edit";
		
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request ,Model model,Food food,@RequestParam(value = "picture", required = false) MultipartFile picture){
		String picDir = request.getServletContext().getRealPath("/")+ "/images/foods";
		File pic = new File(picDir);
		if (!pic.exists()) {
			pic.mkdirs();
		}
		try {
			String picPath = foodService.getFoodById(food.getId()).getPic();
			if (!picture.isEmpty()) {
				
				File picFile = new File(picPath);
				if (picFile.exists()) {
					picFile.delete();
				}
				
				String picURL = UUID.randomUUID().toString().replace("-", "")
						+ picture.getOriginalFilename().substring(
								picture.getOriginalFilename().lastIndexOf("."));
				log.info("url:" + picURL);
				byte[] bytes = picture.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(pic, picURL)));
				stream.write(bytes);
				stream.close();
				food.setPic("/images/foods" + "/" + picURL);
			}else{
				food.setPic(picPath);
			}
		} catch (Exception e) {
			log.info("the food image upload failure");
		}
		foodService.update(food);
		model.addAttribute("message", "update success.");
		return "common/message";
	}
	
	@RequestMapping(value = "/store_list", method = RequestMethod.GET)
	public String stores(Model model,
			@RequestParam(value = "page", required = false) Integer page){
		
		Integer currentPage = 0;
		Integer pageSize = 5;
		if (page == null) {
			currentPage = 1;
			page = 1;
		} else {
			currentPage = page;
		}

		Integer count = storeService.count().intValue();
		Integer pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

		Integer startPage;
		Integer endPage;

		if (pageCount > pageSize) {

			if (pageCount <= (currentPage + 4)) {

				endPage = pageCount;
				startPage = endPage - 4;
			} else {
				startPage = currentPage;
				endPage = startPage + 4;
			}
		} else {
			startPage = 1;
			endPage = pageCount;
		}

		List<Integer> pages = new ArrayList<Integer>();
		for (int i = startPage.intValue(); i <= endPage.intValue(); i++) {
			pages.add(i);
		}

		List<Store> stores = storeService.getStores(currentPage - 1, pageSize);
		model.addAttribute("stores", stores);
		model.addAttribute("currentPage", currentPage);
		if (pageCount==0) {
			pageCount = 1;
		}
		model.addAttribute("pageCount", pageCount);
		if (pages.size() != 0) {
			model.addAttribute("pages", pages);
		}

		return "food/store_list";
	}
	
	@RequestMapping(value="/list" ,  method = RequestMethod.GET)
	public String list(Model model,
			@RequestParam(value = "page", required = false) Integer page,@RequestParam(value="sid")Integer sid) {
		
		Integer currentPage = 0;
		Integer pageSize = 5;
		if (page == null) {
			currentPage = 1;
			page = 1;
		} else {
			currentPage = page;
		}

		Integer count = foodService.count(sid).intValue();
		Integer pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

		Integer startPage;
		Integer endPage;

		if (pageCount > pageSize) {

			if (pageCount <= (currentPage + 4)) {

				endPage = pageCount;
				startPage = endPage - 4;
			} else {
				startPage = currentPage;
				endPage = startPage + 4;
			}
		} else {
			startPage = 1;
			endPage = pageCount;
		}

		List<Integer> pages = new ArrayList<Integer>();
		for (int i = startPage.intValue(); i <= endPage.intValue(); i++) {
			pages.add(i);
		}

		List<Food> foods = foodService.getFoods(sid, currentPage - 1, pageSize);
		model.addAttribute("foods", foods);
		model.addAttribute("currentPage", currentPage);
		if (pageCount==0) {
			pageCount = 1;
		}
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("sid", sid);
		if (pages.size() != 0) {
			model.addAttribute("pages", pages);
		}

		
		return "food/list";
		
	}

}
