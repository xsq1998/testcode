package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;

public interface UserDao {
	List<User> find();
	
	PageModel<User> findAll(int pageIndex);
	
	User findById(int id);
	
	User findByLoginName(String name);
	
	User login(String loginname,String pwd);
	
	boolean addUser(User user);
	
	PageModel<User> findByNameLike(int pageIndex,User user);
	
	boolean delUser(List<Object> ids);

	boolean updateUser(String loginname,String status,String username,String pwd,int id);

}
