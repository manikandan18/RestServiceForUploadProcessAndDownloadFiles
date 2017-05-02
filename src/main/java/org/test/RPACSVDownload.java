package org.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

@Component
public class RPACSVDownload {

	@SuppressWarnings("deprecation")
	public ByteArrayOutputStream[] transformToCSV(ArrayList<WorkSheetData> sheetArr) {
		// TODO Auto-generated method stub
        System.out.println("Creating CSV file");
        ByteArrayOutputStream[] outputStream = new ByteArrayOutputStream[sheetArr.size()];
        int i=0;
        for (WorkSheetData workSheetData:sheetArr) {
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
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
			byteOutputStream.write(rowsOfSingleTab.toString().getBytes());
			outputStream[i] = byteOutputStream;
			byteOutputStream.close();
			i++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }

        System.out.println("Done");
        return outputStream;
    }
	}
    
