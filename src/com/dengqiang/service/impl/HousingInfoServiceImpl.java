package com.dengqiang.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.HousingInfo;
import com.dengqiang.bean.ImageUrl;
import com.dengqiang.service.IHousingInfoService;
@Service
@Transactional
public class HousingInfoServiceImpl extends BaseServiceImpl<HousingInfo>  implements IHousingInfoService {

	@Override
	public void deleteHousingInfo(Long id) throws Exception {
		String hql="update from HousingInfo set delFlag=0 where id=?";
		baseDAO.execHQL(hql, id);
	}

	@Override
	public void updateHousingInfoStatus(HousingInfo housingInfo, Integer status) throws Exception {
		 String hql="update from HousingInfo set status=?,followUpTime=? where id=?";
		 baseDAO.execHQL(hql,status,new Date(), housingInfo.getId());
	}

	@Override
	public void deleteImg(ImageUrl url) throws Exception {
		String hql="delete from ImageUrl where imgUrl=?";
		baseDAO.execHQL(hql, url.getImgUrl());
	}
}
