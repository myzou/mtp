package com.cter.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Repository;

import com.cter.entity.MtpRecordDetailed;
import com.cter.entity.ZqData;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Repository("MtpRecordDetailedDaoImpl")
@SuppressWarnings("all")
public class MtpRecordDetailedDaoImpl extends BaseDaOImpl<MtpRecordDetailed> {
	
	
	
	public   LayuiPage<MtpRecordDetailed>  findMtpRecordDetailedPage(Map<String, String> map, LayuiPage<MtpRecordDetailed> layui) {
		  DBUtils db =  DBUtils.getDBUtils();
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String sql="select * from mtp_record_detailed " + 
		 		"   where 1=1 ";
	      List<Object> params=new ArrayList<Object>();	
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String case_id=  map.get("case_id");

		 if(!StringUtil.isBlank(case_id)){
			 params.add(case_id);
			 sql+=" and case_id  =    ?   ";
		 }
		 String case_status=  map.get("case_status");
		 if(!StringUtil.isBlank(case_status)){
			if( case_status.equals("now")||case_status.equals("history")){
				 params.add(case_status);
				 sql+=" and case_status  = ?    ";
			}
		 }
		 String pass_fail_status=  map.get("pass_fail_status");
		 if(!StringUtil.isBlank(pass_fail_status)){
			if( pass_fail_status.equals("pass")||pass_fail_status.equals("fail")){
				 params.add(pass_fail_status);
				 params.add(pass_fail_status);
				 sql+=" and (before_status  = ? or after_status  = ?)    ";
			}
		 }

		 String show_type=  map.get("show_type");
		 if(!StringUtil.isBlank(show_type)){
			 if(show_type.indexOf("trunk_")>-1){
				 params.add(show_type);
				 sql+=" and show_type  like     concat('',?,'%')    ";
			 }
			 if(show_type.indexOf("remedy_tcp")>-1){
				 params.add(show_type);
				 sql+=" and show_type   = ?    ";
			 }
		 }
		 String before_status=  map.get("before_status");
		 if(!StringUtil.isBlank(before_status)){
			 params.add(before_status);
			 sql+=" and before_status  =    ?   ";
		 }
		 String after_status=  map.get("after_status");
		 if(!StringUtil.isBlank(after_status)){
			 params.add(after_status);
			 sql+=" and after_status  =?   ";
		 }
		 String create_time_before=  map.get("create_time_before");
		 if(!StringUtil.isBlank(create_time_before)){
			 params.add(create_time_before);
			 sql+=" and create_time_before  <= ?   ";
		 }
		 String create_time_after=  map.get("create_time_after");
		 if(!StringUtil.isBlank(create_time_after)){
			 params.add(create_time_after);
			 sql+=" and create_time_after  >= ?   ";
		 }
		 
		int count=0;
		 List<MtpRecordDetailed> List=null;
		try {
			count=db.findCount(sql, params);
			sql=sql+"  order by create_time desc,case_id desc";
			List = db.loadPage(sql, params, MtpRecordDetailed.class, page, limit);
			 layui.setCountSize(count);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
      db.closeDB();
  }
		 if(List!=null ){
			 layui.setDatas(List);
		 }
		return layui;
		
	}
	
	
	
	public MtpRecordDetailed getMtpRecordDetailedDB(String mtp_record_case_id,String showType) {
		DBUtils db=DBUtils.getDBUtils();
		String sql ="select* from mtp_record_detailed  where case_id=?"; 
		List<Object> params=new ArrayList<Object>();
		params.add(mtp_record_case_id);
		 if(!StringUtil.isBlank(showType)){
			 if(showType.indexOf("trunk_")>-1){
				 params.add(showType);
				 sql+=" and show_type  like     concat('',?,'%')    ";
			 }
			 if(showType.indexOf("remedy_tcp")>-1){
				 params.add(showType);
				 sql+=" and show_type   = ?    ";
			 }
		 }
		
		List<MtpRecordDetailed> list=db.executeQueryByRef(db, sql, params, MtpRecordDetailed.class);
		return CommonUtil.objectListGetOne(list);
	}

	/**
	 * 根据sys_code 获取zq_data 数据
	 * @param sys_code
	 * @return
	 */
	public ZqData getZqData(String sys_code) {
		DBUtils db=DBUtils.getDBUtils();
		String sql ="select * from zq_data where sys_code= ?";
		List<Object> params=new ArrayList<Object>();
		params.add(sys_code);
		List<ZqData> list=db.executeQueryByRef(db, sql, params, ZqData.class);
		return CommonUtil.objectListGetOne(list);
	}
	
	
	/**
	 * 根据caseId 和internalSiteId对应的 MtpRecordDetailed
	 * @param caseId
	 * @param internalSiteId
	 * @return
	 */
	public MtpRecordDetailed   getMtpRecordDetailedByCaseIdIntId(String caseId,String internalSiteId,String vrfSiteId) {
		String hql="";
		List<Object> param=new ArrayList<Object>();
		param.add(caseId);
		param.add(internalSiteId);
		if(StrUtil.isBlank(vrfSiteId)){
			hql ="from MtpRecordDetailed where caseId = ? and internalSiteId =?";
		}else{
			hql ="from MtpRecordDetailed where caseId = ? and internalSiteId =? and beforeVrfSiteId=?";
			param.add(vrfSiteId);
		}
		 List<MtpRecordDetailed> list= this.findOByClass(hql, param, MtpRecordDetailed.class);
		return CommonUtil.objectListGetOne(list);
	}
	
	public List<MtpRecordDetailed>  queryMtpRecordDetailed(String caseId,String showType) {
		String hql ="from MtpRecordDetailed where caseId = ?";
		List<Object> param=new ArrayList<Object>();
		param.add(caseId);
		 if(!StringUtil.isBlank(showType)){
			 if(showType.indexOf("trunk_")>-1){
				 param.add(showType);
				 hql+=" and show_type  like     concat('',?,'%')    ";
			 }
			 if(showType.indexOf("remedy_tcp")>-1){
				 param.add(showType);
				 hql+=" and show_type   = ?    ";
			 }
		 }
		 List<MtpRecordDetailed> list= this.findOByClass(hql, param, MtpRecordDetailed.class);
		return CommonUtil.objectListGetList(list);
	}
 
	 
	
	/**
	 * 更新系统参数表的信息  
	 * @param param_value1   参数代码
	 * @param sys_name    参数名称
	 * @param param_value1   参数
	 */
	public int updateKey(String param_value1,String sys_name,String  sys_code) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update zq_data set param_value1 = ? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(param_value1);
	    if(!StringUtil.isBlank(sys_name)){
	        sql+="  ,sys_name=?";
	        params.add(sys_name);
	    }
        sql+=" where sys_code= ?";
        params.add(sys_code);
		int i=db.executeUpdate(db, sql, params);
		return i;
	}
	
	
	/**
	 *  更新所有状态
	 * @param case_status  case状态
	 */
	public int udpateCaseStatus(Map<String, String> map) {
	    String case_status=map.get("case_status");
		DBUtils  db=DBUtils.getDBUtils();
		String sql="update mtp_record_detailed set case_status = ? where 1=1 ";
		List<Object>  params=new ArrayList<Object>();
		params.add(case_status);
		 String show_type=  map.get("show_type");
		 if(!StringUtil.isBlank(show_type)){
			 if(show_type.indexOf("trunk_")>-1){
				 params.add(show_type);
				 sql+=" and show_type  like     concat('',?,'%')    ";
			 }
			 if(show_type.indexOf("remedy_tcp")>-1){
				 params.add(show_type);
				 sql+=" and show_type   = ?    ";
			 }
		 }
		int i=db.executeUpdate(db, sql, params);
		return i;
	}
	
	/**
	 *  根据 caseId 删除数据
	 * @param 删除
	 */
	public int delMtpRecordDetailedByCaseId(String caseId,String showType) {
		DBUtils  db=DBUtils.getDBUtils();
		String sql="delete from  mtp_record_detailed where  case_id=  ?  ";
		List<Object>  params=new ArrayList<Object>();
		params.add(caseId);
		 if(!StringUtil.isBlank(showType)){
			 if(showType.indexOf("trunk_")>-1){
				 params.add(showType);
				 sql+=" and show_type  like     concat('',?,'%')    ";
			 }
			 if(showType.indexOf("remedy_tcp")>-1){
				 params.add(showType);
				 sql+=" and show_type   = ?    ";
			 }
		 }
		int i=db.executeUpdate(db, sql, params);
		return i;
	}
	
	
}
