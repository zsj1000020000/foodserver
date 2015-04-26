package cn.edu.hstc.foodserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.edu.hstc.foodserver.domain.SystemInfo;

@Repository("systemInfoRepository")
public interface SystemInfoRepository extends JpaRepository<SystemInfo, Integer> {
	
	

}
