package org.test;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;

class WorkSheetData {
   public String getDatasheetName() {
		return datasheetName;
	}
	public void setDatasheetName(String datasheetName) {
		this.datasheetName = datasheetName;
	}
	public ArrayList<Row> getArr() {
		return arr;
	}
	public void setArr(ArrayList<Row> arr) {
		this.arr = arr;
	}
String datasheetName;
   ArrayList<Row> arr;
}
