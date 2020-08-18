package com.automation.utilities;

import static org.testng.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class validationUtilities {

	Logger log = Logger.getLogger(getClass().getSimpleName());

	public void validationIsDisplayed(WebElement hypLink_Gamil, AppiumDriver<WebElement> driverMob) {
		assertEquals(hypLink_Gamil.isDisplayed(), true, hypLink_Gamil + " is not displayed");
	}
	public void validationText(String sActual, String sExpected) {
		assertEquals(sActual.equals(sExpected),true);
	}
}
