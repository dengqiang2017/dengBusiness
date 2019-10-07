package com.dengqiang.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

import com.dengqiang.service.IInitLoadService;
/**
 *  容器加载完成监听器
 * @author dengqiang
 *
 */
@Controller
public class DataSourceInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private Logger log = Logger.getLogger(DataSourceInitListener.class);
	private static boolean fisInit=true;
	@Autowired
	private IInitLoadService initLoadService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		//防止重复执行。
//		boolean b=ev.getApplicationContext() instanceof FileSystemXmlApplicationContext;
        if(fisInit){
        	fisInit=false;
        	log.error("系统初始化中请等待......");
//        	log.error("初始化存储过程....");
//        	initLoadService.initProcedure();
//			districtDao.initDistrict();
			log.error("初始化数据.....");
			initLoadService.initData();
//		    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
//	        ServletContext servletContext = webApplicationContext.getServletContext();
//			Kit.deleteTempFile(servletContext);
			log.error("系统初始化完成!");
        }
	}

}
