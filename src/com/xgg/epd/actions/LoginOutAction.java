package com.xgg.epd.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class LoginOutAction extends ActionSupport{
	/**
	 * Function: When you exit the system,this class will be invoked.
	 */
	private static final long serialVersionUID = 1L;

	public String execute()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		String ip=request.getRemoteAddr().toString();
		if(session.getAttribute(ip+"EmpName")==null)
		{
			return ERROR;
		}
		session.setAttribute(ip + "PieDept",null);
		session.setAttribute(session.getAttribute(ip+"EmpName").toString()+"PageNow",null);
		session.setAttribute("tpm"+session.getAttribute(ip+"EmpName").toString(),null);
		session.setAttribute(ip + "tpmchartYear", null);
		session.setAttribute(session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute("pro"+session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute("det"+session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute("tpmname"+session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute("tpmnamePie" + session.getAttribute(ip+"EmpName").toString(),null);
		session.setAttribute("spie" + session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute("epie" + session.getAttribute(ip+"EmpName").toString(), null);
		session.setAttribute(ip+"EmpName", null);
		session.setAttribute(ip+"eid",null);
		session.setAttribute(ip+"basetenYear", null);
		session.setAttribute(ip+"basetenPro", null);
		session.setAttribute(ip+"costtenYear", null);
		session.setAttribute(ip+"costtenPro", null);
		session.setAttribute(ip+"percenttenPro", null);
		session.setAttribute(ip+"percenttenYear", null);
		session.setAttribute("time", null);
		session.setAttribute(ip+"Sec", null);
		session.setAttribute(ip+"mark", null);	
		session.setAttribute(ip+"history", null);
		session.setAttribute(ip+"time", null);
		session.setAttribute(ip+"pageCount", null);
		session.setAttribute("search", null);		
		return SUCCESS;
	}
}
