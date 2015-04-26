package cn.edu.hstc.foodserver.controller.mobile;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hstc.foodserver.domain.Food;
import cn.edu.hstc.foodserver.service.FoodService;

@RestController
@RequestMapping(value = "/mobile")
public class FoodForMobile {

	@Autowired
	private FoodService foodService;
	private static Integer SIZE = 5;

	@RequestMapping(value = "/foods", method = RequestMethod.GET)
	public List<Food> foods(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "sid") Integer sid) {
		if (page == null) {
			page = 1;
		}
		List<Food> foods = foodService.getFoods(sid, page - 1, SIZE);
		return foods;
	}

	@RequestMapping(value = "/foods_date", method = RequestMethod.GET)
	public List<Food> foods(
			@RequestParam(value = "date", required = true) Long date,
			@RequestParam(value = "sid") Integer sid) {
		
		List<Food> foods = foodService.getFoods(new Date(date), sid);
		return foods;
	}

}
