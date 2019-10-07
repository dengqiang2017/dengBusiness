package com.dengqiang.util;

import org.apache.log4j.Logger;

public abstract class LoggerUtils {
public static Logger log=Logger.getLogger(LoggerUtils.class);
public static void info(Object message) {
	log.info(message);
}
public static void error(Object message) {
	log.error(message);
}
public static void error(Object message, Throwable t) {
	log.error(message, t);
}
}
