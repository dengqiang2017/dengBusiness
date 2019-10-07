package com.dengqiang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具
 * 
 * @author dengqiang
 * 
 */
public abstract class DateTimeUtils {
	/**
	 * 日期时间格式化
	 */
	public static final SimpleDateFormat dateTime_format = new SimpleDateFormat(
			ConfigFile.DATETIME_FORMAT, Locale.CHINA);
	public static final SimpleDateFormat date_format = new SimpleDateFormat(
			ConfigFile.DATE_FORMAT, Locale.CHINA);
	/**
	 * 字符串转换为日期
	 * @param source
	 * @return 转换后的日期
	 */
	public static Date strToDate(String source) {
		try {
			return date_format.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转换为日期时间型
	 * @param source
	 * @return 转换后的日期时间
	 */
	public static Date strToDateTime(String source) {
		try {
			return dateTime_format.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期转换为字符串
	 * @param date
	 * @return 转换后的字符串
	 */
	public static String dateToStr(Date date) {
		return date_format.format(date);
	}
	/**
	 * 日期时间型转换为字符串
	 * @param date
	 * @return 转换后的字符串
	 */
	public static String dateTimeToStr(Date date) {
		return dateTime_format.format(date);
	}
	/**
	 * 日期转换为字符串
	 * @return 转换后的字符串
	 */
	public static String dateToStr() {
		return date_format.format(new Date());
	}
	/**
	 * 日期时间型转换为字符串
	 * @return 转换后的字符串
	 */
	public static String dateTimeToStr() {
		return dateTime_format.format(new Date());
	}
}
