package com.dengqiang.service;

import com.dengqiang.bean.Business;

public interface IInitLoadService extends IBaseService<Business>{
	/**
	 * 创建存储过程
	 */
	public  void initProcedure();
	/**
	 * 初始化数据
	 */
	public  void initData();
}
