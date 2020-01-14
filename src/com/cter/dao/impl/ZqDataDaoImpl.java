package com.cter.dao.impl;

import com.cter.entity.ZqData;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository("ZqDataDaoImpl")
@SuppressWarnings("all")
public class ZqDataDaoImpl extends BaseDaOImpl<ZqData> {

    /**
     * 根据sys_code 获取zq_data 数据
     * @param sys_code
     * @return
     */
    public ZqData getZqDataBySysCode(String sys_code) {
        DBUtils db=DBUtils.getDBUtils();
        String sql ="select * from zq_data where sys_code= ?";
        List<Object> params=new ArrayList<Object>();
        params.add(sys_code);
        List<ZqData> list=db.executeQueryByRef(db, sql, params, ZqData.class);
        return CommonUtil.objectListGetOne(list);
    }
    /**
     * 获取主键最大id
     * @return
     */
    public int queryMaxZqDataId( ) {
        DBUtils db =  DBUtils.getDBUtils();
        String sql="select max(code_id) as max from zq_data";
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

}
