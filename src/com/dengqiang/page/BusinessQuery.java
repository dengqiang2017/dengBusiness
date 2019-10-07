package com.dengqiang.page;

import org.apache.commons.lang.StringUtils;

public class BusinessQuery extends ObjectQuery {
	private int level;//查询用户状态
	@Override
	public void addHql() {
		if (StringUtils.isNotBlank(searchKey)) {
			String param = "%" + searchKey + "%";
			addHql("o.businessName like ? ", param);
		}
			addHql("o.level = ? ", level);
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
