package com.xgg.epd.actions;
/*Date：2015-01-20
 * Author： Xu Guanggen
 * Function：
 * 1. Deal with records that submitted by users; (Del, Add,Modtify)
 * 2. Get the rate of every year;* 
 * 
 */
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.data.time.Month;
import org.objectweb.asm.tree.IntInsnNode;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.commons.beanutils.BasicDynaBean;
import com.xgg.epd.beans.DeadlineBean;
import com.xgg.epd.beans.MonthlyControllingBean;
import com.xgg.epd.dbs.BasicDB;

public class ManageRecordAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String delid;


	public String getDelid() {
		return delid;
	}

	public void setDelid(String delid) {
		this.delid = delid;
	}
	public String id;
	public String days;
	public String time;
	HttpSession session = ServletActionContext.getRequest().getSession();
	HttpServletRequest request=ServletActionContext.getRequest();
	String ip=request.getRemoteAddr().toString();
	String enameString = session.getAttribute(ip+"EmpName").toString();

	public String del() {
		ArrayList<MonthlyControllingBean> alArrayList = (ArrayList<MonthlyControllingBean>) session
				.getAttribute(enameString);
		if (delid != null) {
			System.out.println(delid);
			 String[] delValues = this.getDelid().split(", ");
			 Float sumFloat=Float.parseFloat(session.getAttribute(ip+"total").toString());
			 for(int i=delValues.length-1;i>=0;i--)
			 {
				int index=Integer.parseInt(delValues[i].toString());
				Float wdsFloat=alArrayList.get(Integer.parseInt(delValues[i].toString())).getWorkingdays();
				sumFloat=sumFloat-wdsFloat;
				alArrayList.remove(index);
			 }
			 session.setAttribute(ip+"total", sumFloat);			 
		}
		String markString=session.getAttribute(ip+"mark").toString();
		if("1".equals(markString))
		{
			return "tpmdelsuccess";
		}else if("2".equals(markString)||"3".equals(markString))
		{
			return "admindelsuccess";
		}else if("0".equals(markString))
		{
			return "empdelsuccess";
		}
		return "";
	}
	public static int ShowSize=9;
	public int showNow=1;
	public int showCount;
	public String insert() {
		ArrayList<MonthlyControllingBean>alArrayList=(ArrayList<MonthlyControllingBean>)session.getAttribute(enameString);
		String [][]tparas=new String[alArrayList.size()][7];
		String fillmonth="";
		float days=0;
		int factCount=0;
		for(int i=0;i<alArrayList.size();i++)
		{
			MonthlyControllingBean bean=alArrayList.get(i);
			if(bean.getWorkingdays()!=0)
			{
				tparas[factCount][0]=bean.getEmpname();
				tparas[factCount][1]=bean.getSection();
				tparas[factCount][2]=bean.getMonth();
				tparas[factCount][3]=bean.getProname();
				tparas[factCount][4]=bean.getService();
				tparas[factCount][5]=bean.getRemark();
				tparas[factCount][6]=bean.getWorkingdays()+"";
				days=days+Float.parseFloat(tparas[factCount][6]);
				fillmonth=bean.getMonth();
				factCount++;
			}		
		}
		String [][]paras=new String[factCount][7];
		for(int i=0;i<factCount;i++)
		{
			if("Admin".equals(new FillAction().ConfirmClass(tparas[i][3]))||"none".equals(new FillAction().ConfirmClass(tparas[i][3])))
			{
				tparas[i][4]="000General";
			}
			for(int j=0;j<7;j++)
			{
				paras[i][j]=tparas[i][j];
			}
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH)+1;
		int date=c.get(Calendar.DATE);
		if(month==1&&date<=DeadlineBean.getEndDate())
		{
			year=year-1;
		}
		double year_rate=getRate(year+"");
		String enameString=session.getAttribute(ip+"EmpName").toString().trim();
		BasicDB basicDB=new BasicDB();
		//////////////////////////////////////////////////////////////////////////////////////////
		//插入到tb_Summary 表中
		/////////////////////////////////////////////////////////////////////////////////////////
		for(int i=0;i<paras.length;i++)
		{
			String pro_sql="select M_T_Expense from tb_Summary where Year='"+year+"' and Month='"+fillmonth+"' and ProName='"+paras[i][3]+"'";			
			ArrayList<String>pro_exsit_List=basicDB.SQuerydb(pro_sql);
			if(pro_exsit_List!=null&&pro_exsit_List.size()!=0)
			{				
				int beforeValue=(int)(Double.parseDouble(pro_exsit_List.get(0).toString().trim()));
				int afterValue=(int)(year_rate*8*Double.parseDouble(paras[i][6])+beforeValue);
				String update_sql="update tb_Summary set M_T_Expense=? where Year=? and Month=? and ProName=?";
				String []update_paras={afterValue+"",year+"",fillmonth+"",paras[i][3]};
				int result=basicDB.updateDB(update_sql, update_paras);
			}else 
			{			
				int insertValue=(int)(Double.parseDouble(paras[i][6])*8*year_rate);
				//search tpm no
				String search_tpmName_sql="select TPM from tb_Project where ProName='"+paras[i][3]+"'";
				ArrayList<String> tpmNameList=basicDB.SQuerydb(search_tpmName_sql);
				String tpmName=tpmNameList.get(0).toString().trim();
				System.out.println(tpmName);
				if("none".equals(tpmName))
				{
					String insert_sql="insert into tb_Summary values(?,?,'none',?,0,0,?,0)";
					String []insert_paras={paras[i][3],fillmonth,insertValue+"",year+""};
					basicDB.updateDB(insert_sql, insert_paras);
				}else if("Admin".equals(tpmName)) 
				{
					String insert_sql="insert into tb_Summary values(?,?,'Admin',?,0,0,?,0)";
					String []insert_paras={paras[i][3],fillmonth,insertValue+"",year+""};
					basicDB.updateDB(insert_sql, insert_paras);
				}					
			   else
				{
					System.out.println("enter");
					String search_tpmNo_sql="select EId from tb_Employee where EmpName='"+tpmName+"'";
					ArrayList<String>tpmNoList=basicDB.SQuerydb(search_tpmNo_sql);
					System.out.println(tpmNoList.size());
					String tpmNo=tpmNoList.get(0);				
					String insert_sql="insert into tb_Summary values(?,?,?,?,0,0,?,0)";
					String []insert_paras={paras[i][3],fillmonth,tpmNo,insertValue+"",year+""};
					basicDB.updateDB(insert_sql, insert_paras);
				}
			}
		}
		String querysqlString="select totalDays from tb_PersonControlling where Year=? and Month=? and EmpName=?";
		String []query_parasStrings={year+"",fillmonth,enameString};			
		if(basicDB.queryDB(querysqlString, query_parasStrings)==null||(basicDB.queryDB(querysqlString, query_parasStrings)).size()==0)
		{
			String isql="insert into tb_PersonControlling values(?,?,?,?)";
			String []iparas={year+"",fillmonth,enameString,days+""};
			basicDB.updateDB(isql, iparas);
		}else {			
			ArrayList<Object[]>alList=basicDB.queryDB(querysqlString, query_parasStrings);
			Object[]objects=alList.get(0);
			float summitedDays=Float.parseFloat(objects[0].toString().trim());
			float nowDays=summitedDays+days;
			String uSql="update tb_PersonControlling set totalDays=? where Year=? and Month=? and EmpName=?";
			String []uparas={nowDays+"",year+"",fillmonth,enameString};
			basicDB.updateDB(uSql, uparas);
		}
		String sqlString="insert into tb_MonthlyControlling values(?,?,?,?,?,?,?,'"+year+"')";
		int res=basicDB.UpdateMutildb(sqlString, paras);
		String markString=session.getAttribute(ip+"mark").toString();
		ArrayList<MonthlyControllingBean>totalList=getCurrentMcbs(year+"", fillmonth, enameString);
		ArrayList<MonthlyControllingBean>showList=new ArrayList<MonthlyControllingBean>();
		int size=totalList.size();
		if(size<=ShowSize)
		{
			for(int i=0;i<totalList.size();i++)
				showList.add(totalList.get(i));
		}else {
			for(int i=0;i<ShowSize;i++)
				showList.add(totalList.get(i));
		}
		if(size%ShowSize==0)
		{
			showCount=size/ShowSize;
		}else {
			showCount=size/ShowSize+1;
		}
		session.setAttribute(ip+"result", totalList);
		session.setAttribute(ip+"show", showList);
		session.setAttribute(ip+"showCount", showCount);
		session.setAttribute(enameString, null);
		System.out.println("show size"+showList.size());
		if("1".equals(markString))
		{
			return "tpmInsert";
		}else if("2".equals(markString)||"3".equals(markString))
		{
			return "adminInsert";
		}else if("0".equals(markString))
		{
			return "empInsert";
		}
		return "";
	}

	public String modify() {
		HttpServletRequest request = ServletActionContext.getRequest();
		ArrayList<MonthlyControllingBean> aList = (ArrayList<MonthlyControllingBean>) session
				.getAttribute(enameString);
		MonthlyControllingBean bean = aList.get(Integer.parseInt(id));
		request.setAttribute("modbean", bean);
		session.setAttribute("modID", Integer.parseInt(id));
		String markString=session.getAttribute(ip+"mark").toString().trim();
		float totals=Float.parseFloat(session.getAttribute(ip+"total").toString().trim())-bean.getWorkingdays();
		session.setAttribute(ip+"total", totals);
		if("1".equals(markString))
		{
			return "modtpm";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "modadmin";
		}
		if("0".equals(markString))
		{
			return "modemp";
		}
		return "";
	}

	public String AddAgain()
	{
		String markString=session.getAttribute(ip+"mark").toString().trim();
		HttpServletRequest request=ServletActionContext.getRequest();
		ArrayList<MonthlyControllingBean>alist=(ArrayList<MonthlyControllingBean>)session.getAttribute(enameString);
		int mid=Integer.parseInt(id);
		MonthlyControllingBean mcBean=alist.get(mid);
		float beforeDays=mcBean.getWorkingdays();
		float nowDays=(float) (beforeDays+0.5);
		mcBean.setWorkingdays(nowDays);
		float totals=(float) (Float.parseFloat(session.getAttribute(ip+"total").toString().trim())+0.5);
		session.setAttribute(ip+"total", totals);
		if("1".equals(markString))
		{
			return "soloAddTPM";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "soloAddadmin";
		}
		if("0".equals(markString))
		{
			return "soloAddemp";
		}
		return "";
	}
	public String ReduceAgain()
	{
		String markString=session.getAttribute(ip+"mark").toString().trim();
		ArrayList<MonthlyControllingBean>alist=(ArrayList<MonthlyControllingBean>)session.getAttribute(enameString);
		int mid=Integer.parseInt(id);
		MonthlyControllingBean mcBean=alist.get(mid);
		float beforeDays=mcBean.getWorkingdays();
		float nowDays=(float) (beforeDays-0.5);
		mcBean.setWorkingdays(nowDays);
		float totals=(float) (Float.parseFloat(session.getAttribute(ip+"total").toString().trim())-0.5);
		session.setAttribute(ip+"total", totals);
		if("1".equals(markString))
		{
			return "soloReduceTPM";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "soloReduceadmin";
		}
		if("0".equals(markString))
		{
			return "soloReduceemp";
		}
		return "";
	}
	public String ChangeDays()
	{
		String markString=session.getAttribute(ip+"mark").toString().trim();
		id=id.replace("s", "");
		ArrayList<MonthlyControllingBean>alist=(ArrayList<MonthlyControllingBean>)session.getAttribute(enameString);
		MonthlyControllingBean bean=alist.get(Integer.parseInt(id));
		float beforedays=bean.getWorkingdays();
		float nowdays=Float.parseFloat(days);
		bean.setWorkingdays(nowdays);
		float totals=Float.parseFloat(session.getAttribute(ip+"total").toString().trim())-beforedays+nowdays;
		session.setAttribute(ip+"total", totals);
		System.out.println("sumDays:="+totals);
		if("1".equals(markString))
		{
			return "tpmChange";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "adminChange";
		}
		if("0".equals(markString))
		{
			return "empChange";
		}
		return "";
	}
	public static int pageSize=9;
	public int pageNow=1;
	public String showRecords()
	{
		String markString=session.getAttribute(ip+"mark").toString().trim();
		String[] strInfo=time.split("-");
		String year=strInfo[0];
		String month=strInfo[1];
		if(month.contains("0")&&!"10".equals(month))
		{
			month=month.replace("0", "");
		}
		String sql="select * from tb_MonthlyControlling where Year=? and Month=? and EmpName=?";
		String []paras={year,month,enameString};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>aList=new ArrayList<Object[]>();
		aList=basicDB.queryDB(sql, paras);
		int size=aList.size();
		int pageCount=1;
		if(size%pageSize==0)
			pageCount=size/pageSize;
		else {
			pageCount=(size/pageSize)+1;
		}
		if(size<=pageSize)
		{
			session.setAttribute(ip+"history", aList);
		}else
		{
			ArrayList<Object[]> sList=new ArrayList<Object[]>();
			for(int i=0;i<pageSize;i++)
			{
				sList.add(aList.get(i));
			}
			session.setAttribute(ip+"history", sList);			
		}
		session.setAttribute(ip+"pageCount", pageCount);
		session.setAttribute(ip+"time", time);		
		if("1".equals(markString))
		{
			return "tpmShow";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "adminShow";
		}
		if("0".equals(markString))
		{
			return "empShow";
		}
		return "";
	}
	public String showByPage()
	{
		ArrayList<Object[]>sList=new ArrayList<Object[]>();
		String markString=session.getAttribute(ip+"mark").toString().trim();
		String timeString=session.getAttribute(ip+"time").toString().trim();
		String []info=timeString.split("-");
		String year=info[0];
		String month=info[1];
		if(month.contains("0")&&!"10".equals(month))
		{
			month=month.replace("0", "");
		}
		String sql="select * from tb_MonthlyControlling where Year=? and Month=? and EmpName=?";
		String []paras={year,month,enameString};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>aList=new ArrayList<Object[]>();
		aList=basicDB.queryDB(sql, paras);
		int size=aList.size();
		int pageCount=0;
		if(size%pageSize==0)
			pageCount=size/pageSize;
		else {
			pageCount=size/pageSize+1;
		}
		session.setAttribute(ip+"pageCount", pageCount);
		if(pageCount==pageNow)
		{
			for(int i=(pageCount-1)*pageSize;i<size;i++)
			{
				sList.add(aList.get(i));
			}
		}else {
			for(int i=(pageNow-1)*pageSize;i<pageSize*pageNow;i++)
			{
				sList.add(aList.get(i));
			}
		}
		session.setAttribute(ip+"history", sList);
		if("1".equals(markString))
		{
			return "tpmPage";
		}
		if("2".equals(markString)||"3".equals(markString))
		{
			return "adminPage";
		}
		if("0".equals(markString))
		{
			return "empPage";
		}
		return "";
	}
	
	ArrayList<MonthlyControllingBean> getCurrentMcbs(String currentyear,String currentmonth,String name)
	{
		ArrayList<MonthlyControllingBean>alArrayList=new ArrayList<MonthlyControllingBean>();
		String sql="select * from tb_MonthlyControlling where Year=? and Month=? and EmpName=?";
		String []paras={currentyear,currentmonth,name};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>aObjects=basicDB.queryDB(sql, paras);
		for(int i=0;i<aObjects.size();i++)
		{
			Object[]tempObject=aObjects.get(i);
			MonthlyControllingBean mcBean=new MonthlyControllingBean();
			mcBean.setEmpname(tempObject[1].toString().trim());
			mcBean.setSection(tempObject[2].toString().trim());
			mcBean.setMonth(tempObject[3].toString().trim());
			mcBean.setProname(tempObject[4].toString().trim());
			mcBean.setService(tempObject[5].toString().trim());
			mcBean.setRemark(tempObject[6].toString().trim());
			mcBean.setWorkingdays(Float.parseFloat(tempObject[7].toString().trim()));
			mcBean.setYear(tempObject[8].toString().trim());
			alArrayList.add(mcBean);
		}
		return alArrayList;
	}
	
	public String ShowResultByPage()
	{
		String markString=session.getAttribute(ip+"mark").toString().trim();
		ArrayList<MonthlyControllingBean>toBeans=(ArrayList<MonthlyControllingBean>)(session.getAttribute(ip+"result"));
		showCount=Integer.parseInt(session.getAttribute(ip+"showCount").toString().trim());
		System.out.println("Now=:"+showNow);
		System.out.println("tobean' Size="+toBeans.size());
		ArrayList<MonthlyControllingBean>showList=new ArrayList<MonthlyControllingBean>();
		if(showNow==showCount)
		{
			for(int i=(showNow-1)*ShowSize;i<toBeans.size();i++)
			{
				showList.add(toBeans.get(i));
			}
		}else {
			for(int i=(showNow-1)*ShowSize;i<showNow*ShowSize;i++)
			{
				showList.add(toBeans.get(i));
			}
		}
		session.setAttribute(ip+"show", showList);
		if("0".equals(markString))
		{
			return "empRes";
		}else if("1".equals(markString))
		{
			return "tpmRes";
		}else if("2".equals(markString)||"3".equals(markString))
		{
			return "adminRes";
		}
		return "";
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
