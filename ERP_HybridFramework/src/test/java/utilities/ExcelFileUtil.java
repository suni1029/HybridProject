package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil 
{
	XSSFWorkbook wb;
	//write constructor to read excel path
	public ExcelFileUtil(String Excelpath) throws Throwable
	{
	FileInputStream fi = new FileInputStream(Excelpath);
	wb = new XSSFWorkbook(fi);
	}
    //method for counting no of rows in a sheet
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	//method for reading cell data
	public String getCellData(String sheetname,int row,int column)
	{
		String data ="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	//method for setcelldata
	public void setCellData(String sheetname,int row,int column,String status,String wrireExcel)throws Throwable
	{
		//get sheet from wd
		XSSFSheet ws =wb.getSheet(sheetname);
		//get row from sheet
		XSSFRow rowNum =ws.getRow(row);
		//create cell
		XSSFCell cell =rowNum.createCell(column);
		//write status into created cell
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//colour with green
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//colur with Red
			font.setColor(IndexedColors.RED.getIndex());	
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//colour with Blue
			font.setColor(IndexedColors.BLUE.getIndex());	
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(wrireExcel);
		wb.write(fo);
}
}
