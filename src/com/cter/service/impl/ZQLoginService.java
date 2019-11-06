package com.cter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.SysUserDaoImpl;
import com.cter.entity.SysUser;


@Service("ZQLoginService")
public class ZQLoginService {
		
	@Autowired
	private SysUserDaoImpl daoImpl;
	
}
