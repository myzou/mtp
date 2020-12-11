package com.cter.test;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.cter.bean.MTPA;
import com.cter.bean.PePort;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class getParam {


    public static void main(String[] args) {
        getConnJson(null);
    }



    public static void test(){


    }


    /**
     * 根据hostname 获取connection
     * @param hostname
     * @return
     */
    public static Connection  getConnection(String hostname ){
        Connection conn=null;
        try {
            conn= new Connection(hostname.trim());
            conn.connect();
        } catch (IOException e) {
            conn=null;
        }
        return conn;
    }




    public  static void getConnJson(String fileDir){
        Long strLong =System.currentTimeMillis();
        long sta=System.currentTimeMillis();
        MTPA mtpa=new MTPA();
        List<PePort>  pePorts=new ArrayList<PePort>();

        mtpa.setTense("before");
        mtpa.setTicketName("2687324");
        fileDir="D:/2687324.xlsx";//excel 路径
        InputStream is;
        try {
            is = new FileInputStream( new File(fileDir));
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet0=workbook.getSheetAt(0);

            for (int i = 0; i <=xssfSheet0.getLastRowNum(); i++) {
                if(i==0){
                    continue;
                }
                XSSFRow row=xssfSheet0.getRow(i);
                PePort pePort=new PePort();
                pePort.setInternalSiteId(getStringValueFromCell(row.getCell(0)));
                pePort.setPeRouter(getStringValueFromCell(row.getCell(1)));
                pePort.setPePortInterface(getStringValueFromCell(row.getCell(2)));
                pePort.setPeWanIp(getStringValueFromCell(row.getCell(3)));
                pePort.setCeWanIp(getStringValueFromCell(row.getCell(4)));
                pePort.setTcpType("");
                pePorts.add(pePort);
            }
            if(is!=null){
                is.close();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        mtpa.setPePorts(pePorts);
        Long endLong =System.currentTimeMillis();
        Gson gson=new Gson();
        System.out.println((endLong-strLong)/1000.00);
        System.out.println("mtpa:\t\n"+gson.toJson(mtpa));
    }




    /**
     * 根据连接和账号密码来验证登录信息
     * @param conn			连接
     * @param name			账号
     * @param password  密码
     * @return
     */
    public static String  checkdUserInfo(Connection conn,String name,String password){
        String message="";
        try {
            conn.authenticateWithPassword(name, password);
            message="PASS";
        } catch (IOException e) {
            e.printStackTrace();
            message= "ERROR:用户密码验证错误：\r\nname:"+name+"\r\npassword:"+password;
        }
        return message;
    }




    public static String getStringValueFromCell(Cell cell) {
        SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String cellValue = "";
        if(cell == null) {
            return cellValue;
        }
        else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        }

        else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                cellValue = sFormat.format(date);
            }
            else {
                cellValue = decimalFormat.format((cell.getNumericCellValue()));
            }
        }
        else if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            cellValue = "";
        }
        else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        }
        else if(cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cellValue = "";
        }
        else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = cell.getCellFormula().toString();
        }
        return cellValue;
    }


    public static void getExcel() {
        MTPA mtpa=new MTPA();
        mtpa.setTicketName("2647295");
        mtpa.setTense("before");
        String aa="{\"PING 218.96.243.76 (218.96.243.76): 56 data bytes\\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\\n--- 218.96.243.76 ping statistics ---\\n100 packets transmitted, 100 packets received, 0% packet loss\\nround-trip min/avg/max/stddev = 0.597/4.316/109.771/13.175 ms\\n\"}";
    }



}
