package com.gec.hrm.service.serviceImpl;

import java.util.List;

import com.gec.hrm.bean.Notice;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.dao.NoticeDao;
import com.gec.hrm.dao.daoImpl.NoticeDaoImpl;
import com.gec.hrm.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	NoticeDao nd=new NoticeDaoImpl();
	@Override
	public List<Notice> find() {
		// TODO 自动生成的方法存根
		return nd.find();
	}

	@Override
	public PageModel<Notice> findAll(int pageIndex) {
		// TODO 自动生成的方法存根
		return nd.findAll(pageIndex);
	}

	@Override
	public Notice findById(int id) {
		// TODO 自动生成的方法存根
		return nd.findById(id);
	}

	@Override
	public Notice findByName(String name) {
		// TODO 自动生成的方法存根
		return nd.findByName(name);
	}

	@Override
	public PageModel<Notice> findByNameLike(int pageIndex, Notice notice) {
		// TODO 自动生成的方法存根
		return nd.findByNameLike(pageIndex, notice);
	}

	@Override
	public boolean delNotice(List<Object> ids) {
		// TODO 自动生成的方法存根
		return nd.delNotice(ids);
	}

	@Override
	public boolean addNotice(Notice notice,User user) {
		// TODO 自动生成的方法存根
		return nd.addNotice(notice,user);
	}

	@Override
	public boolean editNotice(Notice notice) {
		// TODO 自动生成的方法存根
		return nd.editNotice(notice);
	}



}
