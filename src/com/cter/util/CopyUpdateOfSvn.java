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
 * 根据svn更新记更新代码资料
 * @author op1768
 */
public class CopyUpdateOfSvn {
	
	private static final   String dateStr=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	 /**
	  * 根据文件位置字符串
	  * 把oldFileStr 文件复制到newFileStr
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
		//自定义缓冲对象
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
		newFile.setLastModified(oldFile.lastModified());//把复制的新文件设置成旧文件的时间
		System.out.println("复制文件成功：("+oldFile.getName()+"),从 "+oldFile.getPath()+" \n复制到:"+newFile.getPath());
	}
	
	/**
	 * 读取text文件
	 * @param fileName 文件路径
	 * @return 读取出来的文件字符串
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
		 * 写入txt 文件
		 * @param fileName  文件名 
		 * @param text	文件
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
		 * 获取配置文件的内容
		 * @param filePath   例如： config/db.properties
		 * @param charsetName  字符编码:UTF-8  GBK
		 * @return
		 */
		public static   Map<String,String>   loadProperties(String filePath,String charsetName){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				if(charsetName==null){
					charsetName="UTF-8";
				}
				InputStream ins=CopyUpdateOfSvn.class  .getClassLoader().getResourceAsStream(filePath);//获取输入流
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//使用InputStreamReader 改变字符编码
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
					 System.out.println("键："+ entry.getKey() +"\t\t值:"+entry.getValue());
				}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
			return map;
		}
	
	/**
	 * 获取不相同的字符(TreeSet)
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
	 * 获取不相同的字符
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
	 * 根据输出文件和 目录复制文件
	 * @param treeSet    	 所有不同的文件
	 * @param outFIlePth 输出目录
	 * @param inFIlePth 输入目录
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
//					System.out.println((a++)+"修改："+fileStr);
//					System.out.println(str);
					break;
				case "Added":
					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"添加："+fileStr);
//					System.out.println(str);
					break;
				case "Replacing":
//					System.out.println((a++)+"替换："+fileStr);
//					System.out.println(str);
					break;
				case "Deleted":
//					System.out.println((a++)+"删除："+fileStr);
					break;
				default:
//					System.out.println((a++)+str);
					break;
				}
			 }  
		 }
	}
	
	/**
	 * 去除重复打印唯一的
	 * @param treeSet    	 所有不同的文件
	 * @param outFIlePth 输出目录
	 * @param inFIlePth 输入目录
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
//					System.out.println((a++)+"修改："+fileStr);
					System.out.println(str);
					break;
				case "Added":
//					copyMethod(fileStr, outFIlePth, inFIlePth);
//					System.out.println((a++)+"添加："+fileStr);
					System.out.println(str);
					break;
				case "Replacing":
//					System.out.println((a++)+"替换："+fileStr);
					System.out.println(str);
					break;
				case "Deleted":
//					System.out.println((a++)+"删除："+fileStr);
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
	  * 先进先出排序遍历
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
					System.out.println((a++)+"修改："+fileStr);
					break;
				case "Added":
					System.out.println((a++)+"添加："+fileStr);
					break;
				case "Replacing":
					System.out.println((a++)+"替换："+fileStr);
					break;
				case "Deleted":
					System.out.println((a++)+"删除："+fileStr);
					break;
				default:
					System.out.println((a++)+str);
					break;
				}
			 }
		 }
	} 
  	
  	/**
  	 * 复制文件方法入口
  	 * @param svnStr
  	 * @param outFIlePth
  	 * @param inFIlePth
  	 */
  	public static  void copyIn(String svnStr,String outFIlePth,String inFIlePth){
		TreeSet<String>  treeSet=getDistinctStrSet(svnStr);
		copyFileByTreeSet(treeSet,outFIlePth,inFIlePth);
  	}
  	
  	/**
  	 * 去重打印唯一的svn值
  	 */
  	public static  void copyIn3(String svnStr,String outFIlePth,String inFIlePth){
//		LinkedHashSet<String>  treeSet=getDistinctStrSet1(svnStr);
//		copyFileByTreeSet1(treeSet, null);
		TreeSet<String>  treeSet=getDistinctStrSet(svnStr);
		copyFileByTreeSet3(treeSet,outFIlePth,inFIlePth);
  	}
  	
  	/**
  	 * 加载数据丢进map
  	 * @return
  	 */
  	public static  Map<String,String>  getDataMap(){
  		CopyUpdateOfSvn ofSvn=new CopyUpdateOfSvn();
		 Map<String,String> map=ofSvn.loadProperties("config/updateConfig.properties",null);
		String configText=	ofSvn.getClass().getClassLoader().getResource(map.get("updateTxt")).getPath();
		configText=configText.substring(1, configText.length());
		try {
			configText=java.net.URLDecoder.decode(configText,"utf-8");//把内容换为UTF-8格式
		} catch (UnsupportedEncodingException e) {
			System.out.println("读取编码错误");
			e.printStackTrace();
		}
		//转换中文编码，防止乱码
		String svnStr=read(configText);//读取从svn拉取的文件

		map.put("svnStr", svnStr);
		return map;
  	}
  	
	public static void main(String[] args)throws Exception {
		/*copyFile("D:\\op1768\\workSpace\\java1234\\mars45\\X-admin-2.3\\build\\classes\\com\\cter\\action\\ZQMailAction.class",
				"D:\\op1768\\ZQMailAction.class");*/
/*		String readStr=read("D:\\op1768\\准备写来更新的.txt");
		String writeFilePath="D:\\op1768\\准备写来更新的1.txt";
		write( writeFilePath,readStr);*/
		
  		Map<String,String> map=getDataMap();
  		String svnStr=map.get("svnStr");
		String outFIlePth=map.get("outFIlePth");
		String inFIlePth=map.get("inFIlePth");
		String action=map.get("action");
		if(action.equals("svn")){
			copyIn3(svnStr, outFIlePth, inFIlePth);//去重打印唯一的svn值
		}else if(action.equals("update")){
			copyIn(svnStr, outFIlePth, inFIlePth);//正常方法
		}
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
