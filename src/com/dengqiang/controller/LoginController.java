package com.dengqiang.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.service.IBusinessService;
import com.dengqiang.util.ConfigFile;
import com.dengqiang.util.LoggerUtils;

@Controller
@RequestMapping("/login")
public class LoginController {
	 @Autowired
	 IBusinessService businessService;
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/loginPage")
	public String login(HttpServletRequest request) throws Exception {
		//检查是否注册
		request.getSession().setAttribute(ConfigFile.LICENSE, businessService.checkRegister());
		//管理系统名称
		request.getSession().setAttribute(ConfigFile.SYSTEM_NAME,ConfigFile.SYSTEM_VALUE);
		return "login";
	}
	/**
	 * 登录验证
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/loginAjax")
	@ResponseBody
	public ResultInfo loginAjax(HttpServletRequest request) throws Exception {
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		Long date= format.parse("2016-06-06").getTime();
//		Long now=new Date().getTime();
//		if (now>date) {
//			return new ResultInfo(false,"您使用的是系统已经到期,请联系作者更换新版系统!!!");
//		}
		String loginName=request.getParameter("loginName");
		String pwd=request.getParameter("pwd");
		String msg=null;
		boolean flag=false;
		if (StringUtils.isBlank(loginName)) {
			msg="请输入用户名!";
		}else if (StringUtils.isBlank(pwd)) {
			msg="请输入密码!";
		}else{
			Business business=businessService.checkLogin(loginName,pwd);
			if (business!=null&&business.getPassword().equals(pwd)) {
				request.getSession().setAttribute(ConfigFile.SESSION_BUSINESS_USER, business);
				request.getSession().setAttribute("loginName", business.getLoginName());
				request.getSession().setAttribute(ConfigFile.SYSTEM_NAME,ConfigFile.SYSTEM_VALUE);
				request.getSession().setAttribute("logoUrl",ConfigFile.LOGO_URL);
				request.getSession().setAttribute("loginDate",DateFormatUtils.format(new Date(), ConfigFile.DATETIME_FORMAT));
				request.getSession().setAttribute("userLevel", business.getLevel());
				flag=true;
			}else{
				msg="用户名或密码错误!";
			}
		}
		return new ResultInfo(flag,msg);
	}
	@RequestMapping("/exitLogin")
	public String exitLogin(HttpServletRequest request) throws Exception {
		request.getSession().setAttribute(ConfigFile.SESSION_BUSINESS_USER, null);
		return "redirect:login/loginPage.do";
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) throws Exception {
		return "index";
	}
	/**
	 * 每天0点10分清除临时文件夹里面的文件
	 */
	public void deleteTempFile() {
	    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
		File file=new File(servletContext.getRealPath("/")+"/temp/");
		if (file.exists()) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					e.printStackTrace();
					LoggerUtils.error(e);
				}
		}
//		Kit.deleteTempFile(servletContext);
	}
	public void test() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
		if (webApplicationContext==null) {
			return;
		}
        ServletContext servletContext = webApplicationContext.getServletContext();
		File file=new File(servletContext.getRealPath("/")+"/temp/");
		LoggerUtils.info(file.getPath());
		LoggerUtils.info("任务开始....."+new Date());
	}
}
