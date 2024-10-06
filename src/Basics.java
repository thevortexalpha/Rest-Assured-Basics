import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		String newAddress = "Dubai kurukusandhu, Dubai";
		
		RestAssured.baseURI = "https://216.10.245.166";
		RestAssured.useRelaxedHTTPSValidation();
		
		// Adding a new place
		String response = 
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.headers("Content-Type", "application/json")
			.body(
				new String(
					Files.readAllBytes(
						Paths.get(
							"C:\\Users\\Vsvat\\Documents\\Docs\\VortexScripts\\eclipse-workspace\\DemoProject\\src\\files\\addPlace.json"
						)
					)
				)
			)
		.when()
			.post("maps/api/place/add/json")
		.then()
			.assertThat()
			.statusCode(200)
			.body("scope", equalTo("APP"))
			.header("Server", "Apache")
			.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		// Updating the address
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.headers("Content-Type", "application/json")
			.body(Payload.updatePlace(placeId, newAddress))
		.when()
			.put("maps/api/place/update/json")
		.then()
			.assertThat()
			.statusCode(200)
			.body("msg", equalTo("Address successfully updated"));
			
		// Getting the new address and validating it
		String getPlaceResponse = 
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", placeId)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.assertThat()
			.statusCode(200)
			.body("address", equalTo(newAddress))
			.extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);
	}

}
