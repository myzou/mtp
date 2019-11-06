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

	// ��ʾ�������ݿ���û���
	private  String USERNAME ;
	// �������ݿ������
	private  String PASSWORD ;
	// �������ݿ��������Ϣ
	private  String DRIVER ;
	private  String URL ;

	// �������ݿ������
	private  Connection con = null;
	// ����sql����ִ�ж���
	private PreparedStatement pstmt = null;
	// �����ѯ���صĽ������
	private ResultSet resultSet = null;

	public TempDBUtils(String USERNAME, String PASSWORD, String DRIVER, String URL) {
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
		this.DRIVER = DRIVER;
		this.URL = URL;
		try {
			Class.forName(this.DRIVER);
//					     System.out.println("ע�������ɹ���");
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
	 * ��ɶ����ݿ�ı�����ɾ�����޸ĵĲ���
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
	 * �����ݿ��в�ѯ����
	 *
	 * @param sql		sql
	 * @param params  ? ������ֵ
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
	 * �����ݿ��в�ѯ���� �ر����� �����쳣
	 *
	 * @param sql		sql
	 * @param params  ? ������ֵ
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
	 * �������쳣 ���ҹر�������
	 * @param sql   sql
	 * @param params  ����
	 * @param classz  ���ص�ʵ������
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
	 * jdbc�ķ�װ�����÷����������װ,�Ѵ����ݿ��л�ȡ�����ݷ�װ��һ����Ķ�����
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
			T resultObject = cls.newInstance();  // ͨ��������ƴ���ʵ��
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
				field.setAccessible(true); // ��javabean�ķ���privateȨ��
				//�������û��ֵ��ʱ��BigDecimal��Long���ͻ�תΪString������Ҫ�¼Ӹ���Ӧ��ֵ
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
	 * ���ܣ��������ַ������»��ߺ����ĸ�ĳɴ�д
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
	 * ��ѯ��ҳ������
	 * @param sql  sql���
	 * @param params ����
	 * @param classz  calss����
	 * @param page  �ڼ�ҳ��ʼ
	 * @param limit  һҳ������
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
	 * ����sql��ѯ��ҳ �ر�������
	 * @param sql
	 * @param params  ռλ���Ĳ���
	 * @param classz      ʵ���������
	 * @param page		�ڼ�ҳ��ʼ
	 * @param limit		�ڼ���
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
	 * �ر�����
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
	 * map����ת��Ϊʵ����
	 * @param map mapʵ��������������
	 * @param clazz ����ʵ��������
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
		System.out.println((end-strat)/1000.00+"��");
	}

	/**
	 * �ر�����
	 *  �������Ͳ��� ��dbUtils ���ظ�����Ӱ������
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
	 * �ر�����
	 *  �������Ͳ��� ��dbUtils ���ز�ѯ���
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