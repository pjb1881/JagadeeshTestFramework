package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class reUsables {
	
	private WebDriver driver;

	public reUsables(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForSomeTime(By element,int Seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Seconds));
		WebElement elementVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

}
