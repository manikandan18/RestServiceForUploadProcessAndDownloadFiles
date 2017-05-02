package org.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

@Component
public class RPACSVWrite {

	@SuppressWarnings("deprecation")
	public void transformToCSV(String filePath, ArrayList<WorkSheetData> sheetArr) {
		// TODO Auto-generated method stub
        System.out.println("Creating CSV file");

        for (WorkSheetData workSheetData:sheetArr) {
        	File f = new File(filePath+workSheetData.getDatasheetName()+".csv");
        	if(!f.exists())
				try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(f, true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
            StringBuffer rowsOfSingleTab = new StringBuffer();
        for (Row datatype : workSheetData.getArr()) {
        	boolean rowStart = false;
            for (Cell field : datatype) {
            	if (rowStart == true) {
            	   rowsOfSingleTab.append(",");
            	}
            	if (field.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    switch(field.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                    	rowsOfSingleTab.append(field.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                    	rowsOfSingleTab.append(field.getRichStringCellValue().toString().trim());
                        break;
                    }
            	}    
                else if (field.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                	rowsOfSingleTab.append(field.getNumericCellValue());
                } else if (field.getCellType() == Cell.CELL_TYPE_STRING) {
                	rowsOfSingleTab.append(field.getRichStringCellValue().toString().trim());
                }
                //rowsOfSingleTab.append(field.toString());
                rowStart = true;
            }
            rowsOfSingleTab.append("\n");
        }
        try {
			outputStream.write(rowsOfSingleTab.toString().getBytes());
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }

        System.out.println("Done");
    }
	}
    

