import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		int count = js.getInt("courses.size()");
		int totalPrice = js.getInt("dashboard.purchaseAmount");
		
		int additionOfAllCourses = 0;
		
		for(int j=0; j<count; j++) {
			int currentCoursePrice = (js.getInt("courses["+j+"].price"));
			int currentCopies = (js.getInt("courses["+j+"].copies"));
			int currentTotalPrice = currentCoursePrice * currentCopies;
			additionOfAllCourses = additionOfAllCourses + currentTotalPrice;
		}
		Assert.assertEquals(additionOfAllCourses, totalPrice);
	}
}
