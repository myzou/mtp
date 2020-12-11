package com.cter.util.mtp;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.cter.bean.MTPA;
import com.cter.bean.PePort;
import com.cter.util.BaseLog;
import com.cter.util.StringUtil;
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

public class GetParam {


    public static void main(String[] args) {
        getConnJson(null, "d:/2702686.xlsx.csv", null);
    }


    /**
     * 根据excel路径获取线路组装参数获取参数
     *
     * @param caseId  case id
     * @param fileDir excel 路径，空默认  D:/testCase.xlsx
     * @param period  获取之前还是之后的参数 before after，空默认before
     * @return 返回根据excel获取的参数参数
     */
    public static String getConnJson(String caseId, String fileDir, String period) {
        String retrunExcelParam = "";
        BaseLog log = new BaseLog("MTPQueryLog");

        try {
            Long strLong = System.currentTimeMillis();
            long sta = System.currentTimeMillis();
            MTPA mtpa = new MTPA();
            List<PePort> pePorts = new ArrayList<PePort>();
            if (!StringUtil.isBlank(period)) {
                mtpa.setTense(period);
            } else {
                mtpa.setTense("before");
            }
            if (StringUtil.isBlank(fileDir)) {
                fileDir = "D:/testCase.xlsx";//excel 路径
            }
            if (!StringUtil.isBlank(caseId)) {
                mtpa.setTicketName(caseId);
            } else {
                if (fileDir.endsWith(".csv")) {
                    if (fileDir.lastIndexOf("/") > -1) {
                        mtpa.setTicketName(fileDir.substring(fileDir.lastIndexOf("/") + 1, fileDir.lastIndexOf(".csv")));
                    } else {
                        mtpa.setTicketName(fileDir.substring(fileDir.lastIndexOf("\\") + 1, fileDir.lastIndexOf(".csv")));
                    }
                } else {
                    if (fileDir.lastIndexOf("/") > -1) {
                        mtpa.setTicketName(fileDir.substring(fileDir.lastIndexOf("/") + 1, fileDir.lastIndexOf(".xls")));
                    } else {
                        mtpa.setTicketName(fileDir.substring(fileDir.lastIndexOf("\\") + 1, fileDir.lastIndexOf(".xls")));
                    }
                }

            }
            if (fileDir.endsWith(".csv")) {
                CsvReader reader = CsvUtil.getReader();
                //从文件中读取CSV数据
                CsvData data = reader.read(FileUtil.file(fileDir));
                List<CsvRow> rows = data.getRows();
                //遍历行
                for (int i = 1; i < rows.size(); i++) {
                    CsvRow csvRow = rows.get(i);
                    List<String> rowList = csvRow.getRawList();
                    PePort pePort = new PePort();
                    pePort.setInternalSiteId(rowList.get(0));
                    pePort.setPeRouter(rowList.get(1));
                    pePort.setPePortInterface(rowList.get(2));
                    pePort.setPeWanIp(rowList.get(3));
                    pePort.setCeWanIp(rowList.get(4));
                    pePort.setTcpType("tcp");
                    pePort.setProviderCircuitNum("");
                    pePorts.add(pePort);
                }
            } else if (fileDir.endsWith(".xlsx")) {
                InputStream is;
                is = new FileInputStream(new File(fileDir));
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                XSSFSheet xssfSheet0 = workbook.getSheetAt(0);

                for (int i = 0; i <= xssfSheet0.getLastRowNum(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    XSSFRow row = xssfSheet0.getRow(i);
                    PePort pePort = new PePort();
                    pePort.setInternalSiteId(getStringValueFromCell(row.getCell(0)));
                    pePort.setPeRouter(getStringValueFromCell(row.getCell(1)));
                    pePort.setPePortInterface(getStringValueFromCell(row.getCell(2)));
                    pePort.setPeWanIp(getStringValueFromCell(row.getCell(3)));
                    pePort.setCeWanIp(getStringValueFromCell(row.getCell(4)));
                    pePort.setTcpType("tcp");
                    pePort.setProviderCircuitNum("");
                    pePorts.add(pePort);
                }
                if (is != null) {
                    is.close();
                }
            }
            mtpa.setPePorts(pePorts);

            Long endLong = System.currentTimeMillis();
            Gson gson = new Gson();
            System.out.println((endLong - strLong) / 1000.00);
            retrunExcelParam = gson.toJson(mtpa);
            System.out.println("mtpa:\t\n" + retrunExcelParam);
            return retrunExcelParam;
        } catch (Exception e) {
            retrunExcelParam = "根据excel无法转换参数。请查看excel格式是否有错误。";
            log.info(retrunExcelParam);
            log.printStackTrace(e);
            return retrunExcelParam;
        }
    }

    /**
     * 根据cell非空值
     *
     * @param cell
     * @return
     */
    public static String getStringValueFromCell(Cell cell) {
        SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                cellValue = sFormat.format(date);
            } else {
                cellValue = decimalFormat.format((cell.getNumericCellValue()));
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = cell.getCellFormula().toString();
        }
        return cellValue;
    }

}
