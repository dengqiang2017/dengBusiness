package com.dengqiang.data.pushlet;

import java.io.Serializable;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.util.PushletException;

import org.apache.log4j.Logger;

import com.dengqiang.util.ConfigFile;
import com.dengqiang.util.InitConfig;
import com.dengqiang.util.LoggerUtils;

/**
 * 数据推送服务
 * 
 * @author dengqiang
 * 
 */
public class PmsEventPullSources implements Serializable {
	private static final long serialVersionUID = 8397335113632699297L;
	private static Logger log = Logger.getLogger(PmsEventPullSources.class);

	public static class BaoxiuEvent extends EventPullSource {

		@Override
		protected long getSleepTime() {
			// 刷新时间
			return ConfigFile.POLLDATATIME;
		}

		/**
		 * Create new Session (but add later).
		 */

		public Session createSession(Event anEvent) throws PushletException {
			// Trivial
			// return Session.create(createSessionId()); //原写法
			return Session.create(anEvent.getField("userId", "visitor"));// 修改后的写法
		}

//		private CustomerConnectionController controller;

		@Override
		protected Event pullEvent() {
			Long begin = System.currentTimeMillis();
			InitConfig.initLoad();
			Event event = Event.createDataEvent("dengevent");
//			if (controller == null) {
//				ApplicationContext ac = new FileSystemXmlApplicationContext(
//						"classpath:application-mvc.xml");
////				configService = (RouterConfigService) ac
////						.getBean("routerConfigServiceImpl");
//				controller = (CustomerConnectionController) ac
//						.getBean("customerConnectionController");
//			} else {
//				JSONObject jsonArray = JSONObject.fromObject(controller
//						.connectionInfoCount());
//				JSONObject dailyArray=JSONObject.fromObject(controller.dailyStatistics());
//				JSONObject wifiArray=JSONObject.fromObject(controller.wifiInfoCount());
//				event.setField("info_count", jsonArray.toString());
//				event.setField("daily_count",dailyArray.toString());
//				event.setField("wifi_count",wifiArray.toString());
//			}
//			log.info(System.currentTimeMillis() - begin); 
			return event;
		}
		
	}
}
