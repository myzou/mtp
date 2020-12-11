package com.cter.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
    private Calendar calendar = null;

    private int day, month, year;
    
    

    public DateUtil() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    public DateUtil(java.sql.Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime((java.util.Date) date);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setCalendar(java.util.Date date) {
        this.calendar.setTime(date);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        //由于在Calendar中月是从0开始
        return month + 1;
    }

    public void setMonth(int month) {
        //由于在Calendar中月是从0开始
        this.month = month - 1;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDate() {
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
    
    
    /**
	 * add 7.30
	 * 把String日期转为util日期
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws Exception
	 */
	public static Date getDateByString(String dateStr, String formatStr)
			throws Exception {
		if (!StringUtil.isBlank(dateStr) && !StringUtil.isBlank(formatStr)) {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			return format.parse(dateStr);
		}
		return null;
	}
	

	/**
	 * 静态方法 根据两个日期求相差天数 正数表示d1大于d2天数 负数表示d1小于d2天数
	 * add 7.30
	 * @author zdl
	 */
	public static int dayDiffs(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return 0;
		}
		long tt = d1.getTime() - d2.getTime();
		int days = (int) (tt / (24 * 60 * 60 * 1000));
		return days;

	}
	/**
	 * 静态方法 根据两个日期求相差天数 正数表示d1大于d2天数 负数表示d1小于d2天数
	 * @author zdl
	 */
	public static int dayDiffs(String beginDateStr, String endDateStr) {
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = getDateByString(beginDateStr,"yyyy.MM.dd");
			endDate = getDateByString(endDateStr, "yyyy.MM.dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.dayDiffs(endDate, beginDate);

	}

    public java.sql.Date getSqlDate() {
        return new java.sql.Date(this.getDate().getTime());
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
    	return  DateUtil.getDateStr(date, "yyyy-MM-dd");
    }
    /**
     *yyyyMMdd
     * @param date
     * @return
     */
    public static String getDateStryyyyMMdd(Date date) {
    	return  DateUtil.getDateStr(date, "yyyyMMdd");
    }
    /**
     *yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String getDateStryMdHms(Date date) {
    	return  DateUtil.getDateStr(date, "yyyyMMddHHmmss");
    }
    
    /**
     * 把date时间格式化为yyyy-MM-dd HH:mm:ss返回
     * @param date
     * @return
     */
    public static Date getDate(Date date) {
        String datefor="yyyy-MM-dd HH:mm:ss";
        Date createDate=null;
		try {
			createDate = DateUtil.getDateByString(StringUtil.formatDate(date,datefor), datefor);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return createDate;
    }

    /**
     * 根据日期和格式化字符串得到格式化后的字符串
     * @param date
     * @param Pattern 例如：yyyyMMdd
     * @return
     */
    public static String getDateStr(Date date, String Pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(Pattern);
        return formatter.format(date);
    }

    public static java.sql.Date getNow() {
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public static java.sql.Timestamp getTimeNow() {
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }


	/**
	 * 获取传入时间所在年的第一天
	 * @param date
	 * @return
	 */
	public static Date getYearBeginDate(Date date) {
		if (date == null) {
			return null;
		}
		Date newDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		newDate = new Date(calendar.getTime().getTime());
		return newDate;
	}

	/**
	 * 根据日期区间获取年月日列表集合（yyyy-MM-dd）
	 * @param startDateStr 开始日期（yyyy-MM-dd）
	 * @param endDateStr 结束日期（yyyy-MM-dd）
	 * @return
	 * @throws Exception
	 * @author Gaohm 2010-03-29
	 */
	public static List getDateList(String startDateStr,String endDateStr) throws Exception
	{
		List dateList = new ArrayList();
		if(startDateStr != null && startDateStr != null)
		{
	        Date startDate = getDateByString(startDateStr,"yyyy-MM-dd");
	        Date endDate = getDateByString(endDateStr,"yyyy-MM-dd");
			//日期格式化
		    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		    //当前日期日历对象
	        Calendar calendar1 = Calendar.getInstance();
	        //开始日期的日历对象
	        calendar1.setTime(startDate);
			//获取两个日期相差的天数
			int days = (int)((endDate.getTime()-startDate.getTime())/(1000*60*60*24));
			//添加日期所在的第一个年月日
			dateList.add(sdFormat.format(calendar1.getTime()));
			for(int i = 0;i < days;i++)
			{
				//添加下一个年月日
				calendar1.add(Calendar.DAY_OF_MONTH, 1);
				dateList.add(sdFormat.format(calendar1.getTime()));
			}
		}
		return dateList;
	}

	/**
	 * 根据日期区间获取年月列表集合（yyyy-MM）
	 * @param startDateStr 开始日期（yyyy-MM-dd）
	 * @param endDateStr 结束日期（yyyy-MM-dd）
	 * @return
	 * @throws Exception
	 * @author Gaohm 2010-03-29
	 */
	public static List getMonthList(String startDateStr,String endDateStr) throws Exception
	{
		List monthList = new ArrayList();
		if(startDateStr != null && endDateStr != null)
		{
	        Date startDate = getDateByString(startDateStr,"yyyy-MM");
	        Date endDate = getDateByString(endDateStr,"yyyy-MM");
			//日期格式化
		    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM");
		    //当前日期日历对象
	        Calendar calendar1 = Calendar.getInstance();
	        Calendar calendar2 = Calendar.getInstance();
	        //开始日期的日历对象
	        calendar1.setTime(startDate);
	        //结束日期的日历对象
	        calendar2.setTime(endDate);
			//获取两个日期相差的月数
	        int months = (calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12 + (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH));
	        //添加日期所在的第一个年月
			monthList.add(sdFormat.format(calendar1.getTime()));
			for(int i = 0;i < months;i++)
			{
				//添加下一个年月
				calendar1.add(Calendar.MONTH, 1);
				monthList.add(sdFormat.format(calendar1.getTime()));
			}
		}
		return monthList;
	}
	
	   /**
     * 日期的增加月数
     *
     * @param date     日期
     * @param addMonth 增加的月数,正数表示增加，负数表示减少
     * @return Date 日期的增加月数后的结果
     */
    public static Date addMonth(Date date, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 日期的增加天数
     *
     * @param date   日期
     * @param addDay 增加的天数,正数表示增加，负数表示减少
     * @return Date 日期的增加月数后的结果
     */
    public static Date addDay(Date date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        return new Date(calendar.getTime().getTime());
    }
    
    /**
     * 日期的增加小时
     *
     * @param date   日期
     * @param addHour 增加的小时,正数表示增加，负数表示减少
     * @return Date 日期的增加月数后的结果
     */
    public static Date addHour(Date date, int addHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, addHour);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数 add by pengj
     */
    public static String getNextDay(Date d, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String mdate = "";
            // Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
                    * 60 * 60;
            d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

    
    
    
    
    
}