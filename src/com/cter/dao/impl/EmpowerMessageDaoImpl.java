package com.cter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cter.entity.AuthorizationEmail;
import com.cter.entity.CarryFacility;
import com.cter.entity.CaseEmail;
import com.cter.entity.EmpowerMessage;
import com.cter.entity.IntoPersonnel;
import com.cter.entity.SendEmail;
import com.cter.entity.ZqData;
import com.cter.util.BaseLog;
import com.cter.util.DBUtils;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Repository("EmpowerMessageDaoImpl")
@SuppressWarnings("all")
public class EmpowerMessageDaoImpl extends BaseDaOImpl<EmpowerMessage> {
	
	@Autowired
	private CaseEmailDaoImpl caseEmailDaoImpl;
	private BaseLog log=new BaseLog("EmailLog");
/*	
	*//**
	 * 获取所有popname的集合
	 * @return
	 */
	public Map<String ,String >  getPopCodes(){
		Map<String ,String >  map =new HashMap<String ,String >();
		String hql="     from EmpowerMessage";
		List<EmpowerMessage>messages=	this.find(hql);		
		for(EmpowerMessage emp:messages){
			String popName=emp.getPop_name();
			if(null!=popName&&!"".equals(popName)){
				map.put(popName.trim(),popName.trim());
			}
		}
		return map;
	}
	public 	List<String> loadCityNames(){
		String sql ="select distinct city_name   from empower_message  where city_name<> ''  order by stem_from asc";
		List<String> list=(List<String>)this.queryBySqlStrList(sql);
		 if(null!=list){
			 return list;
		 }
		 return null;
	}
	
	public int queryMaxAuthId() {
		 DBUtils db =  DBUtils.getDBUtils();
		String sql="select max(auth_id) as max from authorization_email";
		int i=0;
		  try {
			  Object o= db.executeQuery(sql, null).get(0).get("max");
			  i=  Integer.valueOf(( o!=null&&!o.equals("") )?o.toString():"0");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
	        db.closeDB();	
		}
		return i ;
	}
	
	
	/**
	 * 根据城市名称加载城市popName  List 列表
	 * @param cityName
	 * @return
	 */
	public  	List<String>   loadPopNames(String cityName){
			List<String>  retList=new ArrayList<String>();
		    DBUtils db =  DBUtils.getDBUtils();
	        String sql = "select pop_name from empower_message where 1=1 ";
	        List<Object> params=new ArrayList<Object>();
	        if(null!=cityName){
	        	sql+=" and city_name =  ?";
	        	params.add(cityName);
	        }
	        try {
	        	List<Map<String, Object>>   list= (List<Map<String, Object>>)db.executeQuery(sql, params);
	        	for(Map<String, Object> o :list) {
	        		String pop_name=	o.get("pop_name").toString();
	        		retList.add(pop_name);
				}
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            db.closeDB();
	        }
        return retList;
	}
	
	
 /**
	 * 保存发送的case单
	 * @param map
	 */
	public void sendEmail(CaseEmail caseEmail) {
		caseEmailDaoImpl.save(caseEmail);
	}
	
	
	/**
	 * 更新系统参数表的信息passwordKeys
	 * @param passwordKeys 
	 */
	public void updateKey(String passwordKeys) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update zq_data set param_value1 = ? where sys_code= 'passwordkeys'";
		List<Object>  list=new ArrayList<Object>();
		list.add(passwordKeys);
		 try {
			int i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
	}
	
	/**
	 * 更新SendEmail
	 */
	public int updateSendEmail(SendEmail email) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update send_email set email_code = ?,email_password = ? ,email_host = ? ,protocol = ?      where email_uuid= ? ";
		List<Object>  list=new ArrayList<Object>();
		list.add(email.getEmail_code());
		list.add(email.getEmail_password());
		list.add(email.getEmail_host());
		list.add(email.getProtocol());
		list.add(email.getEmail_uuid());
		int i=0;
		 try {
			  i=db.executeUpdate(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		 return i;
	}
	
	/**
	 * 根据PopName加载EmpowerMessage 实体类list
	 * @return
	 */
	public List<EmpowerMessage> loadEmpowerMessageByDao(String pop_name) {
		 Object[] param=new Object[1];
		String hql="from EmpowerMessage where  1=1   ";
	      if(!StringUtil.isBlank(pop_name)){
	    	  hql+=" and pop_name =  ?";
	        	param[0]=pop_name;
	        }
	      hql+="  order by stem_from asc";
	      List<Object>list= this.findO(hql, param);
	      List<EmpowerMessage> cList=this.objectListToPojoList(list, EmpowerMessage.class);
			return cList;
	}
	
	public int updateEmailByPopName(Map<String, String> map) {
		String pop_name=map.get("pop_name");
		String city_name=map.get("city_name");
		String update_pop_name=map.get("update_pop_name");
		String update_city_name=map.get("update_city_name");
		String addressee_email=map.get("addressee_email");
		String cc_addressee_email=map.get("cc_addressee_email");
		String supplier_name=map.get("supplier_name");
		addressee_email=addressee_email.replaceAll(";", "###");
		cc_addressee_email=cc_addressee_email.replaceAll(";", "###");
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update empower_message set  city_name= ?  ,pop_name=  ?,addressee_email = ?  ,cc_addressee_email= ?,supplier_name= ?  "
				+ " where city_name= ?  and pop_name=  ? " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(update_city_name);
		list.add(update_pop_name);
		list.add(addressee_email);
		list.add(cc_addressee_email);
		list.add(supplier_name);
		list.add(city_name);
		list.add(pop_name);
		 try {
			  i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}
	
/**
 * 根据机房名称 和城市名称删除对应的机房
 * @param pop_name
 * @param city_name
 * @return
 */
	public int deleteEmailByPopName(String pop_name,String city_name) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="delete  from empower_message where  city_name=?  and  pop_name=? " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(city_name);
		list.add(pop_name);
		 try {
			  i=db.executeUpdate(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}

	
	/**
	 * 从zq_data 表中获取认证的口令
	 * @return
	 */
	public ZqData loadPasswordKeys() {
		String hql="from ZqData where  sys_code=?   ";
		List<Object>list= this.findO(hql, new String[]{"passwordKeys"});
			if(null!=list&&list.size()>0){
				return (ZqData)list.get(0);
			}
			return null;
	}
	
	/**
	 * 根据popName名称获取对应的EmpowerMessage
	 * @param pop_name 
	 * @return
	 */
	public EmpowerMessage getEmpowerMessageBypopName(String pop_name ) {
		String hql="from EmpowerMessage where  pop_name=?   ";
		List<EmpowerMessage>list= this.find(hql, new String[]{pop_name});
			if(null!=list&&list.size()>0){
				return  list.get(0);
			}
			return null;
	}
	
	public List<EmpowerMessage>  loadAll() {
		 List<EmpowerMessage> list= this.findAll();
			if(null!=list&&list.size()>0){
				return  list ;
			}
			return null;
	}
	
	public List<CaseEmail>  loadAllCaseEmail( ) {
		 List<Object> list= this.findAll(CaseEmail.class);
		 List<CaseEmail>  cList=new ArrayList<CaseEmail>();
			if(null!=list&&list.size()>0){
				for(Object o:list){
					CaseEmail email=	(CaseEmail)o;
					cList.add(email);
				}
					return cList;
			}
			return null;
	}
	
	public LayuiPage<CaseEmail>  findCaseEmailPage( Map<String, String>   map ,LayuiPage<CaseEmail>  layui ){
		String 	format="yyyy-MM-dd HH:mm:ss";
		 List<CaseEmail>  caseList=new ArrayList<CaseEmail>();
		 String hql="from CaseEmail where 1=1     ";
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String popNames=  map.get("popNames");
		 String caseId=  map.get("caseId");
		 String start_time=  map.get("start_time");
		 String end_time=  map.get("end_time");
		 String case_status=  map.get("case_status");
		 List  param =new ArrayList<Object>();
		 if(null!=popNames&&!popNames.equals("")){
			 param.add(popNames);
			 hql+=" and pop_name =?  ";
		 }
		 if(null!=caseId&&!caseId.equals("")){
			 param.add(caseId);
			 hql+=" and case_id =?  ";
		 }
		 if(!StringUtil.isBlank(case_status)){
			 if(case_status.equals("DD")){
				 hql+=" and case_status in ('R','T')  ";
			 }else if(case_status.equals("ALL")){  
			 }else  {  
				 param.add(case_status);
				 hql+=" and case_status =?  ";
			 }
		 }else{
			 hql+=" and case_status  in ('R','T','S') ";
		 }
		 if(null!=start_time&&!start_time.equals("")){
			 param.add(start_time);
			 hql+=" and create_time >=  ?  ";
		 }
		 if(null!=end_time&&!end_time.equals("")){
			 param.add(end_time);
			 hql+=" and create_time <=  ?  ";
		 }
		int count=this.countHql(hql ,param);
		
		 layui.setCountSize(count);
		 hql+=" ORDER BY create_time desc ";
		 List<Object> list= this.findO(hql, param, page, limit);
		 if(null!=list&&list.size()>0){
				for(Object o:list){
					CaseEmail entity=	(CaseEmail)o;
					caseList.add(entity);
				}
			}
		 layui.setDatas(caseList);
		return layui;
	}
	

	
	
	public List<SendEmail> loadSendEmail() {
		 List<Object> list= this.findAll(SendEmail.class);
			List<SendEmail> getList=	this.objectListToPojoList(list, SendEmail.class);
			return getList;
	}
	
	/**
	 * 根据email_uuid 获取send_Email
	 * @param email_uuid  send_Email表主键
	 * @return
	 */
	public SendEmail  getSendEmailByKey(String email_uuid) {
		SendEmail email=new SendEmail();
		String hql="from SendEmail where email_uuid=? ";
		String[] strs=new String[]{email_uuid};
		List<Object> list=	this.findO(hql, strs);
		 	if(null!=list&&list.size()>0){
		 		return email=(SendEmail)list.get(0); 
		 	}
		return null;
	}
	
	/**
	 * 根据case_uuid 删除CaseEmail
	 * @param case_uuid
	 * @return
	 */
	public int delCaseEmail(String case_uuid) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update case_email set case_status ='d' where case_uuid= ? " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(case_uuid);
		 try {
			  i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}
	
	public int delSendEmail(String email_uuid) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="delete from  send_email     where email_uuid= ? " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(email_uuid);
		 try {
			  i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}
	
	
	
	/**
	 * 根据case_uuid 删除CaseEmail
	 * @param case_uuid
	 * @return
	 */
	public int caseEmailUpdate(String case_uuid,String case_status) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="UPDATE case_email SET case_status =  ?  WHERE case_uuid=  ? " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(case_status);
		list.add(case_uuid);
		 try {
			  i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}
	
	/**
	 * 根据au_status 删除AuthorizationEmail
	 * @param case_uuid
	 * @return
	 */
	public int authorizationUpdate(String auth_id,String status) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="UPDATE authorization_email SET au_status =  ?   WHERE auth_id= ?  " ;
		int i=0;
		List<Object>  list=new ArrayList<Object>();
		list.add(status);
		list.add(Integer.valueOf(auth_id));
		 try {
			  i=db.executeUpdate(sql, list);
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
		return  i;
	}
	
	public  List<IntoPersonnel>  loadIntoPersonnels(String auth_id){
		List<IntoPersonnel>  getList=new ArrayList<IntoPersonnel>();
		String hql="from IntoPersonnel where auth_id=? ";
		Integer [] ints =new Integer[]{Integer.valueOf(auth_id)};
		List<Object> list=	this.findO(hql, ints);
		getList=this.objectListToPojoList(list, IntoPersonnel.class);
		return getList;
	}
	
	public  List<CarryFacility>  loadCarryFacilitys(String auth_id){
		List<CarryFacility>  getList=new ArrayList<CarryFacility>();
		String hql="from CarryFacility where auth_id=? ";
		Integer [] ints =new Integer[]{Integer.valueOf(auth_id)};
		List<Object> oList=	this.findO(hql, ints);
		getList=this.objectListToPojoList(oList, CarryFacility.class);
		return getList;
	}
	
	public AuthorizationEmail getAuthorizationEmail(String auth_id) {
		String hql="from AuthorizationEmail where auth_id=? ";
		Integer [] ints =new Integer[]{Integer.valueOf(auth_id)};
		List<Object> list=	this.findO(hql, ints);
		 	if(null!=list&&list.size()>0){
		 		AuthorizationEmail authorizationEmail= (AuthorizationEmail)list.get(0);
		 		List<CarryFacility>  cfList=this.loadCarryFacilitys(auth_id);
		 		List<IntoPersonnel>  ipList=this.loadIntoPersonnels(auth_id);
		 		EmpowerMessage message=getEmpowerMessageBypopName(authorizationEmail.getPop_name());
		 		if(message!=null){
			 		authorizationEmail.setAddressee_email(message.getAddressee_email());
			 		authorizationEmail.setCc_addressee_email(message.getCc_addressee_email());
		 		}
		 		if(cfList!=null&&cfList.size()>0){
		 			authorizationEmail.setCarryFacilityList(cfList);
		 		}
		 		if(ipList!=null&&ipList.size()>0){
		 			authorizationEmail.setIntoPersonnelList(ipList);
		 		}
		 		return authorizationEmail; 
		 	}
		return null;
	}
	
	/**
	 * 根据auth_id 删除AuthorizationEmail
	 * 入场人员信息和设备信息
	 * @param passwordKeys 
	 */
	public void delIPCFByAuth_id(String auth_id) {
		DBUtils  db=DBUtils.getDBUtils();
		String cfSql="delete from carry_facility where auth_id= ?  ";
		String ipSql="delete from carry_facility where auth_id= ?  ";
		List<Object>  list=new ArrayList<Object>();
		list.add(Integer.valueOf(auth_id));
		 try {
			int cf=db.executeUpdate(cfSql, list);
			int ip=db.executeUpdate(ipSql, list);
			log.info("更新了auth_id:("+auth_id+")信息,ip:"+ip+",cf:"+cf);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.closeDB();
		}
	}
	
	public LayuiPage<AuthorizationEmail>  findAuthorizationEmailPage( Map<String, String>   map ,LayuiPage<AuthorizationEmail>  layui ){
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String hql="from AuthorizationEmail where 1=1  ";
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String popNames=  map.get("popNames");
		 String caseId=  map.get("caseId");
		 String start_time=  map.get("start_time");
		 String end_time=  map.get("end_time");
		 String au_status=map.get("au_status");
		 String name=map.get("userName");

		 List  param =new ArrayList<Object>();
		 if(null!=popNames&&!popNames.equals("")){
			 param.add(popNames);
			 hql+=" and pop_name =?  ";
		 }
		 if(null!=caseId&&!caseId.equals("")){
			 param.add(caseId);
			 hql+=" and case_id =?  ";;
		 }
		 if(null!=name&&!name.equals("")){
			 param.add(name);
			 hql+=" and name =?  ";
		 }
		 
		 if(null!=start_time&&!start_time.equals("")){
			 hql+=" and create_time >='"+start_time+"'";
		 }
		 if(!StringUtil.isBlank(au_status)){
			  if(au_status.equals("ALL")){  
			 }else  {  
				 param.add(au_status);
				 hql+=" and au_status =?  ";
			 }
		 } 
		 if(null!=end_time&&!end_time.equals("")){
			 hql+=" and create_time <= '"+end_time+"'";
		 }
		int count=this.countHql(hql ,param);
		
		 layui.setCountSize(count);
		 hql+=" order by create_time desc ";
		 List<Object> list= this.findO(hql, param, page, limit);
		 List<AuthorizationEmail>  auList=this.objectListToPojoList(list, AuthorizationEmail.class);
		 if(auList!=null ){
			 layui.setDatas(auList);
		 }
		return layui;
	}
	
	public CaseEmail getCaseEmail(String case_uuid) {
		CaseEmail email=new CaseEmail();
		String hql="from CaseEmail where case_uuid=? ";
		String[] strs=new String[]{case_uuid};
		List<Object> list=	this.findO(hql, strs);
		 	if(null!=list&&list.size()>0){
		 		return  (CaseEmail)list.get(0); 
		 	}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
