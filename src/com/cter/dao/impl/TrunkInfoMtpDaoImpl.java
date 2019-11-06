package com.cter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cter.entity.SysUser;
import com.cter.entity.SysUserRole;
import com.cter.entity.TrunkInfoMtp;
import com.cter.service.impl.ZQTrunkInfoMtpService;
import com.cter.util.BaseLog;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Repository("TrunkInfoMtpDaoImpl")
@SuppressWarnings("all")
public class TrunkInfoMtpDaoImpl extends BaseDaOImpl<TrunkInfoMtp> {
	

	public   LayuiPage<TrunkInfoMtp>  findSysUserPage(Map<String, String> map, LayuiPage<TrunkInfoMtp> layui) {
		  DBUtils db =  DBUtils.getDBUtils();
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String sql="SELECT a.`trunk_id`,a.`provider_circuit_num`,a.`internal_circuit_num`,a.`provider`,a.`trunk_name`,a.`a_end_full_name`,a.`z_end_full_name`,a.`a_end_interface`,a.`z_end_interface`,a.`a_end_interface_ip`,a.`z_end_interface_ip`,a.`link_type`,a.`create_time`,a.`last_updated_time` FROM trunk_info_mtp a " + 
		 		"   where 1=1 ";
	      List<Object> params=new ArrayList<Object>();
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String trunk_id=  map.get("trunk_id");
		 if(!StringUtil.isBlank(trunk_id)){
			 params.add(trunk_id);
			 sql+=" and trunk_id  like     concat('%',?,'%')    ";
		 }
		 String provider_circuit_num=  map.get("provider_circuit_num");
		 if(!StringUtil.isBlank(provider_circuit_num)){
			 params.add(provider_circuit_num);
			 if(!StringUtil.isBlank(map.get("fuzzyQuery"))){
				 sql+=" and provider_circuit_num  like     concat('%',?,'%')    ";
			 }else{
				 sql+=" and provider_circuit_num  =      ?    ";
			 }
		 }
		 String a_end_full_name=  map.get("a_end_full_name");
		 if(!StringUtil.isBlank(a_end_full_name)){
			 params.add(a_end_full_name);
			 sql+=" and a_end_full_name  like     concat('%',?,'%')    ";
		 }
		 String a_end_interface=  map.get("a_end_interface");
		 if(!StringUtil.isBlank(a_end_interface)){
			 params.add(a_end_interface);
			 sql+=" and a_end_interface  like     concat('%',?,'%')    ";
		 }
		 String z_end_full_name=  map.get("z_end_full_name");
		 if(!StringUtil.isBlank(z_end_full_name)){
			 params.add(z_end_full_name);
			 sql+=" and z_end_full_name  like     concat('%',?,'%')    ";
		 }
		 String z_end_interface=  map.get("z_end_interface");
		 if(!StringUtil.isBlank(z_end_interface)){
			 params.add(z_end_interface);
			 sql+=" and z_end_interface  like     concat('%',?,'%')    ";
		 }
		 
		int count=0;
		 List<TrunkInfoMtp> List=null;
		try {
			count=db.findCount(sql, params);
			List = db.loadPage(sql, params, TrunkInfoMtp.class, page, limit);
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
	
	public int delTrunkInfoMtp(String trunkId) {
		 DBUtils db =  DBUtils.getDBUtils();
		String sql="delete from trunk_info_mtp  where  trunk_id= ? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(Integer.valueOf(trunkId));
		int i= 	db.executeUpdate(db, sql, params);
		return i ;
	}
	
	
	public TrunkInfoMtp getTrunkInfoMtpDB(String trunk_id) {
		DBUtils db=DBUtils.getDBUtils();
		String sql ="select* from trunk_info_mtp  where trunk_id=?"; 
		List<Object> params=new ArrayList<Object>();
		params.add(trunk_id);
		List<TrunkInfoMtp> list=db.executeQueryByRef(db, sql, params, TrunkInfoMtp.class);
		return CommonUtil.objectListGetOne(list);
	}

	
	public TrunkInfoMtp getTrunkInfoMtp(Long trunk_id) {
		String hql ="from TrunkInfoMtp where trunk_id = ?";
		List<Object> param=new ArrayList<Object>();
		param.add(trunk_id);
		 List<TrunkInfoMtp> list= this.findOByClass(hql, param, TrunkInfoMtp.class);
		return CommonUtil.objectListGetOne(list);
	}
	
	public TrunkInfoMtp getTrunkInfoMtpByProviderCircuitNum(String  provider_circuit_num) {
		String hql ="from TrunkInfoMtp where providerCircuitNum = ?";
		List<Object> param=new ArrayList<Object>();
		param.add(provider_circuit_num);
		 List<TrunkInfoMtp> list= this.findOByClass(hql, param, TrunkInfoMtp.class);
		return CommonUtil.objectListGetOne(list);
	}
	
	/**
	 * 根据 设备pe名称和端口查询
	 * @param provider_circuit_num
	 * @return
	 */
	public TrunkInfoMtp getTrunkInfoMtpByPE(String  end_full_name,String end_interface) {
		String hql ="from TrunkInfoMtp where ( aEndFullName = ? and aEndInterface= ? ) or (( zEndFullName = ? and zEndInterface= ? ))";
		List<Object> param=new ArrayList<Object>();
		param.add(end_full_name);
		param.add(end_interface);
		param.add(end_full_name);
		param.add(end_interface);		
		List<TrunkInfoMtp> list= this.findOByClass(hql, param, TrunkInfoMtp.class);
		return CommonUtil.objectListGetOne(list);
	}
	 
}
