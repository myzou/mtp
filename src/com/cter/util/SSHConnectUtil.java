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
	 * ��ȡ����ԭ��
	 * @param detailed  
	 * @param period    ʱ�� ����ά��֮ǰ����֮��
	 * @param errorInfo		������Ϣ
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
	 * �Ǹ�����
	 * @param end_full_name ע������ǰ׺
	 * @param hostname  	������ַ
	 * @param port				 	�˿�
	 * @param userName 	�û���
	 * @param password		����
	 * @return
	 */
	public synchronized static   String trunkMtp(MtpRecordDetailed detailed,String period ,String end_full_name,String hostname,String port,String userName,String password) {
		       String errorInfo="";
 
	            //��������
				if(hostname.split("\\.")==null||hostname.split("\\.").length<3){
					hostname=hostname+".noc.citictel-cpc.com";
				}
	            Connection conn= new Connection(hostname);
	            boolean isAuthenticated;
	            try {
					conn.connect();
					isAuthenticated   = conn.authenticateWithPassword(userName, password);
				    if(isAuthenticated ==false) {
						errorInfo="�û�������֤����\r\nuserName:"+hostname+"\r\npassword:"+password;
						getErrorCause(detailed, period, errorInfo);
						OutputResult.append(spanRedOverstriking(errorInfo));
						return OutputResult.toString();
		            }
				} catch (IOException e) {
					if(connectNum==0){
						connectNum+=1;
						return	trunkMtp(detailed, period, end_full_name, hostname, port, userName, password);
					}else{
						errorInfo="�޷����ӵ���"+hostname+"\t�����Ƿ�����ͨ or �Ƿ��Ǻ�����·";
						getErrorCause(detailed, period, errorInfo);
						OutputResult.append(spanRedOverstriking(errorInfo));
						e.printStackTrace();
						return OutputResult.toString();
					}
				}
	            //�����û��������������Ȩ
	    		Long start=System.currentTimeMillis();

	            //�򿪻Ự
	            String command1="show interfaces      "+port;
	           String inter_1=  executeCommand(detailed, period,conn,end_full_name, command1);
	         
	           //���ص�ַ
			String Local;
			String command2;
			// �Զ�ip
			String oppositeDescription;
			try {
				//show PE
				    String Description=  inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50)
				    .substring( inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50).indexOf("to ")+3,   inter_1.substring(inter_1.indexOf("Description")+15, inter_1.indexOf("Description")+50).indexOf("(")).trim();
				    Local = inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30)
				            .substring( inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30).indexOf(" ")+1,   inter_1.substring(inter_1.indexOf("Local")+5, inter_1.indexOf("Local")+30).indexOf(",") ).trim();
				    //Ŀ���ַ �����˿�
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
				errorInfo=command1+" \t(ִ�д�����������������·������ȷ�ԡ�)";
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
                
                String received="";//������
        		try {
        			 received=inter_4.substring(inter_4.indexOf("received,")+9, inter_4.indexOf("%")).trim();
        		} catch (Exception e) {
        		  	errorInfo="ping �쳣��û�з���ping��Ϣ\r\n";
					getErrorCause(detailed, period, errorInfo);
					OutputResult.append(spanRedOverstriking(errorInfo));
        			return OutputResult.toString();
         		}
                if(received.equals("0")){
			                	System.out.println("�����ʣ���" +received+"��");
			                	OutputResult.append("�����ʣ���" +received+"��\r\n");
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
                			 	errorInfo="ά�����ӳ�����1����";
         						getErrorCause(detailed, period, errorInfo);
         						//errorInfo="��ά�����ӳٳ����Ա�֮ǰ���������� 1,֮ǰ�ӳ٣�"+beforeDelay+"\t ֮����ӳ٣�"+afterDelay;
							 errorInfo="The delay after maintenance exceeded that before comparison 1,before delay��"+beforeDelay+"\t after delay��"+afterDelay;
         						OutputResult.append(spanRedOverstriking(errorInfo));
                		 }
                	}  
                }
                OutputResult.append("ɨ�����·��ʱ�䣺"+(System.currentTimeMillis()-start)/1000+"��\r\n\r\n");
	            conn.close();
		return OutputResult.toString();
	}
	
	
	/**
	 * ����session ������ִ�������ý��
	 * @param end_full_name pe������ǰ׺
	 * @param conn				����
	 * @param Command  	����
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
//					OutputResult.append("ִ������>��"+Command+" ������������ʱ�䣺"+(System.currentTimeMillis()-start)/1000);

				} catch (IOException e) {
					String  	errorInfo="ִ���������>error"+e.getMessage()+"\r\n";
					getErrorCause(detailed, period, errorInfo);
					OutputResult.append(spanRedOverstriking(errorInfo));
					e.printStackTrace();
					return executeResult;
				}
				return executeResult;


	}
	
	/**
	 * ����ӴּӺ�
	 * @param spanStr
	 * @return
	 */
	public static String spanRedOverstriking (String spanStr){
		spanStr="<span style=\"color:red;font-weight:bold;\">"+spanStr+"</span>\r\n";
		return spanStr;
	}
	
	/**
	 * ��ȡInputStream ������
	 * @param in
	 * @return
	 */
	private static String readStream(InputStream in){
		try {
			//1.�����ֽ�����������������������ȡ��������
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			//2.���������С
			byte[] buffer=new byte[1024];//1kb ,1kb ��1024�ֽ�
			int len =-1;
			//3.��ȡ�������е�����
			while((len=in.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			//4 ֱ������ת��Ϊ�ַ���
			String content=baos.toString();
			//5.��Դ
			baos.close();
			return content;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
 
}