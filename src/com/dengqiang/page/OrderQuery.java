package com.dengqiang.page;

import org.apache.commons.lang.StringUtils;

public class OrderQuery extends ObjectQuery {

	private Long business;
	private String orderNo;
	private int type; 
	private int delFlag=1; 
	private int status=1;
	private String all;
	@Override
	public void addHql() {
		if (business!=null&&business>0&&StringUtils.isBlank(all)) {//录入人//查询所有人的
			addHql(" o.business.id=? ",business);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			addHql(" o.orderNumber=? ",orderNo);
		}
		if (status>=0) {
			addHql(" o.status =?",status);//信息状态类型
		}
		addHql(" o.housingInfo.type =?",type);//信息类型
		addHql(" o.delFlag =?",delFlag);//信息是否被删除
	}
	public Long getBusiness() {
		return business;
	}
	public void setBusiness(Long business) {
		this.business = business;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}

}
