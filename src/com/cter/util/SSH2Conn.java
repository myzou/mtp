package com.cter.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class SSH2Conn {

	
	public static void main(String[] args) {
		testConn1();
	}
	
	public static Connection  getConnection(String hostname ){
        Connection conn= new Connection(hostname.trim());
		try {
			conn.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return conn;
	}
	
	/**
	 * 根据连接和账号密码来验证登录信息
	 * @param conn			连接
	 * @param name			账号
	 * @param password  密码
	 * @return
	 */
	public static String  checkdUserInfo(Connection conn,String name,String password){
		String message="";
		try {
			conn.authenticateWithPassword(name, password);
			message="PASS";
		} catch (IOException e) {
			e.printStackTrace();
			message= "ERROR:用户密码验证错误：\r\nname:"+name+"\r\npassword:"+password;
		}
		return message;
	}
	
	/**
	 *  根据Connection 获取session
	 * @param conn  连接
	 * @return
	 */
	public static Session getSesssion(Connection conn){
        Session sess=null;
    	try {
			sess= conn.openSession();
			sess.startShell();      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return sess;
	}
	
	public static  void  testConn1(){
        boolean isAuthenticated;
        String hostname="CNSHYCXL1001E.noc.citictel-cpc.com";
        String name="op1768";
        String password="Abc201810151234";
        Connection conn=getConnection(hostname);
       String message= checkdUserInfo(conn, name, password);
       Session sess= getSesssion(conn);
//       String command="ping interface ge-0/1/2.301 rapid source 10.114.110.205 10.114.110.206 count  100";
//		System.out.println(getDateStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"\t"+"Command:"+command);
       
       String command="ping interface ge-0/1/2.301 rapid source 10.114.110.205 10.114.110.206 count ";
			for(int a=1;a<=5;a++){
		        String rCommand= command+(100+a);
				System.out.println("---------------------------------"+a+"----------------------------");
				System.out.println(getDateStr(new Date(),"yyyy-MM-dd HH:mm:ss")+"\t"+"Command:"+rCommand);
				System.out.println(executeCommand(sess,rCommand));
			}
			if(sess!=null){
			sess.close();
			}
			if(conn!=null){
			conn.close();
			}
			System.gc();
	}
	
	public static String executeCommand (Session sess,String command){
		BufferedWriter out =null;
		String temp=null;
        try {
         out = new BufferedWriter(new OutputStreamWriter(sess.getStdin()));
			out.write(command + "\n");
			out.flush();
			Thread.sleep(1000);
			 temp= readInputStream(sess.getStdout());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		} 
		return	temp;

	}
	
	/** 
	 * @功能 读取流 
	 * @param inStream 
	 * @return String
	 * @throws Exception 
	 */  
	public static String readInputStream(InputStream inputStream){
	      			String temp="";
		try {
				    int count = 0;  
				    while (count == 0&&inputStream.available()!=0) {  
				        count = inputStream.available();  
				    }  
				    byte[] b = new byte[count];  
				    inputStream.read(b);  
				    temp=new String(b);
		} catch (Exception e) {
					e.printStackTrace();
		}
					return temp;
	}
	
	
    public static String getDateStr(Date date, String Pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(Pattern);
        return formatter.format(date);
    }
	
}
