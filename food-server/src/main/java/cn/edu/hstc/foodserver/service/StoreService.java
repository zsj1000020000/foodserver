package cn.edu.hstc.foodserver.service;

import java.util.Date;
import java.util.List;

import cn.edu.hstc.foodserver.domain.Store;

public interface StoreService {

	public void save(Store store) ;
	
	public void delete(Integer id) ;
	
	public void update(Store store);
	
	public Store getStoreById(Integer id);
	
	public List<Store> getStores (int page, int size);
	
	public List<Store> getStores (int cid ,int page, int size);
	
	public List<Store> getStores(Integer maxId);
	
	public Long count();
	
	public List<Store> getStores(Date date);
}
