package com.automation.utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.jayway.jsonpath.JsonPath;
import com.automation.listeners.ExtentManager;

import io.appium.java_client.AppiumDriver;

@Listeners(com.automation.listeners.TestListener.class)
public class TestBase {
	public static commonUtilities oCommUtil = new commonUtilities();
	public static seleniumUtilities oSelUti = new seleniumUtilities();
	public static mobileUtilities oMobUti = new mobileUtilities();
	public static restUtilities oResUti = new restUtilities();
	public static validationUtilities oValUtil = new validationUtilities();
	public static constants oCons = new constants();
	public static ExtentManager oExt = new ExtentManager();
	Logger log = Logger.getLogger(getClass().getSimpleName());
	public static String sCurrentApp = "";
	public WebDriver driver;
	public AppiumDriver<WebElement> driverMob;
	public static String sClassNameForScreenShot;
	public static String sJsonConfig;
	public static boolean bWebRun = false;
	@BeforeSuite
	public void TriggerDependencies() throws Exception {
		oCommUtil.deleteScreenShotDirectory();
		sJsonConfig = oCommUtil.readfileReturnInString(
				System.getProperty("user.dir") + "/src/main/java/com/automation/configurations/config.json");
		oCommUtil.loadLog4jProperty(
				System.getProperty("user.dir") + "/src/main/java/com/automation/configurations/log4j.properties");

		oCommUtil.deleteCreateScreenShotDirectorySureFireReports();
		 constants.unixTimeStampCurrentRun = oCommUtil.unixTimeStampInString();
		sCurrentApp = JsonPath.read(sJsonConfig, "$.AutomationRunning").toString().toLowerCase();
		System.out.println(sCurrentApp);
		switch (sCurrentApp) {
		case constants.sAutomationMob:
			driverMob = oMobUti.launchApp();
			break;
		case constants.sAutomationWeb:
			driver = oSelUti.launchBrowser(JsonPath.read(sJsonConfig, "$.Browser").toString());
			bWebRun=true;
			break;
		default:
			log.error("Valid run type should be given");
			break;
		}

	}

	@AfterSuite
	public void ShuttingDownAllDependencies() throws Exception {

		switch (JsonPath.read(sJsonConfig, "$.AutomationRunning").toString().toLowerCase()) {
		case constants.sAutomationMob:
			driverMob.quit();
			break;
		case constants.sAutomationWeb:
			driver.quit();
			break;
		default:
			break;
		}
	}
}
