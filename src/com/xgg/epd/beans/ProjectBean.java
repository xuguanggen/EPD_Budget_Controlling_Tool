package com.xgg.epd.beans;

public class ProjectBean {
	private String proName;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getTPM() {
		return TPM;
	}

	public void setTPM(String tPM) {
		TPM = tPM;
	}

	

	private String proId;
	private String TPM;
	private String bp;
	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}	


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String status;
	private String oem;
	private String customer;
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	private String remark;
	private String category;
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public String abudget;
	public String getAbudget() {
		return abudget;
	}

	public void setAbudget(String abudget) {
		this.abudget = abudget;
	}

	public String getEbudget() {
		return ebudget;
	}

	public void setEbudget(String ebudget) {
		this.ebudget = ebudget;
	}

	public String getRbudget() {
		return rbudget;
	}

	public void setRbudget(String rbudget) {
		this.rbudget = rbudget;
	}



	public String ebudget;
	public String rbudget;
	private String budget;
	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public ProjectBean(String pid, String pname, String tpm,
			String bpString,String ebudget,String abudget,String rbudget,String status, String oem, String customer_name,
			String remark,String category) {
		this.proId = pid;
		this.proName = pname;
		this.TPM = tpm;
		this.bp=bpString;
		this.ebudget=ebudget;
		this.abudget=abudget;
		this.rbudget=rbudget;
		this.budget=Integer.parseInt(ebudget)+Integer.parseInt(abudget)+Integer.parseInt(rbudget)+"";
		this.status=status;
		this.oem=oem;
		this.customer=customer_name;
		this.remark=remark;		
		this.category=category;
	}
}
