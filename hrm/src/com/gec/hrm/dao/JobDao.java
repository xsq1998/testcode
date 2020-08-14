package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.Job;
import com.gec.hrm.bean.PageModel;

public interface JobDao {
	List<Job> find();
	
	PageModel<Job> findAll(int pageIndex);
	
	Job findById(int id);
	
	Job findByName(String name);
	
	PageModel<Job> findByNameLike(int pageIndex,Job job);

	
	boolean delJob(List<Object> ids);
	
	boolean addJob(Job job);
	
	boolean editJob(Job job);
}
