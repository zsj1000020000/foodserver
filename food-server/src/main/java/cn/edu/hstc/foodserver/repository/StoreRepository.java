package cn.edu.hstc.foodserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.edu.hstc.foodserver.domain.Store;

@Repository("storeRepository")
public interface StoreRepository extends PagingAndSortingRepository<Store, Integer>, JpaSpecificationExecutor<Store> {
	
	@Query("select s from Store s where s.id >:maxId order by s.id desc")
	public List<Store> getStores(@Param("maxId")Integer maxId);
	
	@Query("select s from Store s where s.updateTime >:date order by s.id desc")
	public List<Store> getStores(@Param("date")Date date);

	
}
