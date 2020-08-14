package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.dao.UserDao;
import com.gec.hrm.dao.daoImpl.UserDaoImpl;
import com.gec.hrm.service.UserService;

public class UserServiceImpl implements UserService{
	UserDao us=new UserDaoImpl();
	
	@Override
	public User findById(int id) {
		// TODO 自动生成的方法存根
		return us.findById(id);
	}

	@Override
	public User findByLoginName(String name) {
		// TODO 自动生成的方法存根
		return us.findByLoginName(name);
	}

	@Override
	public User login(String loginname, String pwd) {
		// TODO 自动生成的方法存根
		return us.login(loginname, pwd);
	}

	@Override
	public boolean addUser(User user) {
		return us.addUser(user);
	}



	@Override
	public boolean delUser(List<Object> ids) {
		return us.delUser(ids);
	}

	@Override
	public boolean updateUser(String loginname, String status, String username, String pwd, int id) {
		return us.updateUser(loginname, status, username, pwd, id);
	}

	@Override
	public PageModel<User> findAll(int pageIndex) {
		return us.findAll(pageIndex);
	}

	@Override
	public List<User> find() {
		// TODO 自动生成的方法存根
		return us.find();
	}

	@Override
	public PageModel<User> findByNameLike(int pageIndex, User user) {
		// TODO 自动生成的方法存根
		return us.findByNameLike(pageIndex, user);
	}

}
