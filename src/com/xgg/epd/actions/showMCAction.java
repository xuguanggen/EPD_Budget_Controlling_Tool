package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.data.time.Month;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.IXOR;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.regexp.internal.recompile;
import com.xgg.epd.beans.MonthlyControllingBean;
import com.xgg.epd.dbs.BasicDB;

public class showMCAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empname;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	private String month;
	private String section;

	public String getPageNow() {
		return pageNow;
	}

	public void setPageNow(String pageNow) {
		this.pageNow = pageNow;
	}

	private String pageNow;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public static int pageSize = 9;
	public int pageCount = 0;

	public String execute() {
		if (request.getAttribute("sendName") != null) {
			empname = request.getAttribute("sendName").toString().trim();
			section = request.getAttribute("sendSection").toString().trim();
			month = request.getAttribute("sendMonth").toString().trim();
			System.out.println(empname + "=" + section + "=" + month);
		}
		request.setAttribute("ModName", empname);
		request.setAttribute("ModSection", section);
		request.setAttribute("ModMonth", month);
		ArrayList<MonthlyControllingBean> al = new ArrayList<MonthlyControllingBean>();
		al = init();
		ArrayList<MonthlyControllingBean> currentList = new ArrayList<MonthlyControllingBean>();
		int records = al.size();
		if (records > pageSize) {
			request.setAttribute("pageCount", pageCount);
			for (int i = 0; i < pageSize; i++) {
				currentList.add(al.get(i));
			}
		} else {
			for (int i = 0; i < records; i++) {
				currentList.add(al.get(i));
			}
		}
		request.setAttribute("current", currentList);
		return "ok";
	}

	private String dels;

	public String getDels() {
		return dels;
	}

	public void setDels(String dels) {
		this.dels = dels;
	}
	Calendar calendar=Calendar.getInstance();
	int year=calendar.get(Calendar.YEAR);
	public String showPart() {
		empname = empname.replace("@", " ");
		request.setAttribute("ModName", empname);
		request.setAttribute("ModSection", section);
		request.setAttribute("ModMonth", month);
		int spageNow =0;
		if(pageNow==null){
			spageNow=1;
		}
		else {
			spageNow=Integer.parseInt(pageNow);
		}
		ArrayList<MonthlyControllingBean> toBeans=totalBeans(month, empname);
		int size=toBeans.size();
		int showCount;
		if(size%pageSize==0)
		{
			showCount=size/pageSize;
		}else
			showCount=size/pageSize+1;
		ArrayList<MonthlyControllingBean> currentList = new ArrayList<MonthlyControllingBean>();
		if (spageNow ==showCount) {
			for(int i=(spageNow-1)*pageSize;i<size;i++)
			{
				currentList.add(toBeans.get(i));
			}
		} else {
			for(int i=(spageNow-1)*pageSize;i<spageNow*pageSize;i++)
			{
				currentList.add(toBeans.get(i));
			}
		}
		request.setAttribute("current", currentList);
		request.setAttribute("pageCount", showCount);
		return "ok";
	}

	public int ComPageCount() {
		BasicDB basicDB = new BasicDB();
		String sqlString = "select EmpName,Section,Month,ProName,ServiceNr,Remark,WorkingDays,MId from tb_MonthlyControlling where EmpName=? and Month=? and Year=?";
		String[] paras = { empname, month ,year+""};
		ArrayList<Object[]> objList = basicDB.queryDB(sqlString, paras);
		int records = objList.size();
		if (records > pageSize) {
			if (records % pageSize == 0) {
				pageCount = records / pageSize;
			} else {
				pageCount = records / pageSize + 1;
			}
			return pageCount;
		}
		return 0;
	}

	public ArrayList<MonthlyControllingBean> init() {
		ArrayList<MonthlyControllingBean> al = new ArrayList<MonthlyControllingBean>();
		BasicDB basicDB = new BasicDB();
		String sqlString = "select EmpName,Section,Month,ProName,ServiceNr,Remark,WorkingDays,MId from tb_MonthlyControlling where EmpName=? and Month=? and Year=?";
		String[] paras = { empname, month,year+""};
		ArrayList<Object[]> objList = basicDB.queryDB(sqlString, paras);
		for (int i = 0; i < objList.size(); i++) {
			Object obj[] = objList.get(i);
			MonthlyControllingBean mcBean = new MonthlyControllingBean();
			mcBean.setYear(year+"");
			mcBean.setEmpname(obj[0].toString().trim());
			mcBean.setSection(obj[1].toString().trim());
			mcBean.setMonth(obj[2].toString().trim());
			mcBean.setProname(obj[3].toString().trim());
			mcBean.setService(obj[4].toString().trim());
			mcBean.setRemark(obj[5].toString().trim());
			mcBean.setWorkingdays(Float.parseFloat(obj[6].toString().trim()));
			mcBean.setMid(Integer.parseInt(obj[7].toString().trim()));
			al.add(mcBean);
		}
		int records = al.size();
		if (records > pageSize) {
			if (records % pageSize == 0) {
				pageCount = records / pageSize;
			} else {
				pageCount = records / pageSize + 1;
			}
		}
		return al;
	}

	public ArrayList<MonthlyControllingBean> totalBeans(String month,String empname) {
		ArrayList<MonthlyControllingBean> al = new ArrayList<MonthlyControllingBean>();
		BasicDB basicDB = new BasicDB();
		String sqlString = "select EmpName,Section,Month,ProName,ServiceNr,Remark,WorkingDays,MId from tb_MonthlyControlling where EmpName=? and Month=? and Year=?";
		String[] paras = { empname, month,year+""};
		ArrayList<Object[]> objList = basicDB.queryDB(sqlString, paras);
		for (int i = 0; i < objList.size(); i++) {
			Object obj[] = objList.get(i);
			MonthlyControllingBean mcBean = new MonthlyControllingBean();
			mcBean.setYear(year+"");
			mcBean.setEmpname(obj[0].toString().trim());
			mcBean.setSection(obj[1].toString().trim());
			mcBean.setMonth(obj[2].toString().trim());
			mcBean.setProname(obj[3].toString().trim());
			mcBean.setService(obj[4].toString().trim());
			mcBean.setRemark(obj[5].toString().trim());
			mcBean.setWorkingdays(Float.parseFloat(obj[6].toString().trim()));
			mcBean.setMid(Integer.parseInt(obj[7].toString().trim()));
			al.add(mcBean);
		}
		int records = al.size();
		if (records > pageSize) {
			if (records % pageSize == 0) {
				pageCount = records / pageSize;
			} else {
				pageCount = records / pageSize + 1;
			}
		}
		return al;
	}

	private String mid;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String modRecord() {
		BasicDB basicDB = new BasicDB();
		String sql = "select * from tb_MonthlyControlling where MId=?";
		String[] paras = { mid };
		ArrayList<Object[]> aList = basicDB.queryDB(sql, paras);
		Object[] objects = aList.get(0);
		MonthlyControllingBean mcBean = new MonthlyControllingBean();
		mcBean.setMid(Integer.parseInt(mid));
		mcBean.setEmpname(objects[1].toString().trim());
		mcBean.setSection(objects[2].toString().trim());
		mcBean.setMonth(objects[3].toString().trim());
		mcBean.setProname(objects[4].toString().trim());
		mcBean.setService(objects[5].toString().trim());
		mcBean.setRemark(objects[6].toString().trim());
		mcBean.setWorkingdays(Float.parseFloat(objects[7].toString().trim()));
		session.setAttribute("modMC", mcBean);
		String query_asec="select Asection from tb_Employee where EmpName='"+objects[1].toString().trim()+"'";
		ArrayList<String>al=basicDB.SQuerydb(query_asec);
		String empSectionString = objects[2].toString().trim();
		List<String> list = new ArrayList<String>();
		list = basicDB
				.SQuerydb("select ServiceNr from tb_SerOfSec where Section='"
						+ empSectionString + "' or Section='000General' or Section='"+al.get(0)+"'");
		request.setAttribute("serviceList", list);
		return "modOK";
	}

	public String delRecord() {
		// System.out.println("ENTER LE");
		request.setAttribute("sendName", empname);
		request.setAttribute("sendSection", section);
		request.setAttribute("sendMonth", month);
		System.out.println(dels);
		BasicDB basicDB = new BasicDB();
		String[] paras = dels.split(", ");
		for(int i=0;i<paras.length;i++)
		{
			String search_sql="select Year,Month,ProName,WorkingDays from tb_MonthlyControlling where MId=?";
			String []search_paras={paras[i]};
			ArrayList<Object[]>aList=basicDB.queryDB(search_sql, search_paras);
			Object[] mcbObjects=aList.get(0);
			String year=mcbObjects[0].toString().trim();
			String month=mcbObjects[1].toString().trim();
			String proName=mcbObjects[2].toString().trim();
			double wds=Double.parseDouble(mcbObjects[3].toString().trim());
			int reduceValue=(int)(getRate(year)*wds*8);
			
			String before_sql="select M_T_Expense from tb_Summary where Year='"+year+"' and Month='"+month+"' and ProName='"+proName+"'";
			ArrayList<String>beList=basicDB.SQuerydb(before_sql);
			int afterValue=(int)(Double.parseDouble(beList.get(0).toString().trim())-reduceValue);
			
			String update_sql="update tb_Summary set M_T_Expense=? where Year=? and Month=? and ProName=?";
			String[]update_paras={afterValue+"",year,month,proName};
			basicDB.updateDB(update_sql, update_paras);
		}
		float totalDeldays = 0;
		for (int i = 0; i < paras.length; i++) {
			String sqlString = "select WorkingDays from tb_MonthlyControlling where MId='"
					+ paras[i] + "'";
			ArrayList<String> aList = basicDB.SQuerydb(sqlString);
			totalDeldays = totalDeldays + Float.parseFloat(aList.get(0));
		}
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String pcsql = "select totalDays from tb_PersonControlling where EmpName='"
				+ empname
				+ "' and Year='"
				+ year
				+ "' and Month='"
				+ month
				+ "'";
		ArrayList<String> arrayList = basicDB.SQuerydb(pcsql);
		float factDays = Float.parseFloat(arrayList.get(0)) - totalDeldays;
		String usql = "update tb_PersonControlling set totalDays=? where Year=? and Month=? and EmpName=?";
		String[] uparas = { factDays + "", year + "", month, empname };
		basicDB.updateDB(usql, uparas);
		for (int i = 0; i < paras.length; i++) {
			String sqlString = "delete from tb_MonthlyControlling where MId=?";
			String[] p = { paras[i] };
			basicDB.updateDB(sqlString, p);
		}
		return "delok";
	}
	public double getRate(String year)
	{
		double rate=0;
		String rate_sql="select rate from tb_Rate where year='"+year+"'";
		BasicDB basicDB=new BasicDB();
		ArrayList<String>rateList=basicDB.SQuerydb(rate_sql);
		rate=Double.parseDouble(rateList.get(0).toString().trim());
		return rate;
	}
}
