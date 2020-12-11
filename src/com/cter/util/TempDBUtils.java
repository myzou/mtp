package com.cter.util;

import cn.hutool.json.JSONUtil;
import com.cter.entity.EmpowerMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class TempDBUtils {
	public static String mysql_driver="jdbc1.driver=net.sf.log4jdbc.DriverSpy";

	// 表示定义数据库的用户名
	private  String USERNAME ;
	// 定义数据库的密码
	private  String PASSWORD ;
	// 定义数据库的驱动信息
	private  String DRIVER ;
	private  String URL ;

	// 定义数据库的链接
	private  Connection con = null;
	// 定义sql语句的执行对象
	private PreparedStatement pstmt = null;
	// 定义查询返回的结果集合
	private ResultSet resultSet = null;

	public TempDBUtils(String USERNAME, String PASSWORD, String DRIVER, String URL) {
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.DRIVER = DRIVER;
		this.URL = URL;
		try {
			Class.forName(this.DRIVER);
//					     System.out.println("注册驱动成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if(con==null||con.isClosed()){
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





	/**
	 * 完成对数据库的表的添加删除和修改的操作
	 *
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql, List<Object> params) throws SQLException {

		boolean flag = false;
		int result = 0;
		pstmt = con.prepareStatement(sql);

		if (params != null && !params.isEmpty()) {
			int index = 1;
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		return result;
	}


	/**
	 * 从数据库中查询数据
	 *
	 * @param sql		sql
	 * @param params  ? 参数设值
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> executeQuery(String sql,
												  List<Object> params) throws SQLException {
		List<Map<String, Object>> list= new LinkedList<Map<String, Object>>();
//		        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 从数据库中查询数据 关闭连接 处理异常
	 *
	 * @param sql		sql
	 * @param params  ? 参数设值
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> executeQueryCE(String sql, List<Object> params)   {
		List<Map<String, Object>> list= null;
		try {
			list = new LinkedList<Map<String, Object>>();
//		        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			int index = 1;
			pstmt = con.prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index++, params.get(i));
				}
			}
			resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				list.add(map);
			}
			this.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 处理了异常 并且关闭了连接
	 * @param sql   sql
	 * @param params  参数
	 * @param classz  返回的实体类型
	 * @return
	 */
	public <T>  List<T> executeQueryByRefTExc(String sql,List<Object> params ,Class<T>  classz) {
		List<T>  list=new ArrayList<T>();
		try {
			list=  (List<T>) executeQueryByRef(sql, params, classz);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeDB();
		}
		if(list!=null && list.size()>0){
			return  	list;
		}
		return null;
	}


	/**
	 * jdbc的封装可以用反射机制来封装,把从数据库中获取的数据封装到一个类的对象里
	 *
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> executeQueryByRef(String sql, List<Object> params,  Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance();  // 通过反射机制创建实例
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field;
				try {
					field= cls.getDeclaredField(cols_name);
				} catch (Exception e) {
					field = 	cls.getDeclaredField( initcapCol(cols_name));
				}

//		                System.out.println(cols_value+"\t"+cols_name+"\t"+field.getType());
				field.setAccessible(true); // 打开javabean的访问private权限
				//如果返回没有值的时候BigDecimal和Long类型会转为String，所以要新加个对应的值
				if(StringUtil.isBlank(cols_value.toString())){
					if(field.getType().toString() .indexOf("BigDecimal")>-1){
						field.set(resultObject, new BigDecimal(0));
					}else if(field.getType().toString() .indexOf("Long")>-1 ){
						field.set(resultObject, 0L);
					}else if(field.getType().toString() .indexOf("Date")>-1 ){
						field.set(resultObject, null);
					}
				}   else{
					field.set(resultObject, cols_value);
				}
			}
			list.add(resultObject);
		}
		return list;
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
	 * 查询分页后数据
	 * @param sql  sql语句
	 * @param params 参数
	 * @param classz  calss类型
	 * @param page  第几页开始
	 * @param limit  一页多少条
	 * @return
	 * @throws Exception
	 */
	public <T> List<T>  loadPage(String sql,List<Object> params,Class<T> classz,int page,int limit) throws Exception{
		if(page!=0&&limit!=0){
			sql=sql +" limit  "+((page-1)*limit)+","+limit;
		}
		return executeQueryByRef(sql, params, classz);
	}

	/**
	 * 根据sql查询分页 关闭了连接
	 * @param sql
	 * @param params  占位符的参数
	 * @param classz      实体类额类型
	 * @param page		第几页开始
	 * @param limit		第几条
	 * @return
	 */
	public <T>  List<T> loadPageTExc(String sql,List<Object> params ,Class<T>  classz,int page,int limit) {
		List<T>  list=new ArrayList<T>();
		try {
			if(page!=0&&limit!=0){
				sql=sql +" limit  "+((page-1)*limit)+","+limit;
			}
			return executeQueryByRef(sql, params, classz);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeDB();
		}
		if(list!=null && list.size()>0){
			return  	list;
		}
		return null;
	}


	/**
	 *
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int  findCount(String sql,List<Object> params ) throws Exception{
		sql="select count(*) from ("+sql+"  ) a";
		List<Map<String, Object>> list=executeQuery(sql, params);
		return Integer.valueOf(String.valueOf(list.get(0).get("count(*)")));
	}

	/**
	 * 关闭连接
	 */
	public void closeDB() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
		    


	/**
	 * map对象转换为实体类
	 * @param map map实体类对象包含属性
	 * @param clazz 对象实体类类型
	 * @return
	 */
	public static Object Map2Object (Map<String,Object> map,Class<?> clazz){
		if(map==null){
			return null;
		}
		Object obejct=null;
		try {
			obejct=clazz.newInstance();

			Field[] fields=obejct.getClass().getDeclaredFields();
			for (Field field:fields){
				int mod=field.getModifiers();
				if(Modifier.isStatic(mod)||Modifier.isFinal(mod)){
					continue;
				}
				field.setAccessible(true);
				field.set(obejct, map.get(field.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obejct;
	}




	public static void main(String[] args) {
		Long strat=System.currentTimeMillis();
		String username="nss_script";
		String password="ZX4XdUpH";
		String url="jdbc:log4jdbc:mysql://218.97.9.147:3306/nm_shared_info?characterEncoding=utf-8";
		String driver="net.sf.log4jdbc.DriverSpy";
		TempDBUtils tempDBUtils=new TempDBUtils(username,password,driver,url);

		String sql = "select ip,full_name from devices  ";
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Map<String,Map<String, Object>> map=new LinkedHashMap<String,Map<String, Object>>();
		List<Object> params=new ArrayList<Object>();
			list= tempDBUtils.executeQueryCE(sql,params);
			for(Map<String, Object> o:list){
				map.put(o.get("full_name").toString(),o);
//				System.out.println(JSONUtil.toJsonStr(o));
			}
		Long end=System.currentTimeMillis();
		System.out.println((end-strat)/1000.00+"秒");
	}

	/**
	 * 关闭连接
	 *  根据语句和参数 和dbUtils 返回更新受影响行数
	 * @param db
	 * @param sql
	 * @param params
	 * @return
	 */
	public static  int  executeUpdate(TempDBUtils db , String sql, List<Object> params ) {
		int i=0;
		try {
			i=	db.executeUpdate(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeDB();
		}
		return i;
	}



	/**
	 * 关闭连接
	 *  根据语句和参数 和dbUtils 返回查询结果
	 * @param db
	 * @param sql
	 * @param params
	 * @return
	 */
	public static   <T>  List<T> executeQueryByRef(TempDBUtils db , String sql, List<Object> params, Class<T> Class ) {
		int i=0;
		List<T> list=new ArrayList<>();
		try {
			list=	 db.executeQueryByRef(sql, params, Class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeDB();
		}
		return list;
	}




}