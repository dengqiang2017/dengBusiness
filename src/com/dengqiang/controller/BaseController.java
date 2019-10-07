package com.dengqiang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.dengqiang.bean.Business;
import com.dengqiang.util.ConfigFile;
import com.dengqiang.util.DateTimeUtils;
/**
 * 公共控制类
 * @author dengqiang
 * 2014-07-16 14:00:00
 */
public abstract class BaseController {
	/**
	 * 需要过滤的后缀名列表
	 */
	public static final List<String> type_ext_List=new ArrayList<String>();
	
	public static final String time_23=" 23:59:59";
	public static final String time_00=" 00:00:00";
	/**
	 * 获取服务器域名
	 */
	public static String serverName=null;	
	/**
	 * 检查是否登录
	 * @param request
	 * @return  
	 */
	public boolean checkUser(HttpServletRequest request) {
		Business bean= (Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
		if (bean!=null) {
				return true;
		}
		return false;
	}
	/**
	 * 获取商家id
	 * @param request
	 * @return id
	 */
	public Long getBusinessId(HttpServletRequest request) {
		Business bean= (Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
		if (bean!=null) {
			return bean.getId();
		}
		return 0l;
	}
	/**
	 * 获取商家
	 * @param request
	 * @return id
	 */
	public Business getBusiness(HttpServletRequest request) {
		 return (Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
	}
	
	/**
	 * 检查是否是管理员
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean checkAdmin(HttpServletRequest request) throws Exception {
		Business bean= (Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
		if (bean.getLevel()==1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查后缀名是否需要直接放行
	 * @param obj
	 * @return 放行为true
	 */
	public String getFileType(String obj) {
		String type = null; 
		for (String type_ext: type_ext_List) {
			String ext=FilenameUtils.getExtension(obj);
			if (type_ext.contains(ext)) {
				type=type_ext.split(",")[0];
				break;
			} 
		}		 
		return type;
	}
	/**
	 * 获取绝对路径
	 * @param request
	 * @param url
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request, String url) {
		if (StringUtils.isBlank(url)) {
			url=ConfigFile.ROOT;
		}
		return request.getSession().getServletContext().getRealPath(url);
	}
	/**
	 * 获取服务器域名
	 * @param request
	 * @return 服务器域名
	 */
	public String getServerName(HttpServletRequest request) {
		return "http://" + request.getServerName() + ConfigFile.ROOT;
	}
	//////////////////////////////////
	/**
	 * 比较日期大小如果开始时间大于结束时间就将两个日期换位置放入map中
	 * @param map 需要存放日期的map
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 */
	public void dateCompare(Map<String, Object> map, Date beginDate,
			Date endDate) {
		String beginTime=DateFormatUtils.format(beginDate, ConfigFile.DATE_FORMAT);
		String endTime=DateFormatUtils.format(endDate, ConfigFile.DATE_FORMAT);
		if (beginDate.getTime()>endDate.getTime()) {
			map.put("beginTime", endTime+time_00);			
			map.put("endTime", beginTime+time_23);
		}else {
			map.put("beginTime", beginTime+time_00);			
			map.put("endTime", endTime+time_23);
		}
	}
	
 /**
  * 根据传入的开始时间和结束时间,判断是否为空为空的就初始化为当前日期
  * 获取当前天的日期放入到map中
  * @param beginTime 开始时间
  * @param endTime 结束时间
  * @param map 需要存放日期的map
  */
	public void getNowDay(String beginTime,String endTime,Map<String, Object> map) {
		//当开始时间和结束时间都没有的时候默认查询近7天的数据
		if (StringUtils.isBlank(beginTime)) {
			//如果只有开始时间没有就查询近30天的数据
			beginTime=DateFormatUtils.format(new Date(), ConfigFile.DATE_FORMAT);
		}
		map.put("beginTime", beginTime+time_00);			
		if (StringUtils.isBlank(endTime)) {//结束时间为空的时候默认当天
			endTime=DateFormatUtils.format(new Date(), ConfigFile.DATE_FORMAT);
		}
		map.put("endTime", endTime+time_23);
		
	}
	/**
	 * 获取判定后查询时间
	 * @param beginTime
	 * @param endTime
	 * @param map
	 */
	public void getQueryTime(String beginTime,String endTime,Map<String, Object> map) {
		Date beginDate=null;
		Date endDate=null;
		//当开始时间和结束时间都没有的时候默认查询近7天的数据
		if (StringUtils.isBlank(beginTime)&&StringUtils.isBlank(endTime)) {
			beginDate=DateUtils.addDays(new Date(), -7);
		}else if (StringUtils.isBlank(beginTime)) {
			//如果只有开始时间没有就查询近30天的数据
			beginDate=DateUtils.addDays(new Date(), -30);
		}else {
			beginDate =DateTimeUtils.strToDate(beginTime);
		}
		if (StringUtils.isBlank(endTime)) {//结束时间为空的时候默认当天
			endDate=new Date();
		}
		dateCompare(map, beginDate, endDate);		
	}
	/**
	 * 获取昨天的日期并放入到map中
	 * @param map
	 */
	public void getYesterdayDate(Map<String, Object> map) {
		Date date=DateUtils.addDays(new Date(), -1);
		map.put("beginTime", DateTimeUtils.dateToStr(date)+time_00);
		map.put("endTime", DateTimeUtils.dateToStr(date)+time_23);
	}
	
	/**
	 * 获取map中value值,适用于map中存放单条数据的.
	 * @param map
	 * @return
	 */
	public Object getValue(Map<String, Object> map) {
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		Object obj=null;
		while(it.hasNext()){
		Entry<String, Object> entry = (Entry<String, Object>)it.next();
	//	entry.getKey() 返回与此项对应的键
		obj=entry.getValue();
		}
		return obj;
	}
	/**
	 * 获取json数据中的版本号 默认为0
	 * @param bean 需要获取的json对象
	 * @param name 在json中的名称
	 * @return 从json中提取的数据 默认为0 
	 * @throws Exception
	 */
	public Object getVersion(Object bean,String name){
		Object ver=null;
		try {
			ver = PropertyUtils.getProperty(bean,name);
		} catch (Exception e) {}
		if (ver==null||StringUtils.isBlank(ver.toString())) {
			return "0";
		}
		return ver;
	}
	/**
	 * 获取查询所需要的时间,主要用于判断时间传递时,时间没有选择的情况
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param map 封装时间的map
	 */
	public void getQueryDate(String beginTime, String endTime,
			Map<String, Object> map) {
		//当开始时间和结束时间都没有的时候默认查询近7天的数据
		if (StringUtils.isBlank(beginTime)&&StringUtils.isBlank(endTime)) {
			beginTime=DateFormatUtils.format(DateUtils.addDays(new Date(), -7), ConfigFile.DATE_FORMAT);
		}else if (StringUtils.isBlank(beginTime)) {
			//如果只有开始时间没有就查询近30天的数据
			beginTime=DateFormatUtils.format(DateUtils.addDays(new Date(), -30), ConfigFile.DATE_FORMAT);;
		}
		String begin=beginTime;
		if (StringUtils.isBlank(endTime)) {//结束时间为空的时候默认当天
			endTime=DateFormatUtils.format(new Date(), ConfigFile.DATE_FORMAT);
		}
		String end=endTime;// 加了后才能查询到选择日期的数据
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date bDate=format.parse(beginTime);
			Date eDate=format.parse(endTime);
			if (bDate.getTime()>eDate.getTime()) {
				begin=endTime;
				end= beginTime;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("beginTime", begin);
		map.put("endTime", end+time_23);
	}
	/**
	 * 获取类型名称,0-二手房,1-出租房,2-新房
	 * @param type
	 * @return
	 */
	protected String getTypeName(String type) {
		int key=Integer.parseInt(type);
		String msg=null;
		switch (key) {
		case 0:
			msg="二手房";
			break;
		case 1:
			msg="出租房";
			break;
		case 2:
			msg="新房";
			break;
		}
		return msg;
	}
}
