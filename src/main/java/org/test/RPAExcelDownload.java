package org.test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;



@Component
class RPAExcelDownload {
	    
	    public RPAExcelDownload(){}

	    @SuppressWarnings("deprecation")
		public ByteArrayOutputStream[] checkWorkbook(ArrayList<WorkSheetData> sheetArr) {

	        XSSFWorkbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream[] baosArr = new ByteArrayOutputStream[1];

	        int rowNum = 0;
	        System.out.println("Creating excel");
            for (WorkSheetData workSheetData:sheetArr) {
            	XSSFSheet sheet = workbook.createSheet(workSheetData.getDatasheetName());
                
            	for (Row datatype : workSheetData.getArr()) {
	            XSSFRow row = sheet.createRow(rowNum++);
	            int colNum = 0;
                int lastColumn = datatype.getLastCellNum();

                for (int cn = 0; cn < lastColumn; cn++) {
                   Cell c = datatype.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                   XSSFCell cell = row.createCell(colNum++);
                   if (c == null) {
                      // The spreadsheet is empty in this cell
                	   cell.setCellValue(" ");
                   } else {
                      // Do something useful with the cell's contents
                	   switch(c.getCellType()) {
                	   case Cell.CELL_TYPE_NUMERIC:
                           cell.setCellValue(c.getNumericCellValue());
                           break;
                       case Cell.CELL_TYPE_STRING:
                       	cell.setCellValue(c.getRichStringCellValue());
                           break;
                       case Cell.CELL_TYPE_FORMULA:
   	                    switch(c.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            cell.setCellValue(c.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	cell.setCellValue(c.getRichStringCellValue());
                            break;
                         }
                	   }
                   }
                }
	        }
	        rowNum=0;
            }
            ByteArrayOutputStream outputStream = null;
	        try {
	            outputStream = new ByteArrayOutputStream();
	            workbook.write(outputStream);	            
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
          
	        System.out.println("Done");
	        baosArr[0] = outputStream;
	        return baosArr;
	    }
	    
}

