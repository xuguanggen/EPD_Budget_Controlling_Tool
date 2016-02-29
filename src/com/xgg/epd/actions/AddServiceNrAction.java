package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.regexp.internal.recompile;
import com.xgg.epd.dbs.BasicDB;
/*
 * Author:Guanggen Xu
 * Date:2015-01-28
 * Function : used to Add ServiceNr for each Section
 * 
 * 
 */
public class AddServiceNrAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String section;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	private String service;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpSession session=request.getSession();
	public String execute()
	{
		BasicDB basicDB=new BasicDB();
		String sql="insert into tb_SerOfSec values(?,?)";
		String []paras={section,service};
		basicDB.updateDB(sql, paras);
		ArrayList<String> list=getServiceBySection();
		request.setAttribute("serviceList",list);
		request.setAttribute("section", section);
		return SUCCESS;
	}
	public String deleteServicr()
	{
		System.out.println(service);
		String sql="delete from tb_SerOfSec where ServiceNr=?";
		String []paras={service};
		BasicDB basicDB=new BasicDB();
		basicDB.updateDB(sql, paras);
		ArrayList<String> list=getServiceBySection();
		request.setAttribute("serviceList",list);
		request.setAttribute("section", section);
		return "delSuccess";
	}
	
	
	public ArrayList<String> getServiceBySection()
	{
		ArrayList<String>list=new ArrayList<String>();
		String sqlString="select ServiceNr from tb_SerOfSec where Section='"+section+"'";
		BasicDB basicDB=new BasicDB();
		list=basicDB.SQuerydb(sqlString);
		return list;
	}
}
