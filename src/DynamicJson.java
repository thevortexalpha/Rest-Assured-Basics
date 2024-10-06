import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBook(String aisle, String isbn) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String addBookResponse = 
		given()
			.header("Content-Type", "application/json")
			.body(Payload.addBook(aisle, isbn))
		.when()
			.post("Library/Addbook.php")
		.then()
			.assertThat().statusCode(200)
			.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(addBookResponse);
		
		String id = js.getString("ID");
		System.out.println(id);
			
	}
	
	@Test(dataProvider="BooksData")
	public void deleteBook(String aisle, String isbn) {
		String deleteBookResponse = 
		given()
			.header("Content-Type", "application/json")
			.body(Payload.deleteBook(aisle+isbn))
		.when()
			.post("/Library/DeleteBook.php")
		.then()
			.assertThat().statusCode(200)
			.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(deleteBookResponse);
		
		String responseMessage = js.getString("msg");
		
		System.out.println(responseMessage);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {
			{"haowa", "8191"},
			{"vdsji", "6578"},
			{"jnvwo", "6549"}
		};
	}
	
}
