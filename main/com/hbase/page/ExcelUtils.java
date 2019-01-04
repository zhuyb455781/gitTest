package com.hbase.page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static void WriteWb2File(Set<Entry<String, TableAndRW>> set,
			String path) {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("name");
		row.createCell(1).setCellValue("read");
		row.createCell(2).setCellValue("write");
		Row createRow = null;
		int lastRowNum;
		for (Entry<String, TableAndRW> entry : set) {
			lastRowNum = sheet.getLastRowNum();

			createRow = sheet.createRow(lastRowNum + 1);

			TableAndRW value = entry.getValue();
			createRow.createCell(0).setCellValue(value.getName());
			createRow.createCell(1).setCellValue(value.getReadcount());
			createRow.createCell(2).setCellValue(value.getWritecount());

		}

		// 写文件
		String fileName = getFileName();
		System.out.println("结果文件名："+path + fileName);
		File file = new File(path + fileName);
		
		
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);

			wb.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param args
	 *            测试的时候改成main函数
	 */
	public static void aa(String[] args) {
		/* Set<Entry<String, TableAndRW>> set = new HashSet<>(); */
		/*
		 * TableAndRW table1 = new TableAndRW("aa", 1212, 453); TableAndRW
		 * table2 = new TableAndRW("bb", 12312, 224); TableAndRW table3 = new
		 * TableAndRW("cc", 23, 234); Map<String, TableAndRW> map = new
		 * HashMap<>(); map.put("a", table1); map.put("b", table2); map.put("v",
		 * table3);
		 * 
		 * Set<Entry<String, TableAndRW>> entrySet = map.entrySet();
		 * 
		 * WriteWb2File(entrySet, "D:/home/aaa.xlsx");
		 */

		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = f.format(time);
		System.out.println(format);
	}

	public static String getFileName() {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = f.format(time);
		return format+".xlsx";
	}
}
