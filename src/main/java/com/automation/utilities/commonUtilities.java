package com.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class commonUtilities extends  TestBase{

	public static TestBase oTest = new TestBase();
	Logger log = Logger.getLogger(getClass().getSimpleName());
	static Properties props = new Properties();
	static FileInputStream fileIn = null;

	public void loadPropertyFiles(String PropertiesFilePath) throws Exception {

		log.info("Current dir using System:" + PropertiesFilePath);
		fileIn = new FileInputStream(PropertiesFilePath);
		props.load(fileIn);
		System.getProperties().putAll(props);
		// log.info(util.props);
	}

	public void loadLog4jProperty(String PropertiesFilePath) throws Exception {
		log.info("Log4j Property file Path :" + PropertiesFilePath);
		fileIn = new FileInputStream(PropertiesFilePath);
		props.load(fileIn);
		PropertyConfigurator.configure(props);
	}

	public void ufUserException(boolean bExpectedBoolean, boolean ActualBoolean, String strExceptionSummary)
			throws Exception {
		if (bExpectedBoolean != ActualBoolean)
			throw new Exception(strExceptionSummary);
	}

	

	public void hardSleep(int iSeconds) throws Exception {
		Thread.sleep(1000 * iSeconds);
	}

	public void deleteScreenShotDirectory() {
		String destDir = "/screenshots";
		String SRC_FOLDER = System.getProperty("user.dir") + destDir;
		File directory = new File(SRC_FOLDER);
		if (directory.exists()) {
			try {
				delete(directory);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		new File(System.getProperty("user.dir") + destDir).mkdirs();

	}

	public void deleteCreateScreenShotDirectorySureFireReports() {
		String destDir = "screenshots";
		String SRC_FOLDER = System.getProperty("user.dir") + "/target/surefire-reports/" + destDir;
		File directory = new File(SRC_FOLDER);
		if (directory.exists()) {
			try {
				delete(directory);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		new File(System.getProperty("user.dir") + "/target/surefire-reports/" + destDir).mkdirs();

	}

	public void delete(File file) throws IOException {

		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				log.info("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					log.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			log.info("File is deleted : " + file.getAbsolutePath());
		}
	}

	public String convertToSeconds(String sActualTime) {
		String sTotalSec = null;
		String[] strSplit = sActualTime.split(":");
		if (strSplit.length == 3) {
			int iHourToSec = Integer.parseInt(strSplit[0].toString()) * 60 * 60;
			// log.info(iHourToSec);
			int iMinuteToSec = Integer.parseInt(strSplit[1].toString()) * 60;
			// log.info(iMinuteToSec);
			double iSeconds = Double.parseDouble(strSplit[2].toString());
			// log.info(iSeconds);
			double iTotalSec = iHourToSec + iMinuteToSec + iSeconds;
			// log.info(iTotalSec);
			sTotalSec = Double.toString(iTotalSec);
		}

		return sTotalSec;

	}

	

	public String readfileReturnInString(String sPathOfJson) throws IOException {

		byte[] encoded = Files.readAllBytes(Paths.get(sPathOfJson));
		return new String(encoded, StandardCharsets.UTF_8);

	}

	public String unixTimeStampInString() {
		return String.valueOf(Instant.now().getEpochSecond());
	}

	public String unixToNormalTime(String unixTime) {

		long unixTime1 = Long.parseLong(unixTime);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

		final String formattedDtm = Instant.ofEpochSecond(unixTime1).atZone(ZoneId.of("GMT+05:30")).format(formatter);
		return String.valueOf(formattedDtm);

	}

	public String generateDate(int dayCount) {
		// LocalDate today = LocalDate.now();
		log.info(LocalDate.now().plusDays(dayCount).toString());
		return LocalDate.now().plusDays(dayCount).toString();
	}

	public String getSystemIP() {

		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
			System.out.println("System IP Address : " + (localhost.getHostAddress()).trim());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return (localhost.getHostAddress()).trim();
	}


	public int convertMonthToInteger(String MonthInWords) throws Exception {
		int month;
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH);

		TemporalAccessor accessor = parser.parse(MonthInWords);
		month = accessor.get(ChronoField.MONTH_OF_YEAR);
		return month;
	}

	public Date convert_String_To_Date(String sDate) throws Exception {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MMMM/dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MMM dd, yyyy");

		Date d1 = format1.parse(sDate);
		String d2 = format2.format(d1);
		Date d3 = format2.parse(d2);

		return d3;

	}
}

	
