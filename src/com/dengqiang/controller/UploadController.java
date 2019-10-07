package com.dengqiang.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dengqiang.bean.ImageUrl;
import com.dengqiang.bean.ResultInfo;
import com.dengqiang.integerceptor.FileUploadStatus;
import com.dengqiang.service.IHousingInfoService;
import com.dengqiang.util.Kit;
import com.dengqiang.util.LoggerUtils;

/**
 * 上传图片
 * @author dengqiang
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
    @Autowired
	private IHousingInfoService housingInfoService;
	 @RequestMapping("/getBar")
	 public void getBar(HttpServletRequest request, HttpServletResponse response) {
	 	HttpSession session = request.getSession();
	 	FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
	 	try {
	 		response.reset();
	 		if (status==null) {
				status=new FileUploadStatus();
			}
	 		response.getWriter().write("{\"pBytesRead\":"
	 				+status.getPBytesRead()+",\"pContentLength\":"+status.getPContentLength()+"}");
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	 }
	 @RequestMapping("/uploadImage")
	 public void uploadImg(@RequestParam(value = "imgFile") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 String filename=new Date().getTime()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
		 String logo=request.getParameter("type");
		 if (StringUtils.isNotBlank(logo)) {
			filename="logo."+FilenameUtils.getExtension(file.getOriginalFilename());
		}
		 File filepath=new File(getRealPath(request, "/")+"/temp/"+filename);
		 if (filepath.exists()) {
			filepath.delete();
		}
		 if (!filepath.getParentFile().exists()) {
			 filepath.getParentFile().mkdirs();
		}
		 if (StringUtils.isNotBlank(logo)) {
		 		try {
		 			file.transferTo(filepath);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
		}else{
			Kit.pressText(filepath, file);
		}
		 Kit.writeJson(response,"/temp/"+filename);
	}
	 /**
	  * 存储图片
	  * @param request
	  * @param imgs
	  * @return
	  * @throws Exception
	  */
	@RequestMapping("getBusinessList")
	@ResponseBody
	public ResultInfo saveImg(HttpServletRequest request,String[] imgs) throws Exception {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		for (String img : imgs) {
			File destFile=new File(getRealPath(request, "/")+"/"+format.format(new Date())+"/"+img);
			File srcFile=new File(getRealPath(request, "/")+"/temp/"+img);
			FileUtils.moveFile(srcFile, destFile);
		}
		return new ResultInfo();
	}
	/**
	 * 删除图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("removeTemp")
	@ResponseBody
	public ResultInfo removeTemp(HttpServletRequest request) throws Exception {
		String imgUrl=request.getParameter("imgUrl");
		int ext=imgUrl.split(":").length;
		if (StringUtils.isNotBlank(imgUrl)&&ext>0) {
			File srcFile=new File(getRealPath(request, null)+"/"+imgUrl);
			if (srcFile.exists()&&srcFile.isFile()) {
				srcFile.delete();
			}
			 ImageUrl url=new ImageUrl(imgUrl);
			 housingInfoService.deleteImg(url);
			LoggerUtils.info(srcFile);
		}
		return new ResultInfo(true);
	}
}
