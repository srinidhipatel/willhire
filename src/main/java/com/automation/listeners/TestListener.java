package com.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.automation.listeners.ExtentManager;
import com.automation.utilities.TestBase;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.lang.ThreadLocal;

public class TestListener extends TestBase implements ITestListener {

    //Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); 
    Logger log = Logger.getLogger(getClass().getSimpleName());

    @Override
    public synchronized void onStart(ITestContext context) {
        log.info("Test Suite started!");
        
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        log.info(("Test Suite is ending!"));
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        log.info((result.getMethod().getMethodName() + " started!"));
        ExtentManager.extLogger = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(ExtentManager.extLogger);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        log.info((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("Test passed");
        try {
			if(bWebRun)
			{
				ExtentManager.extLogger.log(Status.PASS, "Passed"+ExtentManager.extLogger.addScreenCaptureFromPath(oSelUti.screenShotBrowser(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
			}
			else {
				ExtentManager.extLogger.log(Status.PASS, "Passed"+ExtentManager.extLogger.addScreenCaptureFromPath(oMobUti.takeScreenShotMobReturnPath(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        log.info((result.getMethod().getMethodName() + " failed!"));
        test.get().fail(result.getThrowable());
			try {
				if(bWebRun)
				{
					ExtentManager.extLogger.log(Status.FAIL, "Failed"+ExtentManager.extLogger.addScreenCaptureFromPath(oSelUti.screenShotBrowser(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
				}
				else {
					ExtentManager.extLogger.log(Status.FAIL, "Failed"+ExtentManager.extLogger.addScreenCaptureFromPath(oMobUti.takeScreenShotMobReturnPath(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
       
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        log.info((result.getMethod().getMethodName() + " skipped!"));
        test.get().skip(result.getThrowable());
        try {
			if(bWebRun)
			{
				ExtentManager.extLogger.log(Status.SKIP, "Skipped"+ExtentManager.extLogger.addScreenCaptureFromPath(oSelUti.screenShotBrowser(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
			}
			else {
				ExtentManager.extLogger.log(Status.SKIP, "Skipped"+ExtentManager.extLogger.addScreenCaptureFromPath(oMobUti.takeScreenShotMobReturnPath(sClassNameForScreenShot+"_"+result.getMethod().getMethodName())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }
    
}