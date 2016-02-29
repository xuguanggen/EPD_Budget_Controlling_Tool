package com.xgg.epd.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;
/*
 * Author:Guanggen Xu
 * Function:Add one new Employee
 */
public class RegisterAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empid;
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	private String empname;
	private String section;
	private String email;
	private String password;
	private String mark;
	private String Asection;
	public String getAsection() {
		return Asection;
	}
	public void setAsection(String asection) {
		Asection = asection;
	}
	HttpServletRequest request=ServletActionContext.getRequest();
	public String addEmp()
	{
		System.out.println("add Employee");
		BasicDB basicDB=new BasicDB();
		String sqlString="insert into tb_Employee values(?,?,?,?,?,?,?)";
		String []paras={empid,empname,section,email,password,mark,Asection};
		basicDB.updateDB(sqlString, paras);
		if("".equals(Asection))
		{
			request.setAttribute("murole", "NO");
		}else {
			request.setAttribute("murole",Asection);
		}
		return SUCCESS;
	}
	
	public String modEmp()
	{
		BasicDB basicDB=new BasicDB();
		int dmark=Integer.parseInt(mark);
		String sqlString="UPDATE tb_Employee SET EmpName = ?,Section=?,Email=?,Pwd=?,Asection=?,mark='"+dmark+"' WHERE EId =?";
		String []paras={empname,section,email,password,Asection,empid};
		int res=basicDB.updateDB(sqlString, paras);
		if(res==1)
		{
			return "mod_emp_success";
		}
		return "";
	}
}
