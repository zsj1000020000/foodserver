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

import cn.edu.hstc.foodserver.domain.Store;
import cn.edu.hstc.foodserver.repository.StoreRepository;
import cn.edu.hstc.foodserver.service.StoreService;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public void save(Store store) {
		
		storeRepository.save(store);
		
	}

	@Override
	public void delete(Integer id) {
		
		storeRepository.delete(id);
		
	}

	@Override
	public void update(Store store) {

		storeRepository.save(store);
		
	}

	@Override
	public Store getStoreById(Integer id) {
		
		Store store = storeRepository.findOne(id);
		return store;
	}

	@Override
	public List<Store> getStores(int page, int size) {
		
		List<Store> stores = storeRepository.findAll(new PageRequest(page, size,Sort.Direction.DESC,"id")).getContent();
		
		return stores;
	}

	@Override
	public Long count() {
		
		Long count = storeRepository.count();
		return count;
	}

	@Override
	public List<Store> getStores(final int cid, int page, int size) {
		List<Store> stores = storeRepository.findAll(new Specification<Store>() {
			
			@Override
			public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<Object> category_id = root.get("category_id");
				query.where(cb.equal(category_id, cid));
				return null;
			}
		}, new PageRequest(page, size)).getContent();
		return stores;
	}

	@Override
	public List<Store> getStores(Integer maxId) {
		List<Store> stores = storeRepository.getStores(maxId);
		return stores;
	}

	@Override
	public List<Store> getStores(Date date) {
		List<Store> stores = storeRepository.getStores(date);
		return stores;
	}

}
