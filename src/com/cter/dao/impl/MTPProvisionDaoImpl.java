package com.cter.dao.impl;

import com.cter.entity.MTPProvision;
import com.cter.util.DBUtils;
import com.cter.util.TempDBUtils;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository("MTPProvisionDaoImpl")
@SuppressWarnings("all")
public class MTPProvisionDaoImpl extends BaseDaOImpl<MTPProvision> {
    /**
     * 获取要运行 before 的信息
     *
     * @return
     */
    public List<MTPProvision> getRunBefore() {
        String sql = "select  a.* from  mtp_provision a where DATE_SUB(a.start_time ,INTERVAL 1 DAY_HOUR)<now() and run_before=0 and runing=0";
        DBUtils db = DBUtils.getDBUtils();
        List<MTPProvision> list = db.executeQueryByRef(db, sql, null, MTPProvision.class);
        return list;
    }

    /**
     * 获取要运行 after 的信息
     *
     * @return
     */
    public List<MTPProvision> getRunAfter() {
        String sql = "select  a.* from  mtp_provision a where DATE_SUB(a.end_time ,INTERVAL 30 DAY_MINUTE)<now() and run_after=0 and runing=0";
        DBUtils db = DBUtils.getDBUtils();
        List<MTPProvision> list = db.executeQueryByRef(db, sql, null, MTPProvision.class);
        return list;
    }

    /**
     * 更新是否在运行
     * @param runing
     * @param case_id
     * @return
     */
    public int updateRuning(String runing, String case_id) {
        String sql = "UPDATE `mtp`.`mtp_provision` SET `runing` = ?,update_time=now() WHERE `case_id` = ? ";
        String username = "root";
        String password = "root1234root";
        String url = "jdbc:log4jdbc:mysql://210.5.3.30:3306/mtp?characterEncoding=utf-8";
        String driver = "net.sf.log4jdbc.DriverSpy";
        TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);
        List<Object> params = new ArrayList<>();
        params.add(runing);
        params.add(case_id);
       return tempDBUtils.executeUpdate(tempDBUtils, sql, params);
    }

    /**
       * 更新 RunBefore 字段
       * @param RunBefore
       * @param case_id
       * @return
       */
      public int updateRunBefore(String runBefore, String case_id) {
          String sql = "UPDATE `mtp`.`mtp_provision` SET `run_before` = ?,update_time=now() WHERE `case_id` = ? ";
          String username = "root";
          String password = "root1234root";
          String url = "jdbc:log4jdbc:mysql://210.5.3.30:3306/mtp?characterEncoding=utf-8";
          String driver = "net.sf.log4jdbc.DriverSpy";
          TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);
          List<Object> params = new ArrayList<>();
          params.add(runBefore);
          params.add(case_id);
         return tempDBUtils.executeUpdate(tempDBUtils, sql, params);
      }

    /**
       * 更新 run_after 字段
       * @param runAfter
       * @param case_id
       * @return
       */
      public int updateRunAfter(String runAfter, String case_id) {
          String sql = "UPDATE `mtp`.`mtp_provision` SET `run_after` = ?,update_time=now() WHERE `case_id` = ? ";
          String username = "root";
          String password = "root1234root";
          String url = "jdbc:log4jdbc:mysql://210.5.3.30:3306/mtp?characterEncoding=utf-8";
          String driver = "net.sf.log4jdbc.DriverSpy";
          TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);
          List<Object> params = new ArrayList<>();
          params.add(runAfter);
          params.add(case_id);
         return tempDBUtils.executeUpdate(tempDBUtils, sql, params);
      }

    /**
     * 更新数据
     * @param runing
     * @param case_id
     * @return
     */
    public int updateData(MTPProvision mtpProvision) {
        String sql = "INSERT INTO `mtp`.`mtp_provision`(`case_id`, `jsonstr`, `start_time`, `end_time`, `run_before`, `run_after`, `runing`, `update_time`)" +
                " VALUES (?,?,?,?,'0','0','0',now()) " +
                "ON DUPLICATE KEY UPDATE jsonstr=?,start_time=?,end_time=?,update_time=now(),run_before=0,run_after=0";
        String username = "root";
        String password = "root1234root";
        String url = "jdbc:log4jdbc:mysql://210.5.3.30:3306/mtp?characterEncoding=utf-8";
        String driver = "net.sf.log4jdbc.DriverSpy";
        TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);
        List<Object> params = new ArrayList<>();
        params.add(mtpProvision.getCaseId());
        params.add(mtpProvision.getJsonstr());
        params.add(mtpProvision.getStartTime());
        params.add(mtpProvision.getEndTime());
        params.add(mtpProvision.getJsonstr());
        params.add(mtpProvision.getStartTime());
        params.add(mtpProvision.getEndTime());
        return tempDBUtils.executeUpdate(tempDBUtils, sql, params);
    }


}
