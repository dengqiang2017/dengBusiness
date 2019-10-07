package com.dengqiang.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sun.util.logging.resources.logging;

import com.dengqiang.page.ObjectQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.util.LoggerUtils;

@Repository
public class BaseDAO<T> {

	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	public void save(T t)throws Exception{
		getSession().save(t);
	}

	public void update(T t)throws Exception{
		getSession().update(t);
	}
	public void merge(T t)throws Exception{
		getSession().merge(t);
	}

	public void delete(Class<T> entityClass, Serializable id)throws Exception{
		getSession().delete(get(entityClass, id));
	}

	
	public T get(Class<T> entityClass, Serializable id)throws Exception{
		return (T)this.getSession().get(entityClass,id);
	}

	public List<T> getAll(Class<T> entityClass)throws Exception{
		String hql = "from " + entityClass.getName();
		Query query= this.getSession().createQuery(hql);
		List<?> list=query.list();
		return (List<T>) list;
	}
	/**
	 * 预留增加的查询
	 * @param map 查询条件
	 * @return 结果列表
	 */
	public List<Map<String, Object>> findQuery(Map<String, Object> map)throws Exception{
		 
		return null;
	}
	/**
	 * 分页查询
	 * @param entityClass 实体类的class
	 * @param objectQuery 查询条件对象
	 * @return 分页后查询的数据
	 */
	public PageList<T> findQuery(Class<T> entityClass,
			final ObjectQuery objectQuery)throws Exception{
		int count = findCount(entityClass, objectQuery);
		if (count == 0) {
			return new PageList<T>();
		}
		final StringBuilder builder = new StringBuilder("select o from "
				+ entityClass.getName() + " o");
		String where = objectQuery.getHql();
		if (StringUtils.isNotBlank(where)) {
			builder.append(" where ").append(where);
		}
		final PageList<T> pageList = new PageList<T>(
				objectQuery.getPage(), objectQuery.getPageRecord(),
				count);
		if (StringUtils.isNotBlank(objectQuery.getSidx())) {
			builder.append(" order by o.").append(objectQuery.getSidx())
			.append(" ").append(objectQuery.getSord());
		}
		Query query= this.getSession().createQuery(builder.toString());
		List<Object> list = objectQuery.getParams();
		int index = 0;
		for (Object object : list) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		int first = (pageList.getPage() - 1)
		* pageList.getPageRecord();
		int max = pageList.getPageRecord();
		query.setFirstResult(first).setMaxResults(max);		
		List<T> rows =query.list();
		pageList.setRows(rows);
		return pageList;
	}
/**
 * 分页查询中的总数统计
 * @param entityClass 实体类的class
 * @param objectQuery 查询条件对象
 * @return 总的记录数
 */
	private int findCount(Class<T> entityClass, final ObjectQuery objectQuery)throws Exception{
		final StringBuilder builder = new StringBuilder("select count(o) from "
				+ entityClass.getName() + " o");
		String where = objectQuery.getHql();
		if (StringUtils.isNotBlank(where)) {
			builder.append(" where ").append(where);
		}
		int count =0;
		Query query= this.getSession().createQuery(builder.toString());
		List<Object> list = objectQuery.getParams();
		int index = 0;
		for (Object object : list) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		count= Integer.parseInt(query.uniqueResult().toString());
		return count;
	}
/**
 * 根据自定义hql查询
 * @param hql 自定义hql
 * @param obj 可变参数
 * @return 结果列表
 */
	public List findByHql(String hql, Object... obj)throws Exception{
		Query query= this.getSession().createQuery(hql);
		int index = 0;
		for (Object object : obj) {
			if (object!=null) {LoggerUtils.info(object);
				query.setParameter(index++, object);
			}
		}
//		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return  query.list();
	}
	/**
	 * 查询返回一条记录结果
	 * @param hql 查询语句,返回的值需要在hql中使用as进行明确 
	 * @param obj 可变查询参数
	 * @return 一个map集合,key--as的名称
	 */
	public Map<String, Object> findOneRecordByHql(String hql, Object... obj)throws Exception{
		Query query= this.getSession().createQuery(hql);
		int index = 0;
		for (Object object : obj) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		query.setMaxResults(1);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list=query.list();
		if (list.size()>0) {
			return (Map<String, Object>) list.get(0);
		}else{
			return null;
		}
	}
	public T findOneRecordByObj(String hql, Object... obj)throws Exception{
		Query query= this.getSession().createQuery(hql);
		int index = 0;
		for (Object object : obj) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		query.setMaxResults(1);
		List<T> list=query.list();
		if (list.size()>0) {
			return (T) list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询数据将返回一个int值,多个值下将只返回第一个值
	 * @param hql 查询语句
	 * @param obj 可变查询参数
	 * @return 一个int值
	 */
	public int findCountByHql(String hql,Object...obj)throws Exception{
		return Integer.parseInt(findOneColumnByHql(hql,obj).toString());
	}
	/**
	 *  查询一个只有一个字段的语句
	 * @param hql 查询语句
	 * @param obj 可变查询参数
	 * @return 一个object值
	 */
	public Object findOneColumnByHql(String hql,Object...obj)throws Exception{
		Query query=this.getSession().createQuery(hql);
		int index = 0;
		for (Object object : obj) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		query.setMaxResults(1);
		return query.uniqueResult();
	}
	
	/**
	 * 使用查询缓存进行查询
	 * @param entityClass 实体类的class
	 * @param objectQuery 查询条件对象
	 * @return 查询的缓存分页记录
	 */
	public PageList<T> queryCache(Class<T> entityClass,
			final ObjectQuery objectQuery)throws Exception{
		int count = findCount(entityClass, objectQuery);
		if (count == 0) {
			return new PageList<T>();
		}
		final StringBuilder builder = new StringBuilder("select o from "
				+ entityClass.getName() + " o");
		String where = objectQuery.getHql();
		if (StringUtils.isNotBlank(where)) {
			builder.append(" where ").append(where);
		}
		final PageList<T> pageList = new PageList<T>(
				objectQuery.getPage(), objectQuery.getPageRecord(),
				count);
		Query query = getSession().createQuery(builder.toString());
		List<Object> list = objectQuery.getParams();
		int index = 0;
		for (Object object : list) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		int first = (pageList.getPage() - 1)
				* pageList.getPageRecord();

		int max = pageList.getPageRecord();

		query.setFirstResult(first).setMaxResults(max);
		List<T> rows =query.setCacheable(true) .list();
		pageList.setRows(rows);
		return pageList;
	}
	/**
	 * 执行sql语句
	 * @param sql
	 */
	public int execSQL(String sql,Object...obj)throws Exception{
		SQLQuery query= this.getSession().createSQLQuery(sql);
		int index=0;
		for (Object object : obj) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		int i=query.executeUpdate();
        this.getSession().flush(); //清理缓存，执行批量插入  
        this.getSession().clear(); //清空缓存中的 对象
        return i;
	}
	/**
	 * 执行hql语句
	 * @param hql
	 */
	public int execHQL(String hql,Object...obj)throws Exception{
		Query query= this.getSession().createQuery(hql);
		int index=0;
		for (Object object : obj) {
			if (object!=null) {
				query.setParameter(index++, object);
			}
		}
		int i=query.executeUpdate();
		this.getSession().flush(); //清理缓存，执行批量插入  
		this.getSession().clear(); //清空缓存中的 对象
		return i;
	}
}
