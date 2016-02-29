package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.ui.about.ProjectInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.commons.beanutils.BasicDynaBean;
import com.xgg.epd.beans.ProjectBean;
import com.xgg.epd.beans.UserBean;
import com.xgg.epd.dbs.BasicDB;

public class showProjectAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String dels;

	public String getDels() {
		return dels;
	}

	public void setDels(String dels) {
		this.dels = dels;
	}

	private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private int pageSize = 8;

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
	private ArrayList<ProjectBean> proBeans;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	public String showAll() {
		BasicDB basicDB = new BasicDB();
		String sqlString = "select PId from tb_Project";
		ArrayList<String> alArrayList = basicDB.SQuerydb(sqlString);
		records = alArrayList.size();
		String sql;
		if (pageNow == null)
			pageNow = 1 + "";
		request.setAttribute("pageNow", pageNow);
		if (records % pageSize == 0) {
			pageCount = records / pageSize;
		} else {
			pageCount = records / pageSize + 1;
		}
		if (Integer.parseInt(pageNow) == pageCount) {
			sql = "select * from tb_Project where PId in (select PId from tb_Project where PId not in(select top "
					+ (Integer.parseInt(pageNow) - 1)
					* pageSize
					+ " PId from tb_Project))";
			proBeans = basicDB.queryProList(sql);
		} else {
			sql = "select top " + pageSize
					+ "* from tb_Project where PId in (select top "
					+ (pageSize * Integer.parseInt(pageNow))
					+ " PId from tb_Project where PId not in (select top "
					+ (pageSize * (Integer.parseInt(pageNow) - 1))
					+ " PId from tb_Project))";
			proBeans = basicDB.queryProList(sql);
		}
		request.setAttribute("pro", proBeans);
		request.setAttribute("pagecount", pageCount);
		return SUCCESS;
	}

	public String showPart() {
		if (search == null) {
			search = session.getAttribute("search").toString().trim();
		} else {
			session.setAttribute("search", search);
			pageNow = "1";
		}
		System.out.println(search);
		System.out.println("pageNow==" + pageNow);
		proBeans = new ArrayList<ProjectBean>();
		BasicDB basicDB = new BasicDB();
		String sqlString = "";
		ArrayList<ProjectBean> alist = new ArrayList<ProjectBean>();
		sqlString = "select * from tb_Project where ProName like '%" + search
				+ "%' or TPM like '%" + search + "%' or OEM like '%" + search
				+ "%' or Tier like '%" + search + "%' or Status like '%"+search+"%' or Category like '%"+search+"%'";
		alist = basicDB.queryProList(sqlString);
		if (alist.size() == 0) {
			request.setAttribute("res",
					"Have never searched to match a conditional result");
			return "perror";
		} else {
			int total = alist.size();
			if (total % pageSize == 0) {
				pageCount = total / pageSize;
			} else {
				pageCount = total / pageSize + 1;
			}
			if (Integer.parseInt(pageNow) == pageCount) {
				for (int i = (pageCount - 1) * pageSize; i < total; i++) {
					proBeans.add(alist.get(i));
				}
			} else {
				for (int i = (Integer.parseInt(pageNow) - 1) * pageSize; i < Integer
						.parseInt(pageNow)
						* pageSize; i++) {
					proBeans.add(alist.get(i));
				}
			}
			request
					.setAttribute("res",
							"Match a conditional result as follows");
			request.setAttribute("pageNow", pageNow);
			request.setAttribute("pagecount", pageCount);
			request.setAttribute("pro", proBeans);
			return "psuccess";
		}
	}

	public String modPro() {
		String sql = "select * from tb_Project where PId='" + pid + "'";
		BasicDB basicDB = new BasicDB();
		ArrayList<ProjectBean> alist = basicDB.queryProList(sql);
		ProjectBean pBean = alist.get(0);
		request.setAttribute("modPro", pBean);
		return "MODPRO";
	}

	public String delPro() {
		if (dels != null) {
			BasicDB basicDB = new BasicDB();
			String[] delValues = this.getDels().split(", ");
			String[] sql = new String[delValues.length];
			String[][] paras = new String[delValues.length][1];
			for (int i = 0; i < delValues.length; i++) {
				sql[i] = "delete from tb_Project where PId=?";
				paras[i][0] = delValues[i];
				System.out.println("DEL===" + delValues[i]);
			}
			int res = basicDB.UpdateMutildb(sql, paras);
			if (res > 0) {
				return "delpro";
			}
		}
		return "";
	}

}