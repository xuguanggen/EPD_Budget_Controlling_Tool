package com.xgg.epd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;
/*
 * Author:Guanggen XU
 * Date:2015-01-28
 * Function:Add Project module
 * 
 */
public class AddProjectAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pname;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getTpm() {
		return tpm;
	}
	public void setTpm(String tpm) {
		this.tpm = tpm;
	}
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getOem() {
		return oem;
	}
	public void setOem(String oem) {
		this.oem = oem;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String tpm;
	private String bp;
	private String ebudget;
	private String abudget;
	private String rbudget;
	private String status;
	private String customer;
	private String oem;
	private String remark;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	HttpServletRequest request=ServletActionContext.getRequest();
	public String addPro()
	{
		System.out.println("ADD Project");
		int febudget=Integer.parseInt(ebudget);
		int fabudget=Integer.parseInt(abudget);
		int frbudget=Integer.parseInt(rbudget);
		int budget=febudget+fabudget+frbudget;
		request.setAttribute("budget", budget);
		String sql="insert into tb_Project(ProName,TPM,In_Bp,Status,OEM,Tier,Remark,Category) values(?,?,?,?,?,?,?,?)";
		String []paras={pname,tpm,bp,status,oem,customer,remark,category};
		
		String budget_sql="insert into tb_ProBudget values(?,?,?,?,?,?)";
		String []budget_paras={pname,year,budget+"",febudget+"",fabudget+"",frbudget+""};
		BasicDB basicDB=new BasicDB();
		int res=basicDB.updateDB(sql, paras);
		basicDB.updateDB(budget_sql, budget_paras);
		System.out.println("result="+res);
		return SUCCESS;
	}
	
	public String modPro()
	{
		System.out.println("public String modPro()");
		System.out.println("After mod="+pname);
		BasicDB basicDB=new BasicDB();
		String sqlString="UPDATE tb_Project SET Category=?,TPM = ?,In_Bp=?,Status=?,OEM=?,Tier=? ,Remark=? WHERE ProName =?";
		String []paras={category,tpm,bp,status,oem,customer,remark,pname};
		int res=basicDB.updateDB(sqlString, paras);
		
		String searchTpmNO_sql="select EId from tb_Employee where EmpName='"+tpm+"'";
		ArrayList<String>tpmList=basicDB.SQuerydb(searchTpmNO_sql);
		if(tpmList!=null&&tpmList.size()!=0)
		{
			String tpmNo=tpmList.get(0).toString().trim();			
			String update_sql="update tb_Summary set TPM=? where ProName=?";
			String []update_paras={tpmNo,pname};
			basicDB.updateDB(update_sql, update_paras);
		}
		if(res==1)
		{
			return "mod_pro_success";
		}
		return "";
	}

}
