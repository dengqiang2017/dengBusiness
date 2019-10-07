package com.dengqiang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.OrderBean;
import com.dengqiang.bean.OrderDetail;
import com.dengqiang.service.IOrderService;

@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<OrderBean> implements
		IOrderService {

	@Override
	public OrderBean getOrderByNo(String no) throws Exception {
		String hql="from OrderBean o where o.orderNumber=?";
		List<OrderBean> list=baseDAO.findByHql(hql, no);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<OrderDetail> getOrderDetail(Long id) throws Exception {
		String hql="select o.orderDetails from OrderBean o where o.id=?";
		return baseDAO.findByHql(hql, id);
	}

}
