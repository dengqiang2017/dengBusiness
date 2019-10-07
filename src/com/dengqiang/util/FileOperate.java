package com.dengqiang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dengqiang.bean.FileTree;
import com.dengqiang.bean.ZoneBean;

public class FileOperate {
	private static Logger logger = Logger.getLogger(FileOperate.class);
	public static List<FileTree> getlist(File f, String filename) {// 递归求取目录文件个数
		List<FileTree> fileTrees = new ArrayList<FileTree>();
		File flist[] = f.listFiles();
		if (flist == null) {
			return new ArrayList<FileTree>();
		}
		if (!StringUtils.isNotBlank(filename)) {
			FileTree fileTree = new FileTree("全部文件", "");
			fileTrees.add(fileTree);
			getFileTree(fileTree.getChildren(), flist);
		} else {
			getFileTree(fileTrees, flist);
		}
		return fileTrees;

	}

	private static void getFileTree(List<FileTree> fileTrees, File[] flist) {
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				String path = flist[i].getPath().split(ConfigFile.SPLIT)[1];
				fileTrees.add(new FileTree(flist[i].getName(), path));
				logger.info(fileTrees);
			}
		}
	}

	public List<File> getFileList(String filepath) {
		try {
			File file = new File(filepath);
			if (file.isDirectory()) {// 判断是否目录
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					// if (filelist[i].equals(".svn")) {
					File Ifile = new File(filepath + ConfigFile.ROOT + filelist[i]);
					// System.out.println(Ifile);
					// }

					FileInputStream fis = new FileInputStream(Ifile);
					// 修改时间
					long modifiedTime = Ifile.lastModified();
					Date date = new Date(modifiedTime);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:MM");
					String dd = sdf.format(date);
					logger.info("File name:" + Ifile.getName()
							+ " \tFile size: "
							+ (double) ((double) fis.available() / 1024 / 1024)
							+ "M" + " \tFile create Time: " + dd);
					fis.close();
				}
			}
		} catch (Exception e) {
			if (ConfigFile.PRINT_ERROR) {
				e.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * 读取文件创建时间
	 */
	public static void getCreateTime(String filePath) {
		String strTime = null;
		try {
			Process p = Runtime.getRuntime().exec(
					"cmd /C dir " + filePath + "/tc");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.endsWith(".txt")) {
					strTime = line.substring(0, 17);
					break;
				}
			}
			br.close();
			is.close();
		} catch (IOException e) {
			if (ConfigFile.PRINT_ERROR) {				
				e.printStackTrace();
			}
		}
		System.out.println("创建时间    " + strTime);
		// 输出：创建时间 2009-08-17 10:21
	}

	/**
	 * 读取文件修改时间的方法1
	 */
	@SuppressWarnings("deprecation")
	public static void getModifiedTime_1(String filepath) {
		File f = new File(filepath);
		Calendar cal = Calendar.getInstance();
		long time = f.lastModified();
		cal.setTimeInMillis(time);
		// 此处toLocalString()方法是不推荐的，但是仍可输出
		System.out.println("修改时间[1] " + cal.getTime().toLocaleString());
		// 输出：修改时间[1] 2009-8-17 10:32:38
	}

	/**
	 * 读取修改时间的方法
	 * 
	 * @param filepaths
	 */
	public static String getModifiedTime(File f) {
		Calendar cal = Calendar.getInstance();
		long time = f.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.setTimeInMillis(time);
		return formatter.format(cal.getTime());
	}

	public static String getFileSizeToStr(File file) {
		Double size = (getFileSize(file) * 1.0) / 1024;
		return getFileSizeToStr(size);
	}

	public static String getFileSizeToStr(Double size) {
		String unit = "KB";
		if (size > 1024) {
			size = size / 1024;
			unit = "MB";
			if (size > 1024) {
				size = size / 1024;
				unit = "GB";
			}
		}
		DecimalFormat df = new DecimalFormat("#.00");
		if (size == 0) {
			return "0KB";
		}
		String len = df.format(size) + unit;
		return len;
	}

	// 递归
	public static Long getFileSize(File f)// 取得文件夹大小
	{
		Long size = 0l;
		if (f.isFile()) {
			return f.length();
		}
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 获取zone文件夹下面的文件列表
	 * 
	 * @param f
	 * @return
	 */
	public static List<ZoneBean> getFileList(File f, int type) {
		File flist[] = f.listFiles();
		if (flist == null) {
			return null;
		}
		List<ZoneBean> fileList = new ArrayList<ZoneBean>();
		for (int i = 0; i < flist.length; i++) {
			ZoneBean bean = new ZoneBean();
			bean.setSize(getFileSizeToStr(flist[i]));
			bean.setDate(getModifiedTime(flist[i]));
			String paths = null;
			if (type == 1) {
				String[] fils = flist[i].getName().split("\\)");
				String name = fils[fils.length - 1];
				bean.setFilename(name);
				paths = flist[i].getPath().split("recycle")[1];
				// paths=paths.replaceAll("\\)", ConfigFile.ROOT);
			} else {
				bean.setFilename(flist[i].getName());
				paths = flist[i].getPath().split(ConfigFile.SPLIT)[1];
			}
			bean.setPath(paths);
			if (flist[i].isFile()) {
				bean.setFiletype(true);
			} else {
				bean.setFiletype(false);
			}
			fileList.add(bean);
			logger.info(bean);
		}
//		ComparatorZone comparator = new ComparatorZone();
//		Collections.sort(fileList, comparator);
		return fileList;
	}
	// 移动文件夹
	public static void moveFolder(String src, String dest) {
		File srcFolder = new File(src);
		File destFolder = new File(dest);
		// if (!destFolder.exists()) {
		// destFolder.mkdirs();
		// }
		File newFile = new File(destFolder.getAbsoluteFile() + ConfigFile.ROOT
				+ srcFolder.getName());
		srcFolder.renameTo(newFile);
	}
	/**
	 * 获取文件的绝对路径
	 * @param request
	 * @param filePath 文件存放的固定目录名
	 * @param path 文件相对路径名
	 * @return
	 */
	public static File getFilePath(String filePath,String path) {
		File file = new File(ConfigFile.TOMCAT_DIR+ filePath+ FilenameUtils.getName(path));
		return file;
	}
	
	public static void deleteFile(File file) {
		if (file != null) {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
