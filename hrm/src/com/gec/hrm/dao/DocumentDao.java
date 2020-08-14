package com.gec.hrm.dao;


import java.util.List;

import com.gec.hrm.bean.Document;
import com.gec.hrm.bean.PageModel;

public interface DocumentDao {
	PageModel<Document> findAll(int pageIndex);
	
	Document findById(int id);
	
	boolean uploadFile(Document document);
	
	PageModel<Document> findByTitleLike(int pageIndex,Document document);
	
	List<Document> find();
	
	boolean delDoc(List<Object> ids);
	
	boolean editDoc(Document document);
}
