package com.dengqiang.service;

import com.dengqiang.bean.Business;
import com.dengqiang.page.BusinessQuery;
import com.dengqiang.page.PageList;


/**
 * 商家service业务逻辑层接口定义类
 * @author dengqiang
 */

public interface IBusinessService extends IBaseService<Business>{
	/**
	 * 检查name是否存在
	 * @param name 参数
	 * @return 存在为true,不存在为false
	 */
		boolean checkName(String name)throws Exception;
	/**
	 * 登录验证
	 * @param loginName
	 * @param pwd
	 * @return
	 */
	Business checkLogin(String loginName, String pwd)throws Exception;
	/**
	 * 检查是否注册
	 * @return 注册返回true
	 */
	boolean checkRegister()throws Exception;
	/**
	 * 保存注册码
	 * @param license
	 */
	void saveLicense(String license)throws Exception;
	/**
	 * 检查管理员是否注册
	 * @return
	 */
	Business checkInit()throws Exception;
	/**
	 * 将用户标识为删除状态
	 * @param id
	 */
	void deleteBusiness(Long id)throws Exception;
	/**
	 * 获取用户列表
	 * @param query 
	 * @return
	 */
	PageList<Business> getBusinessList(BusinessQuery query)throws Exception;
	/**
	 * 更新用户部分信息
	 * @param business
	 */
	void updateBusiness(Business business)throws Exception;
	/**
	 * 更新密码 
	 * @param id 用户id
	 * @param newPwd
	 */
	void updatePassword(Long id, String newPwd)throws Exception;
	/**
	 * 检查确认密码
	 * @param oldPwd 旧密码
	 * @param id
	 * @return
	 */
	boolean checkPassword(String oldPwd,Long id)throws Exception;
	/**
	 * 检查数据是否重复
	 * @param obj 需要验证的数据
	 * @param key 数据代码
	 * @param id 
	 * @return 存在返回false,不存在返回true
	 */
	boolean checkData(String obj, Integer key, Long id)throws Exception;
}
