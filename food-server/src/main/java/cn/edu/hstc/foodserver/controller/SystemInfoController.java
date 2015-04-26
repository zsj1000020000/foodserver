package cn.edu.hstc.foodserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import cn.edu.hstc.foodserver.domain.SystemInfo;
import cn.edu.hstc.foodserver.service.SystemInfoService;

@Controller
@RequestMapping(value="/system")
public class SystemInfoController {
	
	@Autowired
	private SystemInfoService systemInfoService;
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Model model,SystemInfo info) {
		
		systemInfoService.update(info);
		model.addAttribute("message", "update success.");
		return "common/message";
	}
	
	@RequestMapping(value="/view" , method = RequestMethod.GET)
	public String view(Model model) {
		
		SystemInfo info = systemInfoService.getInfoById(1);
		if (info != null) {
			model.addAttribute("info", info);
		}
		
		return "system/view";
	}

}
