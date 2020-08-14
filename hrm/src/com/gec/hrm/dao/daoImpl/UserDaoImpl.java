package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.dao.UserDao;
import com.gec.hrm.util.Util;

public class UserDaoImpl extends Util<User> implements UserDao {

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt("ID"));
		user.setLoginname(rs.getString("loginname"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setStatus(rs.getInt("STATUS"));
		user.setCreateDate(rs.getDate("createdate"));
		user.setUsername(rs.getString("username"));
		return user;
	}

	@Override
	public PageModel<User> findAll(int pageIndex) {
		PageModel<User> pageModel=new PageModel<>();
		String sql="select count(*) from user";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from user limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		return pageModel;
	}

	@Override
	public User findById(int id) {
		String sql = "select * from user where ID=?";
		List<User> list = query(sql, id);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public User findByLoginName(String name) {
		List<User> list = query("select * from user where loginname=?", name);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public User login(String loginname, String pwd) {// 登录方法
		String sql = "select * from user where loginname=? and PASSWORD=?";
		List<User> list = query(sql, loginname, pwd);
		if (list.size() > 0) {// 有数据
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean addUser(User user) {
		List<Object> list = new ArrayList<>();
		list.add(user.getLoginname());
		list.add(user.getPassword());
		list.add(user.getStatus());
		list.add(user.getCreateDate());
		list.add(user.getUsername());

		String sql = "insert into user values(null,?,?,?,?,?)";
		return update(sql, list);
	}

	@Override
	public PageModel<User> findByNameLike(int pageIndex,User user) {
		PageModel<User> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql = "select * from user where 1=1";
		if (user.getLoginname()!=null) {
			sql += " and loginname like REPLACE(?,' ','')";
			list.add(user.getLoginname());
		}
		if (user.getUsername()!=null) {
			sql += " and username like REPLACE(?,' ','')";
			list.add(user.getUsername());
		}
		if (user.getStatus()!=0) {
			sql += " and STATUS=?";
			list.add(user.getStatus());	
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		
		return pageModel;
	}

	@Override
	public boolean delUser(List<Object> ids) {//删除用户
		String sql="delete from user where ID=?";
		return update(sql, ids);
	}

	@Override
	public boolean updateUser(String loginname, String status, String username, String pwd,int id) {
		String sql="update user set ";
		int flag=0;
		List<Object> obj=new ArrayList<>();
		if (loginname!=null&&!("".equals(loginname))) {
			sql+="loginname=?";
			obj.add(loginname);
			flag++;
		}
		if (pwd!=null&&!("".equals(pwd))) {
			sql+=",PASSWORD=?";
			obj.add(pwd);
			flag++;
		}
		if (status!=null&&!("".equals(status))) {
			int status1=Integer.parseInt(status);
			sql+=",STATUS=?";
			obj.add(status1);
			flag++;
		}
		if (username!=null&&!("".equals(username))) {
			sql+=",username=?";
			obj.add(username);
			flag++;
		}
		sql+=" where ID=?";
		obj.add(id);
		
		if (flag>0) {
			return update(sql, obj);
		}else {
			return false;
		}
		
		
		
	}

	@Override
	public List<User> find() {
		return query("select * from user");
	}

	
}
