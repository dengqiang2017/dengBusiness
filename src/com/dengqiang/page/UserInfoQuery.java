package com.dengqiang.page;

import org.apache.commons.lang.StringUtils;

public class UserInfoQuery extends ObjectQuery{
	private Long businessId;
	private Integer clientType;
	@Override
	public void addHql() {
		if (StringUtils.isNotBlank(searchKey)) {
			String param = "%" + searchKey + "%";
			addHql("(o.username like ? or o.phone like ?)", param,param);
		}
		if (businessId!=null) {
			addHql(" o.business.id=? ",businessId);
		}
		if (clientType!=null) {
			addHql(" o.clientType=? ",clientType);
		}
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
}
