package com.xgg.epd.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class CheckOldPwdAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oldpwd;

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String execute()
	{
		System.out.println("OldPassWord=="+oldpwd);
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		PrintWriter out=null;
		BasicDB basicDB=new BasicDB();
		String empnameString=session.getAttribute("EmpName").toString().trim();
		String sqlString="select * from tb_Employee where EmpName=? and Pwd=?";
		String []paras={empnameString,oldpwd};
		try {
			out=response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object[]>alList=basicDB.queryDB(sqlString, paras);
		System.out.println(alList.size());
		if(alList.size()==1)
		{
			out.print("true");
		}else
		{
			out.print("false");
		}
		return null;
	}

}
