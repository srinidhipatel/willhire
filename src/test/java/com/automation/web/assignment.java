package com.automation.web;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.automation.poweb.po_signup;
import com.automation.utilities.TestBase;
import com.jayway.jsonpath.JsonPath;

public class assignment extends TestBase {

	
	Logger log = Logger.getLogger(getClass().getSimpleName());
	po_signup oSignUp;
	
	@BeforeTest
	public void requiredSettingsForGoogleSearch() throws Exception {
		sClassNameForScreenShot = getClass().getSimpleName();
		oSignUp = new po_signup(driver);
		
	}

	@Test(priority = 1)
	public void signUpErrorValidation() throws Exception {
		oSignUp.errorValidationOnSignUpPage();
	}
	
	@Test(priority = 2)
	public void signUpUser() throws Exception {
		driver.get(JsonPath.read(sJsonConfig, "$.url").toString().toLowerCase());	
		oSignUp.signUpNewUser();	
	}
	@Test(priority = 3)
	public void loginToAccount() throws Exception {
		driver.get(JsonPath.read(sJsonConfig, "$.url").toString().toLowerCase());	
		oSignUp.loginToAccount();	
	}
}
