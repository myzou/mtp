package com.cter.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import com.cter.entity.MtpRecordDetailed;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
 
public class SSHConnectUtil{
 
	volatile public   static StringBuilder OutputResult=new StringBuilder();
	volatile public   static int   connectNum= 0;

	
	public static void main(String[] args) throws IOException {

	}
 
	/**
	 * 获取错误原因
	 * @param detailed  
	 * @param period    时期 （做维护之前还是之后）
	 * @param errorInfo		错误信息
	 */
	public static void getErrorCause(MtpRecordDetailed detailed,String period,String errorInfo ){
		if( period.equals("before") ){
			detailed.setBeforeStatus("fail");
			detailed.setBeforeErrorCause(errorInfo);
		}else if( period.equals("after") ){
			detailed.setAfterStatus("fail");
			detailed.setAfterErrorCause(errorInfo);
		}	}
	
	
	/**
	 * 骨干类型
	 * @param end_full_name 注解域名前缀
	 * @param hostname  	主机地址
	 * @param port				 	端口
	 * @param userName 	用户名
	 * @param password		密码
	 * @return
	 */
	public synchronized static   String trunkMtp(MtpRecordDetailed detailed,String period ,String end_full_name,String hostname,String port,String userName,String password) {
		       String errorInfo="";
 
	            //建立连接
				if(hostname.split("\\.")==null||hostname.split("\\.").length<3){
					hostname=hostname+".noc.citictel-cpc.com";
				}
	            Connection conn= new Connection(hostname);
	            boolean isAuthenticated;
	            try {
					conn.connect();
					isAuthenticated   = conn.authenticateWithPassword(userName, password);
				    if(isAuthenticated ==false) {
						errorInfo="用户密码验证错误：\r\nuserName:"+hostname+"\r\npassword:"+password;
						getErrorCause(detailed, period, errorInfo);
						OutputResult.append(spanRedOverstriking(errorInfo));
						return OutputResult.toString();
		            }
				} catch (IOException e) {
					if(connectNum==0){
						connectNum+=1;
						return	trunkMtp(detailed, period, end_full_name, hostname, port, userName, password);
					}else{
						errorInfo="无法连接到："+hostname+"\t请检查是否能连通 or 是否是海外线路";
						getErrorCause(detailed, period, errorInfo);
						OutputResult.append(spanRedOverstriking(errorInfo));
						e.printStackTrace();
						return OutputResult.toString();
					}
				}
	            //利用用户名和密码进行授权
	    		Long start=System.currentTimeMillis();

	            //打开会话
	            String command1="show interfaces      "+port;
	           String inter_1=  executeCommand(detailed, period,conn,end_full_name, command1);
	         
	           //本地地址
			String Local;
			String command2;
			// 对端ip
			String oppositeDescription;
			try {
				//show PE
				    String Description=  inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50)
				    .substring( inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50).indexOf("to ")+3,   inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50).indexOf("(")).trim();
				    Local = inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30)
				            .substring( inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30).indexOf(" ")+1,   inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30).indexOf(",") ).trim();
				    //目标地址 包括端口
				    String Destination_30=inter_1.substring(inter_1.indexOf("Destination")+10, inter_1.indexOf("Destination")+50)
				    .substring( inter_1.substring(inter_1.indexOf("Destination")+10, inter_1.indexOf("Destination")+50).indexOf(" ")+1,   inter_1.substring(inter_1.indexOf("Destination")+10, inter_1.indexOf("Destination")+50).indexOf(",")).trim();
				    String Destination=Destination_30.substring(0,Destination_30.length()-3);
				    String port_30_31=Destination_30.substring(Destination_30.length()-2);
				    command2 = "show isis adjacency "+Description+" extensive ";
				    oppositeDescription = "";
				   if(port_30_31.equals("30")){
					    if(Integer.valueOf(Local.split("\\.")[3])-Integer.valueOf(Destination.split("\\.")[3])==2){
					    	oppositeDescription=Destination.substring(0, Destination.lastIndexOf(".")+1)+(Integer.valueOf(Destination.split("\\.")[3])+1);
					    }else if(Integer.valueOf(Local.split("\\.")[3])-Integer.valueOf(Destination.split("\\.")[3])==1) {
					    	oppositeDescription=Destination.substring(0, Destination.lastIndexOf(".")+1)+(Integer.valueOf(Destination.split("\\.")[3])+2);
					    	if(oppositeDescription.equals(Local)){
				    	    	oppositeDescription=Destination.substring(0, Destination.lastIndexOf(".")+1)+(Integer.valueOf(Destination.split("\\.")[3])+2);
					    	}
					    }
				   }else   if(port_30_31.equals("31")){
					    if(Destination_30.substring(0, Destination_30.length()-3).equals(Local)){
					    	oppositeDescription=Destination.substring(0, Destination.lastIndexOf(".")+1)+(Integer.valueOf(Destination.split("\\.")[3])+1);
					    }else{
					    	oppositeDescription=Destination;
					    }
				   }
			} catch (Exception e) {
				errorInfo=command1+" \t(执行此命令结果出错，请检查线路数据正确性。)";
				getErrorCause(detailed, period, errorInfo);
				errorInfo=errorInfo+"\r\n"+detailed.toString();
				OutputResult.append(spanRedOverstriking(errorInfo));
				e.printStackTrace();
				return OutputResult.toString();
			}
               String inter_2=  executeCommand(detailed, period,conn,end_full_name, command2 );
               if(!StringUtil.isBlank(inter_2)&&inter_2.substring(inter_2.indexOf("State:")+6,inter_2.indexOf("State:")+12).indexOf("Up")==-1 ){
   		  			errorInfo="isis ( "+end_full_name+") down\r\n";
  					getErrorCause(detailed, period, errorInfo);
  					OutputResult.append(spanRedOverstriking(errorInfo));
          			return OutputResult.toString();
               }
          
               
               
	           String command3="show ldp neighbor "+oppositeDescription+" extensive ";
               String inter_3=  executeCommand(detailed, period,conn,end_full_name,  command3);
               if(!StringUtil.isBlank(inter_3)&&inter_3.indexOf("Up for")==-1){
  		  			errorInfo="ldp ( "+oppositeDescription+") down\r\n";
 					getErrorCause(detailed, period, errorInfo);
 					OutputResult.append(spanRedOverstriking(errorInfo));
         			return OutputResult.toString();
              }
//	           String command4="ping interface "+port+" rapid source "+Local +" "+oppositeDescription+"  count 500 size 9000 do-not-fragment   ";

	           String command4="ping interface "+port+" rapid source "+Local +" "+oppositeDescription+"  count 200 size "+detailed.getSendSize()+" do-not-fragment   ";
                String inter_4=  executeCommand( detailed, period,conn,end_full_name,command4);
                
                String received="";//丢包率
        		try {
        			 received=inter_4.substring(inter_4.indexOf("received,")+9, inter_4.indexOf("%")).trim();
        		} catch (Exception e) {
        		  	errorInfo="ping 异常，没有返回ping信息\r\n";
					getErrorCause(detailed, period, errorInfo);
					OutputResult.append(spanRedOverstriking(errorInfo));
        			return OutputResult.toString();
         		}
                if(received.equals("0")){
			                	System.out.println("丢包率：（" +received+"）");
			                	OutputResult.append("丢包率：（" +received+"）\r\n");
                	if(period.equals("before")){
                				String  temporary=inter_4.substring(inter_4.indexOf("stddev =")+8).trim();
               		 			BigDecimal beforeDelay=BigDecimal.valueOf(Double.valueOf( temporary.substring(0,temporary.indexOf("/"))));
               		 			detailed.setBeforeDelay(beforeDelay.toString());
                	}else 	if(inter_4.indexOf("stddev =")>-1&&!StringUtil.isBlank( detailed.getBeforeDelay())){
		                		 String  temporary=inter_4.substring(inter_4.indexOf("stddev =")+8).trim();
		                		 BigDecimal afterDelay=BigDecimal.valueOf(Double.valueOf( temporary.substring(0,temporary.indexOf("/"))));
		                		 BigDecimal beforeDelay=BigDecimal.valueOf(Double.valueOf( detailed.getBeforeDelay()));
		                		 detailed.setAfterDelay(afterDelay.toString());
                		 if( afterDelay.subtract(beforeDelay) .floatValue()>=1){
                			 	errorInfo="维护后延迟增加1以上";
         						getErrorCause(detailed, period, errorInfo);
         						//errorInfo="做维护后延迟超过对比之前超过增加了 1,之前延迟："+beforeDelay+"\t 之后的延迟："+afterDelay;
							 errorInfo="The delay after maintenance exceeded that before comparison 1,before delay："+beforeDelay+"\t after delay："+afterDelay;
         						OutputResult.append(spanRedOverstriking(errorInfo));
                		 }
                	}  
                }
                OutputResult.append("扫描此线路总时间："+(System.currentTimeMillis()-start)/1000+"秒\r\n\r\n");
	            conn.close();
		return OutputResult.toString();
	}
	
	
	/**
	 * 根据session 和命令执行命令获得结果
	 * @param end_full_name pe的域名前缀
	 * @param conn				连接
	 * @param Command  	命令
	 * @return
	 * @throws IOException 
	 */
	private static String executeCommand( MtpRecordDetailed detailed,String period ,Connection conn,String end_full_name ,String Command) {
				Long start=System.currentTimeMillis();
				String executeResult="";
				try {
					Session sess = conn.openSession();
					sess.execCommand(Command);
					 InputStream stdout = new StreamGobbler(sess.getStdout());
					 System.out.println(end_full_name+">"+Command);
					OutputResult.append(end_full_name+">"+Command+"\n");
					executeResult = readStream(stdout);
//					 System.out.println(executeResult);
					OutputResult.append(executeResult+"\n");
					sess.close();
					stdout.close();
//					OutputResult.append("执行命令>："+Command+" ——————时间："+(System.currentTimeMillis()-start)/1000);

				} catch (IOException e) {
					String  	errorInfo="执行命令错误>error"+e.getMessage()+"\r\n";
					getErrorCause(detailed, period, errorInfo);
					OutputResult.append(spanRedOverstriking(errorInfo));
					e.printStackTrace();
					return executeResult;
				}
				return executeResult;


	}
	
	/**
	 * 字体加粗加红
	 * @param spanStr
	 * @return
	 */
	public static String spanRedOverstriking (String spanStr){
		spanStr="<span style=\"color:red;font-weight:bold;\">"+spanStr+"</span>\r\n";
		return spanStr;
	}
	
	/**
	 * 获取InputStream 中内容
	 * @param in
	 * @return
	 */
	private static String readStream(InputStream in){
		try {
			//1.创建字节流数组输出流，用来输出读取到的内容
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			//2.创建缓存大小
			byte[] buffer=new byte[1024];//1kb ,1kb 有1024字节
			int len =-1;
			//3.读取输入流中的内容
			while((len=in.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			//4 直接数组转换为字符串
			String content=baos.toString();
			//5.资源
			baos.close();
			return content;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
 
}