package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.Dept;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.DeptDao;
import com.gec.hrm.util.Util;

public class DeptDaoImpl extends Util<Dept> implements DeptDao {
	
	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dept=new Dept();
		dept.setId(rs.getInt("ID"));
		dept.setName(rs.getString("NAME"));
		dept.setRemark(rs.getString("REMARK"));
		dept.setState(rs.getInt("state"));
		return dept;
	}

	@Override
	public List<Dept> find() {
		return query("select * from dept");
	}

	@Override
	public PageModel<Dept> findAll(int pageIndex) {
		PageModel<Dept> pageModel=new PageModel<>();
		String sql="select count(*) from dept";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from dept limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		
		return pageModel;
	}

	@Override
	public Dept findById(int id) {
		String sql="select * from dept where id=?";
		List<Dept> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public PageModel<Dept> findByNameLike(int pageIndex, Dept dept) {
		PageModel<Dept> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql="select * from dept where 1=1";
		if (dept.getName()!=null) {
			sql+=" and NAME like REPLACE(?,' ','')";
			list.add(dept.getName());
		}
		sql+=" limit ?,?";
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		System.out.println(sql);
		pageModel.setList(query(sql, list.toArray()));
		
		return pageModel;
	}

	@Override
	public boolean delDept(List<Object> ids) {
		String sql="delete from dept where ID=?";
		return update(sql, ids);
	}

	@Override
	public boolean add(Dept dept) {
		List<Object> list=new ArrayList<>();
		list.add(dept.getName());
		list.add(dept.getRemark());
		list.add(dept.getState());
		String sql="insert into dept values(null,?,?,?)";
		return update(sql, list);
	}

	@Override
	public boolean edit(Dept dept) {
		String sql="update dept set";
		int flag=0;
		List<Object> list=new ArrayList<>();
		
		if (dept.getName()!=null) {
			sql+=" NAME=?";
			list.add(dept.getName());
			flag++;
		}
		if (dept.getRemark()!=null) {
			if (flag==1) {
				sql+=",REMARK=?";
				list.add(dept.getRemark());
			}else {
				sql+=" REMARK=?";
				list.add(dept.getRemark());
			}
			flag++;
		}
		sql+=" where ID=?";
		System.out.println(sql);
		
		list.add(dept.getId());
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
		
	}

	@Override
	public Dept findByName(String name) {
		String sql="select * from dept where NAME=?";
		List<Dept> list=query(sql, name);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

}
