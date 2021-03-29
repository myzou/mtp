package com.cter.action;

import com.cter.util.BaseLog;
import com.cter.util.LoadPropertiestUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;
import java.util.Map;

public class DownLoadAction extends ActionSupport {
	

	Map<String, String>  otherMap= LoadPropertiestUtil.loadProperties("config/other.properties");

	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));
	
	private String fn;  //文件名
    private InputStream excelStream ;   
    


	public InputStream getExcelStream() {
		return excelStream;
	}


	public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }


    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

	public String getOTTemplate() throws Exception{
		//获得路径和文件名
		try {
			ClassLoader  classLoader=	LoadPropertiestUtil.class.getClassLoader();
			InputStream is=classLoader.getResourceAsStream("config/"+otherMap.get("Template")+".xlsx");
			this.excelStream =is;
		} catch ( Exception e) {
			e.printStackTrace();
		}finally{
		}
		return "excel";
	}





    
}
   
