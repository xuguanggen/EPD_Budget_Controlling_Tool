package com.xgg.epd.utils;

import java.util.ArrayList;
import java.util.Calendar;

import com.xgg.epd.dbs.BasicDB;

public class Common {
	public static String proType;
	public static String[] status = { "Planed", "Running", "SOP" };
	public static String[] proInfo = { "PID", "ProjectName", "Category", "TPM",
			"In_BP", "EPD_Budget","AE_Budget","RBEI_Budget","Status", "OEM", "Customer", "Remark" };
	public static String[] categoryType = { "Big Adapt", "Small Adapt",
			"Pilot", "Minor Change", "Minor Task" };

	public static int[] getBudget(String proName) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int []budget=new int[3];
		String sql = "select TPM,Status from tb_Project where ProName=?";
		String[] paras = { proName };
		BasicDB basicDB = new BasicDB();
		ArrayList<Object[]> alList = basicDB.queryDB(sql, paras);
		String tpm = alList.get(0)[0].toString().trim();
		String status = alList.get(0)[1].toString().trim();
		if ("none".equals(tpm) || "SOP".equals(status) || "Admin".equals(tpm)) // 如果没有ＴＰＭ或者项目已经ＳＯＰ了，就显示０　
		{
			budget[0] = 0;
			budget[1]=0;
			budget[2]=0;
		} else {
			String findBudget_sql = "select EPD_Budget,AE_Budget,RBEI_Budget from tb_ProBudget where ProName=? and Year=?";
			String[] findBudget_paras = { proName, year + "" };
			ArrayList<Object[]> alist = basicDB.queryDB(findBudget_sql,
					findBudget_paras);
			if (alist != null && alist.size() != 0) {
				Object[] objects = alist.get(0);
				budget[0] = Integer.parseInt(objects[0].toString().trim());
				budget[1] = Integer.parseInt(objects[1].toString().trim());
				budget[2] = Integer.parseInt(objects[2].toString().trim());
			} else {
				budget[0] = 0;
				budget[1]=0;
				budget[2]=0;
			}
		}
		return budget;
	}
	
	
	
	public static String getEmpNameByNo(String no)
	{
		String empName="";
		String sql="select EmpName from tb_Employee where EId='"+no+"'";
		ArrayList<String>list=new BasicDB().SQuerydb(sql);
		empName=list.get(0).toString().trim();
		return empName;
	}
	
}
