package cn.edu.hstc.foodserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cn.edu.hstc.foodserver.domain.Food;

@Repository("foodRepository")
public interface FoodRepository extends PagingAndSortingRepository<Food, Integer>, JpaSpecificationExecutor<Food>  {

	@Query("select count(f.id) from Food f where f.store_id =:sid")
	public Long getCount(@Param(value="sid")Integer sid) ;

	@Query("select f from Food f where f.updateTime >:date and f.store_id=:sid")
	public List<Food> getFoods(@Param("date")Date date,@Param("sid")Integer sid);
	
	
	

}
