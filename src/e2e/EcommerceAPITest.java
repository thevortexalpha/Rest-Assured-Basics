package e2e;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EcommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req =new RequestSpecBuilder()
			.setBaseUri("https://rahulshettyacademy.com")
			.setContentType(ContentType.JSON)
			.build();
		
		// ** Log in, Fetching ID & Token ** //
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("vsvatheking@gmail.com");
		loginRequest.setUserPassword("Rockvibin123");
		
		RequestSpecification reqLogin = given().spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
										.then().extract().response().as(LoginResponse.class);

		String authToken = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		// ** Add Product ** //
		RequestSpecification addProductBaseReq =new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", authToken)
				.build();
		RequestSpecification addProductRequest =given()
			.spec(addProductBaseReq)
			.param("productName", "Woman coat").param("productAddedBy", userId)
			.param("productCategory", "fashion").param("productSubCategory", "shirts")
			.param("productPrice", "50").param("productDescription", "Addidas Originals")
			.param("productFor", "women").multiPart("productImage", new File("C:/Users/Vsvat/Downloads/productImage_1650649434146.jpeg"));
		String addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product")
			.then().extract().response().asString();
			// Fetching product ID from response
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		
		// ** Create Order ** //
		RequestSpecification createOrderBaseReq =new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", authToken)
				.setContentType(ContentType.JSON)
				.build();
			
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("Netherlands");
		orderDetail.setProductOrderedId(productId);
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderRequest = given().spec(createOrderBaseReq).body(orders);
		String createOrderResponse = createOrderRequest.when().post("/api/ecom/order/create-order")
			.then().extract().response().asString();
		// Fetching product ID from response
				JsonPath js2 = new JsonPath(createOrderResponse);
		String orderId = js2.get("orders[0]");
		  //  System.out.println(createOrderResponse);
		
		// ** Get Order Details ** //
		RequestSpecification getOrderDetailsBaseReq =new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", authToken)
				.build();
		
		String getOrderDetailsResponse = given().spec(getOrderDetailsBaseReq).queryParam("id", orderId)
			.when().get("/api/ecom/order/get-orders-details")
			.then().extract().response().asString();
		
		JsonPath js3 = new JsonPath(getOrderDetailsResponse);
		String successMessage = js3.get("message");
		Assert.assertEquals(successMessage, "Orders fetched for customer Successfully");
		
		// ** Delete Product ** //
		RequestSpecification deleteProductBaseReq =new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", authToken)
				.build();
		
		RequestSpecification deleteProductRequest = given().spec(deleteProductBaseReq).pathParam("productId", productId);
		String deleteProductResponse = deleteProductRequest.when().delete("/api/ecom/product/delete-product/{productId}")
			.then().extract().response().asString();
		
		JsonPath js4 = new JsonPath(deleteProductResponse);
		String message = js4.get("message");
		Assert.assertEquals(message, "Product Deleted Successfully");
		
	}

}
