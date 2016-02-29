package com.xgg.epd.actions;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
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
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardCategoryURLGenerator;

import sun.font.CreatedFontTracker;

import com.opensymphony.xwork2.ActionSupport;
import com.xgg.epd.dbs.BasicDB;
import com.xgg.epd.utils.Common;
/*
 * Author:2015-1-20
 * Date :Xu Guanggen
 * Function: Draw some column charts 
 * Get Data from table tb_Summary ,tb_ProBudget
 */
public class AllChartAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String proType;
	private String tpmname;
	private String chartType;
	private String pros;

	public String getPros() {
		return pros;
	}

	public void setPros(String pros) {
		this.pros = pros;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getTpmname() {
		return tpmname;
	}

	public void setTpmname(String tpmname) {
		this.tpmname = tpmname.trim();
	}

	HttpServletRequest request = ServletActionContext.getRequest();

	String[] row = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };
	public String year;

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType.trim();
	}

	public TextTitle[] arrTitle = new TextTitle[4];
	String ip = request.getRemoteAddr().toString();

	public String create() throws Exception {
		session.setAttribute(ip + "chartYear", year);
		System.out.println(year);
		String temp = null;
		if (chartType == null) {
			chartType = "";
			temp = "EPD, AE, RBEI";
		} else if ("on, EPD, AE, RBEI".equals(chartType)
				|| "All, EPD, AE, RBEI".equals(chartType)) {
			temp = "EPD, AE, RBEI";
		} else {
			temp = chartType;
		}
		String[] arrpros = pros.split(", ");
		request.setAttribute("check", arrpros);
		request.setAttribute("chartType", chartType);
		HttpSession session = request.getSession();
		String[] fileNames = new String[arrpros.length];
		JFreeChart[] charts = new JFreeChart[arrpros.length];
		JFreeChart totalChart=null;
		String totalfileName="";
		String empnameString = session.getAttribute(ip + "EmpName").toString();
		String markString = session.getAttribute(ip + "mark").toString();
		if (!"".equals(tpmname.trim())) {
			session.setAttribute("tpmname" + empnameString, tpmname.trim());
		} else {
			tpmname = session.getAttribute("tpmname" + empnameString)
					.toString().trim();
		}
		if (!"".equals(pros.trim())) {
			session.setAttribute("pro" + empnameString, pros);
		} else {
			pros = session.getAttribute("pro" + empnameString).toString()
					.trim();
		}
		if("70818773".equals(tpmname))
		{
			List<String> projectList = projectInit(tpmname,year);
			request.setAttribute("projectList", projectList);
			request.setAttribute("rbeiName", CreateRBEIChart(year));
			request.setAttribute("rbeiPie", createPie_RBEI(year));
			if ("1".equals(markString)) {
				return "tpmsuccess";
			} else if ("2".equals(markString)||"3".equals(markString)) {
				return "adminsuccess";
			}else if("4".equals(markString))
			{
				return "guestsuccess";
			}
		}
		if ("EPD, AE, RBEI".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[EPD][AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setSeriesPaint(3, Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
//			plot.setDataset(1,createEPD_Budget_Dataset());
//			plot.setDataset(2,createAE_Budget_Dataset());
//			plot.setDataset(3, createRBEI_Budget_Dataset());
			plot.setDataset(4, createTotalBudgetNoProject(year));
//			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//			lineandshaperenderer
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer1
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer2
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer3
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//			lineandshaperenderer1.setSeriesPaint(0, Color.RED);
//			lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
//			plot.setRenderer(1, lineandshaperenderer);
//			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(3, lineandshaperenderer2);
			plot.setRenderer(4, lineandshaperenderer3);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataSet(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i]
						+ "--[EPD][AE][RBEI]", new Font("sans-serif",
						Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
				lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.GREEN);
				renderer.setSeriesPaint(1, Color.RED);
				renderer.setSeriesPaint(2, Color.YELLOW);
				renderer.setSeriesPaint(3, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				// plot.setDataset(1,createEPD_Budget_Dataset());
				// plot.setDataset(2,createAE_Budget_Dataset());
				// plot.setDataset(3, createRBEI_Budget_Dataset());
				plot.setDataset(4, createTotal_Budget_Dataset(arrpros[i]));
				// LineAndShapeRenderer lineandshaperenderer = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer1 = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer2 = new
				// LineAndShapeRenderer();
			    lineandshaperenderer3 = new LineAndShapeRenderer();
				// lineandshaperenderer
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer1
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer2
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer3
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				// plot.setRenderer(1, lineandshaperenderer);
				// plot.setRenderer(2, lineandshaperenderer1);
				// plot.setRenderer(3, lineandshaperenderer2);
				plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("EPD, AE".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E_A(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[EPD][AE]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(1,createEPDBudgetNoProject(year));
			plot.setDataset(2,createAEBudgetNoProject(year));
//			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
//			lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
//			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(1, lineandshaperenderer);
//			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(3, lineandshaperenderer2);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_E_A(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i] + "--[EPD][AE]",
						new Font("sans-serif", Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.GREEN);
				renderer.setSeriesPaint(1, Color.RED);
				renderer.setSeriesPaint(2, Color.YELLOW);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				plot.setDataset(1, createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2, createAE_Budget_Dataset(arrpros[i]));
				// plot.setDataset(3, createRBEI_Budget_Dataset());
				// plot.setDataset(4, new
				// TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
			    lineandshaperenderer = new LineAndShapeRenderer();
			    lineandshaperenderer1 = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer2 = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer2
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
				// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
				// plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("EPD, RBEI".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[EPD][RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(1,createEPDBudgetNoProject(year));
			plot.setDataset(2,createRBEIBudgetNoProject(year));
//			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			lineandshaperenderer1.setSeriesPaint(0, Color.CYAN);
//			lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
//			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(1, lineandshaperenderer);
//			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(3, lineandshaperenderer2);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_E_R(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i]
						+ "--[EPD][RBEI]", new Font("sans-serif", Font.PLAIN,
						16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.GREEN);
				renderer.setSeriesPaint(1, Color.RED);
				renderer.setSeriesPaint(2, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				plot.setDataset(1, createEPD_Budget_Dataset(arrpros[i]));
				// plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3, createRBEI_Budget_Dataset(arrpros[i]));
				// plot.setDataset(4, new
				// TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
			    lineandshaperenderer = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer1 = new
				// LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer1
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
				// plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("AE, RBEI".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_A_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.YELLOW);
			renderer.setSeriesPaint(1,Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(2,createAEBudgetNoProject(year));
			plot.setDataset(3,createRBEIBudgetNoProject(year));
			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
			//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//			lineandshaperenderer
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer1
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer2
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer3
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
			lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
		//	plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
			plot.setRenderer(3, lineandshaperenderer2);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_A_R(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(
						arrpros[i] + "--[AE][RBEI]", new Font("sans-serif",
								Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.YELLOW);
				renderer.setSeriesPaint(1, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				// plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2, createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3, createRBEI_Budget_Dataset(arrpros[i]));
				// plot.setDataset(4, new
				// TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
				// LineAndShapeRenderer lineandshaperenderer = new
				// LineAndShapeRenderer();
			    lineandshaperenderer1 = new LineAndShapeRenderer();
			    lineandshaperenderer2 = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				// lineandshaperenderer
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				// plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("EPD".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[EPD]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(1,createEPDBudgetNoProject(year));
			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
			lineandshaperenderer
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer1
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer2
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer3
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//			lineandshaperenderer1.setSeriesPaint(0, Color.RED);
//			lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
//			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			plot.setRenderer(1, lineandshaperenderer);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_E(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i] + "--[EPD]",
						new Font("sans-serif", Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.GREEN);
			    renderer.setSeriesPaint(1, Color.RED);
				// renderer.setSeriesPaint(1, Color.YELLOW);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				plot.setDataset(1, createEPD_Budget_Dataset(arrpros[i]));
				// plot.setDataset(2,createAE_Budget_Dataset());
				// plot.setDataset(3, createRBEI_Budget_Dataset());
				// plot.setDataset(4, createTotal_Budget_Dataset());
			    lineandshaperenderer = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer1 = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer2 = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer1
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer2
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
				// plot.setRenderer(2, lineandshaperenderer1);
				// plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("AE".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_A(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[AE]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(2,createAEBudgetNoProject(year));

			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
			lineandshaperenderer1
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
			//plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_A(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i] + "--[AE]",
						new Font("sans-serif", Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				// renderer.setSeriesPaint(0, Color.GREEN);
				renderer.setSeriesPaint(0, Color.YELLOW);
				// renderer.setSeriesPaint(1, Color.YELLOW);
				renderer.setItemMargin(0.4);
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				// plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2, createAE_Budget_Dataset(arrpros[i]));
				// plot.setDataset(3, createRBEI_Budget_Dataset());
				// plot.setDataset(4, createTotal_Budget_Dataset());
				// LineAndShapeRenderer lineandshaperenderer = new
				// LineAndShapeRenderer();
			    lineandshaperenderer1 = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer2 = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				// lineandshaperenderer
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer2
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
				// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				// plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
				// plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		} else if ("RBEI".equals(temp)) {
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(Common.getEmpNameByNo(tpmname)+" Projects--[RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    totalChart.setTitle(textTitle);
		    totalChart.getLegend().setBackgroundPaint(new Color(214,204,201));
			Color lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        ValueAxis axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        StandardChartTheme chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			totalChart.getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(3,createRBEIBudgetNoProject(year));
			LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//			lineandshaperenderer
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer1
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer2
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer3
//					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//			lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//			lineandshaperenderer1.setSeriesPaint(0, Color.RED);
			lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
		//	plot.setRenderer(1, lineandshaperenderer);
//			plot.setRenderer(2, lineandshaperenderer1);
			plot.setRenderer(3, lineandshaperenderer2);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			totalfileName = ServletUtilities.saveChartAsPNG(totalChart, 1000, 500, null,
					session);
			for (int i = 0; i < arrpros.length; i++) {
				charts[i] = ChartFactory.createStackedBarChart("", "Month",
						"Money", getDataset_R(arrpros[i]),
						PlotOrientation.VERTICAL, true, true, true);
				charts[i].getRenderingHints().put(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);
			    plot = charts[i].getCategoryPlot();
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle = new TextTitle(arrpros[i] + "--[RBEI]",
						new Font("sans-serif", Font.PLAIN, 16));
				charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(
						new Color(214, 204, 201));
			    lineColor = new Color(31, 121, 170);
				plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
				plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			    axis = plot.getRangeAxis();
				axis.setAxisLineVisible(true);
				axis.setTickMarksVisible(true);
				// Y轴网格线条
				plot.setRangeGridlinePaint(new Color(192, 192, 192));
				plot.setRangeGridlineStroke(new BasicStroke(1));
				plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
				plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
				plot.setForegroundAlpha(0.65f);
			    chartTheme = new StandardChartTheme("EN");
				chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
				chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
				chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
				chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
				chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
				chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
				// 渲染
				ChartFactory.setChartTheme(chartTheme);
			    numberAxis = (NumberAxis) plot.getRangeAxis();
			    domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
						12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211, 203, 203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
			    renderer = (StackedBarRenderer) plot
						.getRenderer();
				renderer
						.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				// renderer.setSeriesPaint(0, Color.GREEN);
				// renderer.setSeriesPaint(0, Color.RED);
				renderer.setSeriesPaint(0, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				// plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				// plot.setDataset(2,createAE_Budget_Dataset());
				plot.setDataset(3, createRBEI_Budget_Dataset(arrpros[i]));
				// plot.setDataset(4, createTotal_Budget_Dataset());
				// LineAndShapeRenderer lineandshaperenderer = new
				// LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer1 = new
				// LineAndShapeRenderer();
			    lineandshaperenderer2 = new LineAndShapeRenderer();
				// LineAndShapeRenderer lineandshaperenderer3 = new
				// LineAndShapeRenderer();
				// lineandshaperenderer
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer1
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer3
				// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
				// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
				// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				// plot.setRenderer(1, lineandshaperenderer);
				// plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
				// plot.setRenderer(4, lineandshaperenderer3);
			    categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				//==================================================================
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
						500, null, session);
			}
		}
		List<String> projectList = projectInit(tpmname,year);
		request.setAttribute("projectList", projectList);
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		if(month!=2)
		{
			fileNames=null;
			totalfileName=null;				
		}
		request.setAttribute("filename", fileNames);
		if("Geng George".equals(Common.getEmpNameByNo(tpmname)))
		 {
				request.setAttribute("totalFileName", totalfileName);
				request.setAttribute("aeFileName", CreateAEChart(year));
				request.setAttribute("aepie", createPie_AE(year));
	    }
		else
		{
			request.setAttribute("totalFileName", totalfileName);
		}
		if ("1".equals(markString)) {
			return "tpmsuccess";
		} else if ("2".equals(markString)||"3".equals(markString)) {
			return "adminsuccess";
		}else if("4".equals(markString))
		{
			return "guestsuccess";
		}
		return "";
	}

	private String myproname;

	public String getMyproname() {
		return myproname;
	}

	public void setMyproname(String myproname) {
		this.myproname = myproname;
	}

	HttpSession session = request.getSession();
	private String months;
	private String fields_Expense;
	private HttpServletResponse response = ServletActionContext.getResponse();
	public String createBaseTopTen() throws Exception {
		System.out.println(year);
		String[] arrPro = myproname.split(", ");
		String tpmid = session.getAttribute(ip + "eid").toString();
		String[] filenames = new String[arrPro.length];
		JFreeChart[] charts = new JFreeChart[arrPro.length];
		for (int i = 0; i < charts.length; i++) {
			charts[i] = ChartFactory.createStackedBarChart("", "Month",
					"Money", new TpmChartAction().getDataSet(arrPro[i], year),
					PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);
			CategoryPlot plot = charts[i].getCategoryPlot();
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			charts[i].getLegend().setBackgroundPaint(new Color(214, 204, 201));
			Color lineColor = new Color(31, 121, 170);
			plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
			plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			ValueAxis axis = plot.getRangeAxis();
			axis.setAxisLineVisible(true);
			axis.setTickMarksVisible(true);
			// Y轴网格线条
			plot.setRangeGridlinePaint(new Color(192, 192, 192));
			plot.setRangeGridlineStroke(new BasicStroke(1));
			plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
			plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
			plot.setForegroundAlpha(0.65f);
			StandardChartTheme chartTheme = new StandardChartTheme("EN");
			chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
			chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
			chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
			chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
			chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
			chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
			ChartFactory.setChartTheme(chartTheme);
			TextTitle textTitle = new TextTitle(arrPro[i], new Font(
					"sans-serif", Font.PLAIN, 16));
			charts[i].setTitle(textTitle);
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211, 203, 203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot
					.getRenderer();
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			// plot.setDataset(1, new
			// TpmChartAction().createEPD_Budget_Dataset(arrPro[i]));
			// plot.setDataset(2,new
			// TpmChartAction().createAE_Budget_Dataset(arrPro[i]));
			// plot.setDataset(3, new
			// TpmChartAction().createRBEI_Budget_Dataset(arrPro[i]));
			plot.setDataset(4, createTotal_Budget_Dataset(arrPro[i]));
			// LineAndShapeRenderer lineandshaperenderer = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer1 = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer2 = new
			// LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
			// lineandshaperenderer
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer1
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer2
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer3
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
			// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			// plot.setRenderer(1, lineandshaperenderer);
			// plot.setRenderer(2, lineandshaperenderer1);
			// plot.setRenderer(3, lineandshaperenderer2);
			plot.setRenderer(4, lineandshaperenderer3);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			filenames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
					500, null, session);
		}
		request.setAttribute("checkPro", arrPro);
		request.setAttribute("filename", filenames);
		return "basetopten";
	}

	public String createPercentTopTen() throws Exception {
		String[] arrPro = myproname.split(", ");
		String tpmid = session.getAttribute(ip + "eid").toString();
		String[] filenames = new String[arrPro.length];
		JFreeChart[] charts = new JFreeChart[arrPro.length];
		for (int i = 0; i < charts.length; i++) {
			charts[i] = ChartFactory.createStackedBarChart("", "Month",
					"Money", new TpmChartAction().getDataSet(arrPro[i], year),
					PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);
			CategoryPlot plot = charts[i].getCategoryPlot();
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			charts[i].getLegend().setBackgroundPaint(new Color(214, 204, 201));
			Color lineColor = new Color(31, 121, 170);
			plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
			plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			ValueAxis axis = plot.getRangeAxis();
			axis.setAxisLineVisible(true);
			axis.setTickMarksVisible(true);
			// Y轴网格线条
			plot.setRangeGridlinePaint(new Color(192, 192, 192));
			plot.setRangeGridlineStroke(new BasicStroke(1));
			plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
			plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
			plot.setForegroundAlpha(0.65f);
			StandardChartTheme chartTheme = new StandardChartTheme("EN");
			chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
			chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
			chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
			chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
			chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
			chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
			ChartFactory.setChartTheme(chartTheme);
			TextTitle textTitle = new TextTitle(arrPro[i], new Font(
					"sans-serif", Font.PLAIN, 16));
			charts[i].setTitle(textTitle);
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211, 203, 203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot
					.getRenderer();
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			// plot.setDataset(1, new
			// TpmChartAction().createEPD_Budget_Dataset(arrPro[i]));
			// plot.setDataset(2,new
			// TpmChartAction().createAE_Budget_Dataset(arrPro[i]));
			// plot.setDataset(3, new
			// TpmChartAction().createRBEI_Budget_Dataset(arrPro[i]));
			plot.setDataset(4, createTotal_Budget_Dataset(arrPro[i]));
			// LineAndShapeRenderer lineandshaperenderer = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer1 = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer2 = new
			// LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
			// lineandshaperenderer
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer1
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer2
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer3
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
			// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			// plot.setRenderer(1, lineandshaperenderer);
			// plot.setRenderer(2, lineandshaperenderer1);
			// plot.setRenderer(3, lineandshaperenderer2);
			plot.setRenderer(4, lineandshaperenderer3);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			filenames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
					500, null, session);
		}
		request.setAttribute("checkPro", arrPro);
		request.setAttribute("filename", filenames);
		// request.setAttribute("filename", fileName);
		// ================================================
		List<String> list = OverMost();
		request.setAttribute("percentten", list);
		// request.setAttribute("over", over());
		// ===============================================
		return "percenttopten";
	}

	public String createCostTopTen() throws Exception {
		System.out.println("myproname==" + myproname);
		String[] arrPro = myproname.split(", ");
		String tpmid = session.getAttribute(ip + "eid").toString();
		String[] filenames = new String[arrPro.length];
		JFreeChart[] charts = new JFreeChart[arrPro.length];
		for (int i = 0; i < charts.length; i++) {
			charts[i] = ChartFactory.createStackedBarChart("", "Month",
					"Money", new TpmChartAction().getDataSet(arrPro[i], year),
					PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);
			CategoryPlot plot = charts[i].getCategoryPlot();
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			charts[i].getLegend().setBackgroundPaint(new Color(214, 204, 201));
			Color lineColor = new Color(31, 121, 170);
			plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
			plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
			ValueAxis axis = plot.getRangeAxis();
			axis.setAxisLineVisible(true);
			axis.setTickMarksVisible(true);
			// Y轴网格线条
			plot.setRangeGridlinePaint(new Color(192, 192, 192));
			plot.setRangeGridlineStroke(new BasicStroke(1));
			plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
			plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
			plot.setForegroundAlpha(0.65f);
			StandardChartTheme chartTheme = new StandardChartTheme("EN");
			chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
			chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
			chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
			chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
			chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
			chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
			ChartFactory.setChartTheme(chartTheme);
			TextTitle textTitle = new TextTitle(arrPro[i], new Font(
					"sans-serif", Font.PLAIN, 16));
			charts[i].setTitle(textTitle);
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211, 203, 203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			StackedBarRenderer renderer = (StackedBarRenderer) plot
					.getRenderer();
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setMaximumBarWidth(0.5);
			renderer.setMinimumBarLength(0.4);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(1, Color.RED);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			// plot.setDataset(1, new
			// TpmChartAction().createEPD_Budget_Dataset(arrPro[i]));
			// plot.setDataset(2,new
			// TpmChartAction().createAE_Budget_Dataset(arrPro[i]));
			// plot.setDataset(3, new
			// TpmChartAction().createRBEI_Budget_Dataset(arrPro[i]));
			plot.setDataset(4, createTotal_Budget_Dataset(arrPro[i]));
			// LineAndShapeRenderer lineandshaperenderer = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer1 = new
			// LineAndShapeRenderer();
			// LineAndShapeRenderer lineandshaperenderer2 = new
			// LineAndShapeRenderer();
			LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
			// lineandshaperenderer
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer1
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer2
			// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer3
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
			// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
			// lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
			lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			// plot.setRenderer(1, lineandshaperenderer);
			// plot.setRenderer(2, lineandshaperenderer1);
			// plot.setRenderer(3, lineandshaperenderer2);
			plot.setRenderer(4, lineandshaperenderer3);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			filenames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000,
					500, null, session);
		}
		request.setAttribute("checkPro", arrPro);
		request.setAttribute("filename", filenames);
		return "costtopten";
	}

	Double over() {
		Double per = null;
		String sql = "select sum(M_T_Expense),sum(M_A_Expense),sum(M_R_Expense),sum(M_T_Budget),sum(M_A_Budget),sum(M_R_Budget)from tb_Summary where proName=?";
		String[] paras = { proType };
		ArrayList<Object[]> alArrayList = new BasicDB().queryDB(sql, paras);
		Object[] objects = alArrayList.get(0);
		Double expense = Double.parseDouble(objects[0].toString())
				+ Double.parseDouble(objects[1].toString())
				+ Double.parseDouble(objects[2].toString());
		Double budget = Double.parseDouble(objects[3].toString())
				+ Double.parseDouble(objects[4].toString())
				+ Double.parseDouble(objects[5].toString());
		per = (expense - budget) / budget;
		return per;
	}

	public List<String> OverMost() {
		ArrayList<String> topproject = new ArrayList<String>();
		ArrayList<String> allproject = new ArrayList<String>();
		Map<String, Double> hm = new HashMap<String, Double>();
		String sql = "select distinct proName from tb_Summary where Year='"+year+"' and TPM<>'null'";
		BasicDB basicDB = new BasicDB();
		allproject = basicDB.SQuerydb(sql);
		int currentYear=Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
		int countMonth;
		if(currentYear==Integer.parseInt(year))
			countMonth=currentMonth;
		else 
			countMonth=12;
		if(allproject!=null&&allproject.size()!=0){
			for (int i = 0; i < allproject.size(); i++) {
				String proString = allproject.get(i).trim();
				String sqlString = "select sum(M_T_Expense),sum(M_A_Expense),sum(M_R_Expense) from tb_Summary where proName=? and Year=?";
				String[] paras = { proString,year};
				ArrayList<Object[]> CostList = basicDB.queryDB(sqlString, paras);
				int totalCost=0;
				int eCost=0;
				int aCost=0;
				int rCost=0;
				
				int totalBudget=0;
				int eBudget=0;
				int aBudget=0;
				int rBudget=0;
				ArrayList<Object[]>budgetList=new ArrayList<Object[]>();
				if(CostList==null||CostList.size()==0)
				{
					String budget_sql="select Budget,EPD_Budget,AE_Budget,RBEI_Budget from tb_ProBudget where proName=? and Year=?";
					budgetList=basicDB.queryDB(budget_sql, paras);
					if(budgetList==null||budgetList.size()==0)
					{
						totalBudget=0;
						eBudget=0;
						aBudget=0;
						rBudget=0;
						hm.put(proString, 0.0);
					}else {
						Object[]budgetObject=budgetList.get(0);
						totalBudget=Integer.parseInt(budgetObject[0].toString().trim())*countMonth/12;
						hm.put(proString, -1.0);
					}
				}else
				{
					Object[]costObject=CostList.get(0);
					eCost=(int)Double.parseDouble(costObject[0].toString().trim());
					aCost=(int)Double.parseDouble(costObject[1].toString().trim());
					rCost=(int)Double.parseDouble(costObject[2].toString().trim());
					totalCost=eCost+aCost+rCost;
					String budget_sql="select Budget,EPD_Budget,AE_Budget,RBEI_Budget from tb_ProBudget where proName=? and Year=?";
					budgetList=basicDB.queryDB(budget_sql, paras);
					if(budgetList==null||budgetList.size()==0)
					{
						totalBudget=0;
						eBudget=0;
						aBudget=0;
						rBudget=0;
						hm.put(proString, 100.0);
					}else {
						Object[]budgetObject=budgetList.get(0);
						totalBudget=Integer.parseInt(budgetObject[0].toString().trim())*countMonth/12;
						hm.put(proString, 1.0*(totalCost-totalBudget)/totalBudget);
					}
				}
			}
			List<Map.Entry<String,Double>> list_Data=new ArrayList<Map.Entry<String,Double>>(hm.entrySet());
			Collections.sort(list_Data,
					new Comparator<Map.Entry<String, Double>>() {
						public int compare(Map.Entry<String, Double> o1,
								Map.Entry<String, Double> o2) {
							if ((o2.getValue() - o1.getValue())>0)  
						          return 1;  
						        else if((o2.getValue() - o1.getValue())==0)  
						          return 0;  
						        else   
						          return -1;  
						}
					});
			for(int i=0;i<10;i++)
			{
				topproject.add(list_Data.get(i).getKey());
			}
		}else {
			return null;
		}
		return topproject;
	}

	public List<String> projectInit(String tpmname,String year) {
		List<Object[]> pList = new ArrayList<Object[]>();
		List<String> list = new ArrayList<String>();
		if("70818773".equals(tpmname))
		{
			System.out.println(tpmname);
			list.add("RBEI");
		}
		else
		{
			BasicDB basicDB = new BasicDB();
			String sql = "select distinct tb_Summary.ProName from tb_Summary where TPM=? and Year=?";
			String[] paras = { tpmname ,year};
			pList = basicDB.queryDB(sql, paras);
			if(pList!=null&&pList.size()!=0)
			{
				for (int i = 0; i < pList.size(); i++) {
					Object[] objects = pList.get(i);
					list.add(objects[0].toString().trim());
				}	
			}else {
				list.add("RBEI");
			}
		}
		return list;
	}

	public CategoryDataset getDataset_E_A(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,M_A_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 4;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "EPD_Expense","Material_Expense", "AE_Expense" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataset_E_R(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 4;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "EPD_Expense", "Material_Expense","RBEI_Expense" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataset_A_R(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_A_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 3;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "AE_Expense", "RBEI_Expense" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataset_E(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col =3;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "EPD_Expense" ,"Material_Expense"};
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataset_A(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_A_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 2;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "AE_Expense" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataset_R(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 2;
		ArrayList<Object[]> original = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(original, col, year);
		double data[][] = ReturnData(afterList);
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		String[] fields_Expense = { "RBEI_Expense" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset getDataSet(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,M_A_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim(), year };
		int col = 5;
		ArrayList<Object[]> orginalList = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList = AddNoMonthObject(orginalList, col, year);
		double data[][] = ReturnData(afterList);
		String[] fields_Expense = { "EPD_Expense", "Material_Expense","AE_Expense", "RBEI_Expense" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset createEPD_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget = "select EPD_Budget from tb_ProBudget where Year='"
				+ year + "' and ProName like '%"+proString.trim()+"%'";
		ArrayList<String> budget = basicDB.SQuerydb(findBudget);
		double epd_Budget = Double.parseDouble(budget.get(0).toString());
		System.out.println("EPD_Budget:"+epd_Budget);
		double data[][] = new double[1][12];
		for (int i = 1; i <= 12; i++)
			data[0][i - 1] = (epd_Budget * i / 12);
		String[] fields_Expense = { "EPD_Budget" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset createAE_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget = "select AE_Budget from tb_ProBudget where Year='"
				+ year + "' and ProName like '%"+proString.trim()+"%'";
		ArrayList<String> budget = basicDB.SQuerydb(findBudget);
		double ae_Budget = Double.parseDouble(budget.get(0).toString());
		double data[][] = new double[1][12];
		for (int i = 1; i <= 12; i++)
			data[0][i - 1] = (ae_Budget * i / 12);
		String[] fields_Expense = { "AE_Budget" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset createRBEI_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget = "select RBEI_Budget from tb_ProBudget where Year='"
				+ year + "' and ProName like '%"+proString.trim()+"%'";
		ArrayList<String> budget = basicDB.SQuerydb(findBudget);
		double rbei_Budget = Double.parseDouble(budget.get(0).toString());
		double data[][] = new double[1][12];
		for (int i = 1; i <= 12; i++)
			data[0][i - 1] = (rbei_Budget * i / 12);
		String[] fields_Expense = { "RBEI_Budget" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public CategoryDataset createTotal_Budget_Dataset(String proString) {
		System.out.println("Total_Budget="+year+"-"+proString);
		BasicDB basicDB = new BasicDB();
		String findBudget_sql = "select Budget from tb_ProBudget where Year='"+year+"' and ProName like '%"+proString.trim()+"%'";
		ArrayList<String> budget = basicDB.SQuerydb(findBudget_sql);		
		double totalBudget = 0;
		if(budget==null||budget.size()==0)
			totalBudget=0;
		else {
			totalBudget=Double.parseDouble(budget.get(0).toString().trim());
		}
		System.out.println(totalBudget);		
		double[][] data = new double[1][12];
		for (int i = 1; i <= 12; i++)
			data[0][i - 1] = (totalBudget * i / 12);
		String[] fields_Expense = { "Total_Budget" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}

	public void setTextTitle() {
		String sqlString = "select sum(M_T_Expense),sum(M_A_Expense),sum(M_R_Expense),sum(M_T_Budget),sum(M_A_Budget),sum(M_R_Budget)from tb_Summary where proName=?";
		String[] paras = { proType };
		BasicDB basicDB = new BasicDB();
		ArrayList<Object[]> aList = basicDB.queryDB(sqlString, paras);
		Object[] objects = aList.get(0);
		float e_Expense = Float.parseFloat(objects[0].toString().trim());
		float a_Expense = Float.parseFloat(objects[1].toString().trim());
		float r_Expense = Float.parseFloat(objects[2].toString().trim());
		float e_Budget = Float.parseFloat(objects[3].toString().trim());
		float a_Budget = Float.parseFloat(objects[4].toString().trim());
		float r_Budget = Float.parseFloat(objects[5].toString().trim());

		float all_Expense = e_Expense + a_Expense + r_Expense;
		float all_Budget = e_Budget + a_Budget + r_Budget;

		float allper = (all_Expense - all_Budget) / all_Budget;
		float eper = (e_Expense - e_Budget) / e_Budget;
		float aper = (a_Expense - a_Budget) / a_Budget;
		float rper = (r_Expense - r_Budget) / r_Budget;

		Font font = new Font("sans-serif", Font.BOLD, 16);
		arrTitle[0] = new TextTitle(proType + "---EPD,AE,RBEI", font);
		arrTitle[1] = new TextTitle(proType + "---EPD", font);
		arrTitle[2] = new TextTitle(proType + "---AE", font);
		arrTitle[3] = new TextTitle(proType + "---RBEI", font);
		if (allper <= 0) {
			arrTitle[0].setPaint(Color.GREEN);
		} else {
			arrTitle[0].setPaint(Color.RED);
		}

		if (eper <= 0) {
			arrTitle[1].setPaint(Color.GREEN);
		} else {
			arrTitle[1].setPaint(Color.RED);
		}

		if (aper <= 0) {
			arrTitle[2].setPaint(Color.GREEN);
		} else {
			arrTitle[2].setPaint(Color.RED);
		}

		if (rper <= 0) {
			arrTitle[3].setPaint(Color.GREEN);
		} else {
			arrTitle[3].setPaint(Color.RED);
		}

	}

	public double[][] ReturnData(ArrayList<Object[]> alArrayList) {
		double data[][] = null;
		int size = alArrayList.size();
		Object[] fObjects = alArrayList.get(0);
		Object[] lObjects = alArrayList.get(size - 1);
		int cols = fObjects.length;
		int endMonth = Integer.parseInt(lObjects[cols - 1].toString().trim());
		data = new double[cols - 1][12];
		int[] months = new int[size];
		for (int i = 0; i < alArrayList.size(); i++) {
			Object[] objects = alArrayList.get(i);
			months[i] = Integer.parseInt(objects[cols - 1].toString().trim());
			for (int j = 0; j < cols - 1; j++) {
				data[j][i] = (int) Double.parseDouble((objects[j].toString()
						.trim()));
			}
		}
		for (int i = 1; i < endMonth; i++) {
			for (int k = 0; k < cols - 1; k++) {
				data[k][i] = data[k][i] + data[k][i - 1];
			}
		}
		for (int i = endMonth + 1; i <= 12; i++) {
			for (int j = 0; j < cols - 1; j++) {
				data[j][i - 1] = 0;
			}
		}
		return data;
	}


	public ArrayList<Object[]> AddNoMonthObject(ArrayList<Object[]> original,
			int col, String year) {
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentYear = calendar.get(Calendar.YEAR);
		ArrayList<Object[]> afterObjects = new ArrayList<Object[]>();
		int endMonth = currentMonth;
		if (original == null) {
			if (currentYear == Integer.parseInt(year)) {
				for (int i = 1; i <= endMonth; i++) {
					Object[] bc = new Object[col];
					for (int j = 0; j < col; j++)
						bc[j] = 0;
					afterObjects.add(bc);
				}
			} else {
				endMonth = 12;
				for (int i = 1; i <= endMonth; i++) {
					Object[] bc = new Object[col];
					for (int j = 0; j < col; j++)
						bc[j] = 0;
					afterObjects.add(bc);
				}
			}
		} else {
			if (currentYear == Integer.parseInt(year)) {
				endMonth = currentMonth;
			} else {
				endMonth = 12;
			}
			for (int i = 1; i <= endMonth; i++) {
				String tempMonth = i + "";
				int j = 0;
				for (j = 0; j < original.size(); j++) {
					Object[] existObject = original.get(j);
					if (tempMonth
							.equals(existObject[col - 1].toString().trim())) {
						afterObjects.add(existObject);
						break;
					}
				}
				if (j == original.size()) {
					Object[] bc = new Object[col];
					int k = 0;
					for (k = 0; k < col - 1; k++)
						bc[k] = 0;
					bc[k] = i;
					afterObjects.add(bc);
				}
			}
		}
		return afterObjects;
	}

	
	public CategoryDataset getTotalDataset(String year) throws Exception
	{   
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_A_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=5;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_EPD_Expense","Total_Material_Expense","Total_AE_Expense", "Total_RBEI_Expense" };
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_E_A(String year) throws Exception
	{   
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_A_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=4;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_EPD_Expense","Total_Material_Expense","Total_AE_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_E_R(String year) throws Exception
	{   
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=4;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_EPD_Expense","Total_Material_Expense","Total_RBEI_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_A_R(String year) throws Exception
	{   
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_A_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=3;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_AE_Expense","Total_RBEI_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_E(String year) throws Exception
	{ 
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=3;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_EPD_Expense","Total_Material_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_A(String year) throws Exception
	{  
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_A_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=2;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_AE_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	public CategoryDataset getTotalDataset_R(String year) throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmname,year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=2;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={"Total_RBEI_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	
	public CategoryDataset createEPDBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(EPD_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+Common.getEmpNameByNo(tpmname)+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=Double.parseDouble(budget.get(0).toString());
		System.out.println(totalbudget);
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={"Total_EPD_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset createAEBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(AE_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+Common.getEmpNameByNo(tpmname)+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=Double.parseDouble(budget.get(0).toString());
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={"Total_AE_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset createRBEIBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(RBEI_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+Common.getEmpNameByNo(tpmname)+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=Double.parseDouble(budget.get(0).toString());
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={"Total_RBEI_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset createTotalBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+Common.getEmpNameByNo(tpmname)+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			totalbudget=0;
		}else
		{
			totalbudget=Double.parseDouble(budget.get(0).toString());
		}	    
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={"Total_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public String CreateAEChart(String year) throws Exception
	{
		String file_ae="";
		JFreeChart jFreeChart=null;
		jFreeChart = ChartFactory.createStackedBarChart("", "Month",
				"Money",getTotalDataset_A_AllProjects(year),
				PlotOrientation.VERTICAL, true, true, false);
		jFreeChart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jFreeChart.setTextAntiAlias(false);
	    CategoryPlot plot = jFreeChart.getCategoryPlot();
	    jFreeChart.setTextAntiAlias(false);
	    jFreeChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
	    TextTitle textTitle = new TextTitle(year+"_AE",
				new Font("sans-serif", Font.PLAIN, 16));
	    jFreeChart.setTitle(textTitle);
	    jFreeChart.getLegend().setBackgroundPaint(
				new Color(214, 204, 201));
	    Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	    ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLineVisible(true);
		axis.setTickMarksVisible(true);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		plot.setForegroundAlpha(0.65f);
		StandardChartTheme chartTheme = new StandardChartTheme("EN");
		chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
		// 渲染
		ChartFactory.setChartTheme(chartTheme);
	    NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
	    CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
				12));
		domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
				12));
		numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		jFreeChart.getLegend().setItemFont(
				new Font("sans-serif", Font.PLAIN, 12));
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.PINK);
		StackedBarRenderer renderer = (StackedBarRenderer) plot
				.getRenderer();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.4);
		renderer.setMinimumBarLength(0.2);
		renderer.setBaseOutlinePaint(Color.BLACK);
		renderer.setDrawBarOutline(true);
		// renderer.setSeriesPaint(0, Color.GREEN);
		// renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(0, Color.YELLOW);
		renderer.setItemMargin(0.4);
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setShadowVisible(false);
		plot.setRenderer(renderer);
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
		// plot.setDataset(2,createAE_Budget_Dataset());
		plot.setDataset(3, createAEofAllProjects(year));
		// plot.setDataset(4, createTotal_Budget_Dataset());
		// LineAndShapeRenderer lineandshaperenderer = new
		// LineAndShapeRenderer();
		// LineAndShapeRenderer lineandshaperenderer1 = new
		// LineAndShapeRenderer();
		LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
		// LineAndShapeRenderer lineandshaperenderer3 = new
		// LineAndShapeRenderer();
		// lineandshaperenderer
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer1
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		lineandshaperenderer2
				.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer3
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
		// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
		lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
		// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
		// plot.setRenderer(1, lineandshaperenderer);
		// plot.setRenderer(2, lineandshaperenderer1);
		plot.setRenderer(3, lineandshaperenderer2);
		// plot.setRenderer(4, lineandshaperenderer3);
	    CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis
				.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		file_ae = ServletUtilities.saveChartAsPNG(jFreeChart, 1000,
				500, null, session);
		return file_ae;
	}
	
	public CategoryDataset createAEofAllProjects(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(AE_Budget) from tb_ProBudget where tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=Double.parseDouble(budget.get(0).toString());
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={year+"_Total_AE_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset getTotalDataset_A_AllProjects(String year) throws Exception
	{  
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_A_Expense),Month from tb_Summary where  Year=? group by Month";
		String []expense_paras={year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=2;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={year+"_Total_AE_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	
	
	public CategoryDataset createRBEIofAllProjects(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(RBEI_Budget) from tb_ProBudget where tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=Double.parseDouble(budget.get(0).toString());
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalbudget*i/12;
		}
		String []field_Expense={year+"_Total_RBEI_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(field_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset getTotalDataset_R_AllProjects(String year) throws Exception
	{  
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_R_Expense),Month from tb_Summary where  Year=? group by Month";
		String []expense_paras={year};
		ArrayList<Object[]>queryexpenseList=basicDB.queryDB(expense_sql, expense_paras);
		int col=2;
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(queryexpenseList,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String[] fields_Expense={year+"_Total_RBEI_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset=DatasetUtilities.createCategoryDataset(fields_Expense, months,data);
		return dataset;
	}
	
	public String CreateRBEIChart(String year) throws Exception
	{
		String file_rbei="";
		JFreeChart jFreeChart=null;
		jFreeChart = ChartFactory.createStackedBarChart("", "Month",
				"Money",getTotalDataset_R_AllProjects(year),
				PlotOrientation.VERTICAL, true, true, false);
		jFreeChart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jFreeChart.setTextAntiAlias(false);
	    CategoryPlot plot = jFreeChart.getCategoryPlot();
	    jFreeChart.setTextAntiAlias(false);
	    jFreeChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
	    TextTitle textTitle = new TextTitle(year+"_RBEI",
				new Font("sans-serif", Font.PLAIN, 16));
	    jFreeChart.setTitle(textTitle);
	    jFreeChart.getLegend().setBackgroundPaint(
				new Color(214, 204, 201));
	    Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	    ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLineVisible(true);
		axis.setTickMarksVisible(true);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		plot.setForegroundAlpha(0.65f);
		StandardChartTheme chartTheme = new StandardChartTheme("EN");
		chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar
		// 渲染
		ChartFactory.setChartTheme(chartTheme);
	    NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
	    CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
				12));
		domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN,
				12));
		numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		jFreeChart.getLegend().setItemFont(
				new Font("sans-serif", Font.PLAIN, 12));
		plot.setBackgroundPaint(new Color(211, 203, 203));
		plot.setDomainGridlinePaint(Color.BLUE);
		plot.setRangeGridlinePaint(Color.PINK);
		StackedBarRenderer renderer = (StackedBarRenderer) plot
				.getRenderer();
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.4);
		renderer.setMinimumBarLength(0.2);
		renderer.setBaseOutlinePaint(Color.BLACK);
		renderer.setDrawBarOutline(true);
		// renderer.setSeriesPaint(0, Color.GREEN);
		// renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(0, Color.CYAN);
		renderer.setItemMargin(0.4);
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setShadowVisible(false);
		plot.setRenderer(renderer);
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
		// plot.setDataset(2,createAE_Budget_Dataset());
		plot.setDataset(3, createRBEIofAllProjects(year));
		// plot.setDataset(4, createTotal_Budget_Dataset());
		// LineAndShapeRenderer lineandshaperenderer = new
		// LineAndShapeRenderer();
		// LineAndShapeRenderer lineandshaperenderer1 = new
		// LineAndShapeRenderer();
		LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
		// LineAndShapeRenderer lineandshaperenderer3 = new
		// LineAndShapeRenderer();
		// lineandshaperenderer
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer1
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		lineandshaperenderer2
				.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer3
		// .setToolTipGenerator(new StandardCategoryToolTipGenerator());
		// lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
		// lineandshaperenderer1.setSeriesPaint(0, Color.RED);
		lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
		// lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
		// plot.setRenderer(1, lineandshaperenderer);
		// plot.setRenderer(2, lineandshaperenderer1);
		plot.setRenderer(3, lineandshaperenderer2);
		// plot.setRenderer(4, lineandshaperenderer3);
	    CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis
				.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		file_rbei = ServletUtilities.saveChartAsPNG(jFreeChart, 1000,
				500, null, session);
		return file_rbei;
	}
	
	
	public String createPie_AE(String year)throws Exception
	{
		JFreeChart jFreeChart=null;
		String filename;
		DefaultPieDataset dataset = new DefaultPieDataset();
		String pieName=year+"_AE_PieChart";
		String sql="select ProName,sum(M_A_Expense)k from tb_Summary where Year=? group by ProName order by k desc";
		String []paras={year};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>alist=basicDB.queryDB(sql, paras);
		HashMap<String, Double>hm=new HashMap<String, Double>();
		for(int i=0;i<10;i++)
		{
			Object[]object=alist.get(i);
			String pname=object[0].toString().trim();
			Double aeDouble=Double.parseDouble(object[1].toString().trim());
			hm.put(pname, aeDouble);
		}
		Double otherDouble=0.0;
		for(int i=10;i<alist.size();i++)
		{
			Object[]object=alist.get(i);
			otherDouble=otherDouble+Double.parseDouble(object[1].toString().trim());
		}
		hm.put("Other", otherDouble);
		Iterator iter = hm.entrySet().iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			String []info=key.split("=");
			dataset.setValue(info[0], Double.parseDouble(info[1]));
		} 
		jFreeChart = ChartFactory.createPieChart(pieName, dataset, true,
				true, false);
		PiePlot pieplot = (PiePlot) jFreeChart.getPlot();
		pieplot.setBackgroundPaint(new Color(238, 224, 229));
		pieplot.setLabelFont(new Font("sans-serif", 0, 11));
		pieplot.setNoDataMessage("No Data");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.06D);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		filename = ServletUtilities.saveChartAsPNG(jFreeChart, 910, 800,
				null, session);
		return filename;
	}
	
	public String createPie_RBEI(String year)throws Exception
	{
		JFreeChart jFreeChart=null;
		String filename;
		DefaultPieDataset dataset = new DefaultPieDataset();
		String pieName=year+"_RBEI_PieChart";
		String sql="select ProName,sum(M_R_Expense)k from tb_Summary where Year=? group by ProName order by k desc";
		String []paras={year};
		BasicDB basicDB=new BasicDB();
		ArrayList<Object[]>alist=basicDB.queryDB(sql, paras);
		HashMap<String, Double>hm=new HashMap<String, Double>();
		for(int i=0;i<10;i++)
		{
			Object[]object=alist.get(i);
			String pname=object[0].toString().trim();
			Double aeDouble=Double.parseDouble(object[1].toString().trim());
			hm.put(pname, aeDouble);
		}
		Double otherDouble=0.0;
		for(int i=10;i<alist.size();i++)
		{
			Object[]object=alist.get(i);
			otherDouble=otherDouble+Double.parseDouble(object[1].toString().trim());
		}
		hm.put("Other", otherDouble);
		Iterator iter = hm.entrySet().iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			String []info=key.split("=");
			dataset.setValue(info[0], Double.parseDouble(info[1]));
		} 
		jFreeChart = ChartFactory.createPieChart(pieName, dataset, true,
				true, false);
		PiePlot pieplot = (PiePlot) jFreeChart.getPlot();
		pieplot.setBackgroundPaint(new Color(238, 224, 229));
		pieplot.setLabelFont(new Font("sans-serif", 0, 11));
		pieplot.setNoDataMessage("No Data");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.06D);
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		filename = ServletUtilities.saveChartAsPNG(jFreeChart, 910, 800,
				null, session);
		return filename;
	}
}
