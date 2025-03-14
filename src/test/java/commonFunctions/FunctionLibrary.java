package commonFunctions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static WebDriver stratBrowser() throws Throwable {
		conpro=new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
		}
		else {
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	
	public static void openUrl() {
		driver.get(conpro.getProperty("Url"));
	}
	
	public static void waitForElement(String LocatorType, String LocatorValue, String TestData) {
		WebDriverWait mywait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	public static void typeAction(String LocatorType, String LocatorValue, String TestData) {
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name")){
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}	
	}
	public static void clickAction(String LocatorType, String LocatorValue) {
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).click();
		}
	}
	public static void validateTitle(String Expected_Title) {
		String Actual_Title=driver.getTitle();
		try {
		Assert.assertEquals(Actual_Title, Expected_Title, "Title is not matching");
		}catch (Exception e) {
			Reporter.log(e.getMessage(),true);
		}
	}
	public static void closeBrowser() {
		driver.close();
	}
}
	

