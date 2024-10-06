import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
 
public class SpecBuilderTest {
	public static void main(String[] args) {
		List<String> types = new ArrayList<String>();
		AddPlace ap = new AddPlace();
		AddPlaceLocation apl = new AddPlaceLocation();
		
		types.add("Nederlands");
		types.add("India");
		
		apl.setLat(-38.383494);
		apl.setLng(33.427362);
		
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("Tamil-IN");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("www.roadwarriorenterprise.com");
		ap.setName("VortexAlpha");
		ap.setTypes(types);
		ap.setLocation(apl);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String res = 
			given()
				.queryParam("key", "qaclick123")
				.body(ap)
			.when()
				.post("/maps/api/place/add/json")
			.then()
				.assertThat().statusCode(200)
				.extract().response().asString();
		
		System.out.println(res);
	}
}
