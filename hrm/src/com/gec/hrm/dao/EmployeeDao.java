package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.Employee;
import com.gec.hrm.bean.PageModel;

public interface EmployeeDao {
	PageModel<Employee> findAll(int pageIndex);
	
	List<Employee> find();

	Employee findById(int id);
	
	Employee findByName(String name);
	
	PageModel<Employee> findByNameLike(int pageIndex,Employee employee);

	boolean delEmployee(List<Object> ids);
	
	boolean addEmployee(Employee employee);
	
	boolean editEmployee(Employee employee);
	
	boolean findByCardId(String id);
}
