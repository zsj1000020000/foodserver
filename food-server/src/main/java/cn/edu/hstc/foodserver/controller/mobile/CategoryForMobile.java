package cn.edu.hstc.foodserver.controller.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hstc.foodserver.domain.Category;
import cn.edu.hstc.foodserver.service.CategoryService;

@RestController
@RequestMapping(value = "/mobile")
public class CategoryForMobile {

	@Autowired
	private CategoryService categoryService;
	
	private static final Integer SIZE = 5;
	
	@RequestMapping(value = "/categories" , method = RequestMethod.GET )
	public List<Category> list(@RequestParam(value= "page" , required = false)Integer page) {
		
		if (page == null) {
			page = 1;
		}
		List<Category> categories = this.categoryService.getCategories(page - 1, SIZE);
		
		return categories;
		
		
	}
	
}
