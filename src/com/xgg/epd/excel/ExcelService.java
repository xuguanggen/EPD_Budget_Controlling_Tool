package com.xgg.epd.excel;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xgg.epd.dbs.BasicDB;
import java.util.Date;
import java.util.Calendar;

public class ExcelService {

	public String month;
	public String year;

	public ExcelService(String month, String year) {
		this.month = month;
		this.year = year;
		System.out.println(year+"-"+month);
	}
	public static int columuSize=2;
	public void readExcel(String filePath, String uploadType) {
		try {
			Workbook workBook = null;
			FileInputStream in = new FileInputStream(filePath);
			try {
				workBook = new XSSFWorkbook(filePath);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(in);
			}
			int column=columuSize;
			System.out.println("sheets :" + workBook.getNumberOfSheets());
			Sheet sheet = workBook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("first sheet has ===" + rows);
			Row roww = sheet.getRow(1);
			System.out.println("gong you ====" + roww.getLastCellNum());
			String paras[][] = new String[rows - 1][column];
			int count = 0;
			if (rows > 0) {
				sheet.getMargin(Sheet.TopMargin);
				for (int r = 1; r < rows; r++) {
					Row row = sheet.getRow(r);
					if (row != null) {
						int cells = row.getLastCellNum();
						Cell cell_pm = row.getCell(0);
						Cell cell_cost = row.getCell(1);
						if (cell_pm != null && cell_cost != null) {
							String pmString = getValue(cell_pm);
							String costString = getValue(cell_cost);
							if (!"".equals(pmString) && !"".equals(costString)) {
								paras[count][0] = pmString;
								paras[count][1] = costString;
								count++;
							}
						}
					}
				}
				String temp[][] = new String[count][column];
				for (int i = 0; i < count; i++) {
					for (int j = 0; j < column; j++) {
						temp[i][j] = paras[i][j];
					}
				}
				new FileInputStream(filePath).close();
				///////////////////////////////////////////////////////////////////
				//  DB insert
				//////////////////////////////////////////////////////////////////
				BasicDB basicDB=new BasicDB();
				if("AE".equals(uploadType))
				{
					for(int i=0;i<count;i++)
					{
						String search_sql="select count(*) from tb_Summary where Year='"+year+"' and Month='"+month+"' and ProName='"+temp[i][0]+"'";
						ArrayList<String>resultCount=basicDB.SQuerydb(search_sql);
						if(!"0".equals(resultCount.get(0).toString()))
						{
							String update_sql="update tb_Summary set M_A_Expense=? where Year=? and Month=? and ProName=?";
							String []update_paras={temp[i][1].toString(),year,month,temp[i][0].toString()};
							basicDB.updateDB(update_sql, update_paras);
						}else {						
							String tpmNo=FindTpmNo(temp[i][0]);
							String insert_sql="insert into tb_Summary values(?,?,?,0,?,0,?,0)";
							String []insert_paras={temp[i][0],month,tpmNo,temp[i][1],year};
							basicDB.updateDB(insert_sql, insert_paras);
						}
					}
				}else if("RBEI".equals(uploadType))
				{
					for(int i=0;i<count;i++)
					{
						String search_sql="select count(*) from tb_Summary where Year='"+year+"' and Month='"+month+"' and ProName='"+temp[i][0]+"'";
						ArrayList<String>resultCount=basicDB.SQuerydb(search_sql);
						if(!"0".equals(resultCount.get(0).toString()))
						{
							String update_sql="update tb_Summary set M_R_Expense=? where Year=? and Month=? and ProName=?";
							String []update_paras={temp[i][1].toString(),year,month,temp[i][0].toString()};
							basicDB.updateDB(update_sql, update_paras);
						}else {							
							String tpmNo=FindTpmNo(temp[i][0]);
							String insert_sql="insert into tb_Summary values(?,?,?,0,0,?,?,0)";
							String []insert_paras={temp[i][0],month,tpmNo,temp[i][1],year};
							basicDB.updateDB(insert_sql, insert_paras);
						}
					}
				}else if("MaterialExpense".equals(uploadType))
				{
					for(int i=0;i<count;i++)
					{
						String search_sql="select count(*) from tb_Summary where Year='"+year+"' and Month='"+month+"' and ProName='"+temp[i][0]+"'";
						ArrayList<String>resultCount=basicDB.SQuerydb(search_sql);
						if(!"0".equals(resultCount.get(0).toString()))
						{
							String update_sql="update tb_Summary set Material_Expense=? where Year=? and Month=? and ProName=?";
							String []update_paras={temp[i][1].toString(),year,month,temp[i][0].toString()};
							basicDB.updateDB(update_sql, update_paras);
						}else {							
							String tpmNo=FindTpmNo(temp[i][0]);
							String insert_sql="insert into tb_Summary values(?,?,?,0,0,0,?,?)";
							String []insert_paras={temp[i][0],month,tpmNo,temp[i][1],year};
							basicDB.updateDB(insert_sql, insert_paras);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // ???
			if (DateUtil.isCellDateFormatted(cell)) {
				value = DateUtil.getJavaDate(cell.getNumericCellValue())
						.toString();
			} else {
				value = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING: // ????
			value = cell.getRichStringCellValue().toString();
			break;
		case Cell.CELL_TYPE_FORMULA:// ???

			cell.setCellType(Cell.CELL_TYPE_STRING);
			value = cell.getStringCellValue();
			// if (value.equals("NaN")) {// ????????????,?????????
			// value = cell.getRichStringCellValue().toString();
			// }
			break;
		case Cell.CELL_TYPE_BOOLEAN:// ??
			value = " " + cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK: // ??
			value = "";
			break;
		case Cell.CELL_TYPE_ERROR: // ??
			value = "";
			break;
		default:
			value = cell.getRichStringCellValue().toString();
		}
		return value;
	}

	
	public String FindTpmNo(String Pro)
	{
		BasicDB basicDB=new BasicDB();
		String findTPM_sql="select EId from tb_Employee,tb_Project where tb_Employee.EmpName=tb_Project.TPM and ProName='"+Pro+"'";
		ArrayList<String> tpmList=basicDB.SQuerydb(findTPM_sql);
		String tpmNo="";
		if(tpmList!=null&&tpmList.size()!=0)
		{
			tpmNo=tpmList.get(0).toString().trim();
		}else {
			tpmNo="none";
		}
		return tpmNo;
	}
	
	
	
	// public void readExcel(String filePath, String uploadType) {
	// try {
	// Workbook workBook = null;
	// FileInputStream in = new FileInputStream(filePath);
	// try {
	// workBook = new XSSFWorkbook(filePath);
	// } catch (Exception ex) {
	// workBook = new HSSFWorkbook(in);
	// }
	// int column;
	// String sql = "";
	// if ("AE".equals(uploadType)) {
	// column = 2;
	// //sql = "insert into tb_AE values(?,?)";
	//			
	// } else if ("RBEI".equals(uploadType)) {
	// column = 2;
	// sql = "insert into tb_RBEI values(?,?,?)";
	// } else {
	// column = 6;
	// sql = "insert into tb_MonthlyControlling values(?,?,'" + month
	// + "',?,?,?,?,'"+year+"')";
	// }
	// System.out.println("sheets :" + workBook.getNumberOfSheets());
	// BasicDB basicdb = new BasicDB();
	//
	// Sheet sheet = workBook.getSheetAt(0);
	// int rows = sheet.getPhysicalNumberOfRows();
	// System.out.println("first sheet has ===" + rows);
	// Row roww = sheet.getRow(1);
	// System.out.println("gong you ====" + roww.getLastCellNum());
	// String paras[][]=new String [rows-1][column];
	// int count=0;
	// if (rows > 0) {
	// sheet.getMargin(Sheet.TopMargin);
	// for (int r = 1; r < rows; r++) {
	// Row row = sheet.getRow(r);
	// if (row != null) {
	// int cells = row.getLastCellNum();
	// Cell cell_pm = row.getCell(2);
	// Cell cell_wd = row.getCell(5);
	// if (cell_pm != null && cell_wd != null) {
	// String pmString = getValue(cell_pm);
	// String wdString = getValue(cell_wd);
	// if (!"".equals(pmString) && !"".equals(wdString)) {
	// for (short c = 0; c < 6; c++) {
	// Cell cell = row.getCell(c);
	// if (cell != null) {
	// if (!"".equals(getValue(cell))) {
	// String value = getValue(cell);
	// //System.out.print(value + "=====");
	// paras[count][c]=value;
	// }else {
	// paras[count][c]="";
	// }
	// }else {
	// //System.out.print("@@@@@@@");
	// paras[count][c]="";
	// }
	// }
	// count++;
	// //System.out.println();
	// }
	// }
	// }
	// }
	// String temp[][]=new String [count][6];
	// for(int i=0;i<count;i++)
	// {
	// for(int j=0;j<6;j++)
	// temp[i][j]=paras[i][j];
	// }
	// basicdb.UpdateMutildb(sql, temp);
	// new FileInputStream(filePath).close();
	// for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
	// // System.out.println("?" + i + "??????");
	// CellRangeAddress region = sheet.getMergedRegion(i);
	// int row = region.getLastRow() - region.getFirstRow() + 1;
	// int col = region.getLastColumn() - region.getFirstColumn()
	// + 1;
	// System.out.println("start row:" + region.getFirstRow());
	// System.out.println("start col:" + region.getFirstColumn());
	// System.out.println("rows:" + row);
	// System.out.println("cols:" + col);
	// }
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

}
