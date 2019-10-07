package com.dengqiang.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dengqiang.util.InitConfig;
/**
 * 容器启动初始化配置
 * @author dengqiang 
 *
 */
public class InitLoadListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		InitConfig.init();//加载配置		
	}
 
}
