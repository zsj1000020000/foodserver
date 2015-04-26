package cn.edu.hstc.foodserver.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.edu.hstc.foodserver.domain.Category;
import cn.edu.hstc.foodserver.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;


	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add() {
		
		

		return "category/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, Category category) {
		categoryService.save(category);
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

		Integer count = categoryService.count().intValue();

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

		List<Category> categories = categoryService.getCategories(
				currentPage - 1, pageSize);
		model.addAttribute("categories", categories);
		model.addAttribute("currentPage", currentPage);
		if (pageCount==0) {
			pageCount = 1;
		}
		model.addAttribute("pageCount", pageCount);
		if (pages.size() != 0) {
			model.addAttribute("pages", pages);
		}
		return "category/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "cid") int cid, Model model) {
		Category category = categoryService.getCategoryById(cid);
		model.addAttribute("category", category);
		return "category/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Category category, Model model) {
		categoryService.update(category);
		model.addAttribute("message", "update success.");
		return "common/message";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "cid") int cid, Model model) {
		categoryService.delete(cid);
		model.addAttribute("message", "delete success.");
		return "common/message";
	}

}
