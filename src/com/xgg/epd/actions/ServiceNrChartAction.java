package com.xgg.epd.actions;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;

public class ServiceNrChartAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	Calendar calendar=Calendar.getInstance();
	int year=calendar.get(Calendar.YEAR);
	public String execute()throws Exception {
		HttpSession session=request.getSession();
		String category = request.getParameter("lion").toString().substring(0,
				3);
		String pro = request.getParameter("pro").toString().trim();
		System.out.println(pro);
		System.out.println(category);
		String sql = "select EmpName,sum(WorkingDays)*8 k from tb_MonthlyControlling where Year=? and ProName=? and ServiceNr like '%"+category+"%' group by EmpName order by k";
		String[] paras = { year+"",pro };
		BasicDB basicDb = new BasicDB();
		ArrayList<Object[]> alist = basicDb.queryDB(sql, paras);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();  
		for (int i = 0; i < alist.size(); i++) {
			Object[] obj = alist.get(i);
			String temp = obj[0].toString().trim();
			double days = Double.parseDouble(obj[1].toString().trim());
			dataSet.addValue(days, "Employee", temp);
		}

		String title = year+"--"+pro+"--"+request.getParameter("lion").toString();
		JFreeChart chart = ChartFactory.createBarChart(title, "Employee",
				"Working Hours", dataSet, PlotOrientation.VERTICAL, false,
				false, false);
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		 
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
		renderer.setItemLabelAnchorOffset(-10D); 
		renderer.setItemLabelPaint(Color.BLUE); 
		CategoryPlot plot = chart.getCategoryPlot();  
		StandardChartTheme chartTheme = new StandardChartTheme("EN");
		chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		ChartFactory.setChartTheme(chartTheme);
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN+Font.ITALIC, 15));
		domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(
			       Math.PI / 7.0));   
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.PINK);
		renderer.setItemMargin(0.4);
		renderer.setMaximumBarWidth(0.1);
		renderer.setItemLabelFont(new Font("sans-serif", Font.PLAIN, 20));
		plot.setRenderer(renderer);
		String filename = ServletUtilities.saveChartAsPNG(chart, 900, 600,
				null, session);
		request.setAttribute("filename", filename);
		return SUCCESS;
	}
}
