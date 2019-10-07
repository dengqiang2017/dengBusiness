package com.dengqiang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.UserInfo;
import com.dengqiang.service.IUserInfoService;

@Service
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements
		IUserInfoService {
	@Override
	public List<UserInfo> getHomeownersByBusinessId(Business business) throws Exception {
		if (business.getLevel()==1) {
			return baseDAO.findByHql("from UserInfo o");
		}else{
			return baseDAO.findByHql("from UserInfo o where o.business.id=?", business.getId());
		}
	}

	@Override
	public UserInfo getUserInfoByHousing(Long infoId) throws Exception {
		String hql="select o.homeowner from HousingInfo o where o.id=?";
		baseDAO.findOneRecordByObj(hql, infoId);
		return baseDAO.findOneRecordByObj(hql, infoId);
	}

}
