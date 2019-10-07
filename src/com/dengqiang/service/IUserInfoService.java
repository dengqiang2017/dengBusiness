package com.dengqiang.service;

import java.util.List;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.UserInfo;

/**
 * 客户管理服务
 * @author dengqiang
 *
 */
public interface IUserInfoService extends IBaseService<UserInfo> {
	/**
	 * 获取该用户下的客户名称
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	List<UserInfo> getHomeownersByBusinessId(Business business) throws Exception ;
    /**
     *  根据房源信息获取客户
     * @param infoId
     * @return
     * @throws Exception 
     */
	UserInfo getUserInfoByHousing(Long infoId) throws Exception;

}
