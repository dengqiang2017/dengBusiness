package com.dengqiang.service;

import com.dengqiang.bean.dictionary.DataDictionary;

public interface IDataDictionaryService extends IBaseService<DataDictionary> {
	/**
	 * 查询是否已经初始化了
	 * @return
	 */
	int queryCount()throws Exception;

}
