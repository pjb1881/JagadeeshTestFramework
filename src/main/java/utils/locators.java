package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class locators {
	private WebDriver driver;

	public locators(WebDriver driver) {
		this.driver = driver;
	}
	String usN = "//div[@class=\"oxd-input-group oxd-input-field-bottom-space\"]/div/input[@name=\"username\" and @placeholder=\"Username\"]";

	public By userName = By.xpath(usN);
	public By pwd = By.xpath("//input[@name=\"password\"]");
	public By subButton = By.xpath("//button[@type=\"submit\"]");

}
