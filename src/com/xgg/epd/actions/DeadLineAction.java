package com.xgg.epd.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.beans.DeadlineBean;

public class DeadLineAction extends ActionSupport{

	/**Function :Set deadline time for monthlyControlling of each month
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String stime;
	public static String stime_copy="2014-12-30";
	public static String etime_copy="2015-01-05";
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
		stime_copy=stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
		etime_copy=etime;
	}

	private String etime;
	
	public String execute()
	{
		String []detail_stime=stime.split("-");
		String []detail_etime=etime.split("-");
		DeadlineBean.setStartDate(Integer.parseInt(detail_stime[2]));
		DeadlineBean.setEndDate(Integer.parseInt(detail_etime[2]));
		return SUCCESS;
	}

}
