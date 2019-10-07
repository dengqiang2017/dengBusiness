package com.dengqiang.service;

import java.util.List;

import com.dengqiang.bean.OrderBean;
import com.dengqiang.bean.OrderDetail;

public interface IOrderService extends IBaseService<OrderBean> {

	/**
	 * 获取订单根据编号
	 * @param no
	 * @return
	 * @throws Exception 
	 */
	OrderBean getOrderByNo(String no) throws Exception;
	/**
	 * 获取订单明细
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	List<OrderDetail> getOrderDetail(Long id) throws Exception;
}
