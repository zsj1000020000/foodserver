package cn.edu.hstc.foodserver.service;

import cn.edu.hstc.foodserver.domain.SystemInfo;

public interface SystemInfoService {

	public void update(SystemInfo info);
	
	public SystemInfo getInfoById(Integer id) ;
}
