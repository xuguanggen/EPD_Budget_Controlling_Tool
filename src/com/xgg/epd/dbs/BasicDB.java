package com.xgg.epd.dbs;

import java.sql.*;
import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.xgg.epd.beans.ProjectBean;
import com.xgg.epd.beans.UserBean;
import com.xgg.epd.utils.Common;

public class BasicDB {
	private Connection cn = null;

	public Connection getCn() {
		return cn;
	}

	public void setCn(Connection cn) {
		this.cn = cn;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private String sql;

	public void OpenDB() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://10.54.5.100:1433;DatabaseName=EPD_Budget_Controlling_Tool";
			String user = "CC_PS_EPD_CN";
			String psd = "CC_PS_EPD_CN";
			cn = DriverManager.getConnection(url, user, psd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CloseDB() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (cn != null) {
			try {
				cn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Object[]> queryDB(String sql, String[] paras) {
		ArrayList<Object[]> al = new ArrayList<Object[]>();
		this.OpenDB();
		try {
			ps = cn.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
				ps.setString(i + 1, paras[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			while (rs.next()) {
				Object[] objects = new Object[cols];
				for (int i = 0; i < objects.length; i++) {
					objects[i] = rs.getObject(i + 1).toString();
				}
				al.add(objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.CloseDB();
		}
		return al;
	}

	public int updateDB(String sql, String[] paras) {
		int result = 0;
		this.OpenDB();
		try {
			ps = cn.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
				ps.setString(i + 1, paras[i]);
			}
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.CloseDB();
		}
		return result;
	}

	public ArrayList<String> SQuerydb(String sql) {
		ArrayList<String> alist = new ArrayList<String>();
		this.OpenDB();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				alist.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.CloseDB();
		}
		return alist;
	}

	public int UpdateMutildb(String sql, String[][] paras) {
		int result = 0;
		this.OpenDB();
		for (int i = 0; i < paras.length; i++) {
			try {
				ps = cn.prepareStatement(sql);
				for (int j = 0; j < paras[0].length; j++) {
					ps.setString(j + 1, paras[i][j]);
				}
				result = result + ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.CloseDB();
		return result;
	}

	public int UpdateMutildb(String[] sql, String[][] paras) {
		int result = 0;
		this.OpenDB();
		for (int i = 0; i < sql.length; i++) {
			try {
				ps = cn.prepareStatement(sql[i]);
				for (int j = 0; j < paras[0].length; j++) {
					ps.setString(j + 1, paras[i][j]);
				}
				result = result + ps.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		this.CloseDB();
		return result;
	}

	public ArrayList<UserBean> spliltquery(String sql) {
		this.OpenDB();
		ArrayList<UserBean> uList = new ArrayList<UserBean>();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean userBean;
				String eidString = rs.getString("EId").trim();
				String enameString = rs.getString("EmpName").trim();
				String sectionString = rs.getString("Section").trim();
				String email = rs.getString("Email").trim();
				String pwd = rs.getString("Pwd").trim();
				String asectionString=rs.getString("Asection").trim();
				int mark = Integer.parseInt(rs.getString("mark").trim());
				userBean = new UserBean(eidString, enameString, sectionString,asectionString,
						email, pwd, mark);
				uList.add(userBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.CloseDB();
		return uList;
	}

	public ArrayList<ProjectBean> queryProList(String sql) {
		this.OpenDB();
		ArrayList<ProjectBean> projectBeans = new ArrayList<ProjectBean>();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ProjectBean pBean;
				String pid = rs.getString("PId").toString().trim();
				String proname = rs.getString("ProName").toString().trim();
				int[] budgetString=Common.getBudget(proname);
				String tpm = rs.getString("TPM").toString().trim();
				String status = rs.getString("Status").toString().trim();
				String remarkString = rs.getString("Remark").toString().trim();
				String oem = rs.getString("OEM").toString().trim();
				String bpString = rs.getString("In_Bp").toString().trim();
				String tier_name = rs.getString("Tier").toString().trim();
				String catagory=rs.getString("Category").toString().trim();
				pBean = new ProjectBean(pid, proname, tpm, bpString,budgetString[0]+"",budgetString[1]+"",budgetString[2]+"",
						 status, oem, tier_name, remarkString,catagory);
				projectBeans.add(pBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.CloseDB();
		System.out.println("BASICDB=="+projectBeans.size());
		return projectBeans;
	}
}
