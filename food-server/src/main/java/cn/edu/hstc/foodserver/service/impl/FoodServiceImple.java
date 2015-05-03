package cn.edu.hstc.foodserver.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.edu.hstc.foodserver.domain.Food;
import cn.edu.hstc.foodserver.repository.FoodRepository;
import cn.edu.hstc.foodserver.service.FoodService;

@Service("foodService")
public class FoodServiceImple implements FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public void save(Food food) {
		foodRepository.save(food);
		
	}

	@Override
	public void delete(Integer id) {
		foodRepository.delete(id);
		
	}

	@Override
	public void update(Food food) {
		foodRepository.save(food);
		
	}

	@Override
	public Food getFoodById(Integer id) {
		Food food = foodRepository.findOne(id);
		return food;
	}

	@Override
	public List<Food> getFoods(final Integer stroreId, int page ,int size) {
		
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		List<Food> foods = foodRepository.findAll(new Specification<Food>() {
			
			@Override
			public Predicate toPredicate(Root<Food> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Path<Object> store_id = root.get("store_id");
				query.where(cb.equal(store_id, stroreId));
				return null;
			}			
			
		},new PageRequest(page, size,sort)).getContent();
		return foods;
	}

	@Override
	public Long count(Integer storeId) {
		Long count = foodRepository.getCount(storeId);
		return count;
	}

	@Override
	public List<Food> getFoods(Date date, Integer sid) {
		List<Food> foods = foodRepository.getFoods(date, sid);
		return foods;
	}

}
