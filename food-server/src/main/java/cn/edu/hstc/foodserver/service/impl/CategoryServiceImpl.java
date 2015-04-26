package cn.edu.hstc.foodserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.edu.hstc.foodserver.domain.Category;
import cn.edu.hstc.foodserver.repository.CategoryRepository;
import cn.edu.hstc.foodserver.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void save(Category category) {
		
		categoryRepository.save(category);
	}

	@Override
	public void delete(Integer id) {
		
		categoryRepository.delete(id);
		
	}

	@Override
	public void update(Category category) {
		
		categoryRepository.save(category);
				
	}

	@Override
	public Category getCategoryById(Integer id) {
		Category category = categoryRepository.getOne(id);
		return category;
	}

	@Override
	public Long count() {
		long count = categoryRepository.count();
		return count;
	}

	@Override
	public List<Category> getCategories(int page, int size) {
		List<Category> categories = categoryRepository.findAll(new PageRequest(page, size)).getContent();
		return categories;
	}

	@Override
	public List<Category> getCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

}
