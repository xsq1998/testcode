package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.bean.Notice;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;

public interface NoticeDao {
	List<Notice> find();
	
	PageModel<Notice> findAll(int pageIndex);
	
	Notice findById(int id);
	
	Notice findByName(String name);
	
	PageModel<Notice> findByNameLike(int pageIndex,Notice notice);

	
	boolean delNotice(List<Object> ids);
	
	boolean addNotice(Notice notice,User user);
	
	boolean editNotice(Notice notice);

}
