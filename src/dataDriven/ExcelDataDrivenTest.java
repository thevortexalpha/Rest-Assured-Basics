package dataDriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataDrivenTest {
	
	public ArrayList<String> getData(String sheetName, String testcaseName) throws IOException 
	{
		ArrayList<String> a = new ArrayList<String>();
		/* Creating a file input stream object to get the excel via path*/
		FileInputStream fis = new FileInputStream("C:\\Users\\Vsvat\\Documents\\Docs\\VortexScripts\\eclipse-workspace\\RestAssuredBasics\\Testdata.xlsx");
		
		/* Creating an object for the XSSFWorkbook class to access the excel file */
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		/* Excel might have many sheet. Iterating to get the required sheet */
		int sheets = workbook.getNumberOfSheets();
		
		for(int i=0; i<sheets; i++) 
		{
			if(workbook.getSheetName(i).equals(sheetName)) {
				XSSFSheet worksheet = workbook.getSheetAt(i);
				
				Iterator<Row> rows = worksheet.iterator();
				Row firstRow = rows.next();
				int k = 0, column = 0;
				Iterator<Cell> ce =  firstRow.cellIterator();
				while(ce.hasNext()) {
					Cell value = ce.next();
					if(value.getStringCellValue().equals("Testcases")) {
						column = k;
					}
					k++;
				}
				
				System.out.println(column);
				
				while(rows.hasNext()) 
				{
					Row r = rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) 
					{
						Iterator<Cell> cv = r.cellIterator();
						while(cv.hasNext())
						{
							Cell c = cv.next();
							
							if(c.getCellType() == CellType.STRING) {
								a.add(c.getStringCellValue());
							} else {
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
							
							
							System.out.println();
						}
					}
				}
				
				
			}
			
			
		}
		return a;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		
	}

}
