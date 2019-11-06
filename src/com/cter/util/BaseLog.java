package com.cter.util;

import org.apache.log4j.Logger;

/**
 * ���ܣ�ͳһ������־�ࡣ
 * ֻ��Ҫnew BaseLog(LogName)��log4j.properties���ü�������log��־
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
	        logger.error("������Ϣ��" + e.getMessage());
	        StackTraceElement[] trace = e.getStackTrace();
	        for (int i = 0; i < trace.length; i++){
	            logger.error("\tat " + trace[i]);
	          }
	    }
	
}
