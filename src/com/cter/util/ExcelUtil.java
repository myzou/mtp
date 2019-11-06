package com.cter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
			
	
	/**
	 * �����ļ��� �����ڵ�����Ǯ�ɽӿ�����
	 * @param filePaths  ����Ǯ��FTP���ص����ص� �ļ�list
	 * @param dateStr	ʱ�� yyyyMMdd ���������
	 * @return
	 * @throws Exception
	 */
	public int sqbImpExcele()throws Exception{
//			 boolean isXSSFWorkbook = true;//����ʽ�ж� XSSF .xlsx, HSSF .xls
//			 //�ж�Excele���� ��.xlsx ����.xls
//			 if (fileName.substring(fileName.length() - 3, fileName.length()).equals("xls")){
//				 book=new HSSFWorkbook(is);
//			 }else {
//				 book = new XSSFWorkbook(OPCPackage.open(is));
//		            isXSSFWorkbook = false;
//					if(row.getCell(0).getStringCellValue().startsWith("#")||row.getCell(0).getCellType()==HSSFCell.CELL_TYPE_BLANK)
					
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					bt.setSerialNo(UUIDUtil.GetUUID());
//					bt.setTransNo(row.getCell(2).getStringCellValue());
//					bt.setAmt(Double.valueOf(new DecimalFormat("##0.00").format(row.getCell(21).getNumericCellValue())));
//					bt.setJsAmt(Double.valueOf(new DecimalFormat("##0.00").format(row.getCell(22).getNumericCellValue())));
//					bt.setTransTime(sdf1.parse(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()));
					
		        return 1;
	}
	
	
	public   static void ImpExcele() throws IOException, InvalidFormatException{
		InputStream is =new FileInputStream( new File("D:\\�û�Ŀ¼\\�ҵ��ĵ�\\Tencent Files\\944711140\\FileRecv\\ȫ��������Ȩ����ϵ��ʽ20180928.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(is);
			System.out.println("��");
			StringBuffer bf=new StringBuffer();
        // ѭ��������Sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
        		XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            System.out.println("sheet���ƣ�"+xssfSheet.getSheetName());
            // ѭ����Row
                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	  if(xssfRow.getRowNum()==0) {
                      	continue;
                      }
                	  for(int cell=0;cell<3;cell++){
                		  bf.append(  xssfRow.getCell(cell)+"\t");
//                		System.out.print(  xssfRow.getCell(cell));
                	  }
                	  bf.append("\t\n");
//                	  System.out.println();
                }
            }
                System.out.println(  bf.toString());
        }
        if(is!=null){
     	   is.close();
        }
        
/*       д�뵽txt����
 *  File wFile=new File("E:\\��ȡ��exceli.txt");
        if(!wFile.exists()){
        	wFile.createNewFile();
        }
        FileWriter fileWriter=	new FileWriter(wFile);
        BufferedWriter  bwWriter=new BufferedWriter(fileWriter);
        bwWriter.write(bf.toString());
        bwWriter.flush();
        if(bwWriter!=null){
            bwWriter.close();
        }*/
        
     
	}
	
	public static void main(String[] args)throws Exception {
		ImpExcele();
	}
}
