package com.automation.utilities;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class restUtilities extends TestBase {
	public Response ufGet(String sURL) throws Exception {
		RestAssured.baseURI=sURL;
		Response res = RestAssured.given().get();
		System.out.println(res.asString());
		return res;
	}
}
