package com.xgg.epd.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.commons.beanutils.BasicDynaBean;
import com.xgg.epd.dbs.BasicDB;
import com.xgg.epd.utils.Common;

public class SectionChartAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	String service = request.getParameter("lion").toString();

	public String execute() throws Exception {
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		String sql = "select EmpName,sum(WorkingDays)*8 k from tb_MonthlyControlling where ServiceNr=? and Year=? group by EmpName order by k";
		String[] paras = { service ,year+""};
		BasicDB basicDb = new BasicDB();
		ArrayList<Object[]> alist = basicDb.queryDB(sql, paras);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (int i = 0; i < alist.size(); i++) {
			Object[] obj = alist.get(i);
			String temp = obj[0].toString().trim();
			double days = Double.parseDouble(obj[1].toString().trim());
			dataSet.addValue(days, "Employee", temp);
		}

		String title = service + "--Employee--"+year;
		JFreeChart chart = ChartFactory.createBarChart(title, "Employee",
				"Working Hours", dataSet, PlotOrientation.VERTICAL, false,
				false, false);
		BarRenderer3D renderer = new BarRenderer3D();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
		renderer.setItemLabelAnchorOffset(-10D);
		renderer.setItemLabelPaint(Color.BLUE);
		renderer.setItemLabelFont(new Font("sans-serif", Font.PLAIN, 20));
		CategoryPlot plot = chart.getCategoryPlot();
		StandardChartTheme chartTheme = new StandardChartTheme("EN");
		chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		ChartFactory.setChartTheme(chartTheme);
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		ValueAxis axis = plot.getRangeAxis();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN+Font.ITALIC, 15));
		domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(
			       Math.PI / 7.0));   
		axis.setAxisLineVisible(true);
		axis.setTickMarksVisible(true);
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.PINK);
		renderer.setItemMargin(0.15);
		renderer.setMaximumBarWidth(0.05);
		plot.setRenderer(renderer);
		String filename = ServletUtilities.saveChartAsPNG(chart, 1000, 1000,
				null, session);
		request.setAttribute("byEmp", filename);
		request.setAttribute("byProject", ColumnChartByProject());
		return SUCCESS;
	}

	public String ColumnChartByProject() throws Exception {
		String filename = "";
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		String sql = "select tb_MonthlyControlling.ProName,sum(WorkingDays)*8 k from tb_MonthlyControlling,tb_Project where tb_MonthlyControlling.ProName=tb_Project.ProName and tb_Project.TPM!='Admin' and tb_Project.TPM!='none' and tb_Project.TPM!='Li John' and tb_MonthlyControlling.ServiceNr=? and tb_MonthlyControlling.Year=? group by tb_MonthlyControlling.ProName order by k";
		String[] paras = { service ,year+""};
		BasicDB basicDb = new BasicDB();
		ArrayList<Object[]> alist = basicDb.queryDB(sql, paras);
		DefaultPieDataset dataSet = new DefaultPieDataset();
		String title = service + "--Project--"+year;
		if (alist.size() > 15) {
			for (int i = 0; i < 15; i++) {
				Object[] obj = alist.get(i);
				String temp = obj[0].toString().trim();
				double days = Double.parseDouble(obj[1].toString().trim());
				dataSet.setValue(temp, days);
			}
			double other = 0;
			for (int i = 15; i < alist.size(); i++) {
				Object[] obj = alist.get(i);
				String temp = obj[0].toString().trim();
				double days = Double.parseDouble(obj[1].toString().trim());
				other = other + days;
			}
			dataSet.setValue("other", other);
		} else {
			for (int i = 0; i < alist.size(); i++) {
				Object[] obj = alist.get(i);
				String temp = obj[0].toString().trim();
				double days = Double.parseDouble(obj[1].toString().trim());
				dataSet.setValue(temp, days);
			}
		}
		JFreeChart chart = ChartFactory.createPieChart(title, dataSet, true,
				true, false);
		BarRenderer3D renderer = new BarRenderer3D();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
		renderer.setItemLabelAnchorOffset(-10D);
		renderer.setItemLabelPaint(Color.BLUE);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setNoDataMessage("No Data");
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		renderer.setItemMargin(0.4);
		renderer.setMaximumBarWidth(0.05);
		filename = ServletUtilities.saveChartAsPNG(chart, 1000, 800, null,
				session);
		return filename;
	}

	public String createAdminColumnChart() throws Exception {
		String sectionString = request.getParameter("section").toString()
				.trim();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String sql = "select EmpName ,sum(WorkingDays)*8 k from tb_MonthlyControlling where ProName=? and Year=? and Section=? group by EmpName order by k";
		String[] paras = { service, year + "", sectionString };
		BasicDB basicDb = new BasicDB();
		ArrayList<Object[]> alist = basicDb.queryDB(sql, paras);
		double data[][] = null;
		data = new double[1][alist.size()];
		String[] emps = new String[alist.size()];
		for (int i = 0; i < alist.size(); i++) {
			Object[] obj = alist.get(i);
			String temp = obj[0].toString().trim();
			double days = Double.parseDouble(obj[1].toString().trim());
			data[0][i] = days;
			emps[i] = temp;
		}
		String[] icons = { "" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(icons,
				emps, data);		
		String title = service + "--Employee---"+year;
		JFreeChart chart = ChartFactory.createBarChart(title, "Employee",
				"Working Hours", dataset, PlotOrientation.VERTICAL, false,
				false, false);
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setTextAntiAlias(false);
		CategoryPlot plot = chart.getCategoryPlot();
		chart.setTextAntiAlias(false);
		TextTitle textTitle = new TextTitle(title, new Font("sans-serif",
				Font.PLAIN, 20));
		chart.setTitle(textTitle);
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLineVisible(true);
		axis.setTickMarksVisible(true);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		plot.setForegroundAlpha(0.8f);
		StandardChartTheme chartTheme = new StandardChartTheme("EN");
		chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		ChartFactory.setChartTheme(chartTheme);
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN+Font.ITALIC, 15));
		domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(
			       Math.PI / 7.0));   
		numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.PINK);
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
		renderer.setItemLabelAnchorOffset(-10D);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setItemLabelPaint(Color.BLUE);
		renderer.setItemLabelFont(new Font("sans-serif", Font.PLAIN, 20));
		renderer.setMaximumBarWidth(0.4);
		renderer.setMinimumBarLength(0.2);
		renderer.setBaseOutlinePaint(Color.BLACK);
		renderer.setDrawBarOutline(true);
		renderer.setSeriesPaint(0, Color.GREEN);
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setItemMargin(0.4);
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setShadowVisible(false);
		plot.setRenderer(renderer);
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		String filename = ServletUtilities.saveChartAsPNG(chart, 1000, 800,
				null, session);
		request.setAttribute("admin", filename);
		return "AdminSuccess";
	}
}
