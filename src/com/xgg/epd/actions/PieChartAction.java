package com.xgg.epd.actions;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;
/*Date:2015-01-28
 * Author:Guanggen Xu
 * Function:
 * 1. draw two charts for each Section (two charts=ServiceNr PieChart+ Admin PieChart)
 */
public class PieChartAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] row = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };

	private String smonth;

	public String getSmonth() {
		return smonth;
	}

	public void setSmonth(String smonth) {
		this.smonth = smonth;
		String[] time = smonth.split("-");
		dsyear = Integer.parseInt(time[0]);
		dsmonth = Integer.parseInt(time[1]);
	}

	public String getEmonth() {
		return emonth;
	}

	public void setEmonth(String emonth) {
		this.emonth = emonth;
		String[] time = emonth.split("-");
		deyear = Integer.parseInt(time[0]);
		demonth = Integer.parseInt(time[1]);
	}

	private String emonth;

	public String getPros() {
		return pros;
	}

	public void setPros(String pros) {
		this.pros = pros;
		projects = pros.split(", ");
	}

	private String section;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	private String pros;
	public int deyear;
	public int demonth;
	public int dsyear;
	public int dsmonth;
	public String[] projects;
	private String tpmname;

	public String getTpmname() {
		return tpmname;
	}

	public void setTpmname(String tpmname) {
		this.tpmname = tpmname;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	String ip = request.getRemoteAddr().toString();
	String empnameString = session.getAttribute(ip + "EmpName").toString();

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		String markString = session.getAttribute(ip + "mark").toString();
		session.setAttribute("spie" + empnameString, smonth);
		session.setAttribute("epie" + empnameString, emonth);
		request.setAttribute("check", projects);
		if ("2".equals(markString)) {
			if (!"".equals(tpmname.trim())) {
				session.setAttribute("tpmnamePie" + empnameString, tpmname
						.trim());
			} else {
				tpmname = session.getAttribute("tpmnamePie" + empnameString)
						.toString().trim();
			}
		}
		if (!"".equals(pros.trim())) {
			session.setAttribute("proPie" + empnameString, pros);
		} else {
			pros = session.getAttribute("proPie" + empnameString).toString()
					.trim();
		}
		String[] fileNames = new String[projects.length];
		JFreeChart[] charts = new JFreeChart[projects.length];
		DefaultPieDataset[] dataset = new DefaultPieDataset[projects.length];
		for (int i = 0; i < projects.length; i++) {
			String temp_Pro = projects[i];
			// =====================================================================================
			ArrayList<Object[]> aList = new ArrayList<Object[]>();
			BasicDB basicDB = new BasicDB();
			if (dsyear == deyear) {
				for (int month = dsmonth; month <= demonth; month++) {
					String sqlString = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ProName=? and Month=? and ServiceNr!='000General'";
					String[] p = { dsyear + "", temp_Pro, month + "" };
					ArrayList<Object[]> tempObjects = basicDB.queryDB(
							sqlString, p);
					if (tempObjects != null) {
						for (int k = 0; k < tempObjects.size(); k++) {
							aList.add(tempObjects.get(k));
						}
					}
				}
				System.out.println("Same year:" + aList.size());
			} else {
				String start_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ProName=? and Month>=? and Month<=12 and ServiceNr!='000General'";
				String[] start_paras = { dsyear + "", temp_Pro, dsmonth + "" };
				ArrayList<Object[]> start_OList = basicDB.queryDB(start_sql,
						start_paras);
				if (start_OList != null) {
					System.out.println("First Year:" + start_OList.size());
					for (int k = 0; k < start_OList.size(); k++) {
						aList.add(start_OList.get(k));
					}
				}
				for (int mid_year = dsyear + 1; mid_year < deyear; mid_year++) {
					System.out.println(mid_year);
					String mid_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ProName=? and ServiceNr!='000General'";
					String[] mid_paras = { mid_year + "", temp_Pro };
					ArrayList<Object[]> tempObjList = basicDB.queryDB(mid_sql,
							mid_paras);
					if (tempObjList != null) {
						for (int k = 0; k < tempObjList.size(); k++) {
							aList.add(tempObjList.get(k));
						}
					}
				}
				String end_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ProName=? and Month<=? and Month>=1 and ServiceNr!='000General'";
				String[] end_paras = { deyear + "", temp_Pro, demonth + "" };
				ArrayList<Object[]> end_OList = basicDB.queryDB(end_sql,
						end_paras);
				if (end_OList != null) {
					System.out.println("Last Year:" + end_OList.size());
					for (int k = 0; k < end_OList.size(); k++) {
						aList.add(end_OList.get(k));
					}
				}
				System.out.println("Different Year=" + aList.size());
			}
			HashMap<String, Double> hashMap = new HashMap<String, Double>();
			for (int k = 0; k < aList.size(); k++) {
				Object[] objects = aList.get(k);
				String service = objects[0].toString().trim();
				Double wds = Double.parseDouble(objects[1].toString().trim()) * 8;
				if (hashMap.containsKey(service)) {
					Double beforeWds = hashMap.get(service);
					hashMap.put(service, beforeWds + wds);
				} else {
					hashMap.put(service, wds);
				}
			}
			// System.out.println("HashMap size="+hashMap.size());
			// =====================================================================================
			dataset[i]=new DefaultPieDataset();
			if (aList.size() == 0) {
				dataset[i].setValue("NULL", 100);
			} else {
				List<Map.Entry<String, Double>> infoIds =
				    new ArrayList<Map.Entry<String, Double>>(hashMap.entrySet());
				Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {   
				    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
				        //return (o2.getValue() - o1.getValue()); 
				        return (o1.getKey()).toString().compareTo(o2.getKey());
				    }
				}); 
				for(int k=0;k<infoIds.size();k++)
				{
					String []dStrings=infoIds.get(k).toString().split("=");
					String key=dStrings[0].toString().trim();
					double val=Double.parseDouble(dStrings[1].toString().trim());
					dataset[i].setValue(key, val);
				}
			}
			String pieName = projects[i];
			PiePlot pieplot = new PiePlot(dataset[i]);
			pieplot.setURLGenerator(new StandardPieURLGenerator("barview.jsp","lion"));		
			pieplot.setBackgroundPaint(new Color(238, 224, 229));
			pieplot.setLabelFont(new Font("sans-serif", 0, 11));
			pieplot.setNoDataMessage("No Data");
			pieplot.setCircular(true);
			pieplot.setLabelGap(0.06D);
			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
					"{0}={1} ({2})", NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));
			StandardEntityCollection sec = new StandardEntityCollection(); 
			ChartRenderingInfo info = new ChartRenderingInfo(sec); 
			PrintWriter out=response.getWriter();
			PrintWriter w = new PrintWriter(out);
			charts[i] =  new JFreeChart(pieName,new Font("sans-serif", 0, 20), pieplot, true); 			
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 910, 800,
					info, session);
			ChartUtilities.writeImageMap(w, fileNames[i], info, true);
 
		}
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		if(month!=2)
		{
			fileNames=null;		
		}
		List<String> projectList = projectInit(tpmname);
		request.setAttribute("projectList", projectList);
		request.setAttribute("filename", fileNames);
		request.setAttribute("dataset", dataset);
		if ("1".equals(markString)) {
			return "tpmSuccess";
		} else if ("2".equals(markString)||"3".equals(markString)) {
			return "adminSuccess";
		}
		return "";
	}

	public List<String> projectInit(String tpmname) {
		List<Object[]> pList = new ArrayList<Object[]>();
		BasicDB basicDB = new BasicDB();
		String sql = "select distinct tb_Summary.ProName from tb_Summary where TPM=? and Year>=2015";
		String[] paras = { tpmname };
		pList = basicDB.queryDB(sql, paras);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < pList.size(); i++) {
			Object[] objects = pList.get(i);
			list.add(objects[0].toString().trim());
		}
		return list;
	}

	public String CreateSectionPie() throws Exception {
		JFreeChart jFreeChart = null;
		String fileName = "";
		session.setAttribute("spieDept" + empnameString, smonth);
		session.setAttribute("epieDept" + empnameString, emonth);
		session.setAttribute(ip + "PieDept", section);
		ArrayList<Object[]> aList = new ArrayList<Object[]>();
		BasicDB basicDB = new BasicDB();
		String sectionMark = "";
		if ("EPD1".equals(section))
			sectionMark = "1";
		else if ("EPD2".equals(section)) {
			sectionMark = "2";
		} else if ("EPD3".equals(section)) {
			sectionMark = "3";
		} else if ("EPD4".equals(section))
			sectionMark = "4";
		else if ("EPD5".equals(section))
			sectionMark = "5";
		else if ("EPD6".equals(section) || "PJ-AB12-CN".equals(section))
			sectionMark = "6";
		if (dsyear == deyear) {
			for (int month = dsmonth; month <= demonth; month++) {
				String sqlString = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ServiceNr like '"
						+ sectionMark + "%' and Month=?";
				String[] p = { dsyear + "", month + "" };
				ArrayList<Object[]> tempObjects = basicDB.queryDB(sqlString, p);
				if (tempObjects != null) {
					for (int k = 0; k < tempObjects.size(); k++) {
						aList.add(tempObjects.get(k));
					}
				}
			}
		} else {
			String start_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ServiceNr like '"
					+ sectionMark + "%' and Month>=? and Month<=12";
			String[] start_paras = { dsyear + "", dsmonth + "" };
			ArrayList<Object[]> start_OList = basicDB.queryDB(start_sql,
					start_paras);
			if (start_OList != null) {
				System.out.println("First Year:" + start_OList.size());
				for (int k = 0; k < start_OList.size(); k++) {
					aList.add(start_OList.get(k));
				}
			}else {
				System.out.println("start:=0");
			}
			for (int mid_year = dsyear + 1; mid_year < deyear; mid_year++) {
				System.out.println(mid_year);
				String mid_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ServiceNr like '"
						+ sectionMark + "%'";
				String[] mid_paras = { mid_year + "" };
				ArrayList<Object[]> tempObjList = basicDB.queryDB(mid_sql,
						mid_paras);
				if (tempObjList != null) {
					for (int k = 0; k < tempObjList.size(); k++) {
						aList.add(tempObjList.get(k));
					}
				}
				else {
					System.out.println(mid_year+"=0");
				}
			}
			String end_sql = "select ServiceNr,WorkingDays from tb_MonthlyControlling where Year=? and ServiceNr like '"
					+ sectionMark + "%' and Month<=? and Month>=1 ";
			String[] end_paras = { deyear + "", demonth + "" };
			ArrayList<Object[]> end_OList = basicDB.queryDB(end_sql, end_paras);
			if (end_OList != null) {
				System.out.println("Last Year:" + end_OList.size());
				for (int k = 0; k < end_OList.size(); k++) {
					aList.add(end_OList.get(k));
				}
			}else {
				System.out.println("End=0");
			}
		}
		HashMap<String, Double> hashMap = new HashMap<String, Double>();
		for (int k = 0; k < aList.size(); k++) {
			Object[] objects = aList.get(k);
			String service = objects[0].toString().trim();
			Double wds = Double.parseDouble(objects[1].toString().trim()) * 8;
			if (hashMap.containsKey(service)) {
				Double beforeWds = hashMap.get(service);
				hashMap.put(service, beforeWds + wds);
			} else {
				hashMap.put(service, wds);
			}
		}		
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (aList.size() == 0) {
			dataset.setValue("NULL", 100);
		} else {
			List<Map.Entry<String, Double>> infoIds =
			    new ArrayList<Map.Entry<String, Double>>(hashMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {   
			    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
			        //return (o2.getValue() - o1.getValue()); 
			        return (o1.getKey()).toString().compareTo(o2.getKey());
			    }
			}); 
			for(int i=0;i<infoIds.size();i++)
			{
				String []dStrings=infoIds.get(i).toString().split("=");
				String key=dStrings[0].toString().trim();
				double val=Double.parseDouble(dStrings[1].toString().trim());
				dataset.setValue(key, val);
			}
		}
		jFreeChart = ChartFactory.createPieChart(section+"-ServiceNr", dataset, true, true,
				false);
		PiePlot pieplot = (PiePlot) jFreeChart.getPlot();
		pieplot.setBackgroundPaint(new Color(238, 224, 229));
		pieplot.setLabelFont(new Font("sans-serif", 0, 11));
		pieplot.setNoDataMessage("No Data");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.06D);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		fileName = ServletUtilities.saveChartAsPNG(jFreeChart, 910, 600, null,
				session);
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		int nowday=calendar.get(Calendar.DATE);
		if((month==3))
		{
			if(nowday>=2)
			 dataset=null;	
		}else if(month>3){
			 dataset=null;
		}
		request.setAttribute("sdataset", dataset);
		request.setAttribute("filename", fileName);
		request.setAttribute("NoTPMfilename", createNoTPMPiechart());
		return "SectionPie";
	}

	public DefaultPieDataset createNoTPMPiechart() throws Exception {
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]> aList = new ArrayList<Object[]>();
		String fileName = "";
		JFreeChart jFreeChart = null;
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (dsyear == deyear) {
			for (int month = dsmonth; month <= demonth; month++) {
				String sqlString = "select tb_MonthlyControlling.ProName,sum(WorkingDays) from tb_MonthlyControlling,tb_Project where tb_MonthlyControlling.ProName=tb_Project.ProName and  tb_Project.TPM='Admin' and tb_MonthlyControlling.Year=? and tb_MonthlyControlling.Month=? and Section=? group by tb_MonthlyControlling.ProName";
				String[] p = { dsyear + "", month + "",section };
				ArrayList<Object[]> tempObjects = basicDB.queryDB(sqlString, p);
				if (tempObjects != null) {
					for (int k = 0; k < tempObjects.size(); k++) {
						aList.add(tempObjects.get(k));
					}
				}
			}
		} else {
			String start_sql = "select tb_MonthlyControlling.ProName,sum(WorkingDays) from tb_MonthlyControlling,tb_Project where tb_MonthlyControlling.ProName=tb_Project.ProName and  tb_Project.TPM='Admin' and tb_MonthlyControlling.Year=? and tb_MonthlyControlling.Month>=? and tb_MonthlyControlling.Month<=12 and Section=? group by tb_MonthlyControlling.ProName";
			String[] start_paras = { dsyear + "", dsmonth + "",section };
			ArrayList<Object[]> start_OList = basicDB.queryDB(start_sql,
					start_paras);
			if (start_OList != null) {
				System.out.println("First Year:" + start_OList.size());
				for (int k = 0; k < start_OList.size(); k++) {
					aList.add(start_OList.get(k));
				}
			}
			for (int mid_year = dsyear + 1; mid_year < deyear; mid_year++) {
				System.out.println(mid_year);
				String mid_sql = "select tb_MonthlyControlling.ProName,sum(WorkingDays) from tb_MonthlyControlling,tb_Project where tb_MonthlyControlling.ProName=tb_Project.ProName and  tb_Project.TPM='Admin' and tb_MonthlyControlling.Year=?  and Section=? group by tb_MonthlyControlling.ProName";
				String[] mid_paras = { mid_year + "",section };
				ArrayList<Object[]> tempObjList = basicDB.queryDB(mid_sql,
						mid_paras);
				if (tempObjList != null) {
					for (int k = 0; k < tempObjList.size(); k++) {
						aList.add(tempObjList.get(k));
					}
				}
			}
			String end_sql = "select tb_MonthlyControlling.ProName,sum(WorkingDays) from tb_MonthlyControlling,tb_Project where tb_MonthlyControlling.ProName=tb_Project.ProName and  tb_Project.TPM='Admin' and tb_MonthlyControlling.Year=? and tb_MonthlyControlling.Month<=? and tb_MonthlyControlling.Month>=1 and Section=? group by tb_MonthlyControlling.ProName";
			String[] end_paras = { deyear + "", demonth + "",section};
			ArrayList<Object[]> end_OList = basicDB.queryDB(end_sql, end_paras);
			if (end_OList != null) {
				System.out.println("Last Year:" + end_OList.size());
				for (int k = 0; k < end_OList.size(); k++) {
					aList.add(end_OList.get(k));
				}
			}
		}
		HashMap<String, Double> hashMap = new HashMap<String, Double>();
		for (int k = 0; k < aList.size(); k++) {
			Object[] objects = aList.get(k);
			String pro = objects[0].toString().trim();
			Double wds = Double.parseDouble(objects[1].toString().trim());
			if (hashMap.containsKey(pro)) {
				Double beforeWds = hashMap.get(pro);
				hashMap.put(pro, beforeWds + wds);
			} else {
				hashMap.put(pro, wds);
			}
		}
		if (aList.size() == 0) {
			dataset.setValue("NULL", 100);
		} else {
			Iterator<String> iter = hashMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Double val = hashMap.get(key);
				dataset.setValue(key, val*8);
			}
		}
		jFreeChart = ChartFactory.createPieChart(section+"-Admin", dataset, true, true,
				false);
		PiePlot pieplot = (PiePlot) jFreeChart.getPlot();
		pieplot.setBackgroundPaint(new Color(238, 224, 229));
		pieplot.setLabelFont(new Font("sans-serif", 0, 11));
		pieplot.setNoDataMessage("No Data");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.06D);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		fileName = ServletUtilities.saveChartAsPNG(jFreeChart, 910, 600, null,
				session);
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		int nowday=calendar.get(Calendar.DATE);
		if((month==3))
		{
			if(nowday>=2)
			   dataset=null;
		}else if(month>3){
			 dataset=null;
		}
		return dataset;
	}
}
