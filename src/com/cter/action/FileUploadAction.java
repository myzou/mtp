package com.cter.action;

import java.io.File;


import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction  extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private File java1234;//�ϴ����ļ��ı�name
	private String java1234FileName;//�ϴ����ļ���
	private String java1234ContentType;//�ϴ����ļ�����
	
	

	public File getJava1234() {
		return java1234;
	}

	public void setJava1234(File java1234) {
		this.java1234 = java1234;
	}

	public String getJava1234FileName() {
		return java1234FileName;
	}

	public void setJava1234FileName(String java1234FileName) {
		this.java1234FileName = java1234FileName;
	}

	public String getJava1234ContentType() {
		return java1234ContentType;
	}

	public void setJava1234ContentType(String java1234ContentType) {
		this.java1234ContentType = java1234ContentType;
	}


	@Override
	public String execute() throws Exception {
		System.out.println("�ϴ����ļ�����"+java1234FileName);
		System.out.println("�ϴ������ͣ�"+java1234ContentType);
		System.out.println("�ϴ����ļ�·����"+java1234);
		//��ȡҪ�����ļ��е�����·��(����·��)
        String realPath=ServletActionContext.getServletContext().getRealPath("/upload");
		File file=new File(realPath);//�����·��
		File saveFile=new File(file, java1234FileName);
		if(!file.exists()){
			file.mkdirs();
		}
		FileUtils.copyFile(java1234,saveFile);
		System.out.println("������ļ�·����"+saveFile);
		return SUCCESS;
	}


	

}
