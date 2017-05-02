package org.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.model.HiringProcessInput;
import org.springframework.stereotype.Component;

@Component
public class RPAHiringDecision {

	public ArrayList<WorkSheetData> processDecision(HiringProcessInput hiringProcessInput, InputStream excelFile) {
    	ArrayList<WorkSheetData> sheetArr = null;
        try {

            //FileInputStream excelFile = new FileInputStream(new File(inputFileName));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            sheetArr = new ArrayList<WorkSheetData>();
             
            for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            XSSFSheet datatypeSheet = workbook.getSheetAt(i);
            WorkSheetData workSheetData = new WorkSheetData();
            workSheetData.setDatasheetName(datatypeSheet.getSheetName());
            Iterator<Row> iterator = datatypeSheet.iterator();
            ArrayList<Row> rowArr = new ArrayList<Row>();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                /*
                if (currentRow.getRowNum() == 0) 
                	continue;
                */ 	
                Iterator<Cell> cellIterator = currentRow.iterator();
                int cellCount = 0;
                boolean addRow = true;
                StringBuffer reasonString = new StringBuffer();
                while (cellIterator.hasNext()) {
                  //if (rowNo == 1) { 

                    Cell currentCell = cellIterator.next();
                    if (currentRow.getRowNum() == 0) 
                    	continue;                   
                    if ((cellCount == 2) && 
                    	hiringProcessInput.getHiringCriteria().contains("Salary") &&
                    	hiringProcessInput.getSalary() != currentCell.getNumericCellValue()
                    	) {
                    	addRow = false;
                    	reasonString.append("Salary ");
                    }
                    if ((cellCount == 3) && 
                       	hiringProcessInput.getHiringCriteria().contains("Union") &&
                       	!hiringProcessInput.getUnion().equals(currentCell.getStringCellValue())) {
                       	addRow = false;
                       	if (reasonString.length() != 0) {
                       		reasonString.append(", Union ");	
                       	}
                       	else {
                       		reasonString.append("Union ");	
                       	}
                    } 
                    if ((cellCount == 4) && 
                       	hiringProcessInput.getHiringCriteria().contains("Location") &&
                       	!hiringProcessInput.getLocation().equals(currentCell.getStringCellValue())) {
                       	addRow = false;
                       	if (reasonString.length() != 0) {
                       		reasonString.append(", Location ");	
                       	}
                       	else {
                       		reasonString.append("Location ");	
                       	}                       	
                    }                    
                    cellCount += 1;
                //}
 
                }
                //rowNo += 1;
                System.out.println();
                Cell condtn = currentRow.createCell(5);
                Cell reason = currentRow.createCell(6);
                if (currentRow.getRowNum() != 0) {
                   if (addRow == true) {                	
                	  condtn.setCellValue("Yes");                	
                	  reason.setCellValue(" ");	
                   }
                   else {
                	  condtn.setCellValue("No");
                	  reason.setCellValue(reasonString.toString()+" not present");
                   }
                }
                else {
                   condtn.setCellValue("Hiring Decision");
                   reason.setCellValue("Reason");
                }
                rowArr.add(currentRow);	   
            }
            workSheetData.setArr(rowArr);
            sheetArr.add(workSheetData);
            }
            workbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetArr;
    }
    
}
