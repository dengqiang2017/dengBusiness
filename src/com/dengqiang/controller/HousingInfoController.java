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
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.bean.dictionary.DataDictionary;
import com.dengqiang.bean.dictionary.DictionaryDetail;
import com.dengqiang.page.HousingQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IDictionaryDetailService;
import com.dengqiang.service.IHousingInfoService;
import com.dengqiang.service.IUserInfoService;
/**
 * 信息控制
 * @author dengqiang
 *
 */
@Controller
@RequestMapping("/housingInfo")
public class HousingInfoController extends BaseController {
	@Autowired
	private IHousingInfoService housingService;
	@Autowired
	private IDictionaryDetailService dictionaryDetailService; 
	@Autowired
	private IUserInfoService userInfoService;
	/**
	 * 跳转到出租信息页面 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tenement")	
	public String tenement(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String type=request.getParameter("type");
		String del=request.getParameter("del");
		if (StringUtils.isNotBlank(del)&&"del".equals(del)) {
			request.setAttribute("del",del);
		}else{
			request.setAttribute("del","");
		}
		request.setAttribute("typeName", getTypeName(type));
		request.setAttribute("type", type);
		String all=request.getParameter("all");
		request.setAttribute("all", all);
		if (StringUtils.isNotBlank(all)&&"all".equals(all)) {
			request.setAttribute("allName", "所有");
		}
		getSelect(request);
		return "housing/tenement";
	}
	/**
	 * 保存房屋信息
	 * @param request
	 * @param response
	 * @param housingInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveHousing")
	@ResponseBody
	public ResultInfo saveHousingInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute HousingInfo housingInfo) throws Exception {
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//		Long date= format.parse("2016-06-06").getTime();
//		Long now=new Date().getTime();
//		if (now>date) {
//			return new ResultInfo(false,"您使用的是系统已经到期,请联系作者更换新版系统!!!");
//		}
		String[] imgs=request.getParameterValues("imgUrls");
		if (checkData(housingInfo)) {
			return new ResultInfo(false,"数据不完整!");
		}
		saveImage(request, housingInfo, imgs);
		dictionaryDetailService.updateDictionary(housingInfo);
		Business business=new Business();
		business.setId(getBusinessId(request));
		housingInfo.setBusiness(business);
		if (housingInfo.getId()!=null) {
			HousingInfo info= housingService.get(housingInfo.getId());
			housingInfo.setEstablishTime(info.getEstablishTime());
			housingInfo.setNumber(info.getNumber());
			housingInfo.setPublishTime(info.getPublishTime());
			
			housingInfo.setRefreshTime(new Date());
			housingService.merge(housingInfo);
//			housingService.update(housingInfo);
		}else{
			housingInfo.setPublishTime(new Date());
			housingInfo.setEstablishTime(new Date());
			housingInfo.setNumber("F"+new Date().getTime());
			housingInfo.setRefreshTime(new Date());
			housingService.save(housingInfo);
		}
		return new ResultInfo(true);
	}
	private boolean checkData(HousingInfo housingInfo) {
		try {
			if (housingInfo.getDistrict()==null||housingInfo.getDistrict().getName().contains("不限")) {
				housingInfo.setDistrict(null);
//				return true;
			}
			if (housingInfo.getCommunity()==null||housingInfo.getCommunity().getName().contains("不限")) {
				housingInfo.setCommunity(null);
				return true;
			}
			if (housingInfo.getDecorationType()==null||housingInfo.getDecorationType().getName().contains("不限")) {
				housingInfo.setDecorationType(null);
				return true;
			}
			if (housingInfo.getHouseType()==null||housingInfo.getHouseType().getName().contains("不限")) {
				housingInfo.setHouseType(null);
				return true;
			}
			if (housingInfo.getHousingType()==null||housingInfo.getHousingType().getName().contains("不限")) {
				housingInfo.setHousingType(null);
				return true;
			}
			if (housingInfo.getPlate()==null||housingInfo.getPlate().getName().contains("不限")) {
				housingInfo.setPlate(null);
//				return true;
			}
			if (housingInfo.getRentType()==null||housingInfo.getRentType().getName().contains("不限")) {
				housingInfo.setRentType(null);
//				return true;
			}
			if (housingInfo.getHomeowner()==null) {
				housingInfo.setHomeowner(null);
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
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
	private void saveImage(HttpServletRequest request, HousingInfo housingInfo,
			    String[] imgs)
			throws IOException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		if (imgs!=null&&imgs.length>0) {
			Set<ImageUrl> sets=new HashSet<ImageUrl>();
		for (String imageUrl : imgs) {
			String imgUrl=FilenameUtils.getName(imageUrl);
			File destFile=new File(getRealPath(request, "/")+"/"+format.format(new Date())+"/"+imgUrl);
			File srcFile=new File(getRealPath(request, "/")+"/temp/"+imgUrl);
			if (srcFile.exists()) {
				FileUtils.moveFile(srcFile, destFile);
				sets.add(new ImageUrl("/"+format.format(new Date())+"/"+imgUrl));
			}
		}
		if (sets.size()>0) {
			housingInfo.setImgUrls(sets);
		}
		}
	}
	/**
	 * 删除房屋信息并放入到回收站
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteHousingInfo")
	@ResponseBody
	public ResultInfo deleteHousingInfo(HttpServletRequest request,HttpServletResponse response,Long id) throws Exception {
		housingService.deleteHousingInfo(id);
		return new ResultInfo(true);
	}
	/**
	 * 永久删除信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteHousingInfoPermanent")
	@ResponseBody
	public ResultInfo deleteHousingInfoPermanent(HttpServletRequest request,HttpServletResponse response,Long id) throws Exception {
		//查询是否是管理员
		String msg=null;
		boolean flag=false;
		HousingInfo info= housingService.get(id);
		if (checkAdmin(request)||(info!=null&&info.getBusiness().getId()==getBusinessId(request))) {
			try {
				housingService.delete(id);
				flag=true;
			} catch (Exception e) {
				e.printStackTrace();
				msg="删除失败!"+e.getMessage();
			}
		}else{
			msg="删除失败,只能删除自己创建的记录或者管理员才能永久删除!";
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 获取信息分页记录
	 * @param request
	 * @param response
	 * @param query 查询对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryhousing")
	@ResponseBody
	public PageList<HousingInfo> queryHousing(HttpServletRequest request,HttpServletResponse response,@ModelAttribute HousingQuery query) throws Exception {
		Business business=getBusiness(request);
		if (business.getLevel()==0) {
			query.setBusiness(getBusinessId(request));
		}
		 PageList<HousingInfo> list= housingService.findQuery(query);
		 return list;
	}
	/**
	 * 查询回收站中的信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryHousingRecycle")
	@ResponseBody
	public PageList<HousingInfo> queryHousingRecycle(HttpServletRequest request,HttpServletResponse response,@ModelAttribute HousingQuery query) throws Exception {
		query.setDelFlag(0);
		Business business=getBusiness(request);
		if (business.getLevel()==0) {
			query.setBusiness(getBusinessId(request));
		}
		if (!checkAdmin(request)) {
			query.setBusiness(getBusinessId(request));
		}
		return housingService.findQuery(query);
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
		HousingInfo housingInfo=housingService.get(id);
		request.setAttribute("housing", housingInfo);
		//2.获取图片列表
		Set<ImageUrl> list=housingInfo.getImgUrls();
		request.setAttribute("list", list);
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("upName",getTypeName( request.getParameter("type")));
		for (ImageUrl imageUrl : list) {
			request.setAttribute("imageUrl", imageUrl.getImgUrl());
			break;
		}
		return "housing/showImg";
	}
////////////////////////////个人出租信息begin///////////////////////
	/**
	 * 跳转到出租信息添加或者编辑页面
	 * @param request
	 * @param operation add-添加,edit-编辑
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editTenement")
	public String editTenement(HttpServletRequest request,String operation,String type,Long id) throws Exception {
		if (StringUtils.isNotBlank(operation)) {
			if ("edit".equals(operation)&&id!=null) {
				HousingInfo houseInfo=housingService.get(id);
				request.setAttribute("houseInfo", houseInfo);
			}
			getSelect(request);
		}
		request.setAttribute("operation", operation);
		request.setAttribute("type", type);
		request.setAttribute("upName",getTypeName(type));
		return "housing/infoEdit";
	}
	/**
	 * 获取下拉框数据
	 * @param request
	 * @throws Exception
	 */
	private void getSelect(HttpServletRequest request) throws Exception {
		List<DictionaryDetail> districts=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(0));
		request.setAttribute("districts", districts);
		List<DictionaryDetail> plates=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(1));
		request.setAttribute("plates", plates);
		List<DictionaryDetail> communitys=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(2));
		request.setAttribute("communitys", communitys);
		List<DictionaryDetail> houseTypes=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(3));
		request.setAttribute("houseTypes", houseTypes);
		List<DictionaryDetail> rentTypes=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(4));
		request.setAttribute("rentTypes", rentTypes);
		List<DictionaryDetail> decorationTypes=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(5));
		request.setAttribute("decorationTypes", decorationTypes);
		List<DictionaryDetail> housingTypes=dictionaryDetailService.getDetailsByName(DataDictionary.getDictionaryName(6));
		request.setAttribute("housingTypes", housingTypes);
//		List<UserInfo> homeowners= userInfoService.getHomeownersByBusinessId(getBusiness(request));
//		request.setAttribute("homeowners", homeowners);
	}
	
}
