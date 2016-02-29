package com.xgg.epd.actions;
/*
 * Author:Guanggen Xu
 * Date:2015-01-28
 * Function: This is very important for this ERP. 
 * It contains some menus for this ERP.
 */
import com.xgg.epd.beans.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminNavigateTo extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String ip = request.getRemoteAddr().toString();
		String enameString = session.getAttribute(ip + "EmpName").toString()
				.trim();
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DATE);
		if ("/WEB-INF/page/upload.jsp".equals(url)) {
			return "upload";
		} else if ("/WEB-INF/page/adminChart.jsp".equals(url)) {
			return "show";
		} else if ("/WEB-INF/page/adminPieChart.jsp".equals(url)) {
			return "adminPie";
		} else if ("/WEB-INF/page/tpmPieChart.jsp".equals(url)) {
			return "tpmPie";
		} else if ("/WEB-INF/page/adminFillMC.jsp".equals(url)) {
			if (today > DeadlineBean.endDate && today < DeadlineBean.startDate)
				return "adminNotFill";
			else {
				return "fillMonthlyControlling";
			}
		} else if ("/WEB-INF/page/adminAddEmp.jsp".equals(url)) {
			return "addemp";
		} else if ("/WEB-INF/page/AddPro.jsp".equals(url)) {
			return "addpro";
		} else if ("/WEB-INF/page/showMyChart.jsp".equals(url)) {
			return "showmine";
		} else if ("/WEB-INF/page/adminSearchEmp.jsp".equals(url)) {
			return "adminsearchemp";
		} else if ("/WEB-INF/page/AdminFunction.jsp".equals(url)) {
			return "adminback";
		} else if ("/WEB-INF/page/TpmFunction.jsp".equals(url)) {
			return "tpmback";
		} else if ("/WEB-INF/page/adminUpload.jsp".equals(url)) {
			return "upload";
		} else if ("/WEB-INF/page/adminAddPro.jsp".equals(url)) {
			return "adminAddPro";
		} else if ("/WEB-INF/page/adminAddServiceNr.jsp".equals(url)) {
			return "adminAddServiceNr";
		} else if ("/WEB-INF/page/adminRate.jsp".equals(url)) {
			return "adminRate";
		} else if ("/WEB-INF/page/adminResetPwd.jsp".equals(url)) {
			return "adminResetPwd";
		} else if ("/WEB-INF/page/tpmResetPwd.jsp".equals(url)) {
			return "tpmResetPwd";
		} else if ("/WEB-INF/page/tpmFillMC.jsp".equals(url)) {
			if (today > DeadlineBean.endDate && today < DeadlineBean.startDate)
				return "tpmNotFill";
			else
				return "tpmFillMC";
		} else if ("/WEB-INF/page/tpmChart.jsp".equals(url)) {
			return "tpmChart";
		} else if ("/WEB-INF/page/adminCorrect.jsp".equals(url)) {
			return "adminCorrect";
		} else if ("/WEB-INF/page/adminSetCalendar.jsp".equals(url)) {
			return "adminCalendar";
		} else if ("/WEB-INF/page/empFillMC.jsp".equals(url)) {
			if (today > DeadlineBean.endDate && today < DeadlineBean.startDate) {
				return "empNotFill";
			} else {
				return "empFillMC";
			}
		} else if ("/WEB-INF/page/adminEmail.jsp".equals(url)) {
			return "email";
		} else if ("/WEB-INF/page/adminHistory.jsp".equals(url)) {
			session.setAttribute(ip + "history", null);
			session.setAttribute(ip + "time", null);
			return "ahistory";
		} else if ("/WEB-INF/page/empHistory.jsp".equals(url)) {
			session.setAttribute(ip + "history", null);
			session.setAttribute(ip + "time", null);
			return "ehistory";
		} else if ("/WEB-INF/page/tpmHistory.jsp".equals(url)) {
			session.setAttribute(ip + "history", null);
			session.setAttribute(ip + "time", null);
			return "thistory";
		} else if ("/WEB-INF/page/employeeHome.jsp".equals(url)) {
			return "ehome";
		} else if ("/WEB-INF/page/adminHome.jsp".equals(url)) {
			return "ahome";
		} else if ("/WEB-INF/page/tpmHome.jsp".equals(url)) {
			return "thome";
		}else if("/WEB-INF/page/BaseTopTen.jsp".equals(url))
		{
			return "BaseTopTen";
		}else if("/WEB-INF/page/CostTopTen.jsp".equals(url))
		{
			return "CostTopTen";
		}else if("/WEB-INF/page/PercentTopTen.jsp".equals(url))
		{
			return "PercentTopTen";
		}else if("/WEB-INF/page/adminSectionPieChart.jsp".equals(url))
		{
			return "SectionPieChart";
		}else if("/WEB-INF/page/guestColumnChart.jsp".equals(url))
		{
			return "guestChart";
		}
		return "";

	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;

	}
}
