package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.Dept;
import com.gec.hrm.bean.PageModel;

public interface DeptDao {
	List<Dept> find();
	
	PageModel<Dept> findAll(int pageIndex);
	
	Dept findById(int id);
	
	Dept findByName(String name);
	
	PageModel<Dept> findByNameLike(int pageIndex,Dept dept);

	
	boolean delDept(List<Object> ids);
	
	boolean add(Dept dept);
	
	boolean edit(Dept dept);

}
