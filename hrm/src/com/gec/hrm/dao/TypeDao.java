package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.Type;

public interface TypeDao {
	List<Type> find();
	
	PageModel<Type> findAll(int pageIndex);
	
	Type findById(int id);
	
	Type findByName(String name);
	
	PageModel<Type> findByNameLike(int pageIndex,Type type);

	
	boolean delType(List<Object> ids);
	
	boolean addType(Type type);
	
	boolean editType(Type type);

}
