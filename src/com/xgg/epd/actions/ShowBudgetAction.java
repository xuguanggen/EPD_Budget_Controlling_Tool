package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.ProBudgetBean;
import com.xgg.epd.dbs.BasicDB;

public class ShowBudgetAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String year;
	public String getEbudget() {
		return ebudget;
	}
	public void setEbudget(String ebudget) {
		this.ebudget = ebudget;
	}
	public String getAbudget() {
		return abudget;
	}
	public void setAbudget(String abudget) {
		this.abudget = abudget;
	}
	public String getRbudget() {
		return rbudget;
	}
	public void setRbudget(String rbudget) {
		this.rbudget = rbudget;
	}
	private String ebudget;
	private String abudget;
	private String rbudget;
	private String budget;
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	private String proName;
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	HttpServletRequest request=ServletActionContext.getRequest();

	Calendar calendar=Calendar.getInstance();
	int currentYear=calendar.get(Calendar.YEAR);
	public String execute()
	{
		request.setAttribute("proBudget", proName);
		String budget_sql="select Year,ProName,EPD_Budget,AE_Budget,RBEI_Budget,Budget from tb_ProBudget where Year>=? and ProName=?";
		String []budget_paras={currentYear+"",proName};
		ArrayList<Object[]>budgetList=new BasicDB().queryDB(budget_sql, budget_paras);
		ArrayList<ProBudgetBean>proBudgetBeans=new ArrayList<ProBudgetBean>();
		if(budgetList!=null&&budgetList.size()>=0)
		{
			for(int i=0;i<budgetList.size();i++)
			{
				Object[] objects=budgetList.get(i);
				ProBudgetBean bean=new ProBudgetBean();
				String year=objects[0].toString().trim();
				int eBudget=Integer.parseInt(objects[2].toString().trim());
				int aBudget=Integer.parseInt(objects[3].toString().trim());
				int rBudget=Integer.parseInt(objects[4].toString().trim());
				bean.setYear(year);
				bean.setProName(proName);
				bean.setEPD_Budget(eBudget);
				bean.setAE_Budget(aBudget);
				bean.setRBEI_Budget(rBudget);
				proBudgetBeans.add(bean);
			}
			request.setAttribute("BudgetList", proBudgetBeans);
		}
		return SUCCESS;
	}

	public String AddBudget()
	{
		request.setAttribute("proBudget", proName);
		String insert_sql="insert into tb_ProBudget values(?,?,?,?,?,?)";
		String[] insert_paras={proName,year,budget,ebudget,abudget,rbudget};
		new BasicDB().updateDB(insert_sql, insert_paras);
		String budget_sql="select Year,ProName,EPD_Budget,AE_Budget,RBEI_Budget,Budget from tb_ProBudget where Year>=? and ProName=?";
		String []budget_paras={currentYear+"",proName};
		ArrayList<Object[]>budgetList=new BasicDB().queryDB(budget_sql, budget_paras);
		ArrayList<ProBudgetBean>proBudgetBeans=new ArrayList<ProBudgetBean>();
		if(budgetList!=null&&budgetList.size()>=0)
		{
			for(int i=0;i<budgetList.size();i++)
			{
				Object[] objects=budgetList.get(i);
				ProBudgetBean bean=new ProBudgetBean();
				String year=objects[0].toString().trim();
				int eBudget=Integer.parseInt(objects[2].toString().trim());
				int aBudget=Integer.parseInt(objects[3].toString().trim());
				int rBudget=Integer.parseInt(objects[4].toString().trim());
				bean.setYear(year);
				bean.setProName(proName);
				bean.setEPD_Budget(eBudget);
				bean.setAE_Budget(aBudget);
				bean.setRBEI_Budget(rBudget);
				proBudgetBeans.add(bean);
			}
			request.setAttribute("BudgetList", proBudgetBeans);
		}
		return "addbudget_success";
	}
	public void ChangeBudget()
	{
		System.out.println(year);
		System.out.println(proName);
		System.out.println(budget+"="+ebudget+"+"+abudget+"+"+rbudget);
		String sql="update tb_ProBudget set Budget=?,EPD_Budget=?,AE_Budget=?,RBEI_Budget=? where Year=? and ProName=?";
		String []paras={budget,ebudget,abudget,rbudget,year,proName};
		BasicDB basicDB=new BasicDB();
		basicDB.updateDB(sql, paras);
	}
}
