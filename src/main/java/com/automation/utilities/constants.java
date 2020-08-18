package com.automation.utilities;

import org.apache.log4j.Logger;

public class constants {
	
	Logger log = Logger.getLogger(getClass().getSimpleName());
	//Environment and Running app (to avoid driver.quit error)
	public static final String sConstEnvironment="PROD",sAutomationAPI="api",sAutomationWeb="web", sAutomationMob="mob";
	
	public static String unixTimeStampCurrentRun = "";
	
}
