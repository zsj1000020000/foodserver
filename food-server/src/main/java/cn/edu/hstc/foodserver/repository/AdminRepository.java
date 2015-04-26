package cn.edu.hstc.foodserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.edu.hstc.foodserver.domain.Admin;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<Admin, Integer>{

	@Query("select count(a.id) from Admin a where a.name=:name and a.pwd=:pwd")
	public long getAdminByNameAndPwd(@Param("name")String name , @Param("pwd")String pwd) ;
}
