package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import base.baseTest;
import utils.locators;
import utils.reUsables;

public class loginPage {

	private WebDriver driver;
	private locators loc;
	private reUsables reUse;
	private Logger log;

	public loginPage(WebDriver driver) {
		this.driver = driver;
		this.loc = new locators(driver);
		this.reUse = new reUsables(driver);
		this.log = LogManager.getLogger(loginPage.class);
	}

	public void enterUsername(String username) throws Exception {
		try {
			log.info(" username xpath : " + loc.userName);
			reUse.waitForSomeTime(loc.userName, 4);
			driver.findElement(loc.userName).clear();
			driver.findElement(loc.userName).sendKeys(username);
			log.info(" user-name entered into user-names field");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR@enterUsername: " + e);
		}
	}

	public void enterPassword(String password) throws Exception {
		try {
			log.info(" Password xpath : " + loc.pwd);
			reUse.waitForSomeTime(loc.pwd, 4);
			driver.findElement(loc.pwd).clear();
			driver.findElement(loc.pwd).sendKeys(password);
			log.info(" Password entered into password field");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("ERROR@enterPassword: " + e);
		}
	}

	public void clickLogin() {
		try {
			log.info(" Login button xpath : " + loc.subButton);
			reUse.waitForSomeTime(loc.subButton, 4);
			driver.findElement(loc.subButton).click();
			log.info(" Login button submitted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("ERROR@clickLogin: " + e);
		}
	}
}
