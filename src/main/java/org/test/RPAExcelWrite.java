package org.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
class RPAExcelWrite {
	    
	    public RPAExcelWrite(){}

	    @SuppressWarnings("deprecation")
		public void checkWorkbook(String fileName, ArrayList<WorkSheetData> sheetArr) {

	        XSSFWorkbook workbook = new XSSFWorkbook();

	        //XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
	        /*
	        Object[][] datatypes = {
	                {"Datatype", "Type", "Size(in bytes)"},
	                {"int", "Primitive", 2},
	                {"float", "Primitive", 4},
	                {"double", "Primitive", 8},
	                {"char", "Primitive", 1},
	                {"String", "Non-Primitive", "No fixed size"}
	        };
	        XSSFSheet sheet2 = workbook.createSheet("Datatypes2 in Java");
	        Object[][] datatypes2 = {
	                {"Datatype", "Type", "Size(in bytes)"},
	                {"int", "Primitive", 2},
	                {"float", "Primitive", 4},
	                {"double", "Primitive", 8}
	        };
	        */
	        int rowNum = 0;
	        System.out.println("Creating excel");
            for (WorkSheetData workSheetData:sheetArr) {
            	XSSFSheet sheet = workbook.createSheet(workSheetData.getDatasheetName());
                
            	for (Row datatype : workSheetData.getArr()) {
	            XSSFRow row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            /*
	            for (Cell field : datatype) {
	                XSSFCell cell = row.createCell(colNum++);
	                if (field.getCellType() == Cell.CELL_TYPE_NUMERIC) {

	                		
	                    cell.setCellValue(field.getNumericCellValue());
	                } else if (field.getCellType() == Cell.CELL_TYPE_STRING) {
	                	
	                    cell.setCellValue(field.getRichStringCellValue());
	                }
	                else if(field.getCellType() == Cell.CELL_TYPE_FORMULA) {
	                    
	                    switch(field.getCachedFormulaResultType()) {
	                        case Cell.CELL_TYPE_NUMERIC:
	                            cell.setCellValue(field.getNumericCellValue());
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                        	cell.setCellValue(field.getRichStringCellValue());
	                            break;
	                    }
	                }
	                else if (field.getCellType() == Cell.CELL_TYPE_BLANK) {
	                	cell.setCellValue(Cell.CELL_TYPE_BLANK);
	                }
	                else if (datatype.getCell(colNum, Row.RETURN_BLANK_AS_NULL) == null) {
	                	cell.setCellValue(Cell.CELL_TYPE_BLANK);
	                }
	            }
	            */
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
            /*
            rowNum=0;
	        for (Object[] datatype : datatypes2) {
	            XSSFRow row = sheet2.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                XSSFCell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	        */
	        try {
	        	File f = new File(fileName);
	        	if(!f.exists())
	        	    f.createNewFile();
	            FileOutputStream outputStream = new FileOutputStream(f, true);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Done");
	    }
}

