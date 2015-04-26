package cn.edu.hstc.foodserver.service;

import java.util.Date;
import java.util.List;

import cn.edu.hstc.foodserver.domain.Food;

public interface FoodService {

	public void save(Food food);
	
	public void delete(Integer id);
	
	public void update(Food food);
	
	public Food getFoodById(Integer id);
	
	public List<Food> getFoods(Integer stroreId , int page , int size);
	
	public Long count(Integer storeId);
	
	public List<Food> getFoods(Date date, Integer sid);
}
