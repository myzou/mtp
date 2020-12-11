package com.cter.util;

import java.util.List;

/**
 * Layui 分类
 * @author op1768
 *
 */
public class LayuiPage<T>{
	
	private List<T> datas;
	private int countSize;//总条数
	private int page;//页码
	private int limit;//每页条数
	private int  pageSize;//总页码 备用
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getCountSize() {
		return countSize;
	}
	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

}
