package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.DeadlineBean;
import com.xgg.epd.dbs.BasicDB;

public class UserLoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String perID;
	public String getPerID() {
		return perID;
	}
	public void setPerID(String perID) {
		this.perID = perID;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	private String pwd;
	private String loginType;
	public String execute()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session=ServletActionContext.getRequest().getSession();
		BasicDB basicDb=new BasicDB();
		String sql="select * from tb_Employee where EId=? and Pwd=?";
		String []paras={perID,pwd};
		ArrayList<Object[]> al=basicDb.queryDB(sql, paras);
		if(al.size()==1)
		{	
			String ipString=request.getRemoteAddr().toString();
			Cookie cookie=new Cookie(ipString+"perID", perID);
			Cookie cookie2=new Cookie(ipString+"pwd",pwd);
			cookie.setMaxAge(24*3600*30);
			cookie2.setMaxAge(24*3600*30);
			response.addCookie(cookie);
			response.addCookie(cookie2);
			Calendar ca = Calendar.getInstance();
			int todayDate=ca.get(Calendar.DATE);
			int startDate=DeadlineBean.getStartDate();
			int endDate=DeadlineBean.getEndDate();
		    if(Calendar.AM==ca.get(Calendar.AM_PM))
		    {
		    	session.setAttribute("time", "Good Morning, ");
		    }else if(Calendar.PM==ca.get(Calendar.AM_PM)){
				session.setAttribute("time", "Good Afternoon, ");
			}
			Object[] obj=(Object[])al.get(0);
			session.setAttribute(ipString+"EmpName", obj[1].toString());
			session.setAttribute(ipString+"Sec", obj[2].toString());
			session.setAttribute(ipString+"mark", obj[5].toString());
			session.setAttribute(ipString+"asec",obj[6].toString().trim());
			session.setAttribute(ipString+"eid",perID.trim());
			if((obj[5].toString()).equals("0"))
			{
				return "associate";
			
			}else if((obj[5].toString()).equals("1"))
			{
				return "tpm";
			}else if((obj[5].toString()).equals("2")||(obj[5].toString()).equals("3"))
			{
				return "admin";
			}else if(obj[5].toString().equals("4"))
			{
				return "guest";
			}
		}else {
			System.out.println("ERROR");
			request.setAttribute("inf1", "name or psw maybe wrong!");
			return INPUT;
		}
		return "";
	}
}
