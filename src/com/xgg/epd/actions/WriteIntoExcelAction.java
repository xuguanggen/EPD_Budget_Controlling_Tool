package com.xgg.epd.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.CommonDataSource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.rowset.internal.Row;
import com.xgg.epd.beans.ProjectBean;
import com.xgg.epd.dbs.BasicDB;
import com.xgg.epd.utils.Common;

public class WriteIntoExcelAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String d;

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	String year = "";
	String month = "";
	String path;

	public ArrayList<Object[]> CreateData() {
		String[] times = d.split("-");
		year = times[0];
		month = "";
		if ("10".equals(times[1])) {
			month = times[1];
		} else {
			month = times[1].replace("0", "");
		}
		String sqlString = "select Year,Month,EmpName,Section,ProName,ServiceNr,WorkingDays,Remark from tb_MonthlyControlling where Year=? and Month=?";
		String[] paras = { year, month };
		BasicDB basicDB = new BasicDB();
		ArrayList<Object[]> alArrayList = basicDB.queryDB(sqlString, paras);
		return alArrayList;
	}

	public String CreateExcel() {
		path = session.getServletContext().getRealPath("/")		
				+ "MonthlyControlling" + "\\";		
		path=path.substring(0, path.length()-1);
		path = path.replace("\\", "\\\\");
		File MonthFile=new File(path);
		if (!MonthFile .exists()  && !MonthFile .isDirectory())      
		{       
		    System.out.println("//null");  
		    MonthFile .mkdir();    
		} 
		ArrayList<Object[]> alArrayList = CreateData();
						// if (alArrayList == null || alArrayList.size() == 0) {
						// // System.out.println("NULL");
						// response.setContentType("text/html;charset=UTF-8");
						// response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
						// try {
						// PrintWriter out = response.getWriter();
						// out.print("<script>alert('Month Error!')</script>");
						// out.flush();
						// out.close();
						// } catch (IOException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// return ERROR;
						// }
		IsHasFiles();
		ServletOutputStream out;
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(new File(path + year + "-" + month
					+ "-MonthlyControlling.xls"));
			WritableSheet sheet = book.createSheet(year + "-" + month, 0);
			WritableFont font1 = new WritableFont(WritableFont
					.createFont("sans-serif"), 10, WritableFont.BOLD);
			font1.setColour(Colour.BLUE);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			format1.setAlignment(jxl.format.Alignment.CENTRE);
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableFont font2 = new WritableFont(WritableFont
					.createFont("sans-serif"), 10, WritableFont.BOLD);
			font2.setColour(Colour.BLACK);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
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
				Label label = new Label(0, i + 1, objects[0].toString().trim()
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
			e.printStackTrace();
		}
		File file = new File(path + year + "-" + month
				+ "-MonthlyControlling.xls");
		try {
			FileInputStream inputStream = new FileInputStream(file);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ year + "-" + month + "-MonthlyControlling.xls");
			out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				out.write(buffer, 0, b);
			}
			out.flush();
			out.close();
			inputStream.close();
			return SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void IsHasFiles() {
		System.out.println("year=" + year + ".month=" + month);
		path=path+"\\";
		System.out.println(path);
		File file = new File(path);
		String[] fileNames = file.list();
		if (fileNames == null) {
			return;
		} else {
			int i;
			for (i = 0; i < fileNames.length; i++) {
				String name = fileNames[i];
				if ((year + "-" + month + "-MonthlyControlling.xls")
						.equals(name)) {
					File teFile = new File(path + "\\" + name);
					teFile.delete();
					return;
				}
			}
			if (i == fileNames.length) {
				return;
			}
		}
	}

	public String ExportProjects() throws Exception {
		ArrayList<ProjectBean> alBeans = getAllProjects();
		HSSFWorkbook wb = createProjectsExcel(alBeans);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=Project_Information.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
		return "Export_Projects_Success";
	}

	public ArrayList<ProjectBean> getAllProjects() {
		ArrayList<ProjectBean> alBeans = new ArrayList<ProjectBean>();
		String sql = "select * from tb_Project where 1=?";
		String[] paras = { "1" };
		BasicDB basicDB = new BasicDB();
		ArrayList<Object[]> alObjects = new ArrayList<Object[]>();
		alObjects = basicDB.queryDB(sql, paras);
		for (int i = 0; i < alObjects.size(); i++) {
			Object[] objects = alObjects.get(i);
			String pid = objects[0].toString().trim();
			String proName = objects[1].toString().trim();
			int[] budget=Common.getBudget(proName);
			String tpm = objects[2].toString().trim();
			String bp = objects[3].toString().trim();
			String status = objects[4].toString().trim();
			String oem = objects[5].toString().trim();
			String customer = objects[6].toString().trim();
			String remark = objects[7].toString().trim();
			String category=objects[8].toString().trim();
			ProjectBean pBean = new ProjectBean(pid, proName, tpm, bp,budget[0]+"",budget[1]+"",budget[2]+"", status, oem, customer, remark,category);
			alBeans.add(pBean);
		}
		return alBeans;
	}

	public HSSFWorkbook createProjectsExcel(ArrayList<ProjectBean> alBeans) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Project_Overview");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.GREEN.index);
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		for (int i = 0; i < Common.proInfo.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(Common.proInfo[i]);
			cell.setCellStyle(style);
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < alBeans.size(); i++) {
			row = sheet.createRow(i + 1);
			ProjectBean Pbean = alBeans.get(i);
			row.createCell(0).setCellValue(Pbean.getProId());
			row.createCell(1).setCellValue(Pbean.getProName());
			row.createCell(2).setCellValue(Pbean.getCategory());
			row.createCell(3).setCellValue(Pbean.getTPM());
			row.createCell(4).setCellValue(Pbean.getBp());	
			row.createCell(5).setCellValue(Pbean.getEbudget());
			row.createCell(6).setCellValue(Pbean.getAbudget());
			row.createCell(7).setCellValue(Pbean.getRbudget());			
			row.createCell(8).setCellValue(Pbean.getStatus());
			row.createCell(9).setCellValue(Pbean.getOem());
			row.createCell(10).setCellValue(Pbean.getCustomer());
			row.createCell(11).setCellValue(Pbean.getRemark());
		}
		return wb;
	}
}
