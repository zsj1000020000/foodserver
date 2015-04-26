package cn.edu.hstc.foodserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.hstc.foodserver.domain.SystemInfo;
import cn.edu.hstc.foodserver.service.AdminService;
import cn.edu.hstc.foodserver.service.SystemInfoService;
import cn.edu.hstc.utils.Md5Encrypt;

@Controller
public class LoginController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SystemInfoService systemInfoService;

	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value ={"/login"} ,method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	@RequestMapping(value="check")
	public String check(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "pwd", required = true) String pwd,
			HttpServletRequest request) {

		long count = adminService.checkUAndP(name, Md5Encrypt.encrypt(pwd));
		logger.info("name:" + name);
		logger.info("pwd:" + Md5Encrypt.encrypt(pwd));
		logger.info("count:" + count);
		SystemInfo info = systemInfoService.getInfoById(1);
		request.getSession().setAttribute("adminName", name);
		request.getSession().setAttribute("info", info);
		if (count == 0) {
			return "login";
		}
		return "index";
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		Object adminName = request.getSession().getAttribute("adminName");
		if (adminName != null) {
			request.getSession().removeAttribute("adminName");
		}
		return "login";
	}
}
