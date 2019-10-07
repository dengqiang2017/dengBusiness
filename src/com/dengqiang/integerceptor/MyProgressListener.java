package com.dengqiang.integerceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.dengqiang.util.LoggerUtils;

public class MyProgressListener implements ProgressListener {
	private HttpSession session;

	public MyProgressListener(HttpServletRequest req) {
		session=req.getSession();
		FileUploadStatus status = new FileUploadStatus();
		session.setAttribute("status", status);
	}

	public MyProgressListener(HttpSession session) {
		FileUploadStatus status = new FileUploadStatus();
		this.session=session;
		session.removeAttribute("status");
		session.setAttribute("status", status);
	}

	/* pBytesRead  到目前为止读取文件的比特数
	 * pContentLength 文件总大小
	 * pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
		status.setPBytesRead(pBytesRead);
		status.setPContentLength(pContentLength);
		status.setPItems(pItems);
		LoggerUtils.info(status);
	}

}
