package org.test;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Component;

@Component
class RPAExcelUpload {

    public ArrayList<WorkSheetData> readXL(InputStream readFileName) {

    	ArrayList<WorkSheetData> sheetArr = null;
        try {

            //FileInputStream excelFile = new FileInputStream(new File(readFileName));
            
            XSSFWorkbook workbook = new XSSFWorkbook(readFileName);
            sheetArr = new ArrayList<WorkSheetData>();
             
            for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            XSSFSheet datatypeSheet = workbook.getSheetAt(i);
            WorkSheetData workSheetData = new WorkSheetData();
            workSheetData.setDatasheetName(datatypeSheet.getSheetName());
            Iterator<Row> iterator = datatypeSheet.iterator();
            ArrayList<Row> rowArr = new ArrayList<Row>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
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
