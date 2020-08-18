package com.automation.utilities;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;

import org.testng.annotations.*;
import com.google.common.base.Function;
import com.jayway.jsonpath.JsonPath;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class seleniumUtilities extends TestBase{

	Logger log = Logger.getLogger(getClass().getSimpleName());

	/*
	 * @Author: Srinidhi 
	 * Method NAme : launchBrowser Parameters Browser : You have to
	 * pass browser types. Allowed ch(chrome), Ff(Firefox) 
	 * return : void
	 * Description: Launch the type of the browser which is been called
	 */
	@SuppressWarnings("deprecation")
	@Parameters("browserType")
	public WebDriver launchBrowser(String browser) throws Exception {
		log.info("Launching Browser");

		switch (browser) {
		case "ch":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--disable-notifications");
			driver = new ChromeDriver(chromeOptions);
			break;
			
		case "ff" :
			WebDriverManager.firefoxdriver().setup();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("permissions.default.desktop-notification", 1);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(capabilities);
			break;
	
		default:
			throw new Exception("Browser is not correct");
		}
		driver.get(JsonPath.read(sJsonConfig, "$.url").toString().toLowerCase());
		driver.manage().window().maximize();
		return driver;
	}

	@SuppressWarnings("deprecation")
	public boolean waitForElementVisible(WebDriver driver, WebElement ele, int iTimeInSeconds) throws Exception {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				if (ele.isDisplayed()) {
					log.info("Element Displayed : " + ele);
					return ele;
				} else {
					log.info("******Element NOT Displayed : " + ele);
					return null;
				}
			}
		});

		return ele.isDisplayed();
	}

	public boolean waitForElementDisable(WebDriver driver, WebElement ele, int iTimeInSeconds) {

		@SuppressWarnings("deprecation")
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				if (!ele.isDisplayed()) {
					log.info("Element Not Displayed : " + ele);
					return ele;
				} else {
					log.info("******Element is Still Displaying : " + ele);
					return null;
				}
			}
		});

		return !ele.isDisplayed();
	}

	public boolean isDisplayed(WebElement ele) throws Exception {
		boolean bRes_flag = false;
		try {
			if (ele.isDisplayed()) {
				log.info("Displayed " + ele);
				bRes_flag = true;
			}
		} catch (Exception ea) {
			bRes_flag = false;
		}
		return bRes_flag;
	}

	public String screenShotBrowser(String tcName) throws Exception {
		String destDir = "/screenshots/";
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		String sImageName = System.getProperty("user.dir") + destDir + tcName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(sImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sImageName; 
	}

	public void screenShotPhone(AppiumDriver<WebElement> driver, String className) throws Exception {
		String destDir = "screenshots";
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		String destFile = className + ".png";

		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "/test-output/" + destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public boolean ufIsDisplayed(WebElement ele) throws Exception {
		boolean bRes_Flag = false;
		try {
			bRes_Flag = ele.isDisplayed();
		}catch(Exception na) {
			bRes_Flag = false;
		}
		return bRes_Flag;
	}

	public boolean isAttribtuePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public boolean ScrollToView(WebDriver driver, WebElement ele) throws Exception {
		boolean bRes_Flag = false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
		bRes_Flag = true;
		return bRes_Flag;
	}

	public void ClickUsingJS(WebElement ele) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ele);
	}

	public void ufClick(WebElement ele) throws Exception {
		ele.click();
	}

	public void ufSendKeys(WebElement ele, String keysToSend) throws Exception {
		ele.clear();
		ele.sendKeys(keysToSend);
	}

	public String ufGetText(WebElement ele) throws Exception {
		log.info(ele.getText());
		return ele.getText();
	}

}
