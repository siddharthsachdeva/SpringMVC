package com.sid.dashboard.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class ExportExcelUtil extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		response.setHeader("Content-Disposition", "attachment; filename=\"data.xls\"");
		
		@SuppressWarnings("unchecked")
		List<Map<String, String>> data = (List<Map<String, String>>) model.get("data");
		
		Sheet sheet = workbook.createSheet("Data");
		
		// Header row
		Row header = sheet.createRow(0);
		int counter = 0;
		for(String key : data.get(0).keySet()){
			header.createCell(counter++).setCellValue(key);
		}
		
		int rowNum = 1;
		counter = 0;
		
		for(Map<String, String> rowData : data){
			Row row = sheet.createRow(rowNum++);
			for(String key : data.get(0).keySet()){
				row.createCell(counter++).setCellValue(rowData.get(key));	
			}
			counter = 0;
		}
		
		
	}

}
