package cn.edu.hstc.foodserver.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
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

import cn.edu.hstc.foodserver.domain.Category;
import cn.edu.hstc.foodserver.domain.Store;
import cn.edu.hstc.foodserver.service.CategoryService;
import cn.edu.hstc.foodserver.service.StoreService;

@Controller
@RequestMapping(value = "/store")
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private CategoryService categoryService;

	

	private static Logger log = Logger.getLogger(StoreController.class);

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {

		List<Category> categories = categoryService.getCategories();
		model.addAttribute("categories", categories);

		return "store/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			HttpServletRequest request,
			Model model,
			Store store,
			@RequestParam(value = "cid") Integer cid,
			@RequestParam(value = "picture", required = false) MultipartFile picture) {
		store.setCategory_id(cid);
		
		String picDir = request.getServletContext().getRealPath("/")+  "/images/stores";
		
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
				store.setPic("/images/stores" + "/" + picURL);
			} else {
				log.info("the image is empty.");
			}
		} catch (Exception e) {
			log.info("the store image upload failure");
		}
		store.setUpdateTime(new Date());
		this.storeService.save(store);
		model.addAttribute("message", "save success.");

		return "common/message";

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model,
			@RequestParam(value = "page", required = false) Integer page) {

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

		return "store/list";

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "sid") int sid, Model model) {

		Store store = storeService.getStoreById(sid);
		String picPath = store.getPic();
		File picFile = new File(picPath);
		if (picFile.exists()) {
			picFile.delete();
		}
		store.setUpdateTime(new Date());
		store.setIsDelete(true);
		storeService.update(store);
		model.addAttribute("message", "delete success.");
		return "common/message";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "sid") int sid, Model model) {
		Store store = storeService.getStoreById(sid);
		List<Category> categories = categoryService.getCategories();
		model.addAttribute("store", store);
		model.addAttribute("categories", categories);
		return "store/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request,
			Model model,
			Store store,
			@RequestParam(value = "cid") Integer cid,
			@RequestParam(value = "picture", required = false) MultipartFile picture) {
			
		store.setCategory_id(cid);
		String picDir = request.getServletContext().getRealPath("/")+  "/images/stores";
		File pic = new File(picDir);
		if (!pic.exists()) {
			pic.mkdirs();
		}
		try {
			String picPath = storeService.getStoreById(store.getId()).getPic();
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
				store.setPic("/images/stores" + "/" + picURL);
			}else{
				store.setPic(picPath);
				
			}
		} catch (Exception e) {
			log.info("the store image upload failure");
		}
		store.setUpdateTime(new Date());
		storeService.update(store);
		log.info(store.getPic());
		model.addAttribute("message", "update success.");
		return "common/message";
		
	}
	
	

}
