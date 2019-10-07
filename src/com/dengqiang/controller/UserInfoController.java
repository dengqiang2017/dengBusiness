package com.dengqiang.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.bean.UserInfo;
import com.dengqiang.page.PageList;
import com.dengqiang.page.UserInfoQuery;
import com.dengqiang.service.IUserInfoService;

/**
 * UserInfo控制层
 * @author dengqiang
 *
 */
@Controller
@RequestMapping("/user")
public class UserInfoController extends BaseController{
	//注入service接口
	@Autowired 
	IUserInfoService userInfoService;
	@RequestMapping("/user")
	public String userinfo() throws Exception {
		return "dictionary/userinfo";
	}
	@RequestMapping("/userinfoselect")
	public String userinfoselect() {
		return "dictionary/userinfoselect";
	}
	@RequestMapping(value="queryUser")
	@ResponseBody
	public PageList<UserInfo> queryUser(HttpServletRequest request,@ModelAttribute UserInfoQuery query) throws Exception {
		Business bus=getBusiness(request);
		if (bus.getLevel()==0) {
			query.setBusinessId(bus.getId());
		}
		return userInfoService.findQuery(query);
	}
	/**
	 * 保存或者更新客户信息
	 * @param request
	 * @param info
	 * @return
	 * @throws Exception
	 */
 
	@RequestMapping(value="saveUserInfo")
	@ResponseBody
	public ResultInfo saveUserInfo(HttpServletRequest request,@ModelAttribute UserInfo info) throws Exception{
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//		Long date= format.parse("2016-06-06").getTime();
//		Long now=new Date().getTime();
//		if (now>date) {
//			return new ResultInfo(false,"您使用的是系统已经到期,请联系作者更换新版系统!!!");
//		}
		String msg=null;
		boolean flag=false;
		try {
			if (info.getId()!=null) {
				UserInfo user=userInfoService.get(info.getId());
				user.setUsername(info.getUsername());
				user.setPhone(info.getPhone());
				user.setClientType(info.getClientType());
				user.setRemark(info.getRemark());
				user.setDemand(info.getDemand());
				user.setCustomerComments(info.getCustomerComments());
				userInfoService.merge(user);
			}else{
				info.setNumber("K"+new Date().getTime());
				info.setBusiness(getBusiness(request));
				info.setEntering(getBusiness(request));
				info.setPublishTime(new Date());
				userInfoService.save(info);
			}
			msg=info.getId()+"";
			 flag=true;
		} catch (Exception e) {
			 flag=false;
			 msg="电话号码已经存在!";
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 删除客户
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="delUser")
	@ResponseBody
	public ResultInfo delUser(HttpServletRequest request,Long id) throws Exception{
		userInfoService.delete(id);
		return new ResultInfo(true);
	}
	/**
	 * 根据房源信息获取客户
	 * @param request
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUserInfoByHousing")
	@ResponseBody
	public UserInfo getUserInfoByHousing(HttpServletRequest request,Long infoId) throws Exception {
		return userInfoService.getUserInfoByHousing(infoId);
	}
}
