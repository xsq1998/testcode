package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.Job;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.JobDao;
import com.gec.hrm.dao.daoImpl.JobDaoImpl;
import com.gec.hrm.service.JobService;

public class JobServiceImpl implements JobService {
	JobDao jd=new JobDaoImpl();

	@Override
	public List<Job> find() {
		// TODO 自动生成的方法存根
		return jd.find();
	}

	@Override
	public PageModel<Job> findAll(int pageIndex) {
		// TODO 自动生成的方法存根
		return jd.findAll(pageIndex);
	}

	@Override
	public Job findById(int id) {
		// TODO 自动生成的方法存根
		return jd.findById(id);
	}

	@Override
	public Job findByName(String name) {
		// TODO 自动生成的方法存根
		return jd.findByName(name);
	}

	@Override
	public PageModel<Job> findByNameLike(int pageIndex, Job job) {
		// TODO 自动生成的方法存根
		return jd.findByNameLike(pageIndex, job);
	}

	@Override
	public boolean delJob(List<Object> ids) {
		// TODO 自动生成的方法存根
		return jd.delJob(ids);
	}

	@Override
	public boolean addJob(Job job) {
		// TODO 自动生成的方法存根
		return jd.addJob(job);
	}

	@Override
	public boolean editJob(Job job) {
		// TODO 自动生成的方法存根
		return jd.editJob(job);
	}

}
