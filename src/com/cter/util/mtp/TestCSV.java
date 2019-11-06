package com.cter.util.mtp;

/**
 * @author op1768
 * @create 2019-09-04 15:13
 * @project mtp
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import java.io.*;
import java.util.List;

public class TestCSV {

    public static void main(String[] args) throws Exception {

        System.out.println(Read1("d:/2702686.xlsx.csv"));

    }

    //���ж�ȡ�ļ�����
    public static String Read1(String infile) throws Exception    //infile="data/in.txt"
    {
        StringBuffer sb = new StringBuffer();

        CsvReader reader = CsvUtil.getReader();
        //���ļ��ж�ȡCSV����
        CsvData data = reader.read(FileUtil.file(infile));
        List<CsvRow> rows = data.getRows();
        //������
        for (CsvRow csvRow : rows) {
            //getRawList����һ��List�б��б��ÿһ��ΪCSV�е�һ����Ԫ�񣨼ȶ��ŷָ����֣�
            List<String> rowList = csvRow.getRawList();
            for (int i = 0; i < rowList.size(); i++) {
                System.out.print(rowList.get(i) + "|");
            }
            System.out.println();
        }
        return sb.toString();
    }


}