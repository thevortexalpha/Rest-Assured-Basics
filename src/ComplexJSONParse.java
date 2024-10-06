import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJSONParse {
	
	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		System.out.println(js.getInt("dashboard.purchaseAmount"));
		
		System.out.println(js.getString("courses[2].title"));
		
		for(int i=0; i<count; i++) {
			String courseTitle = (js.get("courses["+i+"].title"));
			String coursePrice = (js.get("courses["+i+"].price")).toString();
			System.out.println(courseTitle+" "+coursePrice);
		}
		
		for(int i=0; i<count; i++) {
			String courseTitle = (js.get("courses["+i+"].title"));
			if(courseTitle.equalsIgnoreCase("RPA")) {
				System.out.println("Price of RPA course is "+(js.get("courses["+i+"].price")).toString());
				break;
			}
		}
	}
	
}
