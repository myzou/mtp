package com.cter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeSet;

/**
 * ����svn���¼Ǹ��´�������
 * @author op1768
 */
public class CopyUpdateOfSvn {
	
	private static final   String dateStr=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	 /**
	  * �����ļ�λ���ַ���
	  * ��oldFileStr �ļ����Ƶ�newFileStr
	  * @param oldFileStr
	  * @param newFileStr
	 * @throws FileNotFoundException 
	  */
	public static void copyFile(String oldFileStr,String newFileStr) throws Exception{
		File oldFile=new File(oldFileStr);
		File newFile=new File(newFileStr);
		if(!newFile.exists()){
			new File(newFile.getParent()).mkdirs();
		}
		FileInputStream in=new FileInputStream(oldFile);
		FileOutputStream out =new FileOutputStream(newFile);
		//�Զ��建�����
		byte[] b =new byte[1];
		int n=0;
		while((n=in.read(b))!=-1){
			out.write(b, 0, n);
		}
		if(in!=null){
			in.close();
		}
		if(out!=null){
			out.close();
		}
		newFile.setLastModified(oldFile.lastModified());//�Ѹ��Ƶ����ļ����óɾ��ļ���ʱ��
		System.out.println("�����ļ��ɹ���("+oldFile.getName()+"),�� "+oldFile.getPath()+" \n���Ƶ�:"+newFile.getPath());
	}
	
	/**
	 * ��ȡtext�ļ�
	 * @param fileName �ļ�·��
	 * @return ��ȡ�������ļ��ַ���
	 */
		public static String read(String fileName){
			StringBuilder sb = new StringBuilder();
			try{
				BufferedReader in = new BufferedReader(	new FileReader(
								new File(fileName).getAbsoluteFile()));
				try{
					String s;
					while((s=in.readLine()) != null){
						sb.append(s);
						sb.append("\n");
					}
				}finally{
					in.close();
				}
			}catch(IOException e){
				throw new RuntimeException(e);
			}
			return sb.toString();
		}
		
	 
		/**
		 * д��txt �ļ�
		 * @param fileName  �ļ��� 
		 * @param text	�ļ�
		 */
		public static void write (String fileName, String text){
			
			try{
				File file=new File(fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				PrintWriter out = new PrintWriter( file.getAbsoluteFile());
				try{
	                out.write(text);
				}finally{
					if(out!=null){
						out.close();
					}
				}
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * ��ȡ�����ļ�������
		 * @param filePath   ���磺 config/db.properties
		 * @param charsetName  �ַ�����:UTF-8  GBK
		 * @return
		 */
		public static   Map<String,String>   loadProperties(String filePath,String charsetName){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				if(charsetName==null){
					charsetName="UTF-8";
				}
				InputStream ins=CopyUpdateOfSvn.class  .getClassLoader().getResourceAsStream(filePath);//��ȡ������
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//ʹ��InputStreamReader �ı��ַ�����
				pro.load(in);  
				Iterator<String> iterator=pro.stringPropertyNames().iterator();
				while (iterator.hasNext()){
					String key= iterator.next();	
					String value=(String)pro.get(key);
					map.put(key,value);
				}
			/*	Iterator<Entry<String, String>> iterator1=map.entrySet().iterator();
				while(iterator1.hasNext()){
					 Entry<String, String> entry=iterator1.next();
					 System.out.println("����"+ entry.getKey() +"\t\tֵ:"+entry.getValue());
				}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
			return map;
		}
	
	/**
	 * ��ȡ����ͬ���ַ�(TreeSet)
	 * @param svnstr
	 * @return
	 */
	public static TreeSet<String > getDistinctStrSet(String svnstr){
		TreeSet< String>treeSet=new TreeSet<String >();
		String[] svnstrs=svnstr.split("\n");
		for(String str:svnstrs){
			String strtr=str.trim();
			if(strtr.length()!=0){
				treeSet.add(strtr);
			}
		}
		return treeSet;
	}
	
	/**
	 * ��ȡ����ͬ���ַ�
	 * @param svnstr
	 * @return
	 */
	public static LinkedHashSet<String > getDistinctStrSet1(String svnstr){
		LinkedHashSet< String>treeSet=new LinkedHashSet<String >();
		String[] svnstrs=svnstr.split("\n");
		for(String str:svnstrs){
			String strtr=str.trim();
			if(strtr.length()!=0){
				treeSet.add(strtr);
			}
		}
		return treeSet;
	}
	
	
	
	/**
	 * ��������ļ��� Ŀ¼�����ļ�
	 * @param treeSet    	 ���в�ͬ���ļ�
	 * @param outFIlePth ���Ŀ¼
	 * @param inFIlePth ����Ŀ¼
	 */
	public static void copyFileByTreeSet(TreeSet< String> treeSet,String outFIlePth,String inFIlePth){
		int a =1;
		 for(String str:treeSet){
//			 System.out.println((a++)+" "+str);
 	 	 if(str.length()!=0){
				String start= str.substring(0, str.indexOf(":")-1);
				String fileStr= str.substring(  str.indexOf("/")+1,str.length());
				switch (start) {
				case "Modified":
					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"�޸ģ�"+fileStr);
//					System.out.println(str);
					break;
				case "Added":
					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"��ӣ�"+fileStr);
//					System.out.println(str);
					break;
				case "Replacing":
//					System.out.println((a++)+"�滻��"+fileStr);
//					System.out.println(str);
					break;
				case "Deleted":
//					System.out.println((a++)+"ɾ����"+fileStr);
					break;
				default:
//					System.out.println((a++)+str);
					break;
				}
			 }  
		 }
	}
	
	/**
	 * ȥ���ظ���ӡΨһ��
	 * @param treeSet    	 ���в�ͬ���ļ�
	 * @param outFIlePth ���Ŀ¼
	 * @param inFIlePth ����Ŀ¼
	 */
	public static void copyFileByTreeSet3(TreeSet< String> treeSet,String outFIlePth,String inFIlePth){
		int a =1;
		 for(String str:treeSet){
//			 System.out.println((a++)+" "+str);
 	 	 if(str.length()!=0){
				String start= str.substring(0, str.indexOf(":")-1);
				String fileStr= str.substring(  str.indexOf("/")+1,str.length());
				switch (start) {
				case "Modified":
//					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"�޸ģ�"+fileStr);
					System.out.println(str);
					break;
				case "Added":
//					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"��ӣ�"+fileStr);
					System.out.println(str);
					break;
				case "Replacing":
//					System.out.println((a++)+"�滻��"+fileStr);
					System.out.println(str);
					break;
				case "Deleted":
//					System.out.println((a++)+"ɾ����"+fileStr);
						System.out.println(str);
					break;
				default:
//					System.out.println((a++)+str);
					break;
				}
			 }  
		 }
	}
	
	public static void copyMethod(String fileStr ,String outFIlePth,String inFIlePth){
		String oldFileStr="";
		String newFileStr="";
		if(fileStr.indexOf("/WebContent")>-1 ){
			newFileStr=outFIlePth+"/"+dateStr+"/"+fileStr.replace("/WebContent", "");
			oldFileStr=inFIlePth+"/"+fileStr;
		}else if(fileStr.indexOf("/src")>-1 ){
			if(fileStr.endsWith(".java")){
				fileStr=	fileStr.replace(".java", ".class");
			}
			oldFileStr=inFIlePth+"/"+fileStr.replace("/src", "/build/classes");
			newFileStr=outFIlePth+"/"+dateStr+"/"+fileStr.replace("/src", "/WEB-INF/classes");
		}
		try {
			copyFile(oldFileStr, newFileStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 /**
	  * �Ƚ��ȳ��������
	  * @param LinkedHashSet
	  * @param aa
	  */
  	public static void copyFileByTreeSet1(LinkedHashSet< String> linHashSet,String aa){
		int a=1;
		 for(String str:linHashSet){
//			 System.out.println((a++)+" "+str);
			 if(str.length()!=0){
				String start= str.substring(0, str.indexOf(":")-1);
				String fileStr= str.substring(  str.indexOf("/")+1,str.length());
				switch (start) {
				case "Modified":
					System.out.println((a++)+"�޸ģ�"+fileStr);
					break;
				case "Added":
					System.out.println((a++)+"��ӣ�"+fileStr);
					break;
				case "Replacing":
					System.out.println((a++)+"�滻��"+fileStr);
					break;
				case "Deleted":
					System.out.println((a++)+"ɾ����"+fileStr);
					break;
				default:
					System.out.println((a++)+str);
					break;
				}
			 }
		 }
	} 
  	
  	/**
  	 * �����ļ��������
  	 * @param svnStr
  	 * @param outFIlePth
  	 * @param inFIlePth
  	 */
  	public static  void copyIn(String svnStr,String outFIlePth,String inFIlePth){
		TreeSet<String>  treeSet=getDistinctStrSet(svnStr);
		copyFileByTreeSet(treeSet,outFIlePth,inFIlePth);
  	}
  	
  	/**
  	 * ȥ�ش�ӡΨһ��svnֵ
  	 */
  	public static  void copyIn3(String svnStr,String outFIlePth,String inFIlePth){
//		LinkedHashSet<String>  treeSet=getDistinctStrSet1(svnStr);
//		copyFileByTreeSet1(treeSet, null);
		TreeSet<String>  treeSet=getDistinctStrSet(svnStr);
		copyFileByTreeSet3(treeSet,outFIlePth,inFIlePth);
  	}
  	
  	/**
  	 * �������ݶ���map
  	 * @return
  	 */
  	public static  Map<String,String>  getDataMap(){
  		CopyUpdateOfSvn ofSvn=new CopyUpdateOfSvn();
		 Map<String,String> map=ofSvn.loadProperties("config/updateConfig.properties",null);
		String configText=	ofSvn.getClass().getClassLoader().getResource(map.get("updateTxt")).getPath();
		configText=configText.substring(1, configText.length());
		try {
			configText=java.net.URLDecoder.decode(configText,"utf-8");//�����ݻ�ΪUTF-8��ʽ
		} catch (UnsupportedEncodingException e) {
			System.out.println("��ȡ�������");
			e.printStackTrace();
		}
		//ת�����ı��룬��ֹ����
		String svnStr=read(configText);//��ȡ��svn��ȡ���ļ�

		map.put("svnStr", svnStr);
		return map;
  	}
  	
	public static void main(String[] args)throws Exception {
		/*copyFile("D:\\op1768\\workSpace\\java1234\\mars45\\X-admin-2.3\\build\\classes\\com\\cter\\action\\ZQMailAction.class",
				"D:\\op1768\\ZQMailAction.class");*/
/*		String readStr=read("D:\\op1768\\׼��д�����µ�.txt");
		String writeFilePath="D:\\op1768\\׼��д�����µ�1.txt";
		write( writeFilePath,readStr);*/
		
  		Map<String,String> map=getDataMap();
  		String svnStr=map.get("svnStr");
		String outFIlePth=map.get("outFIlePth");
		String inFIlePth=map.get("inFIlePth");
		String action=map.get("action");
		if(action.equals("svn")){
			copyIn3(svnStr, outFIlePth, inFIlePth);//ȥ�ش�ӡΨһ��svnֵ
		}else if(action.equals("update")){
			copyIn(svnStr, outFIlePth, inFIlePth);//��������
		}
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
