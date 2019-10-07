package com.dengqiang.util;

/**
 * 项目配置文件
 * 
 * @author dengqiang
 * 
 */
public abstract class ConfigFile {
	
	/**
	 * 是否记录日志
	 */
	public static boolean LOG = true; 
	
	/**
	 * 日志保存时间 默认15天
	 */
	public static int  LOGDAY = 15;
	/**
	 * 是否打印错误信息
	 */
	 public static boolean  PRINT_ERROR =false;
	
	/**
	 * 推送间隔时间 默认10秒钟
	 */
	public static int  POLLDATATIME =3000*1000;
	/**
	 * 空间文件存放位置
	 */
	public static final String namespace = "/zone/";
	/**
	 * 回收站
	 */
	public static final String RECYCLE = "/recycle/";
	/**
	 * zone
	 */
	public static final String SPLIT = "zone";
	/**
	 * trx_path
	 */
	public static final String TRX="/loadFiles/trx/";
	/**
	 * loadFiles 系统图片存放位置
	 */
	public static final String LOADFILES="/loadFiles/";  
	/**
	 * 初始化配置文件的修改时间,用来判断文件是否在运行期被修改,修改后就重新加载
	 */
	public static String initModifiedTime;
	/**
	 * 放行xml修改时间
	 */
	public static String passModifiedTime; 

	public static final String BUSINESS="business";
	public static final String BUSINESSINFO="businessinfo";
	public static final String BUSINESSLOGO = "businesslogo";
	public static final String ERWEIMA = "erweima";
	public static final String ROOT = "/";
	/**
	 * 商家登录session
	 */
	public static final String SESSION_BUSINESS_USER = "businessInfo";
	/**
	 * 是否注册
	 */
	public static final String LICENSE = "business_license";//是否注册
	///////
	public static final String DATETIME_FORMAT="yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT =  "yyyy-MM-dd";
	/**
	 * 服务器安装目录
	 */
	public static String TOMCAT_DIR =  null;
	
	public static final String SYSTEM_NAME = "systemName";

	public static final String BUSINESSINIT = "businessinit"; 
	public static String SYSTEM_VALUE = "房屋管理系统"; 
	public static String LOGO_URL = "/images/logo.png"; 
	public static String TEXT_WATERMARK = ""; 
	
}
