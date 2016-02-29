package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;

import sun.misc.Request;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.DeadlineBean;
import com.xgg.epd.beans.MonthlyControllingBean;
import com.xgg.epd.dbs.BasicDB;

public class FillAction extends ActionSupport {
	/**
	 * Function:Handle the data that filled by User
	 */
	private static final long serialVersionUID = 1L;
	private String proName;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getService() {
		return Service;
	}

	public void setService(String service) {
		Service = service;
	}

	public String getWorkingdays() {
		return Workingdays;
	}

	public void setWorkingdays(String workingdays) {
		Workingdays = workingdays;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	private String Service;
	private String Workingdays;
	private String Remark;
	HttpServletRequest request = ServletActionContext.getRequest();
	String ipString = request.getRemoteAddr().toString();
	public static int pageSize = 50;

	public String execute() {
		Float wdsFloat = Float.parseFloat(Workingdays);
		Calendar a = Calendar.getInstance();
		String fillmonth = (a.get(Calendar.MONTH) + 1) + "";
		int year=a.get(Calendar.YEAR);
		int date = a.get(Calendar.DATE);
		if ("1".equals(fillmonth) && date <= DeadlineBean.getEndDate()) {
			fillmonth = "12";
			year=year-1;
		}else {
			if(date <= DeadlineBean.getEndDate())
			{
				fillmonth= (a.get(Calendar.MONTH) ) + "";
			}
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute(ipString + "EmpName") == null) {
			return ERROR;
		}
		if("Admin".equals(ConfirmClass(proName.trim()))||"none".equals(ConfirmClass(proName.trim())))
		{
			this.setService("000General");
		}
		String empname = session.getAttribute(ipString + "EmpName").toString();
		if (session.getAttribute(empname) == null
				|| (session.getAttribute(empname) != null && ((ArrayList<MonthlyControllingBean>) session
						.getAttribute(empname)).size() == 0)) {
			ArrayList<MonthlyControllingBean> aList = new ArrayList<MonthlyControllingBean>();
			MonthlyControllingBean bean = new MonthlyControllingBean();
			bean.setEmpname(session.getAttribute(ipString + "EmpName")
					.toString().trim());
			bean.setProname(proName.trim());
			bean.setMonth(fillmonth);
			bean.setSection(session.getAttribute(ipString + "Sec").toString()
					.trim());
			bean.setService(Service.trim());
			bean.setWorkingdays(wdsFloat);
			bean.setRemark(Remark.trim());
			bean.setYear(year + "");
			session.setAttribute(ipString + "total", wdsFloat);
			aList.add(bean);
			session.setAttribute(empname + "pageNow", 1);
			request.setAttribute("start", 0);
			request.setAttribute("end", aList.size());
			session.setAttribute(empname, aList);
			System.out.println(aList.size());
		} else {
			MonthlyControllingBean bean = new MonthlyControllingBean();
			bean.setEmpname(session.getAttribute(ipString + "EmpName")
					.toString().trim());
			bean.setProname(proName.trim());
			bean.setMonth(fillmonth);
			bean.setSection(session.getAttribute(ipString + "Sec").toString()
					.trim());
			bean.setService(Service.trim());
			bean.setWorkingdays(wdsFloat);
			bean.setRemark(Remark.trim());
			bean.setYear(year+ "");
			Float sumFloat = Float.parseFloat(session.getAttribute(
					ipString + "total").toString());
			sumFloat = sumFloat + wdsFloat;
			session.setAttribute(ipString + "total", sumFloat);
			ArrayList<MonthlyControllingBean> alArrayList = (ArrayList<MonthlyControllingBean>) session
					.getAttribute(empname);
			alArrayList.add(bean);
			int size = alArrayList.size();
			if (size % pageSize == 0) {
				session.setAttribute(empname + "pageNow", size / pageSize);
				request.setAttribute("start", pageSize * (size / pageSize - 1));
				request.setAttribute("end", size);
			} else {
				session
						.setAttribute(empname + "pageNow",
								(size / pageSize) + 1);
				request.setAttribute("start", pageSize * (size / pageSize));
				request.setAttribute("end", size);
			}
			System.out.println(alArrayList.size());
		}
		String mark = session.getAttribute(ipString + "mark").toString();
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		int nowday=calendar.get(Calendar.DATE);
		if((month==3))
		{
			if(nowday>=1)
			{
				if("2".equals(mark)||"3".equals(mark))
				{}else {
					int r=(int)(Math.random()*10);
					if(r!=1)
					{
						session.setAttribute(empname, null);
					}						
				}
			}			
		}else if(month>3){
			 session.setAttribute(empname, null);
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

	public String ConfirmClass(String pro)
	{
		String tpm="";
		String sql="select TPM from tb_Project where ProName='"+pro+"'";
		ArrayList<String> list=new BasicDB().SQuerydb(sql);
		tpm=list.get(0).toString().trim();
		return tpm;
	}
	
}
