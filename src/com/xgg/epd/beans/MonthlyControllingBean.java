package com.xgg.epd.beans;

import java.io.Serializable;

public class MonthlyControllingBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empname;
	private String year;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public float getWorkingdays() {
		return workingdays;
	}
	public void setWorkingdays(float workingdays) {
		this.workingdays = workingdays;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String proname;
	private String month;
	private String section;
	private String service;
	private float workingdays;
	private String remark;
	private int mid;
	
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
}
