package com.cter.util;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * String 工具类
 * @author op1768
 *Date 2018-11-11
 */
public class StringUtil {

	// SQL参数的单引号处理
	public static String SQLConv( String str )
	{
		return str.replaceAll( "'", "''" );
	}

	// 把带html标签的字符串格式化
	public static String formatHtml( String str )
	{
		if ( str == null )
			return "";
		String temp = str;
		temp = temp.replaceAll( "<br>", "\n" );
		temp = temp.replaceAll( "&nbsp;&nbsp;", "   " );
		temp = temp.replaceAll( "&lt;", "<" );
		temp = temp.replaceAll( "&gt;", ">" );
		return temp;
	}

	// 把带html标签的字符串格式化
	public static String formatStrToHtml( String str )
	{
		if ( str == null )
			return "";
		String temp = str;
		temp = temp.replaceAll( "\n", "<br>" );
		temp = temp.replaceAll( "   ", "&nbsp;&nbsp;" );
		temp = temp.replaceAll( "<", "&lt;" );
		temp = temp.replaceAll( ">", "&gt;" );
		return temp;
	}

	// 把null格式化成""
	public static String formatNull( String str )
	{
		if ( str == null || "null".equals( str ) )
			return "";
		else
			return str;
	}

	// 判断一个字符串是否null或""
	public static boolean isBlank( String str )
	{
		if ( str == null )
			return true;
		if ( str.length() == 0 ){
			return true;
			}
		if(str.equals("")){
			return true;
		}
		return false;
	}

	// 判断一个字符串是否null或""或"null"
	public static boolean isBlank_new( String str )
	{ // add by pengjian 2008-6-11 14:34:57
		if ( str == null )
			return true;
		if ( str.length() == 0 )
			return true;
		if ( "null".equals( str ) )
			return true;
		return false;
	}

	/**
	 * 判断字符是否包含数组中元素，数组或者字符串为空返回false
	 * @param array 数组
	 * @param str  字符串
	 * @return  包含返回true,不包含返回false
	 */
	public static boolean stringIndexOfArray(String [] array,String str){
		Boolean  ignoreArrIsContain =false;

		if(array==null||array.length==0){
			return ignoreArrIsContain;
		}
		if(str==null||str.equals("")){
			return ignoreArrIsContain;
		}
		for(String ignoreStr:array){
			if(str.indexOf(ignoreStr)>-1){
				ignoreArrIsContain=true;
				break;
			}
		}
		return ignoreArrIsContain;
	}

	// 日期转字符串
	public static String formatDate( java.util.Date date, String pattern )
	{
		if ( date == null )
		{
			return "";
		}
		return (new SimpleDateFormat( pattern )).format( date );
	}

	// 日期转字符串
	/**
	 * @param date
	 * @param pattern
	 *            yyyy年MM月dd日格式
	 * @return
	 */
	public static String formatDate3( java.util.Date date )
	{
		return (new SimpleDateFormat( "yyyy年MM月dd日" )).format( date );
	}

	// 数字转字符串
	public static String formatNumeric( double numeric, String pattern )
	{
		if ( numeric == -0 )
			numeric = 0;
		DecimalFormat decFormat = new DecimalFormat( pattern );
		return decFormat.format( numeric );
	}

	// 数字转逗号分隔字符串
	public static String formatNumeric( double numeric )
	{
		return formatNumeric( numeric, "#,##0.00" );
	}

	public static String formatNumericEight( double numeric )
	{
		return formatNumeric( numeric, "#,##0.00000000" );
	}

	// 数字转逗号分隔字符串,附加小数位数(保留8位小数，那么dec参数为6，即，最少要有2位小数)
	public static String formatNumeric( double numeric, int dec )
	{
		String p = "";
		for ( int i = 0; i < dec; i++ )
			p += "#";
		return formatNumeric( numeric, "#,##0.00" + p );
	}

	// 数字转逗号分隔字符串；如果数字为零，则返回空
	public static String formatNumericEx( double numeric )
	{
		String result;
		if ( numeric != 0 )
		{
			result = StringUtil.formatNumeric( numeric );
		}
		else
		{
			result = "0.00";
		}
		return result;
	}

	/**
	 * 日期字符串格式化为java.sql.Date，日期字符串需包含年月日，例如2011-1-1或者2011.1.1
	 */
	public static java.sql.Date formatDate( String dateStr )
	{
		java.sql.Date d = null;
		if ( dateStr == null || dateStr.equals( "" ) )
			d = null;
		try
		{
			String[] tmp = null;
			if ( dateStr.indexOf( "-" ) != -1 )
			{
				tmp = dateStr.split( "-" );
			}
			else
			{
				tmp = dateStr.split( "\\u002E" );
			}
			if ( tmp.length != 3 )
			{
				System.out.println( "日期格式不符合要求" );
				return null;
			}
			else
			{
				if ( tmp[1].length() == 1 )
				{
					tmp[1] = "0" + tmp[1];
				}
				if ( tmp[2].length() == 1 )
				{
					tmp[2] = "0" + tmp[2];
				}
				d = java.sql.Date.valueOf( tmp[0] + "-" + tmp[1] + "-" + tmp[2] );
			}
		}
		catch ( Exception e )
		{
			d = null;
		}
		return d;
	}

	// 将大数字格式化为字符串，避免以科学计数法显示
	public static String formatDouble( double numeric )
	{
		return formatNumeric( numeric, "#0.00" );
	}

	public static String formatDouble4( double numeric )
	{
		return formatNumeric( numeric, "#,##0.0000" );
	}

	// 保留6位小数，不足6位用0代替
	public static String formatDouble6( double numeric )
	{
		return formatNumeric( numeric, "#0.000000" );
	}

	// 将大数字格式化为字符串，避免以科学计数法显示5位
	public static String formatDoubles( double numeric )
	{
		return formatNumeric( numeric, "#0.00000" );
	}

	public static String getDateStrYYYYMMDD( java.sql.Date date )
	{
		return DateUtil.getDateStr( date, "yyyyMMdd" );
	}

	public static String getDateStrYYYYMMDD( java.util.Date date )
	{
		return DateUtil.getDateStr( new java.sql.Date( date.getTime() ), "yyyyMMdd" );
	}
	
	public static java.sql.Date getNow()
	{
		return new java.sql.Date( new java.util.Date().getTime() );
	}

	// 将大数字格式化为字符串，避免以科学计数法显示
	public static String formatDouble( double numeric, int dec )
	{
		String p = "";
		for ( int i = 0; i < dec; i++ )
			p += "#";
		return formatNumeric( numeric, "#0.00" + p );
	}

	// 将形如234，567.00的逗号分隔字符串格式化为Double
	public static double formatNumeric( String str )
	{
		try
		{
			return (new DecimalFormat( "#,##0.00######" )).parse( str ).doubleValue();
		}
		catch ( Exception e )
		{
			return 0;
		}
	}

	// 格式化java.sql.Date为句点分隔字符串
	public static String formatDate( java.sql.Date date )
	{
		if ( date == null )
			return "";
		return (new SimpleDateFormat( "yyyy.MM.dd" )).format( date );
	}

	public static String formatDate( java.util.Date date )
	{
		if ( date == null )
			return "";
		return (new SimpleDateFormat( "yyyy.MM.dd" )).format( date );
	}

	// 指定的格式格式date add by luoyh 2009-319
	public static String fromatDate( java.sql.Date date, String pattern )
	{
		return (new SimpleDateFormat( pattern )).format( date );
	}

	// add by xy090118
	public static String formatDateYYYYMD( java.sql.Date date )
	{
		return (new SimpleDateFormat( "yyyy.M.d" )).format( date );
	}

	public static String formatDate2( java.sql.Date date )
	{
		return (new SimpleDateFormat( "yyyy-MM-dd" )).format( date );
	}

	public static String formatUDate( Date date )
	{
		return (new SimpleDateFormat( "yyyy-MM-dd" )).format( date );
	}

	public static String formatUDate( java.util.Date date )
	{
		return (new SimpleDateFormat( "yyyy-MM-dd" )).format( date );
	}

	// 格式化java.sql.Timestamp为句点分隔字符串
	public static String formatLongDate( java.sql.Timestamp date )
	{
		if ( date == null )
		{
			return "";
		}
		else
		{
			return (new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" )).format( date );
		}
	}

	public static String formatLongDate1( java.sql.Timestamp date )
	{
		return (new SimpleDateFormat( "yyyy-MM-dd" )).format( date );
	}

	public static SimpleDateFormat getFormat( String format )
	{
		String timeArea = "GMT+08:00";// 时区变量
		SimpleDateFormat formatter = new SimpleDateFormat( format );// 设置时间格式
		formatter.setTimeZone( TimeZone.getTimeZone( timeArea ) );// 设置时区为东八区
		return formatter;
	}

	public static java.sql.Timestamp formatTimestamp( String dateStr )
	{
		try
		{
			return new java.sql.Timestamp( getFormat( "yyyy-MM-dd HH:mm:ss" ).parse( dateStr.trim() ).getTime() );// (dateStr.substring(0,10)
			// + "
			// "
			// + dateStr.substring(11,19));
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	// 格式化字符 为int
	public static int formatInt( String temp, int defaultNum )
	{
		if ( temp != null && !temp.equals( "" ) )
		{
			int num = defaultNum;
			try
			{
				num = Integer.parseInt( temp );
			}
			catch ( Exception ignored )
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	// 取得当天的日期（sql格式）
	public static java.util.Date getToday()
	{
		java.util.Date date = new java.util.Date();
		return new java.sql.Date( date.getTime() );
	}

	// 取得当天的日期（sql格式）
	public static java.sql.Date getSQLToday()
	{
		java.util.Date date = new java.util.Date();
		return new java.sql.Date( date.getTime() );
	}

	// 把BigDecimal 转化成double
	public static double getDouble( BigDecimal o1 )
	{
		if ( o1 == null )
			return 0;
		else
			return o1.doubleValue();
	}

	public static Timestamp getSqlToday()
	{
		java.util.Date date = new java.util.Date();
		Timestamp date2 = new Timestamp( (date).getTime() );
		return date2;
	}

	// add by lixiuzhong 将小写金额转成大写金额
	public static String ChgMoney( double smallmoney )
	{
		String value = String.valueOf( smallmoney );
		if ( null == value || "".equals( value.trim() ) )
			return "零";
		// String strCheck,strArr,strFen,strDW,strNum,strBig,strNow;
		String strCheck, strFen, strDW, strNum, strBig, strNow;
		strCheck = value + ".";
		int dot = strCheck.indexOf( "." );
		if ( dot > 12 )
		{
			return "数据" + value + "过大，无法处理！";
		}
		try
		{
			int i = 0;
			strBig = "";
			strDW = "";
			strNum = "";
			double dFen = smallmoney * 100;
			// strFen = String.valueOf(intFen);
			strFen = formatNumeric( dFen, "###0" );
			int lenIntFen = strFen.length();
			while ( lenIntFen != 0 )
			{
				i++;
				switch ( i ) {
					case 1 :
						strDW = "分";
						break;
					case 2 :
						strDW = "角";
						break;
					case 3 :
						strDW = "元";
						break;
					case 4 :
						strDW = "拾";
						break;
					case 5 :
						strDW = "佰";
						break;
					case 6 :
						strDW = "仟";
						break;
					case 7 :
						strDW = "万";
						break;
					case 8 :
						strDW = "拾";
						break;
					case 9 :
						strDW = "佰";
						break;
					case 10 :
						strDW = "仟";
						break;
					case 11 :
						strDW = "亿";
						break;
					case 12 :
						strDW = "拾";
						break;
					case 13 :
						strDW = "佰";
						break;
					case 14 :
						strDW = "仟";
						break;
				}
				switch ( strFen.charAt( lenIntFen - 1 ) ) // 选择数字
				{
					case '1' :
						strNum = "壹";
						break;
					case '2' :
						strNum = "贰";
						break;
					case '3' :
						strNum = "叁";
						break;
					case '4' :
						strNum = "肆";
						break;
					case '5' :
						strNum = "伍";
						break;
					case '6' :
						strNum = "陆";
						break;
					case '7' :
						strNum = "柒";
						break;
					case '8' :
						strNum = "捌";
						break;
					case '9' :
						strNum = "玖";
						break;
					case '0' :
						strNum = "零";
						break;
				}
				// 处理特殊情况
				strNow = strBig;
				// 分为零时的情况
				if ( (i == 1) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "整";
				// 角为零时的情况
				else if ( (i == 2) && (strFen.charAt( lenIntFen - 1 ) == '0') )
				{ // 角分同时为零时的情况
					if ( !strBig.equals( "整" ) )
						strBig = "零" + strBig;
				}
				// 元为零的情况
				else if ( (i == 3) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "元" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) != '零')
						&& (strNow.charAt( 0 ) != '元') )
					strBig = "零" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '零') )
				{
				}
				// 拾－仟中一位为零且其前一位是元且为零的情况时跨过
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '元') )
				{
				}
				// 当万为零时必须补上万字
				else if ( (i == 7) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "万" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) != '零')
						&& (strNow.charAt( 0 ) != '万') )
					strBig = "零" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '万') )
				{
				}
				// 拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '零') )
				{
				}
				// 万位为零且存在仟位和十万以上时，在万仟间补零
				else if ( (i < 11) && (i > 8) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '万')
						&& (strNow.charAt( 2 ) == '仟') )
					strBig = strNum + strDW + "万零" + strBig.substring( 1, strBig.length() );
				// 单独处理亿位
				else if ( i == 11 )
				{
					// 亿位为零且万全为零存在仟位时，去掉万补为零
					if ( (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '万')
							&& (strNow.charAt( 2 ) == '仟') )
						strBig = "亿" + "零" + strBig.substring( 1, strBig.length() );
					// 亿位为零且万全为零不存在仟位时，去掉万
					else if ( (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '万')
							&& (strNow.charAt( 2 ) != '仟') )
						strBig = "亿" + strBig.substring( 1, strBig.length() );
					// 亿位不为零且万全为零存在仟位时，去掉万补为零
					else if ( (strNow.charAt( 0 ) == '万') && (strNow.charAt( 2 ) == '仟') )
						strBig = strNum + strDW + "零" + strBig.substring( 1, strBig.length() );
					// 亿位不为零且万全为零不存在仟位时，去掉万
					else if ( (strNow.charAt( 0 ) == '万') && (strNow.charAt( 2 ) != '仟') )
						strBig = strNum + strDW + strBig.substring( 1, strBig.length() );
					// 其他正常情况
					else
						strBig = strNum + strDW + strBig;
				}
				// 拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) != '零') && (strNow.charAt( 0 ) != '亿') )
					strBig = "零" + strBig;
				// 拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) == '亿') )
				{
				}
				// 拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) == '零') )
				{
				}
				// 亿位为零且不存在仟万位和十亿以上时去掉上次写入的零
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) != '0')
						&& (strNow.charAt( 0 ) == '零') && (strNow.charAt( 1 ) == '亿') && (strNow.charAt( 3 ) != '仟') )
					strBig = strNum + strDW + strBig.substring( 1, strBig.length() );
				// 亿位为零且存在仟万位和十亿以上时，在亿仟万间补零
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) != '0')
						&& (strNow.charAt( 0 ) == '零') && (strNow.charAt( 1 ) == '亿') && (strNow.charAt( 3 ) == '仟') )
					strBig = strNum + strDW + "亿零" + strBig.substring( 2, strBig.length() );
				else
					strBig = strNum + strDW + strBig;
				strFen = strFen.substring( 0, lenIntFen - 1 );
				lenIntFen--;
			}
			return strBig;
		}
		catch ( Exception e )
		{
			return "";
		}
	}

	// add by 李秀忠 转换编码 解决请求，响应中的乱码问题
	public static String getEncodeStr( String str )
	{
		if ( str == null )
		{
			return null;
		}
		try
		{
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes( "ISO-8859-1" );
			String temp = new String( temp_t );
			return temp;
		}
		catch ( Exception e )
		{
		}
		return null;
	}

	// add by gwb 编码简单判断 解决是否需编码转换
	public static boolean validateEncode( String str1, String str2 )
	{
		boolean bsuccess = false;
		try
		{
			if ( str1.equals( str2 ) )
			{
				bsuccess = false;
			}
			else
			{
				bsuccess = true;
			}
		}
		catch ( Exception e )
		{
		}
		return bsuccess;
	}

	// 格式化java.sql.Timestamp为句点分隔字符串
	public static String formatTimestamp( java.sql.Timestamp date )
	{
		return (new SimpleDateFormat( "yyyy-MM-dd HH:mm" )).format( date );
	}

	public static String formatSpace( String str )
	{
		if ( str == null )
			return "";
		return str.replaceAll( " ", "&nbsp;" );
	}

	// 数字转逗号分隔字符串；如果 blankZero = true 那么 0 返回空字符串
	public static String formatNumeric( double numeric, boolean blankZero )
	{
		if ( blankZero && numeric == 0 )
			return "";
		return formatNumeric( numeric, "#,##0.00" );
	}

	public static String formatNumeric( double numeric, boolean blankZero, String mask )
	{
		if ( blankZero && numeric == 0 )
			return "";
		return formatNumeric( numeric, mask );
	}

	// 删除字符串最后一个字符
	public static String deleteLastChar( String str )
	{
		if ( str == null )
			return null;
		if ( str.length() == 0 )
			return "";
		return str.substring( 0, str.length() - 1 );
	}

	/**
	 * 将字符串按分隔符分成字符串数组（去除空字符串）
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            分隔符
	 * @return 字符串数组
	 */
	public static String[] split( String str, String format )
	{
		if ( str == null || "".equals( str ) )
		{
			return new String[0];
		}
		String[] strs = str.split( format );
		Collection col = new ArrayList();
		for ( int i = 0; i < strs.length; i++ )
		{
			if ( strs[i] != null && !"".equals( strs[i] ) )
			{
				col.add( strs[i] );
			}
		}
		strs = (String[]) col.toArray( new String[0] );
		return strs;
	}

	/**
	 * 将excel传过来的金额（￥333,333）转换成double数字。
	 * 
	 * @param str
	 * @return
	 */
	public static double convertToDouble( String str )
	{
		str = str.substring( 3 );
		String[] temp = str.split( "," );
		String result = "";
		int size = temp.length;
		for ( int i = 0; i < size; i++ )
		{
			result = result + temp[i];
		}
		return Double.parseDouble( result );
	}

	public static void main( String args[] )
	{
		double t = StringUtil.convertToDouble( "\"$\"33,33,43.00" );
		System.out.print( t );
	}

	/**
	 * 用于取得SQL语句中的IN部分 Add by Wang Xinping, 2006-1-11
	 * 
	 * @param vCorp
	 *            一般是操作员所管理的单位列表
	 * @param sField
	 *            字段名 如: " and  p.corp_id "
	 * @return
	 */
	public static String getInStr( Vector vCorp, String sField )
	{
		int size = vCorp.size();
		if ( size == 0 )
			return "";
		StringBuffer sb = new StringBuffer( sField + " in(" );
		for ( int i = 0; i < size; i++ )
		{
			sb.append( (Integer) vCorp.get( i ) );
			if ( i < size - 1 )
				sb.append( "," );
		}
		sb.append( ") " );
		return sb.toString();
	}

	// add by mbz 转换编码 解决请求，响应中的乱码问题
	public static String getEncodeStr2( String str )
	{
		if ( str == null )
		{
			return null;
		}
		try
		{
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes( "UTF-8" );
			String temp = new String( temp_t );
			return temp;
		}
		catch ( Exception e )
		{
		}
		return null;
	}

	// 将date的前一天返回
	public static java.sql.Date formatDate3( java.sql.Date date )
	{
		DateUtil dateUtil = new DateUtil( date );
		dateUtil.setDay( dateUtil.getDay() - 1 );
		date = dateUtil.getSqlDate();
		return date;
	}

	// 将形如2000.01.30的点号分隔的日期字符串格式化为java.sql.Date
	public static java.sql.Date formatDate2( String dateStr )
	{
		java.util.Date date;
		try
		{
			date = new java.util.Date( dateStr );
		}
		catch ( Exception e )
		{
			date = null;
		}
		try
		{
			if ( date == null )
			{
				date = java.sql.Date.valueOf( dateStr.replace( '.', '-' ) );
			}
		}
		catch ( Exception e )
		{
			throw new java.lang.IllegalArgumentException();
		}
		return new java.sql.Date( date.getTime() );
	}

	public static String formatDateStr( java.sql.Date date )
	{
		if ( date == null )
		{
			return "";
		}
		return (new SimpleDateFormat( "yyyy-MM-dd" )).format( date );
	}

	// 数字转逗号分隔字符串
	public static String formatMoney( double numeric )
	{
		return formatNumeric( numeric, "#,##0.00" );
	}

	public static java.util.Date stringToDate( String date, String pattern )
	{
		try
		{
			return (new SimpleDateFormat( pattern )).parse( date );
		}
		catch ( ParseException e )
		{
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}

	public static boolean isNumeric( String s )
	{
		try
		{
			Double.parseDouble( s );
			return true;
		}
		catch ( Exception e )
		{
			return false;
		}
	}

	// 将形如234，567.00的逗号分隔字符串格式化为Double add by liut 返回如果是0的时候则返回null
	public static Double formatNumericEX( String str )
	{
		Double result = null;
		try
		{
			result = (new DecimalFormat( "#,##0.00######" )).parse( str ).doubleValue();
		}
		catch ( Exception e )
		{
			return null;
		}
		return result == 0 ? null : result;
	}

	public static String formatDateEx( Date date )
	{
		if ( date == null )
			return "";// add 8.11
		return (new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" )).format( date );
	}

	public static String formatDateYYYYMMDDHHMMSS( java.sql.Date date )
	{
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		return sdf.format( date );
	}

	/**
	 * 将当前周的结束时间和传入日期周的开始时间做比较--周预算时间比较
	 * 
	 * @param searchEndDate
	 * @return
	 */
	public static int compareWeekDate( java.sql.Date searchEndDate )
	{
		Calendar calendar = Calendar.getInstance( Locale.CHINA );
		calendar.setFirstDayOfWeek( Calendar.MONDAY );
		java.util.Date d = new Date( searchEndDate.getYear(), searchEndDate.getMonth(), 1 );
		calendar.setTimeInMillis( d.getTime() );
		calendar.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );// 获得某月月初的周末
		java.util.Date d1 = calendar.getTime();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis( searchEndDate.getTime() );
		calendar1.setFirstDayOfWeek( Calendar.MONDAY );
		calendar1.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );// 获得传入时间的周一
		java.util.Date d2 = calendar1.getTime();
		// 如果传入时间大于月第一周周末时间，需要进行滚存
		return DateUtil.dayDiffs( d2, d1 );
	}

	/**
	 * 功能：格式化document防止转义 作者：汪明义 日期：2014-11-26 下午7:43:39
	 * 
	 * @param document
	 * @return
	 */
	public static String formatXml( Document document )
	{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding( "UTF-8" );
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter( sw, format );
		xw.setEscapeText( false );
		try
		{
			xw.write( document );
			xw.flush();
			xw.close();
		}
		catch ( IOException e )
		{
			 e.printStackTrace();
		}
		return sw.toString();
	}

}
