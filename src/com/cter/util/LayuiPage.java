package com.cter.util;

import java.util.List;

/**
 * Layui ����
 * @author op1768
 *
 */
public class LayuiPage<T>{
	
	private List<T> datas;
	private int countSize;//������
	private int page;//ҳ��
	private int limit;//ÿҳ����
	private int  pageSize;//��ҳ�� ����
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
