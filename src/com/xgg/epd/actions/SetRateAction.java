package com.xgg.epd.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.iap.Response;
import com.xgg.epd.dbs.BasicDB;

public class SetRateAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	private String year;
	private String rate;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	public String execute()throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sqlString="insert into tb_Rate values(?,?)";
		String []paras={year,rate};
		basicDB.updateDB(sqlString, paras);
		request.setAttribute("process", "Submit Success !");
		return SUCCESS;
	}

}
