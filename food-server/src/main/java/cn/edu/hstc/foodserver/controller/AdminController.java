package cn.edu.hstc.foodserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.hstc.foodserver.domain.Admin;
import cn.edu.hstc.foodserver.service.AdminService;
import cn.edu.hstc.utils.Md5Encrypt;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private static Logger log = Logger.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {

		return "admin/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Admin admin, Model model) {
		log.info("name:" + admin.getName());
		log.info("pwd:" + admin.getPwd());
		admin.setPwd(Md5Encrypt.encrypt(admin.getPwd()));
		adminService.save(admin);
		model.addAttribute("message", "save success.");
		return "common/message";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model,
			@RequestParam(value = "page", required = false) Integer page) {

		Integer currentPage = 0;
		Integer pageSize = 5;
		if (page == null) {
			currentPage = 1;
			page = 1;
		} else {
			currentPage = page;
		}

		Integer count = adminService.count().intValue();
		Integer pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

		Integer startPage;
		Integer endPage;

		if (pageCount > pageSize) {

			if (pageCount <= (currentPage + 4)) {

				endPage = pageCount;
				startPage = endPage - 4;
			} else {
				startPage = currentPage;
				endPage = startPage + 4;
			}
		} else {
			startPage = 1;
			endPage = pageCount;
		}

		List<Integer> pages = new ArrayList<Integer>();
		for (int i = startPage.intValue(); i <= endPage.intValue(); i++) {
			pages.add(i);
		}

		List<Admin> admins = adminService.getAdmins(currentPage - 1, pageSize);
		model.addAttribute("admins", admins);
		model.addAttribute("currentPage", currentPage);
		if (pageCount==0) {
			pageCount = 1;
		}
		model.addAttribute("pageCount", pageCount);
		if (pages.size() != 0) {
			model.addAttribute("pages", pages);
		}

		return "admin/list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(value = "aid") int aid, Model model) {
		Admin admin = adminService.getAdminById(aid);
		model.addAttribute("admin", admin);
		return "admin/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Admin admin, Model model) {
		admin.setPwd(Md5Encrypt.encrypt(admin.getPwd()));

		adminService.update(admin);
		model.addAttribute("message", "update success.");
		return "common/message";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "aid") int aid, Model model) {

		adminService.delete(aid);
		model.addAttribute("message", "delete success.");
		return "common/message";
	}

}
