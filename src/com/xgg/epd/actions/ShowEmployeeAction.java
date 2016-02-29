package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import sun.misc.Request;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.UserBean;
import com.xgg.epd.dbs.BasicDB;

public class ShowEmployeeAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dels;

	public String getDels() {
		return dels;
	}

	public void setDels(String dels) {
		this.dels = dels;
	}

	private String eid;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	private int pageSize = 8;
	private String idorname;

	public String getIdorname() {
		return idorname;
	}

	public void setIdorname(String idorname) {
		this.idorname = idorname;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	private String search;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private String pageNow;

	public String getPageNow() {
		return pageNow;
	}

	public void setPageNow(String pageNow) {
		this.pageNow = pageNow;
	}

	private int pageCount;
	private int records;
	private ArrayList<UserBean> userBeans;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	public String showAll() {
		BasicDB basicDB = new BasicDB();
		String sqlString = "select EId from tb_Employee";
		ArrayList<String> alArrayList = basicDB.SQuerydb(sqlString);
		records = alArrayList.size();
		String sql;
		if(pageNow==null)
			pageNow=1+"";
		request.setAttribute("pageNow", pageNow);
		if (records % pageSize == 0) {
			pageCount = records / pageSize;
		} else {
			pageCount = records / pageSize + 1;
		}
		if (Integer.parseInt(pageNow) == pageCount) {
			sql = "select * from tb_Employee where EId in (select EId from tb_Employee where EId not in(select top "
					+ (Integer.parseInt(pageNow) - 1)
					* pageSize
					+ " EId from tb_Employee))";
			userBeans = basicDB.spliltquery(sql);
		} else {
			sql = "select top " + pageSize
					+ "* from tb_Employee where EId in (select top "
					+ (pageSize * Integer.parseInt(pageNow))
					+ " EId from tb_Employee where EId not in (select top "
					+ (pageSize * (Integer.parseInt(pageNow) - 1))
					+ " EId from tb_Employee))";
			userBeans = basicDB.spliltquery(sql);
		}
		request.setAttribute("emp", userBeans);
		request.setAttribute("pagecount", pageCount);
		return SUCCESS;
	}

	public String showPart() {
		if(search==null)
		{
			search=session.getAttribute("search").toString().trim();
			idorname="Name";
		}else {
			session.setAttribute("search", search);
			pageNow="1";
		}
		System.out.println(search);
		//System.out.println("pageNow=="+pageNow);
		userBeans=new ArrayList<UserBean>();
		BasicDB basicDB = new BasicDB();
		String sqlString = "";
		ArrayList<UserBean> alist = new ArrayList<UserBean>();
		//sqlString = "select * from tb_Employee where EId='" + search + "'"
		//		+ "or EmpName like '%" + search + "%'";
		sqlString="select * from tb_Employee where EId ='"+search+"' or EmpName like '%"+search+"%'";
		alist = basicDB.spliltquery(sqlString);
		if (alist.size() == 0) {
			request.setAttribute("res",
					"Have never searched to match a conditional result");
			return "perror";
		} else {
			int total=alist.size();
			if(total%pageSize==0)
			{
				pageCount=total/pageSize;
			}else {
				pageCount=total/pageSize+1;
			}
			if (Integer.parseInt(pageNow) == pageCount) {
				for(int i=(pageCount-1)*pageSize;i<total;i++)
				{
					userBeans.add(alist.get(i));
				}
			} else {
				for(int i=(Integer.parseInt(pageNow)-1)*pageSize;i<Integer.parseInt(pageNow)*pageSize;i++)
				{
					userBeans.add(alist.get(i));
				}
			}
			request.setAttribute("res",
					"Match a conditional result as follows");
			request.setAttribute("pageNow", pageNow);
			request.setAttribute("pagecount", pageCount);
			request.setAttribute("emp", userBeans);
			return "psuccess";
		}
	}

	public String modEmp() {
		String sqlString = "select * from tb_Employee where EId='" + eid + "'";
		BasicDB basicDB = new BasicDB();
		ArrayList<UserBean> alist = basicDB.spliltquery(sqlString);
		UserBean userBean = alist.get(0);
		request.setAttribute("modemp", userBean);
		return "MODEMP";
	}

	public String delEmp() {
		if (dels != null) {
			BasicDB basicDB = new BasicDB();
			String[] delValues = this.getDels().split(", ");
			String[] sql = new String[delValues.length];
			String[][] paras = new String[delValues.length][1];
			for (int i = 0; i < delValues.length; i++) {
				sql[i] = "delete from tb_Employee where EId=?";
				paras[i][0] = delValues[i];
				System.out.println("DEL===" + delValues[i]);
			}
			int res = basicDB.UpdateMutildb(sql, paras);
			if (res > 0) {
				return "delemp";
			}
		}
		return "";
	}
}
