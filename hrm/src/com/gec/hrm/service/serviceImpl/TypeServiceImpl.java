package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.Type;
import com.gec.hrm.dao.TypeDao;
import com.gec.hrm.dao.daoImpl.TypeDaoImpl;
import com.gec.hrm.service.TypeService;

public class TypeServiceImpl implements TypeService {
	TypeDao td=new TypeDaoImpl();
	@Override
	public List<Type> find() {
		// TODO 自动生成的方法存根
		return td.find();
	}

	@Override
	public PageModel<Type> findAll(int pageIndex) {
		// TODO 自动生成的方法存根
		return td.findAll(pageIndex);
	}

	@Override
	public Type findById(int id) {
		// TODO 自动生成的方法存根
		return td.findById(id);
	}

	@Override
	public Type findByName(String name) {
		// TODO 自动生成的方法存根
		return td.findByName(name);
	}

	@Override
	public PageModel<Type> findByNameLike(int pageIndex, Type type) {
		// TODO 自动生成的方法存根
		return td.findByNameLike(pageIndex, type);
	}

	@Override
	public boolean delType(List<Object> ids) {
		// TODO 自动生成的方法存根
		return td.delType(ids);
	}

	@Override
	public boolean addType(Type type) {
		// TODO 自动生成的方法存根
		return td.addType(type);
	}

	@Override
	public boolean editType(Type type) {
		// TODO 自动生成的方法存根
		return td.editType(type);
	}


}
