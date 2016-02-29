package com.xgg.epd.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.Renderer;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xgg.epd.dbs.BasicDB;

public class TpmChartAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String myproname;
	private String chartType;

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getMyproname() {
		return myproname;
	}

	public void setMyproname(String myproname) {
		this.myproname = myproname;
	}

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;

	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	String[] row = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };
	String ip = request.getRemoteAddr().toString();
	String enameString = session.getAttribute(ip + "EmpName").toString();

	public String execute() throws Exception {
		session.setAttribute(ip + "tpmchartYear", year);
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
		String[] arrpros = myproname.split(", ");
		String tpmid = session.getAttribute(ip + "eid").toString();
		String[] fileNames = new String[arrpros.length];
		JFreeChart[] charts = new JFreeChart[arrpros.length];
		
		JFreeChart totalChart=null;
		String totalfileName="";
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
		    TextTitle textTitle=new TextTitle("All Projects--[EPD][AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataSet(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			   textTitle=new TextTitle(arrpros[i]+"--[EPD][AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
				lineColor = new Color(31, 121, 170);
		        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        axis = plot.getRangeAxis();
		        axis.setAxisLineVisible(true);
		        axis.setTickMarksVisible(true);
		         //Y轴网格线条
		        plot.setRangeGridlinePaint(new Color(192, 192, 192));
		        plot.setRangeGridlineStroke(new BasicStroke(1));	 
		        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
		        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		        plot.setForegroundAlpha(0.65f);
		        chartTheme=new StandardChartTheme("EN");
		        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		        ChartFactory.setChartTheme(chartTheme);	  
				numberAxis = (NumberAxis) plot.getRangeAxis();
				domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211,203,203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
				renderer = (StackedBarRenderer) plot.getRenderer();
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
//				plot.setDataset(1,createEPD_Budget_Dataset());
//				plot.setDataset(2,createAE_Budget_Dataset());
//				plot.setDataset(3, createRBEI_Budget_Dataset());
				plot.setDataset(4, createTotal_Budget_Dataset(arrpros[i]));
//				LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				lineandshaperenderer3 = new LineAndShapeRenderer();
//				lineandshaperenderer
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer1
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer2
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer3
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//				lineandshaperenderer1.setSeriesPaint(0, Color.RED);
//				lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
//				plot.setRenderer(1, lineandshaperenderer);
//				plot.setRenderer(2, lineandshaperenderer1);
//				plot.setRenderer(3, lineandshaperenderer2);
				plot.setRenderer(4, lineandshaperenderer3);
				categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD, AE".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E_A(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--[EPD][AE]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_E_A(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle=new TextTitle(arrpros[i]+"--[EPD][AE]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
				lineColor = new Color(31, 121, 170);
		        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        axis = plot.getRangeAxis();
		        axis.setAxisLineVisible(true);
		        axis.setTickMarksVisible(true);
		         //Y轴网格线条
		        plot.setRangeGridlinePaint(new Color(192, 192, 192));
		        plot.setRangeGridlineStroke(new BasicStroke(1));	 
		        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
		        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		        plot.setForegroundAlpha(0.65f);
		        chartTheme=new StandardChartTheme("EN");
		        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		        ChartFactory.setChartTheme(chartTheme);	  
				numberAxis = (NumberAxis) plot.getRangeAxis();
				domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211,203,203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
				renderer = (StackedBarRenderer) plot.getRenderer();
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
				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
//				plot.setDataset(3, createRBEI_Budget_Dataset());
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
				lineandshaperenderer = new LineAndShapeRenderer();
				lineandshaperenderer1 = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer2
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
//				lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
//				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD, RBEI".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--[EPD][RBEI]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_E_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    textTitle=new TextTitle(arrpros[i]+"--[EPD][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
				lineColor = new Color(31, 121, 170);
		        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        axis = plot.getRangeAxis();
		        axis.setAxisLineVisible(true);
		        axis.setTickMarksVisible(true);
		         //Y轴网格线条
		        plot.setRangeGridlinePaint(new Color(192, 192, 192));
		        plot.setRangeGridlineStroke(new BasicStroke(1));	 
		        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
		        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		        plot.setForegroundAlpha(0.65f);
		        chartTheme=new StandardChartTheme("EN");
		        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		        ChartFactory.setChartTheme(chartTheme);	  
				numberAxis = (NumberAxis) plot.getRangeAxis();
				domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211,203,203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
				renderer = (StackedBarRenderer) plot.getRenderer();
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
				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
	//			plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3,createRBEI_Budget_Dataset(arrpros[i]));
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
			   lineandshaperenderer = new LineAndShapeRenderer();
	//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer1
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//				lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
//				plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1100, 500, null,
						session);
			}
		}else if("AE, RBEI".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_A_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--[AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_A_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
				textTitle=new TextTitle(arrpros[i]+"--[AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
				lineColor = new Color(31, 121, 170);
		        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        axis = plot.getRangeAxis();
		        axis.setAxisLineVisible(true);
		        axis.setTickMarksVisible(true);
		         //Y轴网格线条
		        plot.setRangeGridlinePaint(new Color(192, 192, 192));
		        plot.setRangeGridlineStroke(new BasicStroke(1));	 
		        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
		        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
		        plot.setForegroundAlpha(0.65f);
		        chartTheme=new StandardChartTheme("EN");
		        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
		        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
		        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
		        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		        ChartFactory.setChartTheme(chartTheme);	  
				numberAxis = (NumberAxis) plot.getRangeAxis();
				domainAxis = plot.getDomainAxis();
				domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
				charts[i].getLegend().setItemFont(
						new Font("sans-serif", Font.PLAIN, 12));
				plot.setBackgroundPaint(new Color(211,203,203));
				plot.setDomainGridlinePaint(Color.BLUE);
				plot.setRangeGridlinePaint(Color.PINK);
				renderer = (StackedBarRenderer) plot.getRenderer();
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true); 
				renderer.setMaximumBarWidth(0.4);
				renderer.setMinimumBarLength(0.2);
				renderer.setBaseOutlinePaint(Color.BLACK);
				renderer.setDrawBarOutline(true);
				renderer.setSeriesPaint(0, Color.YELLOW);
				renderer.setSeriesPaint(1, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
//				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3,createRBEI_Budget_Dataset(arrpros[i]));
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
		//		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
				 lineandshaperenderer1 = new LineAndShapeRenderer();
				 lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//				lineandshaperenderer
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			//	plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_E(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--EPD",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_E(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
			plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			textTitle=new TextTitle(arrpros[i]+"--[EPD]",new Font("sans-serif",Font.PLAIN,16));
			charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
			lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			numberAxis = (NumberAxis) plot.getRangeAxis();
			domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
			renderer.setSeriesPaint(0, Color.GREEN);
		    renderer.setSeriesPaint(1, Color.RED);
//			renderer.setSeriesPaint(0, Color.RED);
//			renderer.setSeriesPaint(1, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
			//plot.setDataset(2,createAE_Budget_Dataset());
			//plot.setDataset(3, createRBEI_Budget_Dataset());
//			plot.setDataset(4, createTotal_Budget_Dataset());
		    lineandshaperenderer = new LineAndShapeRenderer();
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
//			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(3, lineandshaperenderer2);
//			plot.setRenderer(4, lineandshaperenderer3);
			categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}else if("AE".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_A(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--[AE]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_A(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
			plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
		    textTitle=new TextTitle(arrpros[i]+"--[AE]",new Font("sans-serif",Font.PLAIN,16));
	        charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
			lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			numberAxis = (NumberAxis) plot.getRangeAxis();
		    domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
			renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
	//		renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(0, Color.YELLOW);
//			renderer.setSeriesPaint(1, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
			lineandshaperenderer1 = new LineAndShapeRenderer();
			lineandshaperenderer1
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
			//plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
			categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}else if("RBEI".equals(temp))
		{
			totalChart = ChartFactory.createStackedBarChart("", "Month", "Money",
					getTotalDataset_R(year), PlotOrientation.VERTICAL, true, true, false);
			totalChart.getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			totalChart.setTextAntiAlias(false);			
			CategoryPlot plot =totalChart.getCategoryPlot();			
			totalChart.setTextAntiAlias(false);
			totalChart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle("All Projects--[RBEI]",new Font("sans-serif",Font.PLAIN,16));
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
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
		    plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			textTitle=new TextTitle(arrpros[i]+"--[RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
			lineColor = new Color(31, 121, 170);
	        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
	        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
	        axis = plot.getRangeAxis();
	        axis.setAxisLineVisible(true);
	        axis.setTickMarksVisible(true);
	         //Y轴网格线条
	        plot.setRangeGridlinePaint(new Color(192, 192, 192));
	        plot.setRangeGridlineStroke(new BasicStroke(1));	 
	        plot.getRangeAxis().setUpperMargin(0.2);// 设置顶部Y坐标轴间距,防止数据无法显示
	        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距
	        plot.setForegroundAlpha(0.65f);
	        chartTheme=new StandardChartTheme("EN");
	        chartTheme.setPlotBackgroundPaint(Color.GRAY);// 绘制区域
	        chartTheme.setPlotOutlinePaint(Color.white);// 绘制区域外边框
	        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
	        chartTheme.setTickLabelPaint(Color.BLACK);// 刻度数字
	        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
	        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
	        ChartFactory.setChartTheme(chartTheme);	  
			numberAxis = (NumberAxis) plot.getRangeAxis();
			domainAxis = plot.getDomainAxis();
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			domainAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberAxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			charts[i].getLegend().setItemFont(
					new Font("sans-serif", Font.PLAIN, 12));
			plot.setBackgroundPaint(new Color(211,203,203));
			plot.setDomainGridlinePaint(Color.BLUE);
			plot.setRangeGridlinePaint(Color.PINK);
		    renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true); 
			renderer.setMaximumBarWidth(0.4);
			renderer.setMinimumBarLength(0.2);
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer.setDrawBarOutline(true);
//			renderer.setSeriesPaint(0, Color.GREEN);
//			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesPaint(0, Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			//plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
			//plot.setDataset(2,createAE_Budget_Dataset());
			plot.setDataset(3, createRBEI_Budget_Dataset(arrpros[i]));
//			plot.setDataset(4, createTotal_Budget_Dataset());
			//LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
		    lineandshaperenderer2 = new LineAndShapeRenderer();
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
//			plot.setRenderer(4, lineandshaperenderer3);
		    categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}
		request.setAttribute("chartType", chartType);
		request.setAttribute("checkPro", arrpros);
		request.setAttribute("filename", fileNames);
		request.setAttribute("totalFileName", totalfileName);
		return SUCCESS;
	}
	
	
	
	public CategoryDataset getTotalDataset(String year) throws Exception
	{   
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_A_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_A_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_A_Expense),sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_T_Expense),sum(Material_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_A_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
		String tpmid = session.getAttribute(ip + "eid").toString();
		BasicDB basicDB=new BasicDB();
		String expense_sql="select sum(M_R_Expense),Month from tb_Summary where TPM=? and Year=? group by Month";
		String []expense_paras={tpmid,year};
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
	public CategoryDataset getDataSet(String proType,String year)
			throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,M_A_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proType.trim(), year };
		int col=5;
		ArrayList<Object[]> orginalList = basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(orginalList,col,year);
		double data[][] = new AllChartAction().ReturnData(afterList);
		String[] fields_Expense = { "EPD_Expense", "Material_Expense","AE_Expense", "RBEI_Expense" };
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = row[i];
		}
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset createEPDBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(EPD_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+enameString+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalbudget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			totalbudget=0;
		}else {
			totalbudget=Double.parseDouble(budget.get(0).toString());
		}
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
	public CategoryDataset createEPD_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget="select EPD_Budget from tb_ProBudget where Year='"+year+"' and ProName='"+proString.trim()+"'";
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double epd_Budget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			epd_Budget=0;
		}else {
			epd_Budget=Double.parseDouble(budget.get(0).toString());
		}
		double data[][] =new double[1][12];
		for(int i=1;i<=12;i++)
			data[0][i-1]=(epd_Budget*i/12);
		String[] fields_Expense = { "EPD_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset createAEBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(AE_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+enameString+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double ae_Budget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			ae_Budget=0;
		}else {
			ae_Budget=Double.parseDouble(budget.get(0).toString());
		}
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=ae_Budget*i/12;
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
	public CategoryDataset createAE_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget="select AE_Budget from tb_ProBudget where Year='"+year+"' and ProName='"+proString.trim()+"'";
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double ae_Budget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			ae_Budget=0;
		}else {
			ae_Budget=Double.parseDouble(budget.get(0).toString());
		}
		double data[][] =new double[1][12];
		for(int i=1;i<=12;i++)
			data[0][i-1]=(ae_Budget*i/12);
		String[] fields_Expense = { "AE_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset createRBEIBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(RBEI_Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+enameString+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double rbei_Budget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			rbei_Budget=0;
		}else {
			rbei_Budget=Double.parseDouble(budget.get(0).toString());
		}
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=rbei_Budget*i/12;
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
	public CategoryDataset createRBEI_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget="select RBEI_Budget from tb_ProBudget where Year='"+year+"' and ProName='"+proString.trim()+"'";
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double rbei_Budget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			rbei_Budget=0;
		}else {
			rbei_Budget=Double.parseDouble(budget.get(0).toString());
		}
		double data[][] =new double[1][12];
		for(int i=1;i<=12;i++)
			data[0][i-1]=(rbei_Budget*i/12);
		String[] fields_Expense = { "RBEI_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset createTotalBudgetNoProject(String year)
	{
		BasicDB basicDB=new BasicDB();
		String findBudget="select sum(Budget) from tb_ProBudget,tb_Project where tb_ProBudget.ProName=tb_Project.ProName and tb_Project.TPM='"+enameString+"' and tb_ProBudget.Year='"+year+"'";	
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalBudget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			totalBudget=0;
		}else {
			totalBudget=Double.parseDouble(budget.get(0).toString());
		}
		double[][]data=new double[1][12];
		for(int i=1;i<=12;i++)
		{
			data[0][i-1]=totalBudget*i/12;
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
	public CategoryDataset createTotal_Budget_Dataset(String proString) {
		BasicDB basicDB = new BasicDB();
		String findBudget="select Budget from tb_ProBudget where Year='"+year+"' and ProName='"+proString.trim()+"'";
		ArrayList<String>budget=basicDB.SQuerydb(findBudget);
		double totalBudget=0;
		if(budget==null||budget.size()==0||budget.get(0)==null)
		{
			totalBudget=0;
		}else {
			totalBudget=Double.parseDouble(budget.get(0).toString());
		}	   
		double [][]data=new double[1][12];
		for(int i=1;i<=12;i++)
			data[0][i-1]=(totalBudget*i/12);
		String[] fields_Expense = { "Total_Budget"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset getDataSet(String proString) throws Exception {
		BasicDB basicDB = new BasicDB();
		String sql = "select M_T_Expense,Material_Expense,M_A_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String[] paras = { proString.trim() ,year};
		int col=5;
		ArrayList<Object[]> orginalList = basicDB.queryDB(sql, paras);
		ArrayList<Object[]> afterList=new AllChartAction().AddNoMonthObject(orginalList,col,year);
		double data[][] =new AllChartAction().ReturnData(afterList);
		String[] fields_Expense = { "EPD_Expense","Material_Expense", "AE_Expense",
				"RBEI_Expense"};
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	
	public CategoryDataset getDataset_E_A(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_T_Expense,Material_Expense,M_A_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=4;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"EPD_Expense","Material_Expense","AE_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset getDataset_E_R(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_T_Expense,Material_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=4;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"EPD_Expense","Material_Expense","RBEI_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset getDataset_A_R(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_A_Expense,M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=3;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"AE_Expense","RBEI_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset getDataset_E(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_T_Expense,Material_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=3;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"EPD_Expense","Material_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}	
	public CategoryDataset getDataset_A(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_A_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=2;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"AE_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}
	public CategoryDataset getDataset_R(String proString)throws Exception
	{
		BasicDB basicDB=new BasicDB();
		String sql="select M_R_Expense,Month from tb_Summary where ProName=? and Year=? order by Month asc";
		String []paras={proString.trim(),year};
		int col=2;
		ArrayList<Object[]>original=basicDB.queryDB(sql, paras);
		ArrayList<Object[]>afterList=new AllChartAction().AddNoMonthObject(original,col,year);
		double data[][]=new AllChartAction().ReturnData(afterList);
		String []months=new String[12];
		for(int i=0;i<12;i++)
		{
			months[i]=row[i];
		}
		String[] fields_Expense = {"RBEI_Expense"};
		CategoryDataset dataset= DatasetUtilities.createCategoryDataset(fields_Expense, months, data);
		return dataset;
	}

	public String createForGuest()throws Exception
	{
		System.out.println(myproname);
		session.setAttribute(ip + "guestchartYear", year);
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
		String[] arrpros = myproname.split(", ");
		String[] fileNames = new String[arrpros.length];
		JFreeChart[] charts = new JFreeChart[arrpros.length];
		if ("EPD, AE, RBEI".equals(temp)) {
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataSet(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				CategoryPlot plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    TextTitle textTitle=new TextTitle(arrpros[i]+"--[EPD][AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
				charts[i].getLegend().setItemFont(
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
//				plot.setDataset(1,createEPD_Budget_Dataset());
//				plot.setDataset(2,createAE_Budget_Dataset());
//				plot.setDataset(3, createRBEI_Budget_Dataset());
				plot.setDataset(4, createTotal_Budget_Dataset(arrpros[i]));
//				LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//				lineandshaperenderer
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer1
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer2
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer3
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//				lineandshaperenderer1.setSeriesPaint(0, Color.RED);
//				lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
//				plot.setRenderer(1, lineandshaperenderer);
//				plot.setRenderer(2, lineandshaperenderer1);
//				plot.setRenderer(3, lineandshaperenderer2);
				plot.setRenderer(4, lineandshaperenderer3);
				CategoryAxis categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD, AE".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_E_A(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				CategoryPlot plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    TextTitle textTitle=new TextTitle(arrpros[i]+"--[EPD][AE]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
				charts[i].getLegend().setItemFont(
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
				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
//				plot.setDataset(3, createRBEI_Budget_Dataset());
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
				LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
//				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer2
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
//				lineandshaperenderer2.setSeriesPaint(0, Color.YELLOW);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
//				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				CategoryAxis categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD, RBEI".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_E_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				CategoryPlot plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			    TextTitle textTitle=new TextTitle(arrpros[i]+"--[EPD][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
				Color lineColor = new Color(31, 121, 170);
		        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色
		        ValueAxis  axis = plot.getRangeAxis();
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
				charts[i].getLegend().setItemFont(
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
				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
	//			plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3,createRBEI_Budget_Dataset(arrpros[i]));
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
				LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
	//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
				lineandshaperenderer
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer1
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
//				lineandshaperenderer1.setSeriesPaint(0, Color.RED);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
				plot.setRenderer(1, lineandshaperenderer);
//				plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				CategoryAxis categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1100, 500, null,
						session);
			}
		}else if("AE, RBEI".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
				charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
						getDataset_A_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
				charts[i].getRenderingHints().put(
				            RenderingHints.KEY_TEXT_ANTIALIASING,
				            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				charts[i].setTextAntiAlias(false);			
				CategoryPlot plot = charts[i].getCategoryPlot();			
				charts[i].setTextAntiAlias(false);
				charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
				TextTitle textTitle=new TextTitle(arrpros[i]+"--[AE][RBEI]",new Font("sans-serif",Font.PLAIN,16));
			    charts[i].setTitle(textTitle);
				charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
				charts[i].getLegend().setItemFont(
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
				renderer.setSeriesPaint(1, Color.CYAN);
				renderer.setItemMargin(0.4);
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				renderer.setBaseItemLabelsVisible(true);
				renderer.setShadowVisible(false);
				plot.setRenderer(renderer);
				plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
				plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
//				plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
				plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
				plot.setDataset(3,createRBEI_Budget_Dataset(arrpros[i]));
//				plot.setDataset(4, new TpmChartAction().createTotal_Budget_Dataset(arrpros[i]));
		//		LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
				LineAndShapeRenderer lineandshaperenderer2 = new LineAndShapeRenderer();
				//LineAndShapeRenderer lineandshaperenderer3 = new LineAndShapeRenderer();
//				lineandshaperenderer
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer1
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
				lineandshaperenderer2
						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer3
//						.setToolTipGenerator(new StandardCategoryToolTipGenerator());
//				lineandshaperenderer.setSeriesPaint(0, Color.GREEN);
				lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
				lineandshaperenderer2.setSeriesPaint(0, Color.CYAN);
//				lineandshaperenderer3.setSeriesPaint(0, Color.BLUE);
			//	plot.setRenderer(1, lineandshaperenderer);
				plot.setRenderer(2, lineandshaperenderer1);
				plot.setRenderer(3, lineandshaperenderer2);
//				plot.setRenderer(4, lineandshaperenderer3);
				CategoryAxis categoryaxis = plot.getDomainAxis();
				categoryaxis
						.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
				fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
						session);
			}
		}else if("EPD".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_E(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
			CategoryPlot plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			TextTitle textTitle=new TextTitle(arrpros[i]+"--[EPD]",new Font("sans-serif",Font.PLAIN,16));
			charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
			charts[i].getLegend().setItemFont(
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
//			renderer.setSeriesPaint(0, Color.RED);
//			renderer.setSeriesPaint(1, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
			//plot.setDataset(2,createAE_Budget_Dataset());
			//plot.setDataset(3, createRBEI_Budget_Dataset());
//			plot.setDataset(4, createTotal_Budget_Dataset());
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
//			plot.setRenderer(2, lineandshaperenderer1);
//			plot.setRenderer(3, lineandshaperenderer2);
//			plot.setRenderer(4, lineandshaperenderer3);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}else if("AE".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_A(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
			CategoryPlot plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
		    TextTitle textTitle=new TextTitle(arrpros[i]+"--[AE]",new Font("sans-serif",Font.PLAIN,16));
	        charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
			charts[i].getLegend().setItemFont(
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
	//		renderer.setSeriesPaint(0, Color.GREEN);
			renderer.setSeriesPaint(0, Color.YELLOW);
//			renderer.setSeriesPaint(1, Color.YELLOW);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setDataset(2,createAE_Budget_Dataset(arrpros[i]));
			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
			lineandshaperenderer1
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer1.setSeriesPaint(0, Color.YELLOW);
			//plot.setRenderer(1, lineandshaperenderer);
			plot.setRenderer(2, lineandshaperenderer1);
			CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}else if("RBEI".equals(temp))
		{
			for(int i=0;i<arrpros.length;i++)
			{
			charts[i] = ChartFactory.createStackedBarChart("", "Month", "Money",
					getDataset_R(arrpros[i]), PlotOrientation.VERTICAL, true, true, false);
			charts[i].getRenderingHints().put(
			            RenderingHints.KEY_TEXT_ANTIALIASING,
			            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			charts[i].setTextAntiAlias(false);			
		    CategoryPlot plot = charts[i].getCategoryPlot();			
			charts[i].setTextAntiAlias(false);
			charts[i].getLegend().setFrame(new BlockBorder(Color.WHITE));
			TextTitle textTitle=new TextTitle(arrpros[i]+"--[RBEI]",new Font("sans-serif",Font.PLAIN,16));
		    charts[i].setTitle(textTitle);
			charts[i].getLegend().setBackgroundPaint(new Color(214,204,201));
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
			charts[i].getLegend().setItemFont(
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
//			renderer.setSeriesPaint(0, Color.GREEN);
//			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesPaint(0, Color.CYAN);
			renderer.setItemMargin(0.4);
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setShadowVisible(false);
			plot.setRenderer(renderer);
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
			//plot.setDataset(1,createEPD_Budget_Dataset(arrpros[i]));
			//plot.setDataset(2,createAE_Budget_Dataset());
			plot.setDataset(3, createRBEI_Budget_Dataset(arrpros[i]));
//			plot.setDataset(4, createTotal_Budget_Dataset());
			//LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//			LineAndShapeRenderer lineandshaperenderer1 = new LineAndShapeRenderer();
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
//			plot.setRenderer(4, lineandshaperenderer3);
		    CategoryAxis categoryaxis = plot.getDomainAxis();
			categoryaxis
					.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
			fileNames[i] = ServletUtilities.saveChartAsPNG(charts[i], 1000, 500, null,
					session);
			}
		}
		for(int i=0;i<fileNames.length;i++)
			System.out.println(fileNames[i]);
		request.setAttribute("chartType", chartType);
		request.setAttribute("checkPro", arrpros);
		request.setAttribute("filename", fileNames);
		return "guestPng";
	}
}
