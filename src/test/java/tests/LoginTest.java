package tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.baseTest;
import pages.loginPage;


public class LoginTest extends baseTest {
	protected static Logger log = LogManager.getLogger(LoginTest.class);


	@Test
	public void testValidateLogin() throws Exception {
		log.info("testValidateLogin gona start");
		loginPage lp = new loginPage(driver);
		String title = driver.getTitle();
		log.info("current title of the page is : "+ title +" and URl : "+ driver.getCurrentUrl());
		lp.enterUsername("Admin");
		log.info("entered UserName into the input field");
		lp.enterPassword("admin123");
		log.info("entered Password into the input field");
		lp.clickLogin();
		String currentTitle = driver.getTitle();
		Assert.assertEquals(currentTitle, "OrangeHRM");
		log.info("SUCCESS: test completed and current page title is : "+currentTitle);
	}

}
