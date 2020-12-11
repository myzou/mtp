package com.cter.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.MtpRecordDetailedDaoImpl;
import com.cter.dao.impl.TrunkInfoMtpDaoImpl;
import com.cter.entity.MtpRecordDetailed;
import com.cter.entity.TrunkInfoMtp;
import com.cter.entity.ZqData;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.UuidUtil;


@Service("ZQMtpRecordDetailedService")
@Transactional
public class ZQMtpRecordDetailedService {

	@Autowired
	private MtpRecordDetailedDaoImpl mtpRecordDetailedDaoImpl ;
	
	@Autowired
	private TrunkInfoMtpDaoImpl trunkInfoMtpDaoImpl;
	private static Map<String, String >  otherMap=LoadPropertiestUtil.loadProperties("config/other.properties");
	private static final String mtp_project=otherMap.get("mtp_project");

	
	public  LayuiPage<MtpRecordDetailed>  findMtpRecordDetailed ( Map<String, String> map,LayuiPage<MtpRecordDetailed>  layui  ){
		mtpRecordDetailedDaoImpl.findMtpRecordDetailedPage (map, layui);
		return 	layui;
	}



	public int updateKey(Map<String, String> map) {
		String op_name = map.get("op_name");
		String op_password = map.get("op_password");
		String totpKey = map.get("totpKey");
		String param_value1 = op_name + "###" + op_password+"###"+totpKey;
		int i = mtpRecordDetailedDaoImpl.updateKey(param_value1, null, "OP");
		return i;
	}
	
	public int udpateCaseStatus( Map<String,String > map) {
		int  i=mtpRecordDetailedDaoImpl.udpateCaseStatus(map);
		return i;
	}
	



	
	
	
	
	
}
