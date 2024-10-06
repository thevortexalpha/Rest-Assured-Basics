package files;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {

	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
	
	public static JsonPath rawResponseToJson(Response resp) {
		JsonPath js = new JsonPath(resp.asString());
		return js;
	}
}
