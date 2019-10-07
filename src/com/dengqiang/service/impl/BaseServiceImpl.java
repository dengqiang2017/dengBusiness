package com.dengqiang.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.dao.BaseDAO;
import com.dengqiang.page.ObjectQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IBaseService;
@Service
@Transactional
public abstract  class BaseServiceImpl<T> implements IBaseService<T> {
	@Resource
	protected BaseDAO<T> baseDAO; 
	
	protected Class<T> entityClass;

	public BaseServiceImpl() {
		Class clazz=getClass();
		Type type= clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType=(ParameterizedType) type;
			this.entityClass=(Class<T>) parameterizedType.getActualTypeArguments()[0];
		} 
	}
	public void setBaseDAO(BaseDAO<T> baseDAO) {
		this.baseDAO = baseDAO;
	}
	public void save(T t)throws Exception{
		baseDAO.save(t);
	}

	public void update(T t)throws Exception{
		baseDAO.update(t);
	}
	public void merge(T t)throws Exception{
		baseDAO.merge(t);
	}

	public void delete(Serializable id)throws Exception{
		baseDAO.delete(entityClass,id);
	}

	public T get(Serializable id)throws Exception{
		return baseDAO.get(entityClass, id);
	}

	public List<T> getAll()throws Exception{
		return baseDAO.getAll(entityClass);
	}
	public PageList<T> findQuery(ObjectQuery objectQuery)throws Exception{
		return  baseDAO.findQuery(entityClass,objectQuery);
	}
 
	public PageList<T> queryCache(ObjectQuery objectQuery)throws Exception{
		return  baseDAO.queryCache(entityClass,objectQuery);
	}
	
	public List<Map<String, Object>> findQuery(Map<String, Object> map)throws Exception{
		return baseDAO.findQuery(map);
	}
	public InputStream downXls(String[] headers, List<String[]> listContext,String sheetName) throws Exception {
		ByteArrayOutputStream os=new ByteArrayOutputStream();
//		WritableWorkbook workbook =Workbook.createWorkbook(os);
//		WritableSheet sheet = workbook.createSheet(sheetName, 0); 
//		int row=listContext.size();
//		int line=headers.length;
//		for (int j = 0; j < headers.length; j++) {//列
//			//获取第一行的第0个string数组的数据然后在加入数组的索引，所以get(里面写i)[里面加入j]
//			Label label = new Label(j, 0,headers[j]);
//			sheet.addCell(label);
//		}
//		//外层循环为行2，内层循环为列0，
//		for (int i = 1; i < row+1; i++) {//行
//			for (int j = 0; j < line; j++) {//列
//				//获取第一行的第0个string数组的数据然后在加入数组的索引，所以get(里面写i)[里面加入j]
//				Label label = new Label(j, i,listContext.get(i-1)[j]);
//				sheet.addCell(label);
//			}
//		}
//		workbook.write();
//		workbook.close(); 
		return new ByteArrayInputStream(os.toByteArray()); 
	}
}
