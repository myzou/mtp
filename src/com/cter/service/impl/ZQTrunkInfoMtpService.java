package com.cter.service.impl;

import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.TrunkInfoMtpDaoImpl;
import com.cter.entity.TrunkInfoMtp;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;


@Service("ZQTrunkInfoMtpService")
@Transactional
public class ZQTrunkInfoMtpService {

	@Autowired
	private TrunkInfoMtpDaoImpl mtpDaoImpl;
	
	public  LayuiPage<TrunkInfoMtp>  findSysUserPage ( Map<String, String> map,LayuiPage<TrunkInfoMtp>  layui  ){
		mtpDaoImpl.findSysUserPage (map, layui);
		return 	layui;
	}
	
	public void updateTrunkInfoMtp(TrunkInfoMtp oldMtp) {
		TrunkInfoMtp newMtp=mtpDaoImpl.getTrunkInfoMtp(oldMtp.getTrunkId());
		newMtp.setProviderCircuitNum(oldMtp.getProviderCircuitNum());
		newMtp.setInternalCircuitNum(oldMtp.getInternalCircuitNum());
		newMtp.setProvider(oldMtp.getProvider());
		newMtp.setTrunkName(oldMtp.getTrunkName());
		newMtp.setAEndFullName(oldMtp.getAEndFullName());
		newMtp.setAEndInterface(oldMtp.getAEndInterface());
		newMtp.setAEndInterfaceIp(oldMtp.getAEndInterfaceIp());
		newMtp.setZEndFullName(oldMtp.getAEndFullName());
		newMtp.setZEndInterface(oldMtp.getZEndInterface());
		newMtp.setZEndInterfaceIp(oldMtp.getZEndInterfaceIp());
		newMtp.setLinkType(oldMtp.getLinkType());
		newMtp.setLastUpdatedTime(DateUtil.getDate(new Date()));
		mtpDaoImpl.updateO(newMtp);
	}
	
	public void addTrunkInfoMtp(TrunkInfoMtp oldMtp) {
		oldMtp.setCreateTime(DateUtil.getDate(new Date()));
		oldMtp.setLastUpdatedTime(oldMtp.getCreateTime());
		mtpDaoImpl.save(oldMtp);
	}
	
	public int delTrunkInfoMtp(String trunkId) {
		return mtpDaoImpl.delTrunkInfoMtp(trunkId);
	}
	public TrunkInfoMtp getTrunkInfoMtp(String  trunkId) {
		return mtpDaoImpl.getTrunkInfoMtp(Long.valueOf(trunkId));
	}
}
