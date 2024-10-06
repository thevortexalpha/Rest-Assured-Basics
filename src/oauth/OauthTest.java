package oauth;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OauthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		String response = 
			given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust")
			.when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
			.then()
				.extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		GetCourse gc = 
			given()
				.queryParam("access_token", accessToken)
			.when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.as(GetCourse.class);
//			.then()
//				.extract().response().asString();
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		List<WebAutomation> wa = gc.getCourses().getWebAutomation();
		String courseName = "Selenium Webdriver Java";
		ArrayList<String> courseNameAPI = new ArrayList<String>();
		for(int i=0; i<wa.size(); i++) {
			if(wa.get(i).getCourseTitle().equalsIgnoreCase(courseName)) {
				System.out.println("Price of "+courseName+" is "+wa.get(i).getPrice());
			}
		}
		for(int i=0; i<wa.size(); i++) {
//			System.out.println(wa.get(i).getCourseTitle());
			courseNameAPI.add(wa.get(i).getCourseTitle());
		}
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertEquals(expectedList, courseNameAPI);
		
//		System.out.println(gc.getCourses().getMobile().get(0).getCourseTitle());x`
	}

}
