package com.xgg.epd.listeners;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.xgg.epd.dbs.BasicDB;

public class SendEmailTask extends TimerTask {
	
	
	@Override
	 public void run() {	
//		Date date=new Date();
//		String sdateString="2014-10-01 19:20:00";
//		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			date=sdf.parse(sdateString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(date);
//		System.out.println("Calendar.DAY_OF_MONTH="+date.getDate());
//		if(date.getDate()==lastworkday(date))
//		{
//			System.out.println("YES");
//		}else
//		{
//			System.out.println("NO");
//		}
		Calendar calendar=Calendar.getInstance();
		int date=calendar.get(Calendar.DATE);
		if(date==10){
			Write();
		}
	}
	public void Write()
	{
		BasicDB basicDB=new BasicDB();
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		if(month==1)
		{
			//new floder
			File file=new File("S:\\CC-Departments\\NE\\D06_EPS\\06_General\\03_Controlling_Monthly_Report\\"+year+" Controlling");			
			file.mkdir();
			String sqlString = "select Year,Month,EmpName,Section,ProName,ServiceNr,WorkingDays,Remark from tb_MonthlyControlling where Year=? and Month=?";
			String[] paras = { (year-1)+"","12" };
			ArrayList<Object[]> alArrayList = basicDB.queryDB(sqlString, paras);
			try {
				WritableWorkbook book= Workbook.createWorkbook(new File("S:\\CC-Departments\\NE\\D06_EPS\\06_General\\03_Controlling_Monthly_Report\\"+(year-1)+" Controlling\\"+(year-1)+"-12-MonthlyControlling.xls"));
				WritableSheet sheet = book.createSheet((year-1) +"-12", 0);
				WritableFont font1 = new WritableFont(WritableFont
						.createFont("sans-serif"), 10, WritableFont.BOLD);
				font1.setColour(Colour.BLUE);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(jxl.format.Alignment.CENTRE);
				format1
						.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				WritableFont font2 = new WritableFont(WritableFont
						.createFont("sans-serif"), 10, WritableFont.BOLD);
				font2.setColour(Colour.BLACK);
				WritableCellFormat format2 = new WritableCellFormat(font2);
				format2.setAlignment(jxl.format.Alignment.CENTRE);
				format2
						.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				Label labelA = new Label(0, 0, "Month", format1);
				Label labelB = new Label(1, 0, "EmpName", format1);
				Label labelC = new Label(2, 0, "Section", format1);
				Label labelD = new Label(3, 0, "ProName", format1);
				Label labelE = new Label(4, 0, "ServiceNr", format1);
				Label labelF = new Label(5, 0, "WorkingDays", format1);
				Label labelG = new Label(6, 0, "Remark", format1);
				sheet.setColumnView(0, 20);
				sheet.setColumnView(1, 20);
				sheet.setColumnView(2, 20);
				sheet.setColumnView(3, 40);
				sheet.setColumnView(4, 40);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.addCell(labelA);
				sheet.addCell(labelB);
				sheet.addCell(labelC);
				sheet.addCell(labelD);
				sheet.addCell(labelE);
				sheet.addCell(labelF);
				sheet.addCell(labelG);
				for (int i = 0; i < alArrayList.size(); i++) {
					Object[] objects = alArrayList.get(i);
					Label label = new Label(0, i + 1, objects[0].toString()
							.trim()
							+ "-" + objects[1].toString().trim(), format2);
					sheet.addCell(label);
					for (int j = 2; j < objects.length; j++) {
						Label label2 = new Label(j - 1, i + 1, objects[j]
								.toString().trim(), format2);
						sheet.addCell(label2);
					}
				}
				book.write();
				book.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			String sqlString = "select Year,Month,EmpName,Section,ProName,ServiceNr,WorkingDays,Remark from tb_MonthlyControlling where Year=? and Month=?";
			String[] paras = { year+"", (month-1)+"" };
			ArrayList<Object[]> alArrayList = basicDB.queryDB(sqlString, paras);
			try {
				WritableWorkbook book= Workbook.createWorkbook(new File("S:\\CC-Departments\\NE\\D06_EPS\\06_General\\03_Controlling_Monthly_Report\\"+year+" Controlling\\"+year+"-"+(month-1)+"-MonthlyControlling.xls"));
				WritableSheet sheet = book.createSheet(year +"-"+ (month-1), 0);
				WritableFont font1 = new WritableFont(WritableFont
						.createFont("sans-serif"), 10, WritableFont.BOLD);
				font1.setColour(Colour.BLUE);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(jxl.format.Alignment.CENTRE);
				format1
						.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				WritableFont font2 = new WritableFont(WritableFont
						.createFont("sans-serif"), 10, WritableFont.BOLD);
				font2.setColour(Colour.BLACK);
				WritableCellFormat format2 = new WritableCellFormat(font2);
				format2.setAlignment(jxl.format.Alignment.CENTRE);
				format2
						.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				Label labelA = new Label(0, 0, "Month", format1);
				Label labelB = new Label(1, 0, "EmpName", format1);
				Label labelC = new Label(2, 0, "Section", format1);
				Label labelD = new Label(3, 0, "ProName", format1);
				Label labelE = new Label(4, 0, "ServiceNr", format1);
				Label labelF = new Label(5, 0, "WorkingDays", format1);
				Label labelG = new Label(6, 0, "Remark", format1);
				sheet.setColumnView(0, 20);
				sheet.setColumnView(1, 20);
				sheet.setColumnView(2, 20);
				sheet.setColumnView(3, 40);
				sheet.setColumnView(4, 40);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.addCell(labelA);
				sheet.addCell(labelB);
				sheet.addCell(labelC);
				sheet.addCell(labelD);
				sheet.addCell(labelE);
				sheet.addCell(labelF);
				sheet.addCell(labelG);
				for (int i = 0; i < alArrayList.size(); i++) {
					Object[] objects = alArrayList.get(i);
					Label label = new Label(0, i + 1, objects[0].toString()
							.trim()
							+ "-" + objects[1].toString().trim(), format2);
					sheet.addCell(label);
					for (int j = 2; j < objects.length; j++) {
						Label label2 = new Label(j - 1, i + 1, objects[j]
								.toString().trim(), format2);
						sheet.addCell(label2);
					}
				}
				book.write();
				book.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
		}
	}
	public int endDateWeekDay(Date date){
		int wd=0;
		int tdays=totaldays(date);
		System.out.println("totalDAYS=="+tdays);
		SimpleDateFormat sbf =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String eString=(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+tdays+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		Date edate=new Date();
		Calendar calendar=Calendar.getInstance();
		try {
			edate = sbf.parse(eString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("last day=="+edate);
		calendar.setTime(edate);
		//System.out.println("XXXX"+Calendar.DAY_OF_MONTH);
		wd=calendar.get(Calendar.DAY_OF_WEEK);
		if(wd==1)
			wd=7;
		else {
			wd=wd-1;
		}
		return wd;
	}
	public int lastworkday(Date date)
	{
		int lwd=0;
		int tdays=totaldays(date);
		int ewd=endDateWeekDay(date);
	    if(tdays==28)
	    {
	    	if(ewd==7)
	    		lwd=26;
	    	else if(ewd==6)
	    		lwd=27;
	    	else
	    		ewd=28;
	    }else if(tdays==29){
			if(ewd==7)
				lwd=27;
			else if(ewd==6)
				lwd=28;
			else
				lwd=29;
		}else if(tdays==30)
		{
			if(ewd==7)
				lwd=28;
			else if(ewd==6)
				lwd=29;
			else 
				lwd=30;
		}else if(tdays==31)
		{
			if(ewd==7)
				lwd=29;
			else if(ewd==6)
				lwd=30;
			else 
				lwd=31;
		}
	    System.out.println("benyue last workday="+lwd);
		return lwd;
	}
	public int totaldays(Date date)
	{
		int total=0;
		int year=date.getYear()+1900;
		int month=date.getMonth()+1;
		if(judge(year))
		{
			if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
				total=31;
			else if(month==2)
			{
				total=29;
			}else {
				total=30;
			}
		}
		else {
			if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
				total=31;
			else if(month==2)
			{
				total=28;
			}else {
				total=30;
			}
		}
		System.out.println("totalDays=="+total);
		return total;
	}
	public boolean judge(int year)
	{
		if(year%400==0||(year%4==0&&year%100!=0))
		{
			return true;
		}else
			return false;
	}
	public int weekday(Date date)
	{
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sbf =new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		int wd=0;
		String sdate="";
	    sdate=(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+"01 "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	    Date startDate=new Date();
	    try {
	         startDate=sbf.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(startDate);
		wd=calendar.get(Calendar.DAY_OF_WEEK);
		if(wd==1)
			wd=7;
		else {
			wd=wd-1;
		}
		return wd;
	}
}
