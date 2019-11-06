package com.cter.util;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * String ������
 * @author op1768
 *Date 2018-11-11
 */
public class StringUtil {

	// SQL�����ĵ����Ŵ���
	public static String SQLConv( String str )
	{
		return str.replaceAll( "'", "''" );
	}

	// �Ѵ�html��ǩ���ַ�����ʽ��
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

	// �Ѵ�html��ǩ���ַ�����ʽ��
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

	// ��null��ʽ����""
	public static String formatNull( String str )
	{
		if ( str == null || "null".equals( str ) )
			return "";
		else
			return str;
	}

	// �ж�һ���ַ����Ƿ�null��""
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

	// �ж�һ���ַ����Ƿ�null��""��"null"
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
	 * �ж��ַ��Ƿ����������Ԫ�أ���������ַ���Ϊ�շ���false
	 * @param array ����
	 * @param str  �ַ���
	 * @return  ��������true,����������false
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

	// ����ת�ַ���
	public static String formatDate( java.util.Date date, String pattern )
	{
		if ( date == null )
		{
			return "";
		}
		return (new SimpleDateFormat( pattern )).format( date );
	}

	// ����ת�ַ���
	/**
	 * @param date
	 * @param pattern
	 *            yyyy��MM��dd�ո�ʽ
	 * @return
	 */
	public static String formatDate3( java.util.Date date )
	{
		return (new SimpleDateFormat( "yyyy��MM��dd��" )).format( date );
	}

	// ����ת�ַ���
	public static String formatNumeric( double numeric, String pattern )
	{
		if ( numeric == -0 )
			numeric = 0;
		DecimalFormat decFormat = new DecimalFormat( pattern );
		return decFormat.format( numeric );
	}

	// ����ת���ŷָ��ַ���
	public static String formatNumeric( double numeric )
	{
		return formatNumeric( numeric, "#,##0.00" );
	}

	public static String formatNumericEight( double numeric )
	{
		return formatNumeric( numeric, "#,##0.00000000" );
	}

	// ����ת���ŷָ��ַ���,����С��λ��(����8λС������ôdec����Ϊ6����������Ҫ��2λС��)
	public static String formatNumeric( double numeric, int dec )
	{
		String p = "";
		for ( int i = 0; i < dec; i++ )
			p += "#";
		return formatNumeric( numeric, "#,##0.00" + p );
	}

	// ����ת���ŷָ��ַ������������Ϊ�㣬�򷵻ؿ�
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
	 * �����ַ�����ʽ��Ϊjava.sql.Date�������ַ�������������գ�����2011-1-1����2011.1.1
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
				System.out.println( "���ڸ�ʽ������Ҫ��" );
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

	// �������ָ�ʽ��Ϊ�ַ����������Կ�ѧ��������ʾ
	public static String formatDouble( double numeric )
	{
		return formatNumeric( numeric, "#0.00" );
	}

	public static String formatDouble4( double numeric )
	{
		return formatNumeric( numeric, "#,##0.0000" );
	}

	// ����6λС��������6λ��0����
	public static String formatDouble6( double numeric )
	{
		return formatNumeric( numeric, "#0.000000" );
	}

	// �������ָ�ʽ��Ϊ�ַ����������Կ�ѧ��������ʾ5λ
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

	// �������ָ�ʽ��Ϊ�ַ����������Կ�ѧ��������ʾ
	public static String formatDouble( double numeric, int dec )
	{
		String p = "";
		for ( int i = 0; i < dec; i++ )
			p += "#";
		return formatNumeric( numeric, "#0.00" + p );
	}

	// ������234��567.00�Ķ��ŷָ��ַ�����ʽ��ΪDouble
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

	// ��ʽ��java.sql.DateΪ���ָ��ַ���
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

	// ָ���ĸ�ʽ��ʽdate add by luoyh 2009-319
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

	// ��ʽ��java.sql.TimestampΪ���ָ��ַ���
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
		String timeArea = "GMT+08:00";// ʱ������
		SimpleDateFormat formatter = new SimpleDateFormat( format );// ����ʱ���ʽ
		formatter.setTimeZone( TimeZone.getTimeZone( timeArea ) );// ����ʱ��Ϊ������
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

	// ��ʽ���ַ� Ϊint
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

	// ȡ�õ�������ڣ�sql��ʽ��
	public static java.util.Date getToday()
	{
		java.util.Date date = new java.util.Date();
		return new java.sql.Date( date.getTime() );
	}

	// ȡ�õ�������ڣ�sql��ʽ��
	public static java.sql.Date getSQLToday()
	{
		java.util.Date date = new java.util.Date();
		return new java.sql.Date( date.getTime() );
	}

	// ��BigDecimal ת����double
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

	// add by lixiuzhong ��Сд���ת�ɴ�д���
	public static String ChgMoney( double smallmoney )
	{
		String value = String.valueOf( smallmoney );
		if ( null == value || "".equals( value.trim() ) )
			return "��";
		// String strCheck,strArr,strFen,strDW,strNum,strBig,strNow;
		String strCheck, strFen, strDW, strNum, strBig, strNow;
		strCheck = value + ".";
		int dot = strCheck.indexOf( "." );
		if ( dot > 12 )
		{
			return "����" + value + "�����޷�����";
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
						strDW = "��";
						break;
					case 2 :
						strDW = "��";
						break;
					case 3 :
						strDW = "Ԫ";
						break;
					case 4 :
						strDW = "ʰ";
						break;
					case 5 :
						strDW = "��";
						break;
					case 6 :
						strDW = "Ǫ";
						break;
					case 7 :
						strDW = "��";
						break;
					case 8 :
						strDW = "ʰ";
						break;
					case 9 :
						strDW = "��";
						break;
					case 10 :
						strDW = "Ǫ";
						break;
					case 11 :
						strDW = "��";
						break;
					case 12 :
						strDW = "ʰ";
						break;
					case 13 :
						strDW = "��";
						break;
					case 14 :
						strDW = "Ǫ";
						break;
				}
				switch ( strFen.charAt( lenIntFen - 1 ) ) // ѡ������
				{
					case '1' :
						strNum = "Ҽ";
						break;
					case '2' :
						strNum = "��";
						break;
					case '3' :
						strNum = "��";
						break;
					case '4' :
						strNum = "��";
						break;
					case '5' :
						strNum = "��";
						break;
					case '6' :
						strNum = "½";
						break;
					case '7' :
						strNum = "��";
						break;
					case '8' :
						strNum = "��";
						break;
					case '9' :
						strNum = "��";
						break;
					case '0' :
						strNum = "��";
						break;
				}
				// �����������
				strNow = strBig;
				// ��Ϊ��ʱ�����
				if ( (i == 1) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "��";
				// ��Ϊ��ʱ�����
				else if ( (i == 2) && (strFen.charAt( lenIntFen - 1 ) == '0') )
				{ // �Ƿ�ͬʱΪ��ʱ�����
					if ( !strBig.equals( "��" ) )
						strBig = "��" + strBig;
				}
				// ԪΪ������
				else if ( (i == 3) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "Ԫ" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ���Ϊ������ʱ����
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) != '��')
						&& (strNow.charAt( 0 ) != 'Ԫ') )
					strBig = "��" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ�ҲΪ������ʱ���
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��') )
				{
				}
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ��Ϊ������ʱ���
				else if ( (i < 7) && (i > 3) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == 'Ԫ') )
				{
				}
				// ����Ϊ��ʱ���벹������
				else if ( (i == 7) && (strFen.charAt( lenIntFen - 1 ) == '0') )
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) != '��')
						&& (strNow.charAt( 0 ) != '��') )
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��') )
				{
				}
				// ʰ��Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ( (i < 11) && (i > 7) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��') )
				{
				}
				// ��λΪ���Ҵ���Ǫλ��ʮ������ʱ������Ǫ�䲹��
				else if ( (i < 11) && (i > 8) && (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��')
						&& (strNow.charAt( 2 ) == 'Ǫ') )
					strBig = strNum + strDW + "����" + strBig.substring( 1, strBig.length() );
				// ����������λ
				else if ( i == 11 )
				{
					// ��λΪ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					if ( (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��')
							&& (strNow.charAt( 2 ) == 'Ǫ') )
						strBig = "��" + "��" + strBig.substring( 1, strBig.length() );
					// ��λΪ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ( (strFen.charAt( lenIntFen - 1 ) == '0') && (strNow.charAt( 0 ) == '��')
							&& (strNow.charAt( 2 ) != 'Ǫ') )
						strBig = "��" + strBig.substring( 1, strBig.length() );
					// ��λ��Ϊ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					else if ( (strNow.charAt( 0 ) == '��') && (strNow.charAt( 2 ) == 'Ǫ') )
						strBig = strNum + strDW + "��" + strBig.substring( 1, strBig.length() );
					// ��λ��Ϊ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ( (strNow.charAt( 0 ) == '��') && (strNow.charAt( 2 ) != 'Ǫ') )
						strBig = strNum + strDW + strBig.substring( 1, strBig.length() );
					// �����������
					else
						strBig = strNum + strDW + strBig;
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) != '��') && (strNow.charAt( 0 ) != '��') )
					strBig = "��" + strBig;
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) == '��') )
				{
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) == '0')
						&& (strNow.charAt( 0 ) == '��') )
				{
				}
				// ��λΪ���Ҳ�����Ǫ��λ��ʮ������ʱȥ���ϴ�д�����
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) != '0')
						&& (strNow.charAt( 0 ) == '��') && (strNow.charAt( 1 ) == '��') && (strNow.charAt( 3 ) != 'Ǫ') )
					strBig = strNum + strDW + strBig.substring( 1, strBig.length() );
				// ��λΪ���Ҵ���Ǫ��λ��ʮ������ʱ������Ǫ��䲹��
				else if ( (i < 15) && (i > 11) && (strFen.charAt( lenIntFen - 1 ) != '0')
						&& (strNow.charAt( 0 ) == '��') && (strNow.charAt( 1 ) == '��') && (strNow.charAt( 3 ) == 'Ǫ') )
					strBig = strNum + strDW + "����" + strBig.substring( 2, strBig.length() );
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

	// add by ������ ת������ ���������Ӧ�е���������
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

	// add by gwb ������ж� ����Ƿ������ת��
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

	// ��ʽ��java.sql.TimestampΪ���ָ��ַ���
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

	// ����ת���ŷָ��ַ�������� blankZero = true ��ô 0 ���ؿ��ַ���
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

	// ɾ���ַ������һ���ַ�
	public static String deleteLastChar( String str )
	{
		if ( str == null )
			return null;
		if ( str.length() == 0 )
			return "";
		return str.substring( 0, str.length() - 1 );
	}

	/**
	 * ���ַ������ָ����ֳ��ַ������飨ȥ�����ַ�����
	 * 
	 * @param str
	 *            �ַ���
	 * @param format
	 *            �ָ���
	 * @return �ַ�������
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
	 * ��excel�������Ľ���333,333��ת����double���֡�
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
	 * ����ȡ��SQL����е�IN���� Add by Wang Xinping, 2006-1-11
	 * 
	 * @param vCorp
	 *            һ���ǲ���Ա������ĵ�λ�б�
	 * @param sField
	 *            �ֶ��� ��: " and  p.corp_id "
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

	// add by mbz ת������ ���������Ӧ�е���������
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

	// ��date��ǰһ�췵��
	public static java.sql.Date formatDate3( java.sql.Date date )
	{
		DateUtil dateUtil = new DateUtil( date );
		dateUtil.setDay( dateUtil.getDay() - 1 );
		date = dateUtil.getSqlDate();
		return date;
	}

	// ������2000.01.30�ĵ�ŷָ��������ַ�����ʽ��Ϊjava.sql.Date
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

	// ����ת���ŷָ��ַ���
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

	// ������234��567.00�Ķ��ŷָ��ַ�����ʽ��ΪDouble add by liut ���������0��ʱ���򷵻�null
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
	 * ����ǰ�ܵĽ���ʱ��ʹ��������ܵĿ�ʼʱ�����Ƚ�--��Ԥ��ʱ��Ƚ�
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
		calendar.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );// ���ĳ���³�����ĩ
		java.util.Date d1 = calendar.getTime();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis( searchEndDate.getTime() );
		calendar1.setFirstDayOfWeek( Calendar.MONDAY );
		calendar1.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );// ��ô���ʱ�����һ
		java.util.Date d2 = calendar1.getTime();
		// �������ʱ������µ�һ����ĩʱ�䣬��Ҫ���й���
		return DateUtil.dayDiffs( d2, d1 );
	}

	/**
	 * ���ܣ���ʽ��document��ֹת�� ���ߣ������� ���ڣ�2014-11-26 ����7:43:39
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
