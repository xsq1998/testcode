package com.gec.hrm.service.serviceImpl;



import java.util.List;

import com.gec.hrm.bean.Document;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.dao.DocumentDao;
import com.gec.hrm.dao.daoImpl.DocumentDaoImpl;
import com.gec.hrm.service.DocumentService;

public class DocumentServiceImpl implements DocumentService {
	DocumentDao dd=new DocumentDaoImpl();

	@Override
	public Document findById(int id) {
		return dd.findById(id);
	}

	@Override
	public boolean uploadFile(Document document) {
		
		return dd.uploadFile(document);
	}

	@Override
	public PageModel<Document> findAll(int pageIndex) {
		// TODO 自动生成的方法存根
		return dd.findAll(pageIndex);
	}

	@Override
	public PageModel<Document> findByTitleLike(int pageIndex, Document document) {
		// TODO 自动生成的方法存根
		return dd.findByTitleLike(pageIndex, document);
	}

	@Override
	public List<Document> find() {
		// TODO 自动生成的方法存根
		return dd.find();
	}

	@Override
	public boolean delDoc(List<Object> ids) {
		// TODO 自动生成的方法存根
		return dd.delDoc(ids);
	}

	@Override
	public boolean editDoc(Document document) {
		// TODO 自动生成的方法存根
		return dd.editDoc(document);
	}

}
