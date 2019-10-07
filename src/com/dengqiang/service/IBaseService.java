package com.dengqiang.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.dengqiang.page.ObjectQuery;
import com.dengqiang.page.PageList;

public interface IBaseService<T> {
	void save(T t)throws Exception;

	void update(T t)throws Exception;
	
	void merge(T t)throws Exception;

	void delete(Serializable id)throws Exception;

	T get(Serializable id)throws Exception;

	List<T> getAll()throws Exception;

	/**
	 * 分页查询
	 * @param objectQuery
	 * @return
	 */

	PageList<T> findQuery(ObjectQuery query)throws Exception;

	/**
	 * 查询缓存
	 * @param objectQuery
	 * @return
	 */
	PageList<T> queryCache(ObjectQuery query)throws Exception;

/**
 * 将查询数据封装成execl导出
 * @param headers 
 * @param listContext
 * @param sheet
 * @return 文件下载流
 * @throws Exception
 */
	InputStream downXls(String[] headers, List<String[]> listContext,
			String sheet) throws Exception;

}
