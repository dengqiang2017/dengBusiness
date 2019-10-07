package com.dengqiang.service;

import java.util.List;

import com.dengqiang.bean.HousingInfo;
import com.dengqiang.bean.OrderBean;
import com.dengqiang.bean.dictionary.DictionaryDetail;

public interface IDictionaryDetailService extends IBaseService<DictionaryDetail> {
	/**
	 * 根据数据字典名字查询明细
	 * @param name
	 * @return
	 */
	List<DictionaryDetail> getDetailsByName(String name)throws Exception;
	/**
	 * 更新数据字典从节目获取到的数据中
	 * @param housingInfo
	 */
	void updateDictionary(HousingInfo housingInfo)throws Exception;

}
