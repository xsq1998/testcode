package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.Job;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.JobDao;
import com.gec.hrm.util.Util;

public class JobDaoImpl extends Util<Job> implements JobDao {

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job job=new Job();
		job.setId(rs.getInt("ID"));
		job.setName(rs.getString("NAME"));
		job.setRemark(rs.getString("REMARK"));
		job.setState(rs.getInt("state"));
		return job;
	}

	@Override
	public List<Job> find() {
		return query("select * from job");
	}

	@Override
	public PageModel<Job> findAll(int pageIndex) {
		PageModel<Job> pageModel=new PageModel<>();
		String sql="select count(*) from job";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from job limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		return pageModel;
	}

	@Override
	public Job findById(int id) {
		String sql="select * from job where id=?";
		List<Job> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Job findByName(String name) {
		String sql="select * from job where NAME=?";
		List<Job> list=query(sql, name);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public PageModel<Job> findByNameLike(int pageIndex, Job job) {
		PageModel<Job> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql="select * from job where 1=1";
		if (job.getName()!=null) {
			sql+=" and NAME like REPLACE(?,' ','')";
			list.add(job.getName());
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		
		return pageModel;
	}

	@Override
	public boolean delJob(List<Object> ids) {
		String sql="delete from job where ID=?";
		return update(sql, ids);
	}

	@Override
	public boolean addJob(Job job) {
		List<Object> list=new ArrayList<>();
		list.add(job.getName());
		list.add(job.getRemark());
		list.add(job.getState());
		String sql="insert into job values(null,?,?,?)";
		return update(sql, list);
	}

	@Override
	public boolean editJob(Job job) {
		String sql="update job set";
		int flag=0;
		List<Object> list=new ArrayList<>();
		
		if (job.getName()!=null) {
			sql+=" NAME=?";
			list.add(job.getName());
			flag++;
		}
		if (job.getRemark()!=null) {
			if (flag==1) {
				sql+=",REMARK=?";
				list.add(job.getRemark());
			}else {
				sql+=" REMARK=?";
				list.add(job.getRemark());
			}
			flag++;
		}
		sql+=" where ID=?";
		System.out.println(sql);
		
		list.add(job.getId());
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
	}

}
