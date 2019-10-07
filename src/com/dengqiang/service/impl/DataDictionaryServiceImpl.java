package com.dengqiang.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.dictionary.DataDictionary;
import com.dengqiang.service.IDataDictionaryService;

@Service
@Transactional
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary> implements IDataDictionaryService {

	@Override
	public int queryCount() throws Exception {
		String hql="select count(o) from DataDictionary o";
		return baseDAO.findCountByHql(hql);
	}

}
