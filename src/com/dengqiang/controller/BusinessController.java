package com.dengqiang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.page.BusinessQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IBusinessService;
import com.dengqiang.util.ConfigFile;
import com.dengqiang.util.MD5Util;
import com.dengqiang.util.MacAddressUtil;

@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController {
	@Autowired
	IBusinessService businessService;
	/**
	 * 跳转到输入注册码的页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/license")
	public String license(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		return "license";
	}
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register")
	public String register(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute(ConfigFile.LICENSE, !businessService.checkRegister());
		if (businessService.checkInit()!=null) {
			request.setAttribute(ConfigFile.BUSINESSINIT,false);
		}else{
			request.setAttribute(ConfigFile.BUSINESSINIT,true);
		}
		request.setAttribute("mac", MacAddressUtil.getMacAddress());
		return "register";
	}
	
	/**
	 * 跳转到商家页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/business")
	public String business(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setAttribute(ConfigFile.LICENSE, !businessService.checkRegister());
		Business business=businessService.checkInit();
		if (business!=null) {
			request.setAttribute(ConfigFile.BUSINESSINIT,false);
			Business business2=(Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
			//管理员没有注册或者是管理员登录的就可以选择,普通用户不能选择成为管理员
			if (business.getId()==business2.getId()) {
				request.setAttribute("levelSelect", true);
			}else{
				request.setAttribute("levelSelect", false);
			}
		}else{
			request.setAttribute(ConfigFile.BUSINESSINIT,true);
		}
		request.setAttribute("mac", MacAddressUtil.getMacAddress());
		return "business/business";
	}
	@RequestMapping("pwd")
	public String pwdHtml(HttpServletRequest request) throws Exception {
		return "business/pwd";
	}

	/**
	 * 检查管理员是否注册
	 * @return
	 */
	@RequestMapping("checkInit")
	@ResponseBody
	public ResultInfo checkInit(HttpServletRequest request)  throws Exception{
		Business busines=businessService.checkInit();
		if (busines!=null) {
			return new ResultInfo(true);
		}else{
			return new ResultInfo();
		}
	}
	/**
	 * 检查服务器是否注册
	 * @return
	 */
	@RequestMapping("checkRegister")
	@ResponseBody
	public ResultInfo checkRegister(HttpServletRequest request)  throws Exception{
		return new ResultInfo(businessService.checkRegister());
	}
	/**
	 * 检查是否登录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkLogin")
	@ResponseBody
	public ResultInfo checkLogin(HttpServletRequest request)  throws Exception{
		return new ResultInfo(checkUser(request));
	}
	/**
	 * 检查密码
	 * @param oldPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkPassword")
	@ResponseBody
	public boolean checkPassword(HttpServletRequest request,String oldPwd) throws Exception {
		return businessService.checkPassword(oldPwd,getBusinessId(request));
	}
	/**
	 * 检查数据是否重复
	 * @param request
	 * @param obj
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkData")
	@ResponseBody
	public boolean checkData(HttpServletRequest request,String obj,Integer key) throws Exception {
		Business business=(Business) request.getSession().getAttribute(ConfigFile.SESSION_BUSINESS_USER);
		if (business!=null) {
			return businessService.checkData(obj, key,business.getId());
		}
		return businessService.checkData(obj,key,null);
	}
	////////////////////
	/**
	 * 保存输入的注册码
	 * @param request
	 * @param license 注册码
	 * @return
	 */
	@RequestMapping("saveLicense")
	@ResponseBody
	public ResultInfo saveLicense(HttpServletRequest request,String license) throws Exception{
		boolean flag=false;
		String msg = null;
		if (StringUtils.isNotBlank(license)) {
			if (MD5Util.getGeneratedCode().equalsIgnoreCase(license)) {
				businessService.saveLicense(license);
				request.setAttribute(ConfigFile.LICENSE, true);
				flag=true;
			}else {
				msg="注册码不正确!";
			}
		}else{
			msg="注册码不能为空!";
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 用户注册或更新
	 * @param request
	 * @param business
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="saveBusiness",method=RequestMethod.POST)
	@ResponseBody
	public ResultInfo saveBusiness(HttpServletRequest request,@ModelAttribute Business business,String[] license_mac,String pwd)  throws Exception{
//		boolean flag=true;
//		for (String string : license_mac) {
//			if (StringUtils.isBlank(string)) {
//				flag=false;
//				break;
//			}
//		}
//		if (license_mac.length==4&&flag) {
//			business.setLicense(license_mac[0]+"-"+license_mac[1]+"-"+license_mac[2]+"-"+license_mac[3]);
//			if (!MD5Util.getGeneratedCode().equalsIgnoreCase(business.getLicense())) {
//				business.setLicense(null);
//			}
//		}
		if (business.getId()!=null) {
			businessService.updateBusiness(business);
		}else{
			business.setPwd(true);
				businessService.save(business); 
		}
		request.getSession().setAttribute(ConfigFile.SESSION_BUSINESS_USER, business);
		request.getSession().setAttribute("userLevel", business.getLevel());
		return new ResultInfo(true);
	}
	/**
	 * 修改密码
	 * @param request
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("savePwd")
	@ResponseBody
	public ResultInfo savePwd(HttpServletRequest request,String oldPwd,String newPwd,String confirmPwd) throws Exception {
		boolean flag=false;
		String msg=null;
		boolean pwd=checkPassword(request,oldPwd);
		if (StringUtils.isBlank(oldPwd)) {
			msg="旧密码不能为空!";
		}else if (!pwd) {
			msg="旧密码错误!";
		}else if (StringUtils.isBlank(newPwd)) {
			msg="新密码不能为空!";
		}else if (StringUtils.isBlank(confirmPwd)) {
			msg="确认密码不能为空!";
		}else if (!newPwd.equals(confirmPwd)) {
			msg="新密码与确认密码不能为空!";
		}else{
			businessService.updatePassword(getBusinessId(request),newPwd);
			flag=true;
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 彻底删除用户
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deletePermanentBusiness")
	@ResponseBody
	public ResultInfo deletePermanentBusiness(HttpServletRequest request,Long id) throws Exception {
		businessService.delete(id);
		return new ResultInfo(true);	
	}
	/**
	 *  将用户标识为删除状态
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteBusiness")
	@ResponseBody
	public ResultInfo deleteBusiness(HttpServletRequest request,Long id) throws Exception {
		businessService.deleteBusiness(id);
		return new ResultInfo(true);
	}
	/////////////////////////////////
	/**
	 * 获取机器码
	 * @return
	 * @throws SigarException
	 */
	@RequestMapping("getMac")
	@ResponseBody
	public ResultInfo getMac(HttpServletRequest request) throws SigarException {
		return new ResultInfo(true,MacAddressUtil.getMacAddress());
	}
	/**
	 * 获取用户列表
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBusinessList")
	@ResponseBody
	public PageList<Business> getBusinessList(HttpServletRequest request,@ModelAttribute BusinessQuery query) throws Exception {
		return businessService.getBusinessList(query);
	}
	/**
	 * 跳转到商家信息中心
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("businessInfo")
	public String businessInfo(HttpServletRequest request) throws Exception {
		return "business/businessInfo";
	}
	/**
	 * 跳转到系统设置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("settings")
	public String settings(HttpServletRequest request) throws Exception {
		request.setAttribute(ConfigFile.SYSTEM_NAME,ConfigFile.SYSTEM_VALUE);
		request.setAttribute("logo_url",ConfigFile.LOGO_URL);
		request.setAttribute("textWatermark",ConfigFile.TEXT_WATERMARK);
		return "business/settings";
	}
	/**
	 * 保存系统设置
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveSettings")
	@ResponseBody
	public ResultInfo saveSettings(HttpServletRequest request) throws Exception {
		boolean flag=true;
		String msg=null;
		String name=request.getParameter("systemname");
		String logo=request.getParameter("logourl");
		String textWatermark=request.getParameter("textWatermark");
		Properties properties = new Properties();
		ClassLoader loader = BusinessController.class.getClassLoader();
		URL url = loader.getResource("initConfig.txt");
		FileOutputStream oFile = new FileOutputStream(url.getPath());
		if (StringUtils.isNotBlank(name)) {
			ConfigFile.SYSTEM_VALUE=name;
			properties.setProperty(ConfigFile.SYSTEM_NAME, ConfigFile.SYSTEM_VALUE);
			request.getSession().setAttribute(ConfigFile.SYSTEM_NAME,ConfigFile.SYSTEM_VALUE);
		}
		if (StringUtils.isNotBlank(logo)) {
			logo=FilenameUtils.getName(logo);
				File destFile=new File(getRealPath(request, "/")+"/logo/logo."+FilenameUtils.getExtension(logo));
				File srcFile=new File(getRealPath(request, "/")+"/temp/"+logo);
				if (destFile.exists()&&destFile.isFile()) {
					destFile.delete();
				}
				if (srcFile.exists()) {
					FileUtils.moveFile(srcFile, destFile);
				}
			ConfigFile.LOGO_URL="/logo/logo."+FilenameUtils.getExtension(logo);
			properties.setProperty("logoUrl", ConfigFile.LOGO_URL);
			request.getSession().setAttribute("logoUrl",ConfigFile.LOGO_URL);
		}
		if (StringUtils.isNotBlank(textWatermark)) {
			ConfigFile.TEXT_WATERMARK=textWatermark;
			properties.setProperty("textWatermark", ConfigFile.TEXT_WATERMARK);
		}
		 properties.store(oFile, "system");
		 oFile.close();
		return new ResultInfo(flag,msg);
	}
}
