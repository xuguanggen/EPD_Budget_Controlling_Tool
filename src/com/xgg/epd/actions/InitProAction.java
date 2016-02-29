package com.xgg.epd.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class InitProAction extends ActionSupport {
	/**
	 * Function : Confirm the project that should be to show to users to fill
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response = ServletActionContext.getResponse();
	public String execute()throws ServletException,IOException
	{
		BasicDB basicDB=new BasicDB();
		String sql="select distinct ProName from tb_Project where Status='Running'";
		ArrayList<String> list=basicDB.SQuerydb(sql);
		String resultString="";
		for(int i=0;i<list.size();i++)
		{
			resultString=resultString+list.get(i).toString().trim()+",";
		}
		resultString=resultString.substring(0, resultString.length()-1);
		PrintWriter out=response.getWriter();
		out.print(resultString);
		out.flush();
		out.close();
		return SUCCESS;
	}
	private String h;
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	HttpServletRequest request=ServletActionContext.getRequest();
	public String xx()
	{		
		System.out.println(h);
		return SUCCESS;
	}
}
