package com.dengqiang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/publishInfo")
public class PublishInfoController {
	
	/**
	 * 发布数据接收接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/publishInfo")
	@ResponseBody
	public String publishInfo(HttpServletRequest request) {
		return "success";
	}
}
