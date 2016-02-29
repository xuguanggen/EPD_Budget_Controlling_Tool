<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.jfree.data.general.DefaultPieDataset"%> 
<%@ page import="org.jfree.chart.*"%> 
<%@ page import="org.jfree.chart.plot.*"%> 
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%> 
<%@ page import="org.jfree.chart.urls.StandardPieURLGenerator"%> 
<%@ page import="org.jfree.chart.entity.StandardEntityCollection"%> 
<%@ page import="java.io.*"%> 
<HTML> 
<HEAD> 
<META http-equiv=Content-Type content="text/html; charset=utf-8"> 
<TITLE>JfreeChart</TITLE> 
</HEAD> 
<BODY> 
<% 
DefaultPieDataset data = new DefaultPieDataset(); 
data.setValue("gzyx",370); 
data.setValue("gz",1530); 
data.setValue("dz",5700); 
data.setValue("bk",8280); 
data.setValue("ss",4420); 
data.setValue("bs",80); 

PiePlot3D plot = new PiePlot3D(data);//3D饼图 
plot.setURLGenerator(new StandardPieURLGenerator("barview.jsp","lion"));//设定链接 
JFreeChart chart = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, true); 
chart.setBackgroundPaint(java.awt.Color.white);//可选，设置图片背景色 
chart.setTitle("sss");//可选，设置图片标题 

StandardEntityCollection sec = new StandardEntityCollection(); 
ChartRenderingInfo info = new ChartRenderingInfo(sec); 
PrintWriter w = new PrintWriter(out);//输出MAP信息 
//500是图片长度，300是图片高度 
String filename = ServletUtilities.saveChartAsPNG(chart, 1000, 900, info, session); 
ChartUtilities.writeImageMap(w, "map0", info, false); 
String graphURL = request.getContextPath() + "/chart/DisplayChart?filename=" + filename; 
%> 
<P ALIGN="CENTER"> 
<img src="<%= graphURL %>" width=1000 height=900 border=0 usemap="#map0"> 
</P> 
</BODY> 
</HTML> 