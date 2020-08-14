package com.gec.hrm.bean;

import java.util.List;

public class PageModel<T> {
	private int totalRecordSum;//总行数
	private int pageSize=3;//页面大小
	private int pageIndex;//当前页
	private int totalPageSum;//页面总数
	private List<T> list;//存储当前页数据
	
	
	
	public int getTotalRecordSum() {
		return totalRecordSum;
	}
	public void setTotalRecordSum(int totalRecordSum) {
		this.totalRecordSum = totalRecordSum;
		this.setTotalPageSum();
	}
	public int getPageSize() {
		return pageSize;
	}
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//		this.setPageCount();//当总页数改变，重新计算
//	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalPageSum() {
		return totalPageSum;
	}
	public void setTotalPageSum() {
		this.totalPageSum = this.totalRecordSum%this.pageSize==0?
				this.totalRecordSum/this.pageSize:this.totalRecordSum/this.pageSize+1;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
