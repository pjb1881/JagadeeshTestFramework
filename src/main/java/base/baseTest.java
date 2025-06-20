package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class baseTest {

	protected static WebDriver driver;
	protected static Logger log = LogManager.getLogger(baseTest.class);

	@BeforeMethod
	public static void driverSetUp() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		log.info("driver window maximized");
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		log.info("navigated to url : " + driver.getCurrentUrl());
	}

	public static void openUrl() {
		String Url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		driver.get(Url);
		log.info("navigated to url : " + driver.getCurrentUrl());
	}
	
	public static void login(String UserName, String Password) {
		WebElement userName = driver.findElement(By.xpath("//input[@name=\"username\"]"));
		WebElement Pwd = driver.findElement(By.xpath("//input[@name=\"password\"]"));
		WebElement submitButton = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
		userName.sendKeys(UserName);
		Pwd.sendKeys(Password);
		submitButton.click();	
	}

	@AfterMethod
	public void tearDown() {
	    if (driver != null) {
	        driver.quit();
	        System.out.println("Driver closed Successfully.");
	        log.info("SUCCESS: Driver was null, nothing to quit.");
	    } else {
	        System.out.println("Driver was null, nothing to quit.");
	        log.error("ERROR: Driver was null, nothing to quit.");
	    }
	}
	

}
