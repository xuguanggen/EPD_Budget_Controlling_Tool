package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import jsx3.net.Request;

import com.xgg.epd.dbs.*;
import com.opensymphony.xwork2.ActionSupport;

public class EmailAction extends ActionSupport {
	/**
	 * Function: Send emails to these whose days <= workingDays
	 */
	private static final long serialVersionUID = 1L;
	private String d;

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String year = "";
	public String month = "";
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	public String execute() {
		String[] time = d.split("-");
		year = time[0];
		month = time[1];
		if (month.contains("0") && !"10".equals(month)) {
			month = month.replace("0", "");
		}
		BasicDB basicDB = new BasicDB();
		String daysSql = "select Days from tb_Calendar where Year='" + year
				+ "' and Month='" + month + "'";
		ArrayList<String> alArrayList = basicDB.SQuerydb(daysSql);
		int workingDays = Integer.parseInt(alArrayList.get(0));
		System.out.println(year + "-" + month + "-WoringDays=" + workingDays);
		String sql = "select tb_PersonControlling.EmpName,tb_Employee.Email from tb_PersonControlling,tb_Employee where Year=? and Month=? and totalDays<? and tb_PersonControlling.EmpName=tb_Employee.EmpName";
		String[] paras = { year, month, workingDays + "" };
		ArrayList<Object[]> Small = basicDB.queryDB(sql, paras);
		String sql_copy = "select tb_Employee.EmpName,tb_Employee.Email from tb_Employee where mark<=3 and EmpName not in(select  EmpName from tb_PersonControlling where Year=? and Month=?)";
		String[] paras_copy = { year, month };
		ArrayList<Object[]> Zero = basicDB
				.queryDB(sql_copy, paras_copy);
		ArrayList<String> emplist = new ArrayList<String>();
		ArrayList<String> emailList = new ArrayList<String>();
		ArrayList<String> workList=new ArrayList<String>();
		if (alArrayList != null) {
			for (int i = 0; i < Small.size(); i++) {
				Object[] objects = Small.get(i);
				String nameString = objects[0].toString().trim();
				String email = objects[1].toString().trim();
				System.out.println(nameString + "--" + email);
				emplist.add(nameString);
				emailList.add(email);				
			}
			for(int i=0;i<emplist.size();i++)
			{
				String empName=emplist.get(i).trim();
				String sqlString="select totalDays from tb_PersonControlling where Year='"+year+"' and Month='"+month+"' and EmpName='"+empName+"'";
				ArrayList<String> tmp=basicDB.SQuerydb(sqlString);
				workList.add(tmp.get(0));
			}
		}
		for (int i = 0; i < Zero.size(); i++) {
			Object[] objects = Zero.get(i);
			String nameString = objects[0].toString().trim();
			String email = objects[1].toString().trim();
			System.out.println(nameString + "--" + email);
			emplist.add(nameString);
			emailList.add(email);
			workList.add(0+"");
		}
		request.setAttribute("query_month", year+"-"+month);
		request.setAttribute("warnEmplist", emplist);
		request.setAttribute("warnEmailList", emailList);
		request.setAttribute("workList", workList);
		request.setAttribute("workDays", workingDays);
		return SUCCESS;
	}
}
