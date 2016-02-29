package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.MonthlyControllingBean;

public class UpdateMcAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String proName;
	public String Service;
	public String Workingdays;
	public String Remark;
	public String execute()
	{
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String ip=request.getRemoteAddr().toString();	
		HttpSession session=request.getSession();
		if(session.getAttribute(ip+"EmpName")==null)
		{
			return ERROR;
		}
		String ename=session.getAttribute(ip+"EmpName").toString();
		String idString=session.getAttribute("modID").toString();
		int id=Integer.parseInt(idString);
		ArrayList<MonthlyControllingBean>alArrayList=(ArrayList<MonthlyControllingBean>)session.getAttribute(ename);
		MonthlyControllingBean bean=alArrayList.get(id);
		bean.setProname(proName.trim());
		bean.setService(Service.trim());
		bean.setWorkingdays(Float.parseFloat(Workingdays.trim()));
		bean.setRemark(Remark);
		alArrayList.set(id, bean);
		String markString=session.getAttribute(ip+"mark").toString().trim();
		System.out.println("Before=="+session.getAttribute(ip+"total").toString().trim());
		float totaldays=Float.parseFloat(session.getAttribute(ip+"total").toString().trim())+Float.parseFloat(Workingdays);
		session.setAttribute(ip+"total", totaldays);
		System.out.println("After=="+session.getAttribute(ip+"total").toString().trim());
		if("1".equals(markString))
		{
			return "tpmsuccess";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "adminsuccess";
		}
		if("0".equals(markString))
		{
			return "empsuccess";
		}
		return "";		
	}
}
