package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.xgg.epd.beans.MonthlyControllingBean;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ChangeLastMonthAction extends ActionSupport {

	/**
	 * Handle the change of last month records 
	 */
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpSession session=request.getSession();
	String ip=request.getRemoteAddr().toString();
	String empName=session.getAttribute(ip+"EmpName").toString();
	String mark=session.getAttribute(ip+"mark").toString();
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id.substring(1, id.length());
	}
	private String remark;
	private String service;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String changeRemark()
	{
		ArrayList<MonthlyControllingBean> mcbeans=(ArrayList<MonthlyControllingBean>)session.getAttribute(empName);
		int index=Integer.parseInt(id);
		mcbeans.get(index).setRemark(remark);
		if ("1".equals(mark)) {
			return "tpmfillsuccess";
		} else if ("2".equals(mark)) {
			return "adminfillsuccess";
		} else if ("0".equals(mark)) {
			return "empfillsuccess";
		}
		return "";
	}

	public String changeService()
	{
		ArrayList<MonthlyControllingBean> mcbeans=(ArrayList<MonthlyControllingBean>)session.getAttribute(empName);
		int index=Integer.parseInt(id);
		String pString=mcbeans.get(index).getProname().trim();
		if("Admin".equals(new FillAction().ConfirmClass(pString))||"none".equals(new FillAction().ConfirmClass(pString)))
		{
			mcbeans.get(index).setService("000General");
		}else {
			mcbeans.get(index).setService(service);
		}
		if ("1".equals(mark)) {
			return "tpmfillsuccess";
		} else if ("2".equals(mark)||"3".equals(mark)) {
			return "adminfillsuccess";
		} else if ("0".equals(mark)) {
			return "empfillsuccess";
		}
		return "";
	}
}
