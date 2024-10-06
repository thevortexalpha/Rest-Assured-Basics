import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
 
public class Serialization {
	public static void main(String[] args) {
		List<String> types = new ArrayList<String>();
		AddPlace ap = new AddPlace();
		AddPlaceLocation apl = new AddPlaceLocation();
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		types.add("Nederlands");
		types.add("India");
		
		apl.setLat(-38.383494);
		apl.setLng(33.427362);
		
		RequestSpecification reqSpec = new RequestSpecBuilder()
			.setBaseUri("https://rahulshettyacademy.com")
			.addQueryParam("key", "qaclick123")
			.setContentType(ContentType.JSON)
			.build();
		ResponseSpecification resSpec = new ResponseSpecBuilder()
			.expectStatusCode(200)
			.expectContentType(ContentType.JSON)
			.build();
		
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("Tamil-IN");
		ap.setPhone_number("(+91) 983 123 3937");
		ap.setWebsite("www.roadwarriorenterprise.com");
		ap.setName("VortexAlpha");
		ap.setTypes(types);
		ap.setLocation(apl);
		
		
		
		RequestSpecification requestSpec = given().spec(reqSpec).body(ap);
		String res= requestSpec.when()
				.post("/maps/api/place/add/json")
			.then().spec(resSpec).extract().response().asString();
		
		System.out.println(res);
	}
}
