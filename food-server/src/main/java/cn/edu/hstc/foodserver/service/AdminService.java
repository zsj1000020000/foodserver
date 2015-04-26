package cn.edu.hstc.foodserver.service;

import java.util.List;

import cn.edu.hstc.foodserver.domain.Admin;

public interface AdminService {

	public void save(Admin admin);
	
	public Admin getAdminById(Integer id);
	
	public void update(Admin admin) ;
	
	public void delete(Integer id) ;
	
	public List<Admin> getAdmins(int page , int size);
	
	public Long count() ;
	
	public long checkUAndP(String name,String password);
}
