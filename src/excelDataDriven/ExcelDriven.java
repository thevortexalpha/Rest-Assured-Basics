package excelDataDriven;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.testng.annotations.Test;

import dataDriven.ExcelDataDrivenTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ExcelDriven {
	
	@Test
	public void addBook() throws IOException {
		HashMap<String, Object> jsonAsMap = new HashMap<>();
		ExcelDataDrivenTest eddt = new ExcelDataDrivenTest();
		ArrayList data = eddt.getData("RestAssured", "RestAddBook");
		
		jsonAsMap.put("name", data.get(1));
		jsonAsMap.put("isbn", data.get(2));
		jsonAsMap.put("aisle", data.get(3));
		jsonAsMap.put("author", data.get(4));
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String addBookResponse = given()
					.header("Content-Type", "application/json")
					.body(jsonAsMap)
				.when()
					.post("/Library/Addbook.php")
				.then()
					.assertThat().statusCode(200)
					.extract().response().asString();
	
		JsonPath js = new JsonPath(addBookResponse);
		String id = js.getString("ID");
		System.out.println(id);
		
	}

}
