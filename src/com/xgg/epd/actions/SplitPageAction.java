package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.MonthlyControllingBean;

public class SplitPageAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String page;
	HttpServletRequest request=ServletActionContext.getRequest();
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public static int pageSize=FillAction.pageSize;
	public String execute()
	{
		HttpSession session=request.getSession();
		String ip=request.getRemoteAddr().toString();
		String empnameString=session.getAttribute(ip+"EmpName").toString();
		if(page!=null)
		{
			session.setAttribute(empnameString+"pageNow", page);
		}else {
			page=session.getAttribute(empnameString+"pageNow").toString().trim();
		}
		int pageNum=Integer.parseInt(page);
		ArrayList<MonthlyControllingBean> alArrayList=(ArrayList<MonthlyControllingBean>)session.getAttribute(empnameString);
		int pageCount;
		int size=alArrayList.size();
		if(size%(pageSize)==0)
		{
			pageCount=size/pageSize;
		}else {
			pageCount=(size/pageSize)+1;
		}
		if(pageCount==pageNum)
		{
			request.setAttribute("end", alArrayList.size());
		}else {
			request.setAttribute("end", pageNum*pageSize);
		}
		request.setAttribute("start", pageSize*(pageNum-1));
		String markString=session.getAttribute(ip+"mark").toString();
		if("1".equals(markString))
		{
			return "tpmfillsuccess";
		}else if("2".equals(markString)||"3".equals(markString)) {
			return "adminfillsuccess";
		}else if("0".equals(markString))
		{
			return "empfillsuccess";
		}
		return "";
	}
}
