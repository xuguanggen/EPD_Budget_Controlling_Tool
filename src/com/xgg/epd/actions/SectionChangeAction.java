package com.xgg.epd.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class SectionChangeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	
	private String empname;
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
	private String section;
	public String createOption()throws ServletException,IOException{
		String section=request.getParameter("section").toString().trim();
		StringBuffer empnameBuffer=new StringBuffer("<empnames>");
		List<String> list=empnameInit(section);
		session.setAttribute("empList", list);
		 for(int i=0;i<list.size();i++) {
				 empnameBuffer.append("<empname>"+list.get(i)+"</empname>");
	         }            
		empnameBuffer.append("</empnames>");
		PrintWriter out = response.getWriter();
		out.print(empnameBuffer.toString());
		out.flush();
		out.close();
		return "createoptionsuccess";
	}
	public String createServices()throws ServletException,IOException
	{
	    section=request.getParameter("section").toString().trim();
		StringBuffer serviceBuffer=new StringBuffer("<Services>");
		List<String> list=serviceInit(section);
		//request.setAttribute("serviceList", list);
		for(int i=0;i<list.size();i++)
		{
			System.out.println("==="+list.get(i)+"========");
			serviceBuffer.append("<service>"+list.get(i)+"</service>");
		}
		serviceBuffer.append("</Services>");
		PrintWriter out=response.getWriter();
		out.print(serviceBuffer.toString());
		out.flush();
		out.close();
		return "createservices";
	}
	public List<String> empnameInit(String section) {
		List<Object[]> pList = new ArrayList<Object[]>();
		BasicDB basicDB = new BasicDB();
		String sql = "select EmpName from tb_Employee where Section=?";
		String []paras={section};
		pList=basicDB.queryDB(sql, paras);
		List<String> list=new ArrayList<String>();
		for(int i=0;i<pList.size();i++)
		{
			Object []objects=pList.get(i);
			list.add(objects[0].toString().trim());
		}
		return list;
	}
	
	
	public List<String> serviceInit(String section) {
		List<Object[]> pList = new ArrayList<Object[]>();
		BasicDB basicDB = new BasicDB();
		String sql = "select ServiceNr from tb_SerOfSec where Section=?  or Section='000 General'";
		String []paras={section};
		pList=basicDB.queryDB(sql, paras);
		List<String> list=new ArrayList<String>();
		for(int i=0;i<pList.size();i++)
		{
			Object []objects=pList.get(i);
			list.add(objects[0].toString().trim());
		}
		return list;
	}
	
	
	public String showServiceNr()
	{
		System.out.println(section);
		String sql="select Section,ServiceNr from tb_SerOfSec where Section=?";
		String []paras={section};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]> alist=basicDB.queryDB(sql, paras);
		ArrayList<String>serviceList=new ArrayList<String>();
		for(int i=0;i<alist.size();i++)
		{
			Object[]objects=alist.get(i);
			String s=objects[1].toString().trim();
			serviceList.add(s);
		}
		request.setAttribute("section",section);
		request.setAttribute("serviceList",serviceList);
		return "showService";
	}
}
