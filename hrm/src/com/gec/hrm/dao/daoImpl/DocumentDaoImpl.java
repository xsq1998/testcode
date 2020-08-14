package com.gec.hrm.dao.daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.hrm.bean.Document;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.DocumentDao;
import com.gec.hrm.service.UserService;
import com.gec.hrm.service.serviceImpl.UserServiceImpl;
import com.gec.hrm.util.Util;

public class DocumentDaoImpl extends Util<Document> implements DocumentDao {

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		UserService us=new UserServiceImpl();
		Document document=new Document();
		document.setId(rs.getInt("ID"));
		document.setTitle(rs.getString("TITLE"));
		document.setFileName(rs.getString("filename"));
		document.setFileType(rs.getString("filetype"));
		document.setFileBytes(rs.getLong("filebytes"));
		document.setRemark(rs.getString("REMARK"));
		document.setCreateDate(rs.getDate("CREATE_DATE"));
		document.setUser(us.findById(rs.getInt("USER_ID")));
		return document;
	}

	@Override
	public PageModel<Document> findAll(int pageIndex) {
		PageModel<Document> pageModel=new PageModel<>();
		String sql="select count(*) from document";
		pageModel.setTotalRecordSum(queryCount(sql));
		sql="select * from document limit ?,?";
		pageModel.setList(query(sql, (pageIndex-1)*pageModel.getPageSize(),pageModel.getPageSize()));
		
		return pageModel;
	}

	@Override
	public Document findById(int id) {
		String sql="select * from document where id=?";
		List<Document> list=query(sql, id);
		if (list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public boolean uploadFile(Document document) {
		List<Object> list=new ArrayList<>();
		list.add(document.getTitle());
		list.add(document.getFileName());
		list.add(document.getFileType());
		list.add(document.getFileBytes());
		list.add(document.getRemark());
		list.add(document.getCreateDate());
		list.add(document.getUser().getId());
		
		String sql="insert into document values(null,?,?,?,?,?,?,?)";
		return update(sql, list);
	}

	@Override
	public PageModel<Document> findByTitleLike(int pageIndex, Document document) {
		PageModel<Document> pageModel=new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		List<Object> list=new ArrayList<>();
		String sql="select * from document where 1=1";
		if (document.getTitle()!=null) {
			sql+=" and TITLE like REPLACE(?,' ','')";
			list.add(document.getTitle());
		}
		sql+=" limit ?,?";
		System.out.println(sql);
		list.add((pageIndex-1)*pageModel.getPageSize());
		list.add(pageModel.getPageSize());
		pageModel.setList(query(sql, list.toArray()));
		return pageModel;
	}

	@Override
	public List<Document> find() {
		return query("select * from document");
	}

	@Override
	public boolean delDoc(List<Object> ids) {
		String sql="delete from document where ID=?";
		return update(sql, ids);
	}

	@Override
	public boolean editDoc(Document document) {
		String sql="update document set ";
		int flag=0;
		List<Object> list=new ArrayList<>();
		
		if (document.getTitle()!=null) {
			sql+="TITLE=?";
			list.add(document.getTitle());
			flag++;
		}
		if (document.getFileName()!=null) {
			if (flag>0) {
				sql+=",filename=?,filetype=?,filebytes=?";
			}else {
				sql+="filename=?,filetype=?,filebytes=?";
			}
			list.add(document.getFileName());
			list.add(document.getFileType());
			list.add(document.getFileBytes());
		}
		if (document.getRemark()!=null) {
			if (flag>0) {
				sql+=",REMARK=?";
			}else {
				sql+="REMARK=?";
			}
			list.add(document.getRemark());
		}
		
		sql+=" where ID=?";
		System.out.println(sql);
		list.add(document.getId());
		
		if (flag>0) {
			return update(sql, list);
		}else {
			return false;
		}
		
	}
	
	

}
