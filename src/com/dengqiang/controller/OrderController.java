package com.dengqiang.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.HousingInfo;
import com.dengqiang.bean.ImageUrl;
import com.dengqiang.bean.OrderBean;
import com.dengqiang.bean.OrderDetail;
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.page.OrderQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IHousingInfoService;
import com.dengqiang.service.IOrderService;
import com.dengqiang.service.IUserInfoService;
/**
 * 订单控制
 * @author dengqiang
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private IHousingInfoService housingInfoService;
	@Autowired
	private IOrderService orderService; 
	@Autowired
	private IUserInfoService userInfoService;
	@RequestMapping("orderList")
	public String orderList(HttpServletRequest request) throws Exception {
		String type=request.getParameter("type");
		request.setAttribute("typeName", getTypeName(type));
		request.setAttribute("type", type);
		String all=request.getParameter("all");
		request.setAttribute("all", all);
		if (StringUtils.isNotBlank(all)&&"all".equals(all)) {
			request.setAttribute("allName", "所有");
		}
		return "order/orderList";
	}
	
	/**
	 * 跳转到出租订单编辑页面
	 * @param request
	 * @param id 房源id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tenementOrder")
	public String tenementOrder(HttpServletRequest request,HttpServletResponse response, Long id) throws Exception {
		HousingInfo info=checkStatus(response, id);
		request.setAttribute("housingInfo",  info);
		request.setAttribute("clients", userInfoService.getHomeownersByBusinessId(getBusiness(request)));
		if (info==null) {
			return null;
		}
		if (info.getType()==1) {
			return "order/tenementOrder";
		}else{
			return "order/saleOrder";
		}
	}
	/**
	 * 获取订单明细历史
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOrderDetail")
	@ResponseBody
	public List<OrderDetail> getOrderDetail(HttpServletRequest request,Long id) throws Exception {
		return orderService.getOrderDetail(id);
	}
	
	/**
	 * 更新订单状态
	 * @param request
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateOrderProgress")
	@ResponseBody
	public ResultInfo updateOrderProgress(HttpServletRequest request,@ModelAttribute OrderBean order) throws Exception {
		String msg=null;
		boolean flag=false;
		List<OrderDetail> details=order.getOrderDetails();
		if (details==null||details.size()==0) {
			msg="必须输入进度内容!";
		}else{
			//1.判断是否有图片并保存 
			String[] imgs=request.getParameterValues("imgUrls");
			saveImage(request, order, imgs);
			//2.存储进度内容
			order=orderService.get(order.getId());
			order.setOrderDetails(details);
			orderService.update(order);
			//3.更新房源状态,房屋已售出和已取消
			HousingInfo info= housingInfoService.get(order.getHousingInfo().getId());
			info.setFollowUpTime(new Date());
			if (order.getStatus()==4) {
				housingInfoService.updateHousingInfoStatus(info,4);
			}else if (order.getStatus()==0) {
				housingInfoService.updateHousingInfoStatus(info,0);
			}else{
				housingInfoService.updateHousingInfoStatus(info,3);
			}
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 检查订单状态
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private HousingInfo checkStatus(HttpServletResponse response, Long id) throws Exception{
		HousingInfo info=housingInfoService.get(id);
		if (info.getStatus()>0) {
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write("false".getBytes());
			return null;
		}
		return info;
	}
	/**
	 * 保存订单
	 * @param request
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveOrder")
	@ResponseBody
	public ResultInfo saveOrder(HttpServletRequest request,@ModelAttribute OrderBean order) throws Exception {
		if (order.getClient()==null) {
			return new ResultInfo(false, "请输入客户名称!");
		}
		String type=request.getParameter("type");
		if ("1".equals(type)) {
			order.setEndTime(new Date());
		}
		String[] imgs=request.getParameterValues("imgUrls");
		saveImage(request, order, imgs);
		order.setOrderNumber("D"+new Date().getTime());
		order.setBusiness(getBusiness(request));
		orderService.save(order);
		Integer status=1;
		if (!"1".equals(type)) {
			status=2;
		}
		housingInfoService.updateHousingInfoStatus(order.getHousingInfo(),status);
		return new ResultInfo(true);
	}
	/**
	 * 保存图片到正式文件夹
	 * @param request
	 * @param housingInfo
	 * @param sets
	 * @param format
	 * @param imgs
	 * @throws IOException
	 */
	private void saveImage(HttpServletRequest request, OrderBean order,
			    String[] imgs)
			throws IOException {
		if (imgs!=null&&imgs.length>0) {
			Set<ImageUrl> sets=new HashSet<ImageUrl>();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		for (String imageUrl : imgs) {
			String imgUrl=FilenameUtils.getName(imageUrl);
			File destFile=new File(getRealPath(request, "/")+"/order/"+format.format(new Date())+"/"+imgUrl);
			File srcFile=new File(getRealPath(request, "/")+"/temp/"+imgUrl);
			if (srcFile.exists()) {
				FileUtils.moveFile(srcFile, destFile);
				sets.add(new ImageUrl("/order/"+format.format(new Date())+"/"+imgUrl));
			}
		}
		if (sets.size()>0) {
			order.setImgUrls(sets);
		}
		}
	}
	/**
	 * 更新订单状态
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateOrderStatus")
	@ResponseBody
	public ResultInfo updateOrderStatus(HttpServletRequest request,Long id,Integer status,Date endTime) throws Exception {
		OrderBean bean=orderService.get(id);
//		0-未出租,1-已出租,0-未卖出,2-已预订,3-交易中,4-已卖出
		if (status==4) {
			if (endTime==null) {
				endTime=new Date();
			}
			bean.setEndTime(endTime);
		}
		bean.setStatus(status);
		orderService.update(bean);
		housingInfoService.updateHousingInfoStatus(bean.getHousingInfo(),status);
		return new ResultInfo(true);
	}
	/**
	 * 删除订单
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteOrder")
	@ResponseBody
	public ResultInfo deleteOrder(HttpServletRequest request,Long id) throws Exception {
		OrderBean bean=orderService.get(id);
		if (bean.getDelFlag()==1) {
			bean.setDelFlag(0);
			bean.setStatus(0);
			orderService.update(bean);
		}else{
			orderService.delete(bean.getId());
		}
		return new ResultInfo(true);
	}
	/**
	 * 查询订单分页信息
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryOrder")
	@ResponseBody
	public PageList<OrderBean> queryOrder(HttpServletRequest request,@ModelAttribute OrderQuery query) throws Exception {
		Business business=getBusiness(request);
		if (business.getLevel()==0) {
			query.setBusiness(getBusinessId(request));
		}
		return orderService.findQuery(query);
	}
	/**
	 * 显示图片
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showImg")
	public String showImg(HttpServletRequest request,Long id) throws Exception {
		///小区名称+楼层数;下面就是图片
		//1.获取数据信息
		OrderBean bean=orderService.get(id);
		request.setAttribute("housing", bean.getHousingInfo());
		//2.获取图片列表
		Set<ImageUrl> list=bean.getImgUrls();
		request.setAttribute("list", list);
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("upName",getTypeName(request.getParameter("type")));
		for (ImageUrl imageUrl : list) {
			request.setAttribute("imageUrl", imageUrl.getImgUrl());
			break;
		}
		return "housing/showImg";
	}
	/**
	 * 显示订单信息
	 * @param request
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showOrderInfo")
	public String showOrderInfo(HttpServletRequest request,String no) throws Exception {
		OrderBean bean=orderService.getOrderByNo(no);
		request.setAttribute("bean", bean);
		return "order/showOrderInfo";
	}
}
