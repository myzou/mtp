package com.cter.util;
 
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;


/**
 * POJO 工具生成类
 * @author  yixiu
 * 
 */
public class GenEntity {
     
//	private String package_url="com.cter.entity";//包的路径 用来输出包头
//	private String outputPath ="D:\\op1768\\workSpace\\java1234\\mars45\\X-admin-2.3\\src\\com\\cter\\entity" ;//实体类的包路径
//	private String affixName="yixiu";//备注名称
	private String package_url ;//包的路径 用来输出包头
	private String outputPath;//实体类的包路径
	private String affixName;//备注名称
	
	   //数据库连接
    private static final String URL ="jdbc:mysql://localhost:3306/mtp?characterEncoding=utf-8";//jdbc:mysql://连接数据库信息
    private static final String NAME = "root";//用户名
    private static final String PASS = "root";//密码
    private static final String DRIVER ="com.mysql.jdbc.Driver";//数据库连接驱动
    private String codingType="GBK";//字符编码类型
	
    private String [] colnameMappNames;//字段数组
    private String[] colnames; // 列名数组
    private String[] colTypes; //列名类型数组
    private String[] colDescs; //列名描述数组
    private int[] colSizes; //列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*

    /**
     * 	构造函数
     * 	@param tableNames  	表名数组				new String[]{"tableName1","tableName2"}
	 * 	@param package_url	 	包路径    				com.cter.entity
	 * 	@param outputPath		实体类的包路径	D:\\op1768\\workSpace\\java1234\\mars45\\X-admin-2.3\\src\\com\\cter\\entity
	 * 	@param affixName			署名名称		
     */
    public GenEntity(String [] tableNames,String package_url,String outputPath,String affixName){
    	this.package_url=package_url;
    	this.outputPath=outputPath;
    	this.affixName=affixName;
        //创建连接
        Connection con = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL,NAME,PASS);
            DatabaseMetaData dbmd=con.getMetaData();
            
            
            for(String tableName:tableNames){
                ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
                rs.last();
                int size = rs.getRow();
                rs.beforeFirst();
                colnameMappNames=new String[size];
                colnames = new String[size];
                colTypes = new String[size];
                colSizes = new int[size];
                colDescs = new String[size];
                int i=0;
                System.out.println("表名："+tableName+"\t\n表字段信息：");
                while(rs.next()){
                    System.out.println(rs.getString("COLUMN_NAME")+"----"+rs.getString("REMARKS")+"----"+rs.getString("TYPE_NAME"));
                    colnameMappNames[i]= rs.getString("COLUMN_NAME");
                    colnames[i] = initcapCol(rs.getString("COLUMN_NAME"));
                    colTypes[i] = rs.getString("TYPE_NAME");
                    colDescs[i] = rs.getString("REMARKS");
                    colSizes[i] = rs.getInt("COLUMN_SIZE");
                        if(colTypes[i].equalsIgnoreCase("datetime")){
                            f_util = true;
                        }
                        if(colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")){
                            f_sql = true;
                        }
                        i++;
                }
                String content = parse(colnames,colTypes,colSizes,tableName,con);
                printFile(content,tableName);  
                
            }
       
        } catch (SQLException e) {
            e.printStackTrace();
        }/* finally{
          try {
              con.close();
          } catch (SQLException e) {
              e.printStackTrace();
          }
        }*/
    }
 
    /**
     * 输出文件
     * @param content  内容
     * @param tableName   表名
     */
    public void printFile(String content,String tableName) {
        try {
        	String outPath=outputPath+"\\"+initcap(tableName) + ".java";
            System.out.println("输出路径："+outPath);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath),codingType)));
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * 功能：生成实体类主体代码
     * @param colnames  列名数组
     * @param colTypes	列类型数组
     * @param colSizes		列名大小数组
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes,String tableName,Connection con) {
        StringBuffer sb = new StringBuffer();
         
        //判断是否导入工具包
        sb.append("package "+package_url+";\r\n");
        if(f_util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\nimport javax.persistence.*;\r\n");
        sb.append("\r\n");
        //注释部分
        sb.append("   /**\r\n");
        sb.append("    * "+tableName+"\t实体类\r\n");
        sb.append("    * @author\t"+affixName+"\r\n");
        sb.append("    * @date\t"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm")+" \r\n");
        sb.append("    */ \r\n\r\n");
        sb.append("@Entity\r\n");
        sb.append("@Table(name=\""+tableName+"\")");
        
        //实体部分
        sb.append("\r\npublic class " + initcap(tableName) + "{\r\n");
        processAllAttrs(sb,tableName,con);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");
         
        //System.out.println(sb.toString());
        return sb.toString();
    }
     
    /**
     * 功能：生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb,String tableName,Connection con) {
        for (int i = 0; i < colnames.length; i++) {
            addAnnotation(sb, con, colnameMappNames[i],tableName);
         /*   if(StringUtil.isBlank( sqlType2JavaType(colTypes[i],colSizes[i]) ) ){
            	System.out.println("");
            }*/
            if( sqlType2JavaType(colTypes[i],colSizes[i]) .equals("Date")){
            	sb.append("	@Temporal(TemporalType.TIMESTAMP)  \r\n");
            }
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i],colSizes[i]) + " " + colnames[i] + ";"+
            (!StringUtil.isBlank(colDescs[i])?"//"+colDescs[i]:"")  +"\r\n\r\n");
 
        }
    }
    
/**
 * 添加注解
 * @param sb			
 * @param con		
 * @param columnName		字段名称
 */
    private void addAnnotation(StringBuffer sb,Connection con,String columnName,String tableName){
    	
    	try {
    		//判断是不是主键
			DatabaseMetaData dbmd=con.getMetaData();
			   ResultSet pk = dbmd.getPrimaryKeys(null, null, tableName);
//			   System.out.println("表"+tableName+":");
			   while( pk.next() ) {
//			   System.out.println("PKTABLE_CAT(数据库名):"+pk.getObject(1));
//			   System.out.println("PKTABLE_SCHEM(表模式):"+pk.getObject(2));
//			   System.out.println("PKTABLE_NAME(表名称):"+pk.getObject(3));
//			   System.out.println("COLUMN_NAME(主键名):"+pk.getObject(4));
//			   System.out.println("KEY_SEQ(第几个主键):"+pk.getObject(5));
//			   System.out.println("PK_NAME:"+pk.getObject(6));
			   if(pk.getObject(4).equals(columnName)){
				   sb.append( "\t@Id\r\n");
			   }
			   }
			   //判断是不是自增
			   PreparedStatement ps = con.prepareStatement("select * from "+tableName);   
			   ResultSet rs1 = ps.executeQuery();  
			   ResultSetMetaData rsme = rs1.getMetaData();   
			   int columnCount = rsme.getColumnCount();   
//			   System.out.println("ResultSet对象中的列数"+ columnCount);   
			   for (int i = 1; i <=columnCount ; i++) {   
//			       System.out.println("列名称: "+ rsme.getColumnName(i));   
//			       System.out.println("列类型(DB): " + rsme.getColumnTypeName(i));   
//			       System.out.println("长度: "+ rsme.getPrecision(i) );   
//			       System.out.println("是否自动编号: "+ rsme.isAutoIncrement(i));   
				   if(rsme.getColumnName(i).equals(columnName)){
				       sb.append(rsme.isAutoIncrement(i)?"\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n ":"");
				   }
//			       System.out.println("是否可以为空: "+ rsme.isNullable(i));   
//			       System.out.println("是否可以写入: "+ rsme.isReadOnly(i));   
			   }
		} catch (SQLException e) {
			e.printStackTrace();
		}  
    	sb.append("\t @Column(name=\""+ columnName+"\")\r\n");
    }
 
    /**
     * 功能：生成所有方法
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
         
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\r\n\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i],colSizes[i]) + " " + 
                    colnames[i] + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\r\n\tpublic " + sqlType2JavaType(colTypes[i],colSizes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
         
    }
     
    /**
     * 功能：将输入字符串的首字母及下划线后的字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str) {
         
        /*char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
            ch[0] = (char)(ch[0] - 32);
        }*/
       String[] arr= str.split("_");
       String tempStr ="";
       if(arr.length>0){
    	   for(String st:arr){
    		   char[] c = st.toCharArray();
    		   if(c[0] >= 'a' && c[0] <= 'z'){
    	            c[0] = (char)(c[0] - 32);
    	        }
    		   tempStr += new String(c);
    	   }
       }
         
        return tempStr;
    }
    
    /**
     * 功能：将输入字符串的下划线后的字母改成大写
     * @param str
     * @return
     */
    private String initcapCol(String str) {
       String[] arr= str.split("_");
       String tempStr ="";
       if(arr.length>1){
    	   int i=0;
    	   for(String st:arr){
    		   if(i>0){
    			   char[] c = st.toCharArray();
    			   if(c[0] >= 'a' && c[0] <= 'z'){
    				   c[0] = (char)(c[0] - 32);
    			   }
    			   tempStr += new String(c);
    		   }else{
    			   tempStr += st;
    		   }
    		   i++;
    	   }
       }else{
    	   tempStr = str; 
       }
         
        return tempStr;
    }
 
    /**
     * 功能：获得列的数据类型
     * @param sqlType   列类型
     * @return
     */
    private String sqlType2JavaType(String sqlType,int typeSize) {
         
        if(sqlType.equalsIgnoreCase("bit")){
            return "Boolean";
        }else if(sqlType.equalsIgnoreCase("tinyint")){
            return "Byte";
        }else if(sqlType.equalsIgnoreCase("smallint")){
            return "Short";
        }else if(sqlType.equalsIgnoreCase("int")){
//        	if(typeSize>=10){
//        		return "Long";
//        	}else{
        		return "Integer";
//        	}
        }else if(sqlType.equalsIgnoreCase("bigint")){
            return "Long";
        }else if(sqlType.equalsIgnoreCase("float")){
            return "Float";
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") 
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") 
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")){
            return "Double";
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") 
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") 
                || sqlType.equalsIgnoreCase("text")){
            return "String";
        }else if(sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")){
            return "Date";
        }else if(sqlType.equalsIgnoreCase("image")){
            return "Blod";
        }else if(sqlType.equalsIgnoreCase("bigint")){
        	return "BigInteger";
        }
         
        return null;
    }
     
    /**
     * 出口
     * TODO
     * @param args
     */
    public static void main(String[] args) {
        new GenEntity(new String []{"mtp_record_detailed"},"com.cter.entity",
        		"D:\\op1768\\tool\\idea\\MyOldObject\\mtp\\src\\com\\cter\\entity","op1768");
    }
 
}