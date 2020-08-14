package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.Employee;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.EmployeeDao;
import com.gec.hrm.service.DeptService;
import com.gec.hrm.service.JobService;
import com.gec.hrm.service.serviceImpl.DeptServiceImpl;
import com.gec.hrm.service.serviceImpl.JobServiceImpl;
import com.gec.hrm.util.Util;

public class EmployeeDaoImpl extends Util<Employee> implements EmployeeDao {
	
	DeptService ds=new DeptServiceImpl();
	JobService js=new JobServiceImpl();
	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		
		Employee e=new Employee();
		e.setId(rs.getInt("ID"));
		e.setName(rs.getString("NAME"));
		e.setCardId(rs.getString("CARD_ID"));
		e.setAddress(rs.getString("ADDRESS"));
		e.setPostCode(rs.getString("POST_CODE"));
		e.setTel(rs.getString("TEL"));
		e.setPhone(rs.getString("PHONE"));
		e.setQq(rs.getString("QQ_NUM"));
		e.setEmail(rs.getString("EMAIL"));
		e.setSex(rs.getInt("SEX"));
		e.setParty(rs.getString("PARTY"));
		e.setBirthday(rs.getDate("BIRTHDAY"));
		e.setRace(rs.getString("RACE"));
		e.setEducation(rs.getString("EDUCATION"));
		e.setSpeciality(rs.getString("SPECIALITY"));
		e.setHobby(rs.getString("HOBBY"));
		e.setRemark(rs.getString("REMARK"));
		e.setCreateDate(rs.getDate("CREATE_DATE"));
		e.setState(rs.getInt("state"));
		e.setDept(ds.findById(rs.getInt("dept_id")));
		e.setJob(js.findById(rs.getInt("job_id")));
		return e;
	}

	@Override
	public PageModel<Employee> findAll(int pageIndex) {
		PageModel<Employee> pageModel=new PageModel<>();
		String sql="select count(*) from employee";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from employee limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		return pageModel;
	}

	@Override
	public List<Employee> find() {
		return query("select * from employee");
	}

	@Override
	public Employee findById(int id) {
		String sql="select * from employee where ID=?";
		List<Employee> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Employee findByName(String name) {
		String sql="select * from employee where NAME=?";
		List<Employee> list=query(sql, name);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public PageModel<Employee> findByNameLike(int pageIndex, Employee employee) {
		PageModel<Employee> pageModel=new PageModel<>();
		List<Object> list=new ArrayList<>();
		pageModel.setPageIndex(pageIndex);
		String sql="select * from employee where 1=1";
		if (employee.getJob()!=null) {
			sql+=" and job_id=?";
			list.add(employee.getJob().getId());
		}
		if (employee.getSex()>0) {
			sql+=" and SEX=?";
			list.add(employee.getSex());
		}
		if (employee.getName()!=null) {
			sql+=" and NAME like REPLACE(?,' ','')";
			list.add(employee.getName());
		}
		if (employee.getPhone()!=null) {
			sql+=" and PHONE like REPLACE(?,' ','')";
			list.add(employee.getPhone());
		}
		if (employee.getCardId()!=null) {
			sql+=" and CARD_ID like REPLACE(?,' ','')";
			list.add(employee.getCardId());
		}
		if (employee.getDept()!=null) {
			sql+=" and dept_id=?";
			list.add(employee.getDept().getId());
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		
		return pageModel;
	}

	@Override
	public boolean delEmployee(List<Object> ids) {
		String sql="delete from employee where ID=?";
		return update(sql, ids);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		List<Object> list=new ArrayList<>();
		list.add(employee.getName());
		list.add(employee.getCardId());
		list.add(employee.getAddress());
		list.add(employee.getPostCode());
		list.add(employee.getTel());
		list.add(employee.getPhone());
		list.add(employee.getQq());
		list.add(employee.getEmail());
		list.add(employee.getSex());
		list.add(employee.getParty());//政治面貌
		list.add(employee.getBirthday());
		list.add(employee.getRace());//民族
		list.add(employee.getEducation());//学历
		list.add(employee.getSpeciality());//专业
		list.add(employee.getHobby());
		list.add(employee.getRemark());
		list.add(employee.getCreateDate());//
		list.add(0);
		list.add(employee.getDept().getId());
		list.add(employee.getJob().getId());
		
		String sql="insert into employee values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		return update(sql, list);
	}

	@Override
	public boolean editEmployee(Employee employee) {
		String sql="update employee set ";
		int flag=0;
		List<Object> list=new ArrayList<>();
		if (employee.getName()!=null) {
			sql+="NAME=?,";
			list.add(employee.getName());
			flag++;
		}
		if (employee.getCardId()!=null) {
			sql+="CARD_ID=?,";
			list.add(employee.getCardId());
			flag++;		
			}
		if (employee.getAddress()!=null) {
			sql+="ADDRESS=?,";
			list.add(employee.getAddress());
			flag++;
		}
		if (employee.getPostCode()!=null) {
			sql+="POST_CODE=?,";
			list.add(employee.getPostCode());
			flag++;
		}
		if (employee.getTel()!=null) {
			sql+="TEL=?,";
			list.add(employee.getTel());
			flag++;
		}if (employee.getPhone()!=null) {
			sql+="PHONE=?,";
			list.add(employee.getPhone());
			flag++;
		}
		if (employee.getQq()!=null) {
			sql+="QQ_NUM=?,";
			list.add(employee.getQq());
			flag++;
		}
		if (employee.getEmail()!=null) {
			sql+="EMAIL=?,";
			list.add(employee.getEmail());
			flag++;
		}
		if (employee.getSex()>0) {
			sql+="SEX=?,";
			list.add(employee.getSex());
			flag++;
		}
		if (employee.getParty()!=null) {
			sql+="PARTY=?,";
			list.add(employee.getParty());
			flag++;
		}
		if (employee.getBirthday()!=null) {
			sql+="BIRTHDAY=?,";
			list.add(employee.getBirthday());
			flag++;
		}
		if (employee.getRace()!=null) {
			sql+="RACE=?,";
			list.add(employee.getRace());
			flag++;
		}
		if (employee.getEducation()!=null) {
			sql+="EDUCATION=?,";
			list.add(employee.getEducation());
			flag++;
		}
		if (employee.getSpeciality()!=null) {
			sql+="SPECIALITY=?,";
			list.add(employee.getSpeciality());
			flag++;
		}
		if (employee.getHobby()!=null) {
			sql+="HOBBY=?,";
			list.add(employee.getHobby());
			flag++;
		}
		if (employee.getRemark()!=null) {
			sql+="REMARK=?,";
			list.add(employee.getRemark());
			flag++;
		}
		if (employee.getDept()!=null) {
			sql+="dept_id=?,";
			list.add(employee.getDept().getId());
			flag++;
		}
		if (employee.getJob()!=null) {
			sql+="job_id=?";
			list.add(employee.getJob().getId());
			flag++;
		}
		sql+=" where ID=?";
		list.add(employee.getId());
		
		System.out.println(sql+","+flag);
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
	}

	@Override
	public boolean findByCardId(String id) {
		String sql="select * from employee where CARD_ID=?";
		List<Employee> list=query(sql, id);
		if (list.size()>0) {
			return true;
		}else {
			return false;
		}
	}

}
