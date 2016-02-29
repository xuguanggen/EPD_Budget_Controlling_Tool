package com.xgg.epd.dao;
import java.util.ArrayList;
import java.util.List;

import com.xgg.epd.beans.UserBean;

public class PageDao {
	
	
	 public List<UserBean> queryByPage (int pageSize, int pageNow) 
	 {
		 ArrayList<UserBean> employees=new ArrayList<UserBean>();
		 String sqlString="select * from tb_Employee where EId in(select top 2 EId from tb_Employee where EId not in (select top 0 EId from tb_Employee ))";
		 return employees;
	 }
}
