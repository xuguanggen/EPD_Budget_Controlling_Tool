package com.xgg.epd.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.excel.ExcelService;

public class UploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uploadtype;
	private String month;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getUploadtype() {
		return uploadtype;
	}
	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	public String getMyFileContentType() {
		return myFileContentType;
	}
	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	private File myFile;
	private String myFileContentType;
	private String myFileFileName;
	private String d;
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	HttpServletRequest request=ServletActionContext.getRequest();
	public String execute() throws Exception
	{
		String []times=d.split("-");
		year=times[0];
		month=Integer.parseInt(times[1])+"";
		System.out.println("upload type=="+uploadtype);
		InputStream is=new FileInputStream(myFile);
		String uploadPath=ServletActionContext.getServletContext().getRealPath("/upload");
		if(myFile!=null)
		{
			File saveDir=new File(uploadPath);
			if(!saveDir.exists())
			{
				saveDir.mkdirs();
			}
			File toFile=new File(uploadPath,this.getMyFileFileName());
			OutputStream os=new FileOutputStream(toFile);
			byte []buffer=new byte[1024];
			int length=0;
			while((length=is.read(buffer))>0)
			{
				os.write(buffer, 0,length);
			}
			System.out.println("upload FileName "+myFileFileName);
			System.out.println("upload FileContentType "+myFileContentType);
			is.close();
			os.close();
		}
		ExcelService es=new ExcelService(month,year);
		es.readExcel(uploadPath+"/"+myFileFileName, uploadtype);
		return SUCCESS;
	}
	
}
