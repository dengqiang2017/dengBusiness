package com.dengqiang.service;

import com.dengqiang.bean.HousingInfo;
import com.dengqiang.bean.ImageUrl;

/**
 * 房屋出租服务接口
 * @author dengqiang
 *
 */
public interface IHousingInfoService extends IBaseService<HousingInfo>{
	/**
	 * 删除房屋信息并放入到回收站
	 * @param id
	 */
	void deleteHousingInfo(Long id)throws Exception;
	/**
	 * 更新房源状态
	 * @param housingInfo
	 * @param status
	 * @throws Exception
	 */
	void updateHousingInfoStatus(HousingInfo housingInfo, Integer status)throws Exception;
	/**
	 * 删除图片
	 * @param url
	 */
	void deleteImg(ImageUrl url)throws Exception ;

}
