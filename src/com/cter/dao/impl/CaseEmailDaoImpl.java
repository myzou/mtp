package com.cter.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cter.entity.CaseEmail;


@Repository("CaseEmailImpl")
@SuppressWarnings("all")
public class CaseEmailDaoImpl extends BaseDaOImpl<CaseEmail> {
	
	public List<CaseEmail>  loadAll() {
		 List<CaseEmail> list= this.findAll();
			if(null!=list&&list.size()>0){
				return  list ;
			}
			return null;
	}

}
