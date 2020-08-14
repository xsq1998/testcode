package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.Type;
import com.gec.hrm.dao.TypeDao;
import com.gec.hrm.service.UserService;
import com.gec.hrm.service.serviceImpl.UserServiceImpl;
import com.gec.hrm.util.Util;

public class TypeDaoImpl extends Util<Type> implements TypeDao {
	UserService us=new UserServiceImpl();
	@Override
	public Type getEntity(ResultSet rs) throws Exception {
		Type type=new Type();
		type.setId(rs.getInt("id"));
		type.setName(rs.getString("name"));
		type.setCreateDate(rs.getDate("create_date"));
		type.setState(rs.getInt("state"));
		type.setUser(us.findById(rs.getInt("user_id")));
		type.setModifyDate(rs.getDate("modify_date"));
		return type;
	}

	@Override
	public List<Type> find() {
		return query("select * from type");
	}

	@Override
	public PageModel<Type> findAll(int pageIndex) {
		PageModel<Type> pageModel=new PageModel<>();
		String sql="select count(*) from type";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from type limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		return pageModel;
	}

	@Override
	public Type findById(int id) {
		String sql="select * from type where id=?";
		List<Type> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;}
		
	}

	@Override
	public Type findByName(String name) {
		String sql="select * from type where id=?";
		List<Type> list=query(sql, name);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}

	@Override
	public PageModel<Type> findByNameLike(int pageIndex, Type type) {
		PageModel<Type> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql="select * from type where 1=1";
		if (type.getName()!=null) {
			sql+=" and name like REPLACE(?,' ','')";
			list.add(type.getName());
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		return pageModel;
	}

	@Override
	public boolean delType(List<Object> ids) {
		String sql="delete from type where id=?";
		return update(sql, ids);
	}

	@Override
	public boolean addType(Type type) {
		List<Object> list=new ArrayList<>();
		list.add(type.getName());
		list.add(type.getCreateDate());
		list.add(type.getState());
		list.add(type.getUser().getId());
		list.add(type.getModifyDate());
		String sql="insert into type values(null,?,?,?,?,?)";
		return update(sql, list);
	}

	@Override
	public boolean editType(Type type) {
		String sql="update type set";
		int flag=0;
		List<Object> list=new ArrayList<>();
		
		if (type.getName()!=null) {
			sql+=" name=?";
			list.add(type.getName());
			flag++;
		}
		if (type.getUser()!=null) {
			if (flag==1) {
				sql+=",user_id=?";
			}else {
				sql+=" user_id=?";;
			}
			list.add(type.getUser().getId());
			flag++;
		}
		if (type.getModifyDate()!=null) {
			if (flag>0) {
				sql+=",modify_date=?";
			}else {
				sql=" modify_date=?";
			}
			list.add(type.getModifyDate());
		}
		sql+=" where ID=?";
		System.out.println(sql);
		list.add(type.getId());
		
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
	}



}
