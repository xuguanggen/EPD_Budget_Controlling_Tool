package com.xgg.epd.dao;

import java.util.ArrayList;
import java.util.List;

import com.xgg.epd.dbs.BasicDB;

public class FindProject {
	private List projectlist;

	public List getProjectlist() {
		return projectlist;
	}

	public void setProjectlist(List projectlist) {
		this.projectlist = projectlist;
	}

	public List getproject(String tpmname) {
		String sql="select ProName from tb_Summary,tb_Employee where tb_Summary.TPM=tb_Employee.EId and tb_Employee.EmpName=?";
		String []paras={tpmname};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>alList=basicDB.queryDB(sql, paras);
		for(int i=0;i<alList.size();i++)
		{
			Object []obj=alList.get(i);
			projectlist.add(obj[0].toString().trim());
		}
		return projectlist;
	}
}
