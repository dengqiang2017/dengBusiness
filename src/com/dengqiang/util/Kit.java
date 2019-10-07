package com.dengqiang.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dengqiang.bean.ResultInfo;
import com.dengqiang.util.word.Application;
import com.dengqiang.util.word.Document;
import com.dengqiang.util.word.Selection;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class Kit { 
	public static final String ERROR_MESSAGE = "error_message";
	public static final String ERROR_PAGE = "Error/error1";
	public static final boolean DEBUG = true; 
	 
	/**
	 * 生成随机字符串
	 * @return
	 */
	public static String getRandom(){
		char[] c = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','Y','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
		//char[] c = {'0','1','2','3','4','5','6','7','8','9'};
		int max = c.length-1;
		String code = "";
		String[] time = (new Date().getTime() + "").trim().split("");
		//for(int i=0;i<time.length;i++){ //27wei
		for(int i=0;i<8;i++){ //15wei
	        int num = (int)Math.round(Math.random()*max);
	        code+=c[num]+time[i];
	    }
	    return code;
	}
	
	/**
	 * 生成随机字符串
	 * @return
	 */
	public static String getRandompwd(){
		char[] c = {'0','1','2','3','4','5','6','7','8','9'};
		int max = c.length-1;
		String code = "";
		String[] time = (new Date().getTime() + "").trim().split("");
		//for(int i=0;i<time.length;i++){ //27wei
		for(int i=0;i<8;i++){ //15wei
	        int num = (int)Math.round(Math.random()*max);
	        code+=c[num]+time[i];
	    }
	    return code;
	}
	
	/**
	 * 生成6位随机数
	 * 2014-4-29
	 * @return
	 */
	public static String genRandom(){
		Random random6 = new Random();
		String result="";
		for(int i=0;i<6;i++){
		  result+=random6.nextInt(10);
		}
		//System.out.print(result);
		return result;
	}
	
	/**
	 * 生成6位不重复的随机数
	 * 2014-4-29
	 * @return
	 */
	public static String generalRandom(){
		//生成6位不重复的随机数 start
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < 6; i++)
		    result = result * 10 + array[i];
		LoggerUtils.info(result);
		//生成6位不重复的随机数 end
		return String.valueOf(result);
	}
	                             
  	/**
  	 * 获取日期时间差
  	 * @param endTime yyyy-MM-dd HH:mm:ss格式
  	 * @return 日期加时间 [yyyy-MM-dd HH:mm:ss]
  	 */
  	public synchronized static String getDateTimeDifference(String endTime){
  		if (StringUtils.isBlank(endTime)) {
  			return "不能使用空字符串!,请输入一个日期yyyy-MM-dd HH:mm:ss格式.";
		}

  		SimpleDateFormat formatTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
  		SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
  		Date begin=new Date();
  		
  		Date end=null;
  		try {
  			end=formatTime.parse(endTime);
  			LoggerUtils.info("end:"+formatTime.format(end));
		} catch (Exception e) {
			return "日期格式不正确!";
		}
  		Long beginL=begin.getTime();
  		LoggerUtils.info("beginL:"+formatTime.format(beginL));
  		Long endL=end.getTime();
  		LoggerUtils.info("endL"+formatTime.format(endL));
  	/*	if (beginL>endL) {
  			
  			return "结束时间小于开始时间!";
  		}*/
  		String now=formatDate.format(new Date());
  		Long cha=endL-beginL;
  		try {
  			String s = null;
  			s=formatTime.format(new Date(formatDate.parse(now).getTime()+ cha));
			return s;
		} catch (ParseException e) {
			return "计算日期差值出错!";
		}
  	}
  	/**
  	 * 获取时间差
  	 * @param endTime yyyy-MM-dd HH:mm:ss格式
  	 * @return 时间差值[时:分:秒]
  	 */
  	public synchronized static String getTimeDifference(String endTime){
  		
  		if (StringUtils.isBlank(endTime)) {
  			return "不能使用空字符串!,请输入一个日期yyyy-MM-dd HH:mm:ss格式.";
		}
  		LoggerUtils.info(endTime);
  		String[] times=getDateTimeDifference(endTime).split(" ");
  		LoggerUtils.info(times);
  		if (times.length>0) {
			return times[1];
		}else{
			return "没有分钟值";
		}
	}
  	/**
  	 * 获取时间差值的数组
  	 * @param endTime yyyy-MM-dd HH:mm:ss格式
  	 * @return 时间差值数组[时,分,秒]
  	 */
  	public synchronized static String[] getTimesDifference(String endTime) {
  		if (StringUtils.isBlank(endTime)) {
  			throw new RuntimeException("不能使用空字符串!,请输入一个日期yyyy-MM-dd HH:mm:ss格式.");
		}
		return getTimeDifference(endTime).split(":");
	}
	/**
	 * 获取日期差
	 * @param endTime yyyy-MM-dd HH:mm:ss格式或者传入yyyy-MM-dd格式,将会自动在后面添加00:00:00
	 * @return 日期天数间隔[天]
	 */
  	public synchronized static String getDateDifference(String endTime){
  		if (StringUtils.isBlank(endTime)) {
  			return "不能使用空字符串!,请输入一个日期yyyy-MM-dd HH:mm:ss格式.";
		}
  		String[] end=endTime.split(" ");
  		if (end.length>0&&end.length<2) {
			endTime=endTime+" 00:00:00";
		}
  		String date=getDateTimeDifference(endTime).split(" ")[0];
  		date=date.split("-")[2];
		return date;
	}
  	/**
  	 * 
  	 * @Description: 去除字符串中的空格、回车、换行符、制表符
  	 * @param @param str
  	 * @param @return   
  	 * @return String  
  	 * @throws
  	 * @author XT
  	 * @date 2014年12月2日
  	 */
    public synchronized static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            java.util.regex.Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    public synchronized static String timeDifference(String endTime){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
    	long l;
    	String timeDate=null;
		try {
			l = sdf.parse(endTime).getTime() - new Date().getTime();
			if (l > 0) {
	              long day = l / (24 * 60 * 60 * 1000);
	              long hour = (l / (60 * 60 * 1000) - day * 24);
	              long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
	              long se = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	              timeDate = hour+":"+min+":"+se;
	             // LoggerUtils.info("：" + hour + "小时" + min + "分" + se + "秒 ");
	              LoggerUtils.info("timeDate:"+timeDate);
	    }
		} catch (ParseException e) {
			e.printStackTrace();
		}
          
		return timeDate;
}
	public synchronized static void writeJson(HttpServletResponse response,String msg) throws IOException {
		ResultInfo info=new ResultInfo(true,msg);
		JSONObject json=JSONObject.fromObject(info);
		response.setContentType("text/html;charset=utf-8");
		response.getOutputStream().write(json.toString().getBytes("utf-8"));
	}
	private synchronized static int getFontSize(int height) {
		int size=4;
		if (height>200&&height<400) {
			size=16;
		}else if (height>400&&height<500) {
			size=25;
		}else if (height>500&&height<600) {
			size=30;
		}else if (height>600&&height<800) {
			size=35;
		}else if (height>800&&height<1000) {
			size=40;
		}else{
			size=48;
		}
		return size;
	}
	/**
     * 打印文字水印图片
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */
    public static void pressText(File file,MultipartFile fileReq) {
    	String ext=FilenameUtils.getExtension(file.getName());
    	if (StringUtils.isBlank(ConfigFile.TEXT_WATERMARK)) {
    		try {
				fileReq.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		return;
		}
    	if ("jpg".equalsIgnoreCase(ext)||"png".equalsIgnoreCase(ext)||"gif".equalsIgnoreCase(ext)||"jpeg".equalsIgnoreCase(ext)||"bmp".equalsIgnoreCase(ext)) {
        try {
            ByteInputStream stream= new ByteInputStream(fileReq.getBytes(), fileReq.getBytes().length);
            Image src = ImageIO.read(stream);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            int fontSize=getFontSize(wideth);
            int x=wideth/10;
            int y=height/10;
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            String[] pressText=new String(ConfigFile.TEXT_WATERMARK).split(":");            
            g.setColor(new Color(159, 147, 123));//|Font.ITALIC
            g.setFont(new Font("宋体", Font.BOLD, fontSize));
            g.rotate(Math.toRadians(20));//,(double) x, (double) y);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.8f));
            g.drawString(pressText[0],  x,y );
//            g.drawString(pressText[1],  x,y+fontSize+30);
//            g.drawString(pressText[2],  x+20,y+fontSize+100);
            g.dispose();
            if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
            FileOutputStream out = new FileOutputStream(file);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image); 
            out.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    	}else if ("doc".equals(ext)||"docx".equals(ext)) {
			try {
				fileReq.transferTo(file);	//修改 
				word(file.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if ("pdf".equals(ext)) {
			try {
				fileReq.transferTo(file);	//修改 
				waterMark(file,fileReq.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if("xls".equals(ext)||"xlsx".equals(ext)){//修改 
    		try {
				fileReq.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}else{	//修改 
    		try {
				fileReq.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    	
    }
    
    public static void word(String filepath){
		Document doc=null;
		try {
			Application app=new Application();		
			doc = app.openExistDocument(filepath);
			Selection sel=app.getSelection();
			String img=filepath.split("upload")[0];
			File file=new File(img);
			 doc.setHeaderText("", sel.getInstance());//设置页眉
			doc.setImageWaterMark(file.getPath()+"/images/shy.png", sel.getInstance(),30,30,310,349);
			doc.save();
			doc.close();
		} catch (Exception e) {
			try {
				doc.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
    
    public static void waterMark(File file, byte[] bs) {
    	String[] waterMarkName="".split(":");
    	PdfStamper stamper =null;
        try {
            PdfReader reader = new PdfReader(bs);
            stamper = new PdfStamper(reader, new FileOutputStream(file));  
            BaseFont base = BaseFont.createFont(
                    "C:/WINDOWS/Fonts/SIMSUN.TTC,1", "Identity-H", true);// 使用系统字体  
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;  
            int rise = 0;
            for (int i = 1; i < total; i++) {
                rise = 400;
                under = stamper.getUnderContent(i);  
                pdfWriteText(waterMarkName[0], base, under, rise);
                pdfWriteText(waterMarkName[1], base, under, rise-50);
                pdfWriteText(waterMarkName[2], base, under, rise-100);
                under.setLineWidth(1f);  
                under.stroke();  
            }
            stamper.close();
        } catch (Exception e) {
        	try {
				stamper.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            e.printStackTrace();  
        }  
    }

	private static void pdfWriteText(String waterMarkName, BaseFont base,
			PdfContentByte under, int rise) {
		char c;
		under.beginText();
		under.setFontAndSize(base, 30);
		int j = waterMarkName.length();
		    under.setTextMatrix(100, 50);
		    for (int k = 0; k < j; k++) {
		        under.setTextRise(rise);  
		        c = waterMarkName.charAt(k);  
		        under.showText(c + "");  
		        rise -= 18;
		}
		// 添加水印文字  
		under.endText();
	} 
	
	/**
	 * 每天0点10分清除临时文件夹里面的文件
	 */
	public static void deleteTempFile(ServletContext servletContext) {
		File file=new File(servletContext.getRealPath("/")+"/temp/");
		if (file.exists()) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					e.printStackTrace();
					LoggerUtils.error(e);
				}
		}
	}
}
