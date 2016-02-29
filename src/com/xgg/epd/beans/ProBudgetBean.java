package com.xgg.epd.beans;

public class ProBudgetBean {
	private String proName;
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getEPD_Budget() {
		return EPD_Budget;
	}
	public void setEPD_Budget(int ePDBudget) {
		EPD_Budget = ePDBudget;
	}
	public int getAE_Budget() {
		return AE_Budget;
	}
	public void setAE_Budget(int aEBudget) {
		AE_Budget = aEBudget;
	}
	public int getRBEI_Budget() {
		return RBEI_Budget;
	}
	public void setRBEI_Budget(int rBEIBudget) {
		RBEI_Budget = rBEIBudget;
	}
	private String year;
	private int EPD_Budget;
	private int AE_Budget;
	private int RBEI_Budget;
	
}
