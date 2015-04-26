package cn.edu.hstc.foodserver.service;

import java.util.List;

import cn.edu.hstc.foodserver.domain.Category;

public interface CategoryService {

	public void save(Category category);
	
	public void delete(Integer id) ;
	
	public void update(Category category) ;
	
	public Category getCategoryById(Integer id);
	
	public Long count() ;
	
	public List<Category> getCategories(int page , int size);
	
	public List<Category> getCategories();
	
}
