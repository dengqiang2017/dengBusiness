package com.dengqiang.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dengqiang.util.Kit;
import com.dengqiang.util.LoggerUtils;


@Component
public  class MyExceptionHandler implements HandlerExceptionResolver{
 
   public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex){
	   if (Kit.DEBUG) {
	      return null;
		}else{
			Map<String, Object> model=new HashMap<String,Object>();
			model.put("ex", ex);
			LoggerUtils.error(ex,ex.fillInStackTrace()); 
			if(ex instanceof IOException){
				return new ModelAndView("error");
			}else if(ex instanceof SQLException){
				return new ModelAndView("error");
			}else if(ex instanceof NullPointerException){
				if(request.getSession().getAttribute("kitty")==""){
					return new ModelAndView("/login");
				}
				return new ModelAndView("error");
			}else if(ex instanceof IndexOutOfBoundsException){
				request.setAttribute("error_message", "数据异常，请稍候重试！"+ex.getMessage());
				return new ModelAndView("error");
			}else{
				return new ModelAndView("error");
			}
		}
   }
}