package com.cter.util;

import org.apache.log4j.Logger;

/**
 * 功能：统一设置日志类。
 * 只需要new BaseLog(LogName)和log4j.properties配置即可设置log日志
 * @author op1768
 *
 */
public class BaseLog {
			
	  private Logger logger = null;
	    
	    public BaseLog(String logName){
	        logger=Logger.getLogger(logName);
	    }

	    public void debug(String msg) {
	        logger.debug(msg);
	    }
	    
	    public void info(String msg) {
	        logger.info(msg);
	    }

	    public void error(String msg) {
	        logger.error(msg);
	    }

	    public synchronized void printStackTrace(Exception e)  {
	        logger.error("错误信息：" + e.getMessage());
	        StackTraceElement[] trace = e.getStackTrace();
	        for (int i = 0; i < trace.length; i++){
	            logger.error("\tat " + trace[i]);
	          }
	    }
	
}
