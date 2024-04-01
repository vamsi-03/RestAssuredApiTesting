package com.practice;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

public class Practice {
	
	@Test(priority=1,enabled = true)
	void getUsers() {
		
	given()
	
	.when()
		.get("http://localhost:3000/Employee")
	
	.then()
		.statusCode(200)
		.log().all()
    	.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("practice.json"))
    	;
    			
	
	}
	
	@Test(priority=2,enabled = true)
	void getList()
	{
		given()
		
		.when()
			.get("http://localhost:3001/Assignment")
		.then()
		.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Assignment.json"))
		.log().all();
	}
	
	@Test(priority=3,enabled = true)
	void xmlSchemaValidation()
	{
		given()
		.when()
			.get("https://mocktarget.apigee.net/xml")
		.then()
			.assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchemaValidate.xsd"))
			.log().all();
	}
	

	
}
