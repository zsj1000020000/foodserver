package cn.edu.hstc.foodserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hstc.foodserver.domain.SystemInfo;
import cn.edu.hstc.foodserver.repository.SystemInfoRepository;
import cn.edu.hstc.foodserver.service.SystemInfoService;

@Service("systemInfoService")
public class SystemInfoServiceImpl implements SystemInfoService {

	@Autowired
	private SystemInfoRepository systemInfoRepository;
	
	@Override
	public void update(SystemInfo info) {
		
		systemInfoRepository.save(info);
	}

	@Override
	public SystemInfo getInfoById(Integer id) {
		SystemInfo info = systemInfoRepository.getOne(id);
		return info;
	}

}
