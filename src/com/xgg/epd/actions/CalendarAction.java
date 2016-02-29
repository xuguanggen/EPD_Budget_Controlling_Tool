package com.xgg.epd.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class CalendarAction extends ActionSupport {
	/**Function : Add the next year calendar
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jan;

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getFeb() {
		return feb;
	}

	public void setFeb(String feb) {
		this.feb = feb;
	}

	public String getMar() {
		return mar;
	}

	public void setMar(String mar) {
		this.mar = mar;
	}

	public String getApr() {
		return apr;
	}

	public void setApr(String apr) {
		this.apr = apr;
	}

	public String getMay() {
		return may;
	}

	public void setMay(String may) {
		this.may = may;
	}

	public String getJun() {
		return jun;
	}

	public void setJun(String jun) {
		this.jun = jun;
	}

	public String getJul() {
		return jul;
	}

	public void setJul(String jul) {
		this.jul = jul;
	}

	public String getAug() {
		return aug;
	}

	public void setAug(String aug) {
		this.aug = aug;
	}

	public String getSep() {
		return sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public String getOct() {
		return oct;
	}

	public void setOct(String oct) {
		this.oct = oct;
	}

	public String getNov() {
		return nov;
	}

	public void setNov(String nov) {
		this.nov = nov;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	private String feb;
	private String mar;
	private String apr;
	private String may;
	private String jun;
	private String jul;
	private String aug;
	private String sep;
	private String oct;
	private String nov;
	private String dec;
	private String year;

	public String execute() {
		BasicDB basicDB = new BasicDB();
		int[] days = { Integer.parseInt(jan.trim()),
				Integer.parseInt(feb.trim()), Integer.parseInt(mar.trim()),
				Integer.parseInt(apr.trim()), Integer.parseInt(may.trim()),
				Integer.parseInt(jun.trim()), Integer.parseInt(jul.trim()),
				Integer.parseInt(aug.trim()), Integer.parseInt(sep.trim()),
				Integer.parseInt(oct.trim()), Integer.parseInt(nov.trim()),
				Integer.parseInt(dec.trim()) };
		for(int i=0;i<12;i++)
		{
			String sqlString="insert into tb_Calendar values(?,?,?)";
			String []paras={year,(i+1)+"",days[i]+""};
			basicDB.updateDB(sqlString, paras);
		}
		return SUCCESS;
	}
}
