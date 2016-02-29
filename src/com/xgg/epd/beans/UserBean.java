package com.xgg.epd.beans;

public class UserBean {
	private String EId;
	public String getEId() {
		return EId;
	}
	public void setEId(String eId) {
		EId = eId;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getSection() {
		return Section;
	}
	public void setSection(String section) {
		Section = section;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public int getMark() {
		return Mark;
	}
	public void setMark(int mark) {
		Mark = mark;
	}
	public UserBean(String Eid,String nameString,String section,String asection,String email,String pass,int mark)
	{
		this.EId=Eid;
		this.EmpName=nameString;
		this.Section=section;
		this.Asection=asection;
		this.Email=email;
		this.Pwd=pass;
		this.Mark=(mark);
	}
	private String EmpName;
	private String Section;
	private String Email;
	private String Pwd;
	private int Mark;
	private String Asection;
	public String getAsection() {
		return Asection;
	}
	public void setAsection(String asection) {
		Asection = asection;
	}
}
