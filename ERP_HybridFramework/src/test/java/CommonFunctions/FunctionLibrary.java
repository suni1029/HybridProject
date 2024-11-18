package CommonFunctions;

import static org.testng.Assert.assertThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
//method launching browser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load property file
	conpro.load(new FileInputStream("./PropertyFile\\Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser value is Not Matching",true);
	}
	return driver;
	
}
//method for launching url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}
//method for wait for any element
public static void waitForElement(String Ltype,String Lvalue,String Mywait)
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Mywait)));
	if(Ltype.equalsIgnoreCase("xpath"))
	{
		//wait until element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Lvalue)));
	}
	if(Ltype.equalsIgnoreCase("name"))
	{
		//wait until element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Lvalue)));
	}
	if(Ltype.equalsIgnoreCase("id"))
	{
		//wait until element is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Lvalue)));
	}
}
//method for textbox
public static void typeAction(String Ltype,String Lvalue,String TestData)
{
	if(Ltype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Lvalue)).clear();
		driver.findElement(By.xpath(Lvalue)).sendKeys(TestData);
	}
	if(Ltype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Lvalue)).clear();
		driver.findElement(By.name(Lvalue)).sendKeys(TestData);
	}
	if(Ltype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Lvalue)).clear();
		driver.findElement(By.id(Lvalue)).sendKeys(TestData);
	}
}
//method for clickaction
public static void clickAction(String Ltype,String Lvalue)
{
	if(Ltype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Lvalue)).click();
	}
	if(Ltype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Lvalue)).click();
	}
	if(Ltype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Lvalue)).sendKeys(Keys.ENTER);
	}
}
public static void validateTitle(String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
		Assert.assertEquals(Actual_Title, Expected_Title, "Title is Not Matching");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
public static void closeBrowser()
{
	driver.quit();
}
//method for listboxes
public static void dropDownAction(String LType,String LValue,String TestData)
{
	if(LType.equalsIgnoreCase("xpath"))
	{
		//convert testadat into int type
		int value =Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.xpath(LValue)));
		element.selectByIndex(value);
		
	}
	if(LType.equalsIgnoreCase("id"))
	{
		//convert testadat into int type
		int value =Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(LValue)));
		element.selectByIndex(value);
		
	}
	if(LType.equalsIgnoreCase("name"))
	{
		//convert testadat into int type
		int value =Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.name(LValue)));
		element.selectByIndex(value);
		
	}
}
//method for capture stock number
public static void capturestock(String LType,String LValue) throws Throwable
{
	String stockNum="";
	if(LType.equalsIgnoreCase("xpath"))
	{
		stockNum = driver.findElement(By.xpath(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("id"))
	{
		stockNum = driver.findElement(By.id(LValue)).getAttribute("value");
	}
	if(LType.equalsIgnoreCase("name"))
	{
		stockNum = driver.findElement(By.name(LValue)).getAttribute("value");
	}
	//create notepad into capturedata folder and write
	FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(stockNum);
	bw.flush();
	bw.close();
}
public static void stockTable()throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data =br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath("search-textbox")).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(5000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Exp_Data+"     "+Act_Data,true);
	try {
		Assert.assertEquals(Act_Data, Exp_Data, "Stock Number Not Found In Table");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
//method for capturing supplier number into notepad	
public static void capturesup(String Ltype,String LValue)throws Throwable	
{
	String supplierNum="";
	if(Ltype.equalsIgnoreCase("xpath"))
	{
		supplierNum=driver.findElement(By.xpath(LValue)).getAttribute("value");
	}
	if(Ltype.equalsIgnoreCase("name"))
	{
		supplierNum=driver.findElement(By.name(LValue)).getAttribute("value");
	}
	if(Ltype.equalsIgnoreCase("id"))
	{
		supplierNum=driver.findElement(By.id(LValue)).getAttribute("value");
	}

    //create note pad and write suppliernum
    FileWriter fw = new FileWriter("./CaptureData/supplier.txt");
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(supplierNum);
    bw.flush();
    bw.close();	
}
//method validate supplier number in table
public static void supplierTable()throws Throwable
{
	//read supplier number from note pad
	FileReader fr = new FileReader("./CaptureData/supplier.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	//click search panel if search textbox not displayed
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
	//click searchpanel button
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data+"      "+Act_Data);
	try {
		Assert.assertEquals(Act_Data,Exp_Data,"Supplier Number Not Matching");
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
}
//method capturing Customer number into notepad
public static void captureCust(String LocatorType,String LocatorValue)throws Throwable
{
	String customerNum="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		customerNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		customerNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		customerNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}

  //create note pad and write customerNum
  FileWriter fw = new FileWriter("./CaptureData/customer.txt");
  BufferedWriter bw = new BufferedWriter(fw);
  bw.write(customerNum);
  bw.flush();
  bw.close();
  }
//method validate customer number in table
public static void CustomerTable() throws IOException, InterruptedException
{
	//read customer number from note pad
	FileReader fr = new FileReader("./CaptureData/customer.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	//click search panel if search textbox not displayed
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
	//click searchpanel button
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	Reporter.log(Exp_Data+"      "+Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data,Act_Data,"Customer Number Not Matching");
	} catch (Exception e) {
		Reporter.log(e.getMessage());
	}
}

//method for generate date formate
public static String generateData()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
	return df.format(date);
	
}

}












