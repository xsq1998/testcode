package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.Dept;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.DeptDao;
import com.gec.hrm.dao.daoImpl.DeptDaoImpl;
import com.gec.hrm.service.DeptService;

public class DeptServiceImpl implements DeptService {
	DeptDao dd=new DeptDaoImpl();

	@Override
	public List<Dept> find() {
		// TODO 自动生成的方法存根
		return dd.find();
	}

	@Override
	public PageModel<Dept> findAll(int pageIndex) {
		// TODO 自动生成的方法存根
		return dd.findAll(pageIndex);
	}

	@Override
	public Dept findById(int id) {
		// TODO 自动生成的方法存根
		return dd.findById(id);
	}

	@Override
	public PageModel<Dept> findByNameLike(int pageIndex, Dept dept) {
		// TODO 自动生成的方法存根
		return dd.findByNameLike(pageIndex, dept);
	}

	@Override
	public boolean delDept(List<Object> ids) {
		// TODO 自动生成的方法存根
		return dd.delDept(ids);
	}

	@Override
	public boolean add(Dept dept) {
		// TODO 自动生成的方法存根
		return dd.add(dept);
	}

	@Override
	public boolean edit(Dept dept) {
		// TODO 自动生成的方法存根
		return dd.edit(dept);
	}

	@Override
	public Dept findByName(String name) {
		// TODO 自动生成的方法存根
		return dd.findByName(name);
	}

}
