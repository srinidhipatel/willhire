package com.automation.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class mobileUtilities extends TestBase {
	public static enum DIRECTION {
	    DOWN, UP, LEFT, RIGHT;
	}
	public static String sJsonMobileConfig;

	public AppiumDriver<WebElement> launchApp() throws Exception {
		sJsonMobileConfig = oCommUtil.readfileReturnInString(
				System.getProperty("user.dir") + "/src/main/java/com/automation/configurations/appiumConfig.json");
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");

		if (Boolean.parseBoolean(JsonPath.read(sJsonMobileConfig, "$.emulator").toString()) == true)
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		else
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
					JsonPath.read(sJsonMobileConfig, "$.deviceName").toString());

		if (Boolean.parseBoolean(JsonPath.read(sJsonMobileConfig, "$.deviceBrowser").toString()) == true) {
			desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			desiredCapabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		} else {
			desiredCapabilities.setCapability("appPackage",
					JsonPath.read(sJsonMobileConfig, "$.appPackageName").toString());
			desiredCapabilities.setCapability("appActivity",
					JsonPath.read(sJsonMobileConfig, "$.appActivity").toString());
		}
		//desiredCapabilities.setCapability("newCommandTimeout", 222220);
		driverMob = new AndroidDriver<WebElement>(
				new URL("http://" + JsonPath.read(sJsonMobileConfig, "$.appiumServerIP").toString() + "/wd/hub"),
				desiredCapabilities);

		return driverMob;
	}
	@SuppressWarnings({ "deprecation", "unchecked" , "rawtypes" })
	public void waitForElementVisibleMob(AppiumDriver<WebElement> driver, WebElement ele, int iTimeInSeconds) throws Exception {		
		Wait wait = new FluentWait(driver)
		        .withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
		        .pollingEvery(250, TimeUnit.MILLISECONDS)
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		        wait.until(ExpectedConditions.visibilityOf(ele));
	}
	@SuppressWarnings({ "deprecation", "unchecked" , "rawtypes" })
	public void waitForElementDisableMob(AppiumDriver<WebElement> driver, WebElement ele, int iTimeInSeconds) throws Exception{			
		Wait wait = new FluentWait(driver)
        .withTimeout(iTimeInSeconds, TimeUnit.SECONDS)
        .pollingEvery(250, TimeUnit.MILLISECONDS)
        .ignoring(NoSuchElementException.class)
        .ignoring(TimeoutException.class);

        wait.until(ExpectedConditions.invisibilityOf(ele));
	}

	public String takeScreenShotMobReturnPath(String tcName) throws Exception {
		String destDir = "/screenshots/";
		TakesScreenshot scrShot = ((TakesScreenshot) driverMob);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		String sImageName = System.getProperty("user.dir") + destDir + tcName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(sImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sImageName;
	}
	
	public void swipe(AppiumDriver<WebElement> driver, DIRECTION direction, long duration) {
	    Dimension size = driver.manage().window().getSize();

	    int startX = 0;
	    int endX = 0;
	    int startY = 0;
	    int endY = 0;
	    @SuppressWarnings("rawtypes")
		TouchAction swp = new TouchAction(driver);
	    switch (direction) {
	        case RIGHT:
	            startY = (int) (size.height / 2);
	            startX = (int) (size.width * 0.90);
	            endX = (int) (size.width * 0.05);
	         
	            swp.press(PointOption.point(startX, startY)).
	            waitAction(new WaitOptions().withDuration(Duration.ofMillis(duration))).
	            moveTo(PointOption.point(endX, startY)).
	            release().perform();
	            
	            /*new TouchAction(driver)
	                    .press(startX, startY)
	                    .waitAction(Duration.ofMillis(duration))
	                    .moveTo(endX, startY)
	                    .release()
	                    .perform();*/
	            break;

	        case LEFT:
	            startY = (int) (size.height / 2);
	            startX = (int) (size.width * 0.05);
	            endX = (int) (size.width * 0.90);
	            
	            swp.press(PointOption.point(startX, startY)).
	            waitAction(new WaitOptions().withDuration(Duration.ofMillis(duration))).
	            moveTo(PointOption.point(endX, startY)).
	            release().perform();
	           /* new TouchAction(driver)
	                    .press(startX, startY)
	                    .waitAction(Duration.ofMillis(duration))
	                    .moveTo(endX, startY)
	                    .release()
	                    .perform();*/

	            break;

	        case UP:
	            endY = (int) (size.height * 0.70);
	            startY = (int) (size.height * 0.30);
	            startX = (size.width / 2);
	            swp.press(PointOption.point(startX, startY)).
	            waitAction(new WaitOptions().withDuration(Duration.ofMillis(duration))).
	            moveTo(PointOption.point(endX, startY)).
	            release().perform();
	           /* new TouchAction(driver)
	                    .press(startX, startY)
	                    .waitAction(Duration.ofMillis(duration))
	                    .moveTo(endX, startY)
	                    .release()
	                    .perform();*/
	            break;


	        case DOWN:
	            startY = (int) (size.height * 0.70);
	            endY = (int) (size.height * 0.30);
	            startX = (size.width / 2);
	            swp.press(PointOption.point(startX, startY)).
	            waitAction(new WaitOptions().withDuration(Duration.ofMillis(duration))).
	            moveTo(PointOption.point(startX, endY)).
	            release().perform();
	           /* new TouchAction(driver)
	                    .press(startX, startY)
	                    .waitAction(Duration.ofMillis(duration))
	                    .moveTo(startX, endY)
	                    .release()
	                    .perform();*/

	            break;

	    }
	}
	
	public MobileElement scrollToElementVertically(AppiumDriver<WebElement> driverMob, String sResourceID, String sContainsText) throws Exception {
		
		System.out.println("Scroll Start");
		MobileElement element = (MobileElement) driverMob.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\""+sResourceID+"\")).scrollIntoView("
				+ "new UiSelector().textContains(\""+sContainsText+"\"))"));
		System.out.println("Scroll End");
		//element.getCenter();
		return element;
}
	public MobileElement scrollToElementHorizantally(AppiumDriver<WebElement> driverMob, String sResourceID, String sContainsText) throws Exception {
		System.out.println("Horizantal Scroll Start");
		MobileElement element = (MobileElement) driverMob.findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().resourceId(\""+sResourceID+"\")).setAsHorizontalList().scrollIntoView("
			+ "new UiSelector().textContains(\""+sContainsText+"\"))"));
		System.out.println("Horizantal Scroll End");
		/*MobileElement element = (MobileElement) driverMob.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().fromParent(new UiSelector().resourceId(\""+sResourceID+"\"))).setAsHorizontalList().getChildByText("
				+ "new UiSelector().className(\"android.widget.TextView\"), \""+sContainsText+"\"))"));*/
		//element.getCenter();
	
	//Perform the action on the element
	//System.out.println(element.getAttribute("id"));
	return element;
	}
}
