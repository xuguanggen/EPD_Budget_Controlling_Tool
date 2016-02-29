package com.xgg.epd.actions;
import com.xgg.epd.utils.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.CommonDataSource;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class TpmChangeAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	String[] row = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };
	private String proType;
	private String tpmname;
	String ip=request.getRemoteAddr().toString().trim();
	String eid=session.getAttribute(ip+"eid").toString().trim();
	public String getTpmname() {
		return tpmname;
	}
	public void setTpmname(String tpmname) {
		this.tpmname = tpmname.trim();
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType.trim();
	}
	private String year;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String createOption()throws ServletException,IOException{
		//response.setContentType(CONTENT_TYPE);
		String tpmname = request.getParameter("tpmname").toString().trim();
		StringBuffer projectBuffer = new StringBuffer("<proTypes>");
		List<String> list = projectInit(tpmname);
		 for(int i=0;i<list.size();i++) {
			// System.out.println("==="+list.get(i)+"========");
			 projectBuffer.append("<proType>"+list.get(i)+"</proType>");
         }            
		projectBuffer.append("</proTypes>");
		PrintWriter out = response.getWriter();
		out.print(projectBuffer.toString());
		out.flush();
		out.close();
		return "createoptionsuccess";
	}
	public List<String> projectInit(String tpmname) {
		List<Object[]> pList = new ArrayList<Object[]>();
		BasicDB basicDB = new BasicDB();
		String sql = "select distinct tb_Summary.ProName from tb_Summary where TPM=? and Year=?";
		String []paras={tpmname,year};
		pList=basicDB.queryDB(sql, paras);
		List<String> list=new ArrayList<String>();
		if("70818773".equals(tpmname))
		{
			list.add("RBEI");
			return list;
		}
		if(pList!=null&&pList.size()!=0)
		{
			for(int i=0;i<pList.size();i++)
			{
				Object[]object=pList.get(i);
				list.add(object[0].toString().trim());
			}
		}else
		{
			list.add("NULL");
		}
		return list;
	}
	
	public String createProByYear()throws ServletException,IOException
	{
		session.setAttribute(ip + "tpmchartYear", year);
		return "createProOfYear";
	}
	
	
	public String projectInitByTPM() throws ServletException,IOException{
		String tpmname = request.getParameter("tpmname").toString().trim();
		System.out.println(tpmname);
		StringBuffer projectBuffer = new StringBuffer("<proTypes>");
		List<String> list = projectOnlyByTPM(tpmname);
		 for(int i=0;i<list.size();i++) {
			// System.out.println("==="+list.get(i)+"========");
			 projectBuffer.append("<proType>"+list.get(i)+"</proType>");
         }            
		projectBuffer.append("</proTypes>");
		PrintWriter out = response.getWriter();
		out.print(projectBuffer.toString());
		out.flush();
		out.close();
		return "pieChart";
	}
	
	
	public List<String> projectOnlyByTPM(String tpmname) {
		BasicDB basicDB = new BasicDB();
		ArrayList<String>pList=new ArrayList<String>();
		String sql = "select distinct tb_Summary.ProName from tb_Summary where TPM='"+tpmname+"' and Year>=2015";
		pList=basicDB.SQuerydb(sql);
		if(!(pList!=null&&pList.size()!=0))
		{
			pList.add("NULL");
		}
		return pList;
	}
}
