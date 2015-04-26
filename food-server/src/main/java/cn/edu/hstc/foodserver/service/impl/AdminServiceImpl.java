package cn.edu.hstc.foodserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.edu.hstc.foodserver.domain.Admin;
import cn.edu.hstc.foodserver.repository.AdminRepository;
import cn.edu.hstc.foodserver.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public void save(Admin admin) {
		adminRepository.save(admin);

	}

	@Override
	public Admin getAdminById(Integer id) {

		Admin admin = adminRepository.getOne(id);
		return admin;
	}

	@Override
	public void update(Admin admin) {
		adminRepository.save(admin);

	}

	@Override
	public void delete(Integer id) {

		adminRepository.delete(id);

	}

	@Override
	public List<Admin> getAdmins(int page, int size) {
		
		List<Admin> admins = adminRepository.findAll(new PageRequest(page, size)).getContent();
		return admins;
	}

	@Override
	public Long count() {
		
		Long count = adminRepository.count();
		return count;
	}

	@Override
	public long checkUAndP(String name, String pwd) {
		long count = adminRepository.getAdminByNameAndPwd(name, pwd);
		return count;
	}

}
