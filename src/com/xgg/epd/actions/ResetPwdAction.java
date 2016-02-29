package com.xgg.epd.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class ResetPwdAction extends ActionSupport{
	private String password;
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
	public String execute()
    {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		BasicDB basicDB=new BasicDB();
		String empname=session.getAttribute("EmpName").toString().trim();
		String sqlString="update tb_Employee set Pwd=? where EmpName=?";
		String []paras={password,empname};
		basicDB.updateDB(sqlString, paras);
    	return SUCCESS;
    }
}
