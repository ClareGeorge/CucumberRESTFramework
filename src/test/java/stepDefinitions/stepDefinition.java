package stepDefinitions;


 import static io.restassured.RestAssured.*;
 import static io.restassured.matcher.RestAssuredMatchers.*;
 import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.ResPayloadCreateProduct;
import resources.EcomUtils;
import resources.TestDataBuild;

public class stepDefinition extends EcomUtils {
	
	public static RequestSpecification reqSpec;
	public static Response res = null ;
	public static  JsonPath jResponse = null ;
	
	public static String user_token = null;
	public static String user_id = null;
	public static String productId = null;


	@Given("I am able to call API  {string} using email {string} and password {string}")
	public void i_am_able_to_call_api_using_email_and_password(String nameAPI, String userEmail, String userPassword) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		reqSpec = given()
					.spec(requestSpecification())
					.body(TestDataBuild.reqPayloadLoginPOJOJSON ( userEmail,  userPassword) );
		
		res = reqSpec
				.when()
					.post( getPropertyValue(nameAPI))
				.then()
					.spec(responseSpecification())
					.header("Content-Length", Integer::parseInt, greaterThan(0))
					.extract()
					.response();
		
		jResponse = convertToJSONusingJsonPath(res);
		user_token = jResponse.getString("token").toString();
		user_id = jResponse.getString("userId").toString();
		System.out.println(user_id + " has logged in successfully");
		
		
	}
	
	@When("I call API {string} with product-name {string} productSubCategory	 {string}  productPrice 	{string}")
	public void i_call_api_with_product_name_product_sub_category_product_price(String nameAPI, String productName, String productSubCategory, String productPrice) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	
		reqSpec = given()
				.spec(requestSpecification())
				.header("authorization" ,user_token)
				.contentType(ContentType.MULTIPART)
				.formParams(TestDataBuild.getFormdataForCreateProduct(
										productName, user_id, productSubCategory, productPrice))
				.multiPart("productImage",new File("C:\\Users\\clare\\OneDrive\\Pictures\\Screenshots\\NewBook.jpeg"));

		res = reqSpec
			.when()
				.post( getPropertyValue(nameAPI))
			.then()
				.spec(responseCreateProductSpecification())
				.header("Content-Length", Integer::parseInt, greaterThan(0))
				.extract()
				.response();

		jResponse = convertToJSONusingJsonPath(res);
		productId = jResponse.getString("productId");
		
	}
	@Given("the {string} is {string}")
	public void the_is(String key, String value) {
	    // Write code here that turns the phrase above into concrete actions
		
		assertEquals(value, jResponse.getString(key).toString());
		
	}
	/*
	 * @Then("the {string} is {string}") public void the_is(String string, String
	 * string2) { // Write code here that turns the phrase above into concrete
	 * actions throw new io.cucumber.java.PendingException(); }
	 */
	@Then("I am able to call API  {string} successfully with  {string} {string}")
	public void i_am_able_to_call_api_successfully_with(String nameAPI, String key, String value) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		reqSpec = given()
				.spec(requestSpecification())
				.header("authorization" ,user_token)
				.contentType(ContentType.ANY)
				.pathParam("productId", productId);
		
		res = reqSpec
				.when()
				.delete(getPropertyValue(nameAPI))
			.then()
				.spec(responseSpecification())
				.header("Content-Length", Integer::parseInt, greaterThan(0))
				.extract().response();
		jResponse = convertToJSONusingJsonPath(res);
		assertEquals(value, jResponse.getString(key).toString());
	}



}
