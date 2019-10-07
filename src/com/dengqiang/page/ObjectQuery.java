package com.dengqiang.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ObjectQuery {
	// 当前页
	private int page=1;
	//每页记录数
	private int pageRecord=10;
	private String sord="asc";
	private String sidx;
	//存放hql的stringbuilder
	private StringBuilder builder=new StringBuilder();
	//存放参数的list
	private List<Object> params=new ArrayList<Object>();
	//
	protected String searchKey;
	
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageRecord() {
		return pageRecord;
	}
	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}
	public String getHql() {
		if (builder.length()==0) {
			addHql();
		}
		return builder.toString();
	}
	public List<Object> getParams() {
		return params;
	}
	//由子类进行Hql组装
	public abstract void addHql();
	//组合Hql和添加对应的参数
	public void addHql(String hql,Object...objects) {
		if (builder.length()==0) {
			builder.append(hql);
		}else{
			builder.append(" and ").append(hql);
		}
		params.addAll(Arrays.asList(objects));
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	
//	public abstract String getCount();
}
