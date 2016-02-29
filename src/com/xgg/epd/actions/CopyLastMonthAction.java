package com.xgg.epd.actions;
/*
 * Author:2015-1-20
 * Date :Xu Guanggen
 * Function: Get what you submitted last month ,except for some have been SOP * 
 */
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.DeadlineBean;
import com.xgg.epd.beans.MonthlyControllingBean;
import com.xgg.epd.dbs.BasicDB;

public class CopyLastMonthAction extends ActionSupport {

	/**
	 * 
	 */
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	String ipString = request.getRemoteAddr().toString();
	public static int pageSize = 50;
	private static final long serialVersionUID = 1L;

	public String execute() {
		String empname = session.getAttribute(ipString + "EmpName").toString();
		ArrayList<MonthlyControllingBean> lastMcBeans = getLastMonth(empname);
		if (lastMcBeans != null && lastMcBeans.size() != 0) {
			if (session.getAttribute(empname) == null
					|| (session.getAttribute(empname) != null && ((ArrayList<MonthlyControllingBean>) session
							.getAttribute(empname)).size() == 0)) {
				ArrayList<MonthlyControllingBean> aList = new ArrayList<MonthlyControllingBean>();
				float wdsFloat = 0;
				for (int i = 0; i < lastMcBeans.size(); i++) {
					aList.add(lastMcBeans.get(i));
					wdsFloat = wdsFloat + lastMcBeans.get(i).getWorkingdays();
				}
				session.setAttribute(ipString + "total", wdsFloat);
				session.setAttribute(empname + "pageNow", 1);
				request.setAttribute("start", 0);
				request.setAttribute("end", aList.size());
				session.setAttribute(empname, aList);
			} else {
				ArrayList<MonthlyControllingBean> alArrayList = (ArrayList<MonthlyControllingBean>) session
						.getAttribute(empname);
				Float sumFloat = Float.parseFloat(session.getAttribute(
						ipString + "total").toString());
				for (int i = 0; i < lastMcBeans.size(); i++) {
					alArrayList.add(lastMcBeans.get(i));
					sumFloat = sumFloat + lastMcBeans.get(i).getWorkingdays();
				}
				session.setAttribute(ipString + "total", sumFloat);
				int size = alArrayList.size();
				if (size % pageSize == 0) {
					session.setAttribute(empname + "pageNow", size / pageSize);
					request.setAttribute("start", pageSize
							* (size / pageSize - 1));
					request.setAttribute("end", size);
				} else {
					session.setAttribute(empname + "pageNow",
							(size / pageSize) + 1);
					request.setAttribute("start", pageSize * (size / pageSize));
					request.setAttribute("end", size);
				}
			}
		}
		String mark = session.getAttribute(ipString + "mark").toString();
		if ("1".equals(mark)) {
			return "tpmcopysuccess";
		} else if ("2".equals(mark)||"3".equals(mark)) {
			return "admincopysuccess";
		} else if ("0".equals(mark)) {
			return "empcopysuccess";
		}
		return "";
	}

	public ArrayList<MonthlyControllingBean> getLastMonth(String empName) {
		ArrayList<MonthlyControllingBean> lastMonthReList = new ArrayList<MonthlyControllingBean>();
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDate = calendar.get(Calendar.DATE);
		int fillYear = currentYear;
		int fillMonth = currentMonth;
		if (currentMonth == 1 && currentDate <= DeadlineBean.getEndDate()) {
			fillYear = currentYear - 1;
			fillMonth = 12;
		}
		if (currentDate >= DeadlineBean.getStartDate()) {
			fillYear = currentYear;
			fillMonth = currentMonth;
		} else if (currentDate <= DeadlineBean.getEndDate()
				&& currentMonth != 1) {
			fillMonth = currentMonth - 1;
			fillYear = currentYear;
		}
		System.out.println("fill month:" + fillYear + "-" + fillMonth);
		int lastyear = 0;
		int lastmonth = 0;
		if (currentMonth == 1 && currentDate <= DeadlineBean.getEndDate()) {
			lastmonth = 11;
			lastyear = currentYear - 1;
		} else if (currentMonth == 1
				&& currentDate >= DeadlineBean.getStartDate()) {
			lastmonth = 12;
			lastyear = currentYear - 1;
		} else if (currentMonth == 2
				&& currentDate <= DeadlineBean.getEndDate()) {
			lastmonth = 12;
			lastyear = currentYear - 1;
		} else if (currentDate >= DeadlineBean.getStartDate()
				&& currentMonth >= 2) {
			lastmonth = currentMonth - 1;
			lastyear = currentYear;
		} else if (currentDate <= DeadlineBean.getEndDate()
				&& currentMonth >= 3) {
			lastmonth = currentMonth - 2;
			lastyear = currentYear;
		}
		System.out.println(lastyear+"-"+lastmonth);
		String sql = "select EmpName,Section,Month,ProName,ServiceNr,Remark,WorkingDays,Year from tb_MonthlyControlling where Year=? and Month=? and EmpName=?";
		String[] paras = { lastyear + "", lastmonth + "", empName };
		ArrayList<Object[]> alObjects = new BasicDB().queryDB(sql, paras);
		if (alObjects != null && alObjects.size() != 0) {
			for (int i = 0; i < alObjects.size(); i++) {
				Object[] tempObject = alObjects.get(i);
				if (!"SOP".equals(getState(tempObject[3].toString().trim()))) {
					MonthlyControllingBean mcBean = new MonthlyControllingBean();
					mcBean.setEmpname(empName);
					mcBean.setMonth(fillMonth + "");
					mcBean.setYear(fillYear + "");
					mcBean.setProname(tempObject[3].toString().trim());
					mcBean.setService(tempObject[4].toString().trim());
					mcBean.setSection(tempObject[1].toString().trim());
					mcBean.setRemark(tempObject[5].toString().trim());
					mcBean.setWorkingdays(0);
					lastMonthReList.add(mcBean);
				}
			}
		}
		return lastMonthReList;
	}

	public String getState(String pro) {
		String currentState = "";
		String sql = "select Status from tb_Project where ProName='" + pro
				+ "'";
		BasicDB basicDB = new BasicDB();
		currentState = basicDB.SQuerydb(sql).get(0).toString().trim();
		System.out.println(pro+"-"+currentState);
		return currentState;
	}
}
