package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.MonthlyControllingBean;
import com.xgg.epd.dbs.BasicDB;

public class AdminUpdateMCAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empname;
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getWorkingdays() {
		return workingdays;
	}
	public void setWorkingdays(String workingdays) {
		this.workingdays = workingdays;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String section;
	private String month;
	private String proName;
	private String service;
	private String workingdays;
	private String remark;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpSession session=request.getSession();
	Calendar calendar=Calendar.getInstance();
	int year=calendar.get(Calendar.YEAR);
	public String execute()
	{
		System.out.println(service);
		MonthlyControllingBean mcb=(MonthlyControllingBean) session.getAttribute("modMC");
		empname=mcb.getEmpname();
		String mid=mcb.getMid()+"";
		String usql="update tb_MonthlyControlling set EmpName=?,Section=?,Month=?,ProName=?,ServiceNr=?,Remark=?,WorkingDays=?,Year=? where MId=?";
		String[]paras={empname,section,month,proName,service,remark,workingdays,year+"",mid};
		BasicDB basicDB=new BasicDB();
		basicDB.updateDB(usql, paras);
		String query_sql="select sum(WorkingDays) from tb_MonthlyControlling where Month='"+month+"' and EmpName='"+empname+"' and Year='"+year+"'";
		ArrayList<String> al=basicDB.SQuerydb(query_sql);
		float factDays=Float.parseFloat(al.get(0).toString().trim());
		String pcsql="update tb_PersonControlling set totalDays=? where EmpName=? and Month=? and Year=?";
		String []pcparas ={factDays+"",empname,month,year+""};
		basicDB.updateDB(pcsql, pcparas);
		request.setAttribute("sendName", empname);
		request.setAttribute("sendSection", section);
		request.setAttribute("sendMonth", month);
		return SUCCESS;
	}
}
