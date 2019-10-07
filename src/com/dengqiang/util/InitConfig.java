package com.dengqiang.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dengqiang.controller.BaseController;
import com.dengqiang.integerceptor.Integerceptor;

/**
 * 加载"initConfig.txt配置文件内容
 * 
 * @author dengqiang
 * 
 */
public abstract class InitConfig {
	/**
	 * 获取boolean值
	 * 
	 * @param properties
	 * @param key
	 *            配置项的key值
	 * @param def
	 *            默认值
	 * @return
	 */
	private static Boolean getBoolean(Properties properties, String key,
			boolean def) {
		String log_str = properties.getProperty(key);
		if (StringUtils.isBlank(log_str)) {
			return def;
		}
		Boolean log = Boolean.parseBoolean(log_str.trim());
		return log;
	}

	/**
	 * 获取字符串值
	 * 
	 * @param properties
	 * @param key
	 *            配置项的key值
	 * @param def
	 *            默认值
	 * @return
	 */
	private static String getString(Properties properties, String key,
			String def) {
		String log_str = properties.getProperty(key);
		if (StringUtils.isBlank(log_str)) {
			return def;
		}
		return log_str.trim();
	}

	/**
	 * 获取Integer整数值
	 * 
	 * @param properties
	 * @param key
	 *            配置项的key值
	 * @param def
	 *            默认值
	 * @return
	 */
	private static Integer getIneger(Properties properties, String key, int def) {
		String log_str = properties.getProperty(key);
		if (StringUtils.isBlank(log_str)) {
			return def;
		}
		Integer log = Integer.parseInt(log_str.trim());
		return log;
	}

	/**
	 * 初始化
	 */
	public static void init() {
		try {
			Properties properties = new Properties();
			ClassLoader loader = InitConfig.class.getClassLoader();
			InputStream inStream = loader.getResourceAsStream("initConfig.txt");
			sax_url(loader.getResourceAsStream("passlist.xml"));
			properties.load(inStream);
			ConfigFile.LOG = getBoolean(properties, "log", ConfigFile.LOG);
			// //
			ConfigFile.PRINT_ERROR = getBoolean(properties, "print_error",
					ConfigFile.PRINT_ERROR);
			// //
			ConfigFile.SYSTEM_VALUE = getString(properties, "system_value", "房屋管理系统");
			ConfigFile.TEXT_WATERMARK = getString(properties, "textWatermark", "");
			ConfigFile.LOGO_URL = getString(properties, "logoUrl", "/images/logo.png");
			// //
			ConfigFile.LOGDAY = 0 - getIneger(properties, "logDay", 15);
			// //
			ConfigFile.POLLDATATIME = getIneger(properties, "pollDataTime", 30) * 1000;
			// ///
			ConfigFile.TOMCAT_DIR = System.getProperty("user.dir");
			if (ConfigFile.TOMCAT_DIR.contains("bin")) {
				File file=new File(ConfigFile.TOMCAT_DIR);
				ConfigFile.TOMCAT_DIR=file.getParent();
			}
			ConfigFile.TOMCAT_DIR = ConfigFile.TOMCAT_DIR+"/"
					+ getString(properties, "appBase", ConfigFile.TOMCAT_DIR);
			// //
			String initString = InitConfig.class.getClassLoader()
					.getResource("initConfig.txt").getPath();
			System.out.println("当前initConfig.txt所在路径:" + initString);
			ConfigFile.initModifiedTime = InitConfig
					.getModifiedTime("initConfig.txt");
			ConfigFile.passModifiedTime = InitConfig
					.getModifiedTime("passlist.xml");
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析需要放行的请求xml
	 * 
	 * @param in
	 */
	@SuppressWarnings("unchecked")
	public static void sax_url(InputStream in) {
		if (in == null) {
			ClassLoader loader = InitConfig.class.getClassLoader();
			in = loader.getResourceAsStream("passlist.xml");
		}
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element rootElm = document.getRootElement();
			List<Element> nodes = rootElm.elements("item");
			for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
				Element elm = it.next();
				String url = elm.attributeValue("url");
				if (StringUtils.isNotBlank(url)) {
					Integerceptor.url_list.add(url);
				}
				String type = elm.attributeValue("type");
				String ext = elm.attributeValue("ext");
				if (StringUtils.isNotBlank(ext) && StringUtils.isNotBlank(type)) {
					BaseController.type_ext_List.add(type + "," + ext);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件的修改时间 默认文件名initConfig.txt
	 * 
	 * @return 文件修改时间
	 */
	public static String getModifiedTime(String filename) {
		if (!StringUtils.isNotBlank(filename)) {
			filename = "initConfig.txt";
		}
		String filepath = InitConfig.class.getClassLoader()
				.getResource(filename).getPath();
		File initFile = new File(filepath);
		return FileOperate.getModifiedTime(initFile);
	}

	/**
	 * 动态加载initConfig文件
	 */
	public static void initLoad() {
		String initString = getModifiedTime(null);
		if (!initString.equals(ConfigFile.initModifiedTime)) {
			init();
		}
		String pass = getModifiedTime("passlist.xml");
		if (!pass.equals(ConfigFile.passModifiedTime)) {
			sax_url(null);
		}
	}
}
