package com.automation.poweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;
import com.automation.utilities.TestBase;
import com.automation.utilities.constants;
import com.jayway.jsonpath.JsonPath;

public class po_signup extends TestBase{
	Logger log = Logger.getLogger(getClass().getSimpleName());
	WebDriver driver;
	public String sJsonTD_SignUp;
	public po_signup(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		sJsonTD_SignUp = oCommUtil.readfileReturnInString(
				System.getProperty("user.dir") + "/Resources/TestData/td_signUp.json");
			}
	
	
	@FindBy(id = "signup_form-firstname")
	WebElement ph_SignUpFirstName;
	@FindBy(id = "signup_form-lastname")
	WebElement ph_SignUpLastName;
	@FindBy(id = "signup_form-email")
	WebElement ph_SignUpEmail;
	@FindBy(id = "signup_form-password")
	WebElement ph_SignUpPassword;
	@FindBy(xpath = "//button[contains(text(),'Join')]")
	WebElement button_SignUpJoin;
	@FindBy(xpath = "//button[contains(text(),'Accept')]")
	WebElement button_Accept;
	/*@FindBy(id="recaptcha-anchor")
	WebElement cb_captchImNotRobot;
	*/
	@FindBy(xpath="//div[@class='signUpThankU_Block']/h3")
	WebElement header_thankyou;
	public void signUpNewUser() throws Exception {
		try {
		oSelUti.waitForElementVisible( driver, ph_SignUpFirstName, 10);}catch(Exception a) {
			if(oSelUti.ufIsDisplayed(button_Accept))
				button_Accept.click();
			
		}
		oSelUti.ufSendKeys(ph_SignUpFirstName, "Srinidhi"+constants.unixTimeStampCurrentRun);
		oSelUti.ufSendKeys(ph_SignUpLastName, "B"+constants.unixTimeStampCurrentRun);
		oSelUti.ufSendKeys(ph_SignUpEmail, "srinidhi.test+"+constants.unixTimeStampCurrentRun+"@gmail.com");
		log.info(JsonPath.read(sJsonTD_SignUp, "$.signUp.password").toString());
		oSelUti.ufClick(ph_SignUpPassword);
		oSelUti.ufSendKeys(ph_SignUpPassword, JsonPath.read(sJsonTD_SignUp, "$.signUp.password").toString());
		/*WebElement cb_captchImNotRobot = driver.findElement(By.id("recaptcha-anchor"));
		oSelUti.ClickUsingJS(cb_captchImNotRobot);*/
		oSelUti.ufClick(button_SignUpJoin);
		oSelUti.waitForElementVisible( driver, header_thankyou, 5);
		oSelUti.ufGetText(header_thankyou);
	}
	//niranjan , abhi
	@FindBy(id="errusername")
	WebElement text_err_requireFirstAndLastName;
	@FindBy(id="erremail")
	WebElement text_err_requireValidEmail;
	@FindBy(id="errpassword")
	WebElement text_err_password;
	public void errorValidationOnSignUpPage() throws Exception{
		oSelUti.waitForElementVisible( driver, ph_SignUpFirstName, 10);
		oSelUti.ufClick(button_SignUpJoin);
		oSelUti.waitForElementVisible( driver, text_err_requireFirstAndLastName , 3);
		oValUtil.validationText(oSelUti.ufGetText(text_err_requireFirstAndLastName), "A valid first name, last name is required");
		oValUtil.validationText(oSelUti.ufGetText(text_err_requireValidEmail), "A valid email ID is required");
		oValUtil.validationText(oSelUti.ufGetText(text_err_password), "Password must have 8 character or longer, at least 1 uppercase letter, 1 number and 1 symbol (like !@#$%^&*)");
	}
	
	@FindBy(id="loginButton")
	WebElement button_SignUplogin;
	@FindBy(xpath="//span[contains(text(),'Forgot Password?')]")
	WebElement hl_loginforgotPassword;
	@FindBy(id="loginform-username")
	WebElement ph_loginEmail;
	@FindBy(id="loginform-password")
	WebElement ph_loginPassword;
	@FindBy(xpath="(//button[contains(text(),'Login')])[2]")
	WebElement button_login;
	@FindBy(xpath="//div[@class='kt-header__topbar-user']")
	WebElement header_User;
	public void loginToAccount() throws Exception {
		oSelUti.waitForElementVisible( driver, button_SignUplogin, 10);
		button_SignUplogin.click();
		oSelUti.waitForElementVisible( driver, hl_loginforgotPassword, 10);
		oSelUti.ufSendKeys(ph_loginEmail, JsonPath.read(sJsonTD_SignUp, "$.Login.email").toString());
		oSelUti.ufSendKeys(ph_loginPassword, JsonPath.read(sJsonTD_SignUp, "$.Login.password").toString());
		oSelUti.ufClick(button_login);
		oSelUti.waitForElementVisible( driver, header_User, 10);
	}
}
