package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.objectweb.asm.tree.IntInsnNode;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class TopTenAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpSession session=request.getSession();
	String ip=request.getRemoteAddr().toString();
	public String year;
	public String BaseTopTen()
	{
		ArrayList<String>pArrayList=new ArrayList<String>();
		BasicDB basicDB=new BasicDB();
		String sql="select top 10 ProName from tb_ProBudget where Year='"+year+"'  order by budget desc";
		pArrayList=basicDB.SQuerydb(sql);
		if(pArrayList==null||pArrayList.size()==0)
		{
			pArrayList.add("Null");
		}
		session.setAttribute(ip+"basetenYear", year);
		session.setAttribute(ip+"basetenPro", pArrayList);
		return "basetopten";
	}
	
	public String CostTopTen()
	{
		ArrayList<String> topArrayList=new ArrayList<String>();
		ArrayList<Object[]>cList=new ArrayList<Object[]>();
		String sql="select ProName,sum(M_T_Expense),sum(M_A_Expense),sum(M_R_Expense)from tb_Summary where Year=? and TPM<>'none' and TPM<>'Admin' group by ProName";
		String []paras={year};
		BasicDB basicDB=new BasicDB();
		cList=basicDB.queryDB(sql, paras);
		if(cList==null||cList.size()==0)
		{
			topArrayList.add("Null");
			session.setAttribute(ip+"costtenYear", year);
			session.setAttribute(ip+"costtenPro", topArrayList);
			return "costtopten";
		}
		Map<String, Double> hMap=new HashMap<String, Double>();
		int i=0;
		for(i=0;i<cList.size();i++)
		{
			Object[]objects=cList.get(i);
			String pnameString=objects[0].toString().trim();
			Double tepd=Double.parseDouble(objects[1].toString().trim());
			Double tae=Double.parseDouble(objects[2].toString().trim());
			Double trbei=Double.parseDouble(objects[3].toString().trim());
			Double sumDouble=tepd+tae+trbei;
			hMap.put(pnameString, sumDouble);
		}
		List<Map.Entry<String,Double>> list_Data=new ArrayList<Map.Entry<String,Double>>(hMap.entrySet());
		Collections.sort(list_Data,
				new Comparator<Map.Entry<String, Double>>() {
					public int compare(Map.Entry<String, Double> o1,
							Map.Entry<String, Double> o2) {
						if ((o2.getValue() - o1.getValue())>0)  
					          return 1;  
					        else if((o2.getValue() - o1.getValue())==0)  
					          return 0;  
					        else   
					          return -1;  
					}
				});
		for(i=0;i<10;i++)
		{
			topArrayList.add(list_Data.get(i).getKey());
		}
		session.setAttribute(ip+"costtenYear", year);
		session.setAttribute(ip+"costtenPro", topArrayList);
		return "costtopten";
	}
	public String PercentTopTen()
	{
		List<String>pArrayList=OverMost();
		if(pArrayList==null||pArrayList.size()==0)
		{
			pArrayList=new ArrayList<String>();
			pArrayList.add("Null");
		}
		session.setAttribute(ip+"percenttenPro", pArrayList);
		session.setAttribute(ip+"percenttenYear", year);
		return "percenttopten";
	}
	public List<String> OverMost() {
		ArrayList<String> topproject = new ArrayList<String>();
		ArrayList<String> allproject = new ArrayList<String>();
		Map<String, Double> hm = new HashMap<String, Double>();
		String sql = "select distinct proName from tb_Summary where Year='"+year+"' and TPM<>'none' and TPM<>'Admin'";
		BasicDB basicDB = new BasicDB();
		allproject = basicDB.SQuerydb(sql);
		int currentYear=Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
		int countMonth;
		if(currentYear==Integer.parseInt(year))
			countMonth=currentMonth;
		else 
			countMonth=12;
		if(allproject!=null&&allproject.size()!=0){
			for (int i = 0; i < allproject.size(); i++) {
				String proString = allproject.get(i).trim();
				String sqlString = "select sum(M_T_Expense),sum(M_A_Expense),sum(M_R_Expense) from tb_Summary where proName=? and Year=?";
				String[] paras = { proString,year};
				ArrayList<Object[]> CostList = basicDB.queryDB(sqlString, paras);
				int totalCost=0;
				int eCost=0;
				int aCost=0;
				int rCost=0;
				
				int totalBudget=0;
				int eBudget=0;
				int aBudget=0;
				int rBudget=0;
				ArrayList<Object[]>budgetList=new ArrayList<Object[]>();
				if(CostList==null||CostList.size()==0)
				{
					String budget_sql="select Budget,EPD_Budget,AE_Budget,RBEI_Budget from tb_ProBudget where proName=? and Year=?";
					budgetList=basicDB.queryDB(budget_sql, paras);
					if(budgetList==null||budgetList.size()==0)
					{
						totalBudget=0;
						eBudget=0;
						aBudget=0;
						rBudget=0;
						hm.put(proString, 0.0);
					}else {
						Object[]budgetObject=budgetList.get(0);
						totalBudget=Integer.parseInt(budgetObject[0].toString().trim())*countMonth/12;
						hm.put(proString, -1.0);
					}
				}else
				{
					Object[]costObject=CostList.get(0);
					eCost=(int)Double.parseDouble(costObject[0].toString().trim());
					aCost=(int)Double.parseDouble(costObject[1].toString().trim());
					rCost=(int)Double.parseDouble(costObject[2].toString().trim());
					totalCost=eCost+aCost+rCost;
					String budget_sql="select Budget,EPD_Budget,AE_Budget,RBEI_Budget from tb_ProBudget where proName=? and Year=?";
					budgetList=basicDB.queryDB(budget_sql, paras);
					if(budgetList==null||budgetList.size()==0)
					{
						totalBudget=0;
						eBudget=0;
						aBudget=0;
						rBudget=0;
						hm.put(proString, 100.0);
					}else {
						Object[]budgetObject=budgetList.get(0);
						totalBudget=Integer.parseInt(budgetObject[0].toString().trim())*countMonth/12;
						hm.put(proString, 1.0*(totalCost-totalBudget)/totalBudget);
					}
				}
			}
			List<Map.Entry<String,Double>> list_Data=new ArrayList<Map.Entry<String,Double>>(hm.entrySet());
			Collections.sort(list_Data,
					new Comparator<Map.Entry<String, Double>>() {
						public int compare(Map.Entry<String, Double> o1,
								Map.Entry<String, Double> o2) {
							if ((o2.getValue() - o1.getValue())>0)  
						          return 1;  
						        else if((o2.getValue() - o1.getValue())==0)  
						          return 0;  
						        else   
						          return -1;  
						}
					});
			for(int i=0;i<10;i++)
			{
				topproject.add(list_Data.get(i).getKey());
			}
		}else {
			return null;
		}
		return topproject;
	}

}
