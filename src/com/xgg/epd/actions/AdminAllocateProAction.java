package com.xgg.epd.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class AdminAllocateProAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	private String eid;
	private String ename;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpSession session=request.getSession();
	String ip=request.getRemoteAddr().toString().trim();
	public String execute()
	{
		session.setAttribute(ip+"allid", eid);
		session.setAttribute(ip+"allname", ename);
		request.setAttribute("proList", getProjectList());
		return SUCCESS;
	}
	
	public List<Map.Entry<String, String>> getProjectList()
	{
		Map<String, String>hMap=new HashMap<String, String>();
		String sql="select TPM,ProName from tb_Project where 1=?";
		String[]paras={"1"};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>alist=basicDB.queryDB(sql, paras);
		for(int i=0;i<alist.size();i++)
		{
			Object[]object=alist.get(i);
			String tpm=object[0].toString().trim();
			String pro=object[1].toString().trim();
			if(!("Admin".equals(tpm)||"none".equals(tpm)||"Li John".equals(tpm)))
			{
				hMap.put(pro, tpm);
			}
		}
		List<Map.Entry<String, String>> infoIds =
		    new ArrayList<Map.Entry<String, String>>(hMap.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {   
		    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {     	     
		        return (o1.getValue()).toString().compareTo(o2.getValue());
		    }
		}); 
		return infoIds;
	}

	
	public String pro;
	
	public String allocatePro()
	{
		String allname=session.getAttribute(ip+"allname").toString().trim();
		String []pros=pro.split(",");
		BasicDB basicDB=new BasicDB();
		for(int i=0;i<pros.length;i++)
		{
			String sqlString="insert into tb_GuestPro values(?,?)";
			String []paras={allname,pros[i]};
			basicDB.updateDB(sqlString, paras);
		}
		return "allocateSuccess";
	}
}
