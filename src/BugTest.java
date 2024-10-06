import static io.restassured.RestAssured.given;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://thevortexalpha.atlassian.net/";
		
		String createIssueResponse = given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Basic dnN2YXRoZWtpbmdAZ21haWwuY29tOkFUQVRUM3hGZkdGMGM2WVNWMVBKR0laOGkxazkwYWdVU09HSXlkX2hRczNwWWFhSGhLN2tLVTd1LWtsRFktcUlDbHNrZlhiVU1aMlltN1ozX21vbDRtQVpmQXA3SG5Ranh5UzNYZWVtZExNeEs5QUt2OEJjYWpSZ2NYZmI1UXd1UExkVmRvQ051MmU0LWdDejJoU1Zvb2ZPWlp2UFF2NHcwSDlzY2hfZDczT245SUJPb0tUR09COD1FRjZBOTkzNw==")
			.body("{\r\n"
					+ "    \"fields\": {\r\n"
					+ "       \"project\":\r\n"
					+ "       {\r\n"
					+ "          \"key\": \"VVJIR\"\r\n"
					+ "       },\r\n"
					+ "       \"summary\": \"Hamburger button not working-Automation\",\r\n"
					+ "       \"description\": \"Check the Hamburger button again please\",\r\n"
					+ "       \"issuetype\": {\r\n"
					+ "          \"name\": \"Bug\"\r\n"
					+ "       }\r\n"
					+ "   }\r\n"
					+ "}")
			.log().all()
		.when()
			.post("rest/api/2/issue")
		.then()
			.log().all()
			.assertThat().statusCode(201)
			.extract().response().asString();
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println("The issue id is "+issueId);
		
		given()
			.pathParam("key", issueId)
			.header("X-Atlassian-Token", "no-check")
			.header("Authorization", "Basic dnN2YXRoZWtpbmdAZ21haWwuY29tOkFUQVRUM3hGZkdGMGM2WVNWMVBKR0laOGkxazkwYWdVU09HSXlkX2hRczNwWWFhSGhLN2tLVTd1LWtsRFktcUlDbHNrZlhiVU1aMlltN1ozX21vbDRtQVpmQXA3SG5Ranh5UzNYZWVtZExNeEs5QUt2OEJjYWpSZ2NYZmI1UXd1UExkVmRvQ051MmU0LWdDejJoU1Zvb2ZPWlp2UFF2NHcwSDlzY2hfZDczT245SUJPb0tUR09COD1FRjZBOTkzNw==")
			.multiPart("file", new File("C:\\Users\\Vsvat\\Pictures\\Bing Images\\AlpineMarmot_EN-IN2558708163_UHD.jpg"))
		.when()
			.post("rest/api/2/issue/{key}/attachments")
		.then()
			.log().all()
			.assertThat().statusCode(200);
			
	}	

}
