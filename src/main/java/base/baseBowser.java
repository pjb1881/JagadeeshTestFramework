package base;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.loginPage;
import utils.locators;

public class baseBowser {

	public locators locators;
	public loginPage loginpage;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static Logger log;

	public static WebDriver driver;

	@BeforeSuite
	@Parameters({ "browserType" })
	public void launchBrowser(String browserType) {

		if (browserType.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);
			log.info("Started Chrome driver : " + browserType);

		} else if (browserType.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			driver = new EdgeDriver(options);
			log.info("Started Edge driver : " + browserType);
			
		}

		driver.manage().window().maximize(); // Always start maximized
	}

	@BeforeClass
	public void init() {
		loginpage = new loginPage(driver);
		locators = new locators(driver);
	}

	@AfterSuite
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
			log.info("SUCCESS: Browser closed Successfully");
		}
	}

	@AfterTest
	public void sleep() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
