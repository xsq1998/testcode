package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gec.hrm.bean.Notice;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.dao.NoticeDao;
import com.gec.hrm.service.TypeService;
import com.gec.hrm.service.UserService;
import com.gec.hrm.service.serviceImpl.TypeServiceImpl;
import com.gec.hrm.service.serviceImpl.UserServiceImpl;
import com.gec.hrm.util.Util;

public class NoticeDaoImpl extends Util<Notice> implements NoticeDao {
	TypeService ts=new TypeServiceImpl();
	UserService us=new UserServiceImpl();

	@Override
	public Notice getEntity(ResultSet rs) throws Exception {
		Notice notice=new Notice();
		notice.setId(rs.getInt("id"));
		notice.setName(rs.getString("name"));
		notice.setCreateDate(rs.getDate("create_date"));
		notice.setType(ts.findById(rs.getInt("type_id")));
		notice.setContent(rs.getString("content"));
		notice.setUser(us.findById(rs.getInt("user_id")));
		notice.setModifyDate(new Date());
		
		return notice;
	}

	@Override
	public List<Notice> find() {
		// TODO 自动生成的方法存根
		return query("select *from notice");
	}

	@Override
	public PageModel<Notice> findAll(int pageIndex) {
		PageModel<Notice> pageModel=new PageModel<>();
		String sql="select count(*) from notice";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from notice limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		return pageModel;
	}

	@Override
	public Notice findById(int id) {
		String sql="select * from notice where id=?";
		List<Notice> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Notice findByName(String name) {
		String sql="select * from notice where id=?";
		List<Notice> list=query(sql, name);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public PageModel<Notice> findByNameLike(int pageIndex, Notice notice) {
		PageModel<Notice> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql="select * from notice where 1=1";
		if (notice.getName()!=null) {
			sql+=" and name like REPLACE(?,' ','')";
			list.add(notice.getName());
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		return pageModel;
	}

	@Override
	public boolean delNotice(List<Object> ids) {
		String sql="delete from notice where id=?";
		return update(sql, ids);
	}

	@Override
	public boolean addNotice(Notice notice,User user) {
		
		List<Object> list=new ArrayList<>();
		list.add(notice.getName());
		list.add(new Date());
		list.add(notice.getType().getId());
		list.add(notice.getContent());
		list.add(user.getId());
		list.add(new Date());
		String sql="insert into notice values(null,?,?,?,?,?,?)";
		return update(sql, list);
	}

	@Override
	public boolean editNotice(Notice notice) {
		String sql="update notice set";
		int flag=0;
		List<Object> list=new ArrayList<>();
		
		if (notice.getName()!=null) {
			sql+=" name=?";
			list.add(notice.getName());
			flag++;
		}
		if (notice.getType()!=null) {
			if (flag==1) {
				sql+=",type_id=?";
			}else {
				sql+=" type_id=?";;
			}
			list.add(notice.getType().getId());
			flag++;
		}
		if (notice.getContent()!=null) {
			if (flag>0) {
				sql+=",content=?";
			}else {
				sql=" content=?";
			}
			list.add(notice.getContent());
		}
		if (notice.getModifyDate()!=null) {
			if (flag>0) {
				sql+=",modify_date=?";
			}else {
				sql=" modify_date=?";
			}
			list.add(new Date());
		}
		sql+=" where ID=?";
		System.out.println(sql);
		list.add(notice.getId());
		
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
	}



}
