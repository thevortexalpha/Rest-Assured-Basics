package excelDataDriven;

import java.io.IOException;
import java.util.ArrayList;

import dataDriven.ExcelDataDrivenTest;

public class NewTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ExcelDataDrivenTest eddt = new ExcelDataDrivenTest();
		
		ArrayList data = eddt.getData("Sheet1", "RestAddBook");
	}

}
