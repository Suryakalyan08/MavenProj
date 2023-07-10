package commonFunctions;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static String Expecteddata ="";
	public static String ActualData ="";
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();

		}
		return driver;
	}
	//method for launch url
	public static void openUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String wait)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(wait));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));

		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	//method for textboxes
	public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for buttons,checkbox,links,images,radiobutton
	public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	//method for valiodate title
	public static void validateTitle(WebDriver driver,String ExpectedTitle)
	{
		String ActualTitle = driver.getTitle();
		try {
			Assert.assertEquals(ActualTitle, ExpectedTitle,"Title is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	//method for listboxes
	public static void selectDropDown(WebDriver driver,String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(TestData);
			WebElement element = driver.findElement(By.xpath(LocatorValue));
			Select select = new Select(element);
			select.selectByIndex(value);
			
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(TestData);
			WebElement element = driver.findElement(By.id(LocatorValue));
			Select select = new Select(element);
			select.selectByIndex(value);
			
		}
	}
	//method for capturestockitem number
	public static void capturestockitem(WebDriver driver,String LocatorType,String Locatorvalue)
	{
		Expecteddata =driver.findElement(By.name(Locatorvalue)).getAttribute("value");
	}
	
	//method for stock table
	public static void stockTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//if search textbox is displayed dont click search panel button or else click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		ActualData = driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
		System.out.println(Expecteddata+"       "+ActualData);
		Assert.assertEquals(Expecteddata, ActualData, "Stock Number is Not Matching");
	}
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Items')])[2]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
	}
	//method for category table
	public static void categoryTable(WebDriver driver,String Expecteddata) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed dont click search panel button or else click
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
			Thread.sleep(3000);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
			String ActualData =driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
			System.out.println(Expecteddata+"     "+ActualData);
			Assert.assertEquals(Expecteddata, ActualData,"Stock category Name not Matching");
	}
	//method capture supplier number
	public static void capureData(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		Expecteddata= driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	//method for supplier table
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed dont click search panel button or else click
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
			Thread.sleep(3000);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(3000);
		ActualData = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expecteddata+"     "+ActualData);
		Assert.assertEquals(Expecteddata, ActualData,"Supplier Number Not Matching");
		
	}
	//method for date geneation
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
				
	}
}

