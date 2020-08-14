package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.Employee;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.EmployeeDao;
import com.gec.hrm.dao.daoImpl.EmployeeDaoImpl;
import com.gec.hrm.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDao ed=new EmployeeDaoImpl();
	@Override
	public PageModel<Employee> findAll(int pageIndex) {
		return ed.findAll(pageIndex);
	}

	@Override
	public List<Employee> find() {
		// TODO 自动生成的方法存根
		return ed.find();
	}

	@Override
	public Employee findById(int id) {
		// TODO 自动生成的方法存根
		return ed.findById(id);
	}

	@Override
	public Employee findByName(String name) {
		// TODO 自动生成的方法存根
		return ed.findByName(name);
	}

	@Override
	public PageModel<Employee> findByNameLike(int pageIndex, Employee employee) {
		// TODO 自动生成的方法存根
		return ed.findByNameLike(pageIndex, employee);
	}

	@Override
	public boolean delEmployee(List<Object> ids) {
		// TODO 自动生成的方法存根
		return ed.delEmployee(ids);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		// TODO 自动生成的方法存根
		return ed.addEmployee(employee);
	}

	@Override
	public boolean editEmployee(Employee employee) {
		// TODO 自动生成的方法存根
		return ed.editEmployee(employee);
	}

	@Override
	public boolean findByCardId(String id) {
		// TODO 自动生成的方法存根
		return ed.findByCardId(id);
	}

}
