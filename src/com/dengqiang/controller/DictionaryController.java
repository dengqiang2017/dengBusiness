package com.dengqiang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.bean.ResultInfo;
import com.dengqiang.bean.dictionary.DataDictionary;
import com.dengqiang.bean.dictionary.DictionaryDetail;
import com.dengqiang.page.DataDictionaryQuery;
import com.dengqiang.page.DictionaryDetailQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IDataDictionaryService;
import com.dengqiang.service.IDictionaryDetailService;
/**
 * 数据字典和明细控制
 * @author dengqiang
 *
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private IDataDictionaryService dataDictionaryService;
	@Autowired
	private IDictionaryDetailService dictionaryDetailService;
	/**
	 * 跳转到数据字典页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dictionary")
	public String dictionary() throws Exception {
		return "dictionary/dictionary";
	}
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param response
	 * @param id 数据id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editDictionary")
	public String editDictionary(HttpServletRequest request,HttpServletResponse response,Long id) throws Exception {
		if (id!=null) {
			request.setAttribute("dictionary", dictionaryDetailService.get(id));
			request.setAttribute("operation", "edit");
		}else{
			request.setAttribute("operation", "add");
		}
		request.setAttribute("dictionarys", dataDictionaryService.getAll());
		return "dictionary/dictionaryEdit";
	}
	/**
	 * 查询数据字典分页列表
	 * @param request
	 * @param response
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryDataDictionary")
	@ResponseBody
	public PageList<DataDictionary> queryDataDictionary(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DataDictionaryQuery query) throws Exception {
		return dataDictionaryService.findQuery(query);
	}
	
	/**
	 * 查询数据字典明细分页列表
	 * @param request
	 * @param response
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryDictionaryDetail")
	@ResponseBody
	public PageList<DictionaryDetail> queryDictionaryDetail(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DictionaryDetailQuery query) throws Exception {
		return dictionaryDetailService.findQuery(query);
	}
	/**
	 * 保存或者更新数据字典信息
	 * @param request
	 * @param response
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveDataDictionary")
	@ResponseBody
	public ResultInfo saveDataDictionary(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DataDictionary dataDictionary) throws Exception {
		boolean flag=false;
		String msg=null;
		try {
			if (dataDictionary.getId()!=null) {
				dataDictionaryService.update(dataDictionary);
			}else{
				dataDictionaryService.save(dataDictionary);
			}
			flag=true;
		} catch (Exception e) {
			msg="数据异常请检查!"+e.getMessage();
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 保存或者更新数据字典明细
	 * @param request
	 * @param response
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveDictionaryDetail")
	@ResponseBody
	public ResultInfo saveDictionaryDetail(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DictionaryDetail detail) throws Exception {
		boolean flag=false;
		String msg=null;
		try {
			if (detail.getId()!=null){
				dictionaryDetailService.update(detail);
			}else{
				dictionaryDetailService.save(detail);
			}
			flag=true;
		} catch (Exception e) {
			msg="数据异常请检查!"+e.getMessage();
		}
		return new ResultInfo(flag,msg);
	}
	/**
	 * 删除数据字典
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteDataDictionary")
	@ResponseBody
	public ResultInfo deleteDataDictionary(HttpServletRequest request,HttpServletResponse response,Long id) throws Exception {
		dataDictionaryService.delete(id);
		return new ResultInfo(true);
	}
	/**
	 * 删除数据字典明细
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteDictionaryDetail")
	@ResponseBody
	public ResultInfo deleteDictionaryDetail(HttpServletRequest request,HttpServletResponse response,Long id) throws Exception {
		dictionaryDetailService.delete(id);
		return new ResultInfo(true);
	}
	/**
	 * 根据数据字典名字查询明细
	 * @param request
	 * @param response
	 * @param name 数据字典明细
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDetailsByName")
	@ResponseBody
	public List<DictionaryDetail> getDetailsByName(HttpServletRequest request,HttpServletResponse response,String name) throws Exception {
		return dictionaryDetailService.getDetailsByName(name);
	}
}
