package com.dengqiang.integerceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dengqiang.bean.Business;
import com.dengqiang.util.ConfigFile;
import com.dengqiang.util.LoggerUtils;
/**
 * 请求拦截器
 * @author dengqiang
 *
 */
public class Integerceptor implements HandlerInterceptor {
	private Logger log = Logger.getLogger(Integerceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exp)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	}
	/**
	 * 需要过滤的url列表
	 */
	public static final List<String> url_list = new ArrayList<String>();
	/**
	 * 检查url是否需要直接放行
	 * @param obj
	 * @return 放行为true
	 */
	private boolean checkRequest(String obj) {
//		boolean flag = url_list.contains(obj);
//		for (int i = 0; i < url_list.size(); i++) {
//			flag = obj.contains(url_list.get(i));
//			 if (url_list.contains(obj)) {
//				
//			}
//			if (flag) {
//				break;
//			}
//		}
		return  url_list.contains(obj);
	}
	private static String URL;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("preHandle===>>>" + request.getRequestURI());
		// 访问的是放行列表里面的实例直接放行
		Business businessBean=(Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
		//需要检查session
		if (businessBean==null) {
			String params=request.getQueryString();
			if (StringUtils.isNotBlank(params)) {
				params="?"+params;
			}else{
				params="";
			}
			String url=request.getRequestURI();
			URL = url+params;
			String login="/login/loginPage.do";
			if (login.equals(URL)) {
				URL=request.getHeader("Referer");
					return true;
			}
			if (StringUtils.isNotBlank(url)&&!checkRequest(url)) {
				response.sendRedirect(login);
				return false;
			}else{
				URL=null;
				return true;
			}
		}else if (StringUtils.isNotBlank(URL)) {
			LoggerUtils.error(URL);
			response.sendRedirect(URL);
			URL=null;
			return false;
		}else{
			log.info("放行:" + request.getRequestURI());
			return true;
		}
	}

}
