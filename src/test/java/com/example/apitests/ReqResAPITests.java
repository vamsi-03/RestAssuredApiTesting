package com.example.apitests;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


//schema validation
//import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.jsv.JsonSchemaValidator;


public class ReqResAPITests {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test(priority = 1)
    public void testListUsers() {
        System.out.println("1. List Users using GET Method");
        
        given()
        	.queryParam("page", 2)
        .when()
        	.get("api/users")
        .then()
        	.assertThat().statusCode(200)
        	.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("reqresFile.json5"))
        	.log().all();
        Reporter.log("List Users using GET is Working",true);
//        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not as expected");

        System.out.println("-------------------------------------------");
    }

    @Test(priority = 2)
    public void testSingleUser() {
        System.out.println("2. Single User using GET Method");
        
        given()
        	.queryParam("id", 3)
        .when()
        	.get("api/users")
        .then()
        	.assertThat().statusCode(200)
        	.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("singleUser.json5"))
        	.log().all();
        
        Reporter.log("List Single User using GET is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 3)
    public void testSingleUserNotFound() {
        System.out.println("3. Single User not Found Using GET Method");
        
        given()
        	.queryParam("id", 24)
        
        .when()
        	.get("api/users")
        .then()
        	.assertThat().statusCode(404)
        	.log().all();
        
        Reporter.log("SIngle User not Found  using GET is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 4)
    public void testListResource() {
        System.out.println("4. List Resource Using GET Method");
        
        given()
        	.queryParam("page", 2)
        .when()
        	.get("api/unknown")
        
        .then()
        	.assertThat().statusCode(200)
        	.log().all();
        
        Reporter.log("List Resource Using GET Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 5)
    public void testSingleResource() {
        System.out.println("5. Single Resource Using GET Method");
        
        given()
        	.queryParam("page", 2)
        .when()
        	.get("api/unknown/2")
        .then()
        .assertThat().statusCode(200)	
        	.log().all();
        Reporter.log("Single Resource Using GET Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 6)
    public void testSingleResourceNotFound() {
        System.out.println("6. Single Resource Not Found Using GET Method");
        
        given()
        	.queryParam("page", 2)
        .when()
        	.get("api/unknown/23")
        .then()
        .assertThat().statusCode(404)
        	.log().all();
        Reporter.log("Single Resource Not Found Using GET Method is Working",true);
        System.out.println("-------------------------------------------");
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(priority = 7)
    public void testCreateUser() {
        System.out.println("7. Create User Using POST Method");
        
        HashMap data = new HashMap();
        
        data.put("name","morpheus");
        data.put("job","leader");
        
        given()
        	.contentType("application/json")   // if we didn't specify this... we can't get the json format object in log()
        	.body(data)
        .when()
        	.post("api/users")
        .then()
        .assertThat().statusCode(201)
        	.log().all();

        Reporter.log("Create User Using POST Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(priority = 8)
    public void testUpdateUser() {
        System.out.println("8. Update User Using PUT Method");
        
        HashMap data = new HashMap();
        
        data.put("name","morpheus");
        data.put("job","zion resident");
        
        
        given()
        	.contentType("application/json")
        	.body(data)
        .when()
        	.put("api/users/2")
        .then()
        .assertThat().statusCode(200)
        	.log().all();
        
        Reporter.log("Update User Using PUT Method is Working",true);
        
        System.out.println("-------------------------------------------");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(priority = 9)
    public void testPatchUpdateUser() {
        System.out.println("9. Update User Using PATCH Method");
        
        HashMap data = new HashMap();
        
        data.put("name","morpheus");
        data.put("job","manager");
        
        given()
        	.contentType(ContentType.JSON)
        	.body(data)
        .when()
        	.patch("api/users/2")
        .then()
        .assertThat().statusCode(200)
        	.log().all();
        
        Reporter.log("Update User Using PATCH Method is Working",true);
        
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 10)
    public void testDeleteUser() {
        System.out.println("10. Delete User Using DELETE Method");
        
        given()
        	.queryParam("id", 2)
        .when()
        	.delete("api/users")
        .then()
        .assertThat().statusCode(204)
        	.log().all();
        Reporter.log("Delete User Using DELETE Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 11)
    public void testRegisterSuccess() {
        System.out.println("11. Success Register Using POST Method");
        
        JSONObject data=new JSONObject();
        
        data.put("email","eve.holt@reqres.in");
        data.put("password","pistol");

        given()
	    	.contentType("application/json")
	    	.body(data.toString())
    	
	    .when()
	    	.post("api/register")
	    .then()
	    .assertThat().statusCode(200)
	    	.log().all();
        Reporter.log("Success Register Using POST Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 12)
    public void testRegisterUnSuccess() {
        System.out.println("12. UnSuccess Register Using POST Method");
        
        JSONObject data=new JSONObject();
        
        data.put("email","eve.holt@reqres.in");
        	
        given()
        	.contentType(ContentType.JSON)
        	.body(data.toString())
        .when()
        	.post("api/register")
        
        .then()
        .assertThat().statusCode(400)
        	.log().all();
        Reporter.log("UnSuccess Register Using POST Method is Working",true);
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 13)
    public void testLoginSuccess() throws FileNotFoundException {
        System.out.println("13. Success Login Using POST Method");
        
        File f = new File("C:\\Users\\I7014\\eclipse-workspace\\RestAssuredAssignment\\src\\test\\java\\com\\example\\apitests\\Login.json");
        FileReader fr = new FileReader(f);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);
        
        given()
        	.contentType("application/json")
        	.body(data.toString())
        
        .when()
        	.post("api/login")
        .then()
        .assertThat().statusCode(200)
        	.log().all();
        
        Reporter.log("Success Login Using POST Method is Working",true);
        
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 14)
    public void testLoginUnSuccess() {
        System.out.println("14. UnSuccess Login Using POST Method");
        
        JSONObject data=new JSONObject();
        
        data.put("email","peter@klaven");
        
        given()
        	.contentType(ContentType.JSON)
        	.body(data)
        .when()
        	.post("api/register")
        
        .then()
        .assertThat().statusCode(400)
        	.log().all();
        
        Reporter.log("UnSuccess Login Using POST Method is Working",true);
        
        System.out.println("-------------------------------------------");
    }

    @Test(priority = 15)
    public void testDelayedResponse() {
        System.out.println("15. Delayed Response using GET Method");
        
        given()
        	.queryParam("delay", 2)
        .when()
        	.get("api/users")
        .then()
        .assertThat().statusCode(200)
        	.log().all();
        Reporter.log("Delayed Response using GET Method is Working",true);
        System.out.println("-------------------------------------------");
    }
    
    @AfterTest
    public void afterTest()
    {
    	System.out.println("Test Suite Execution Completed");
    }
}
