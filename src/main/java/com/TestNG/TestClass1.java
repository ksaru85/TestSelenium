package com.TestNG;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.Utility.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.input.WindowsLineEndingInputStream;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestClass1 extends library {

	@Test(priority = 0)
	public void ValidateGMO_OnLineLoadedSuccessfully() {
		System.out.println("inside ValidateGMOONlineLoadedSuccessfully");
		String AcutualTitle = driver.getTitle();
		System.out.println("AcutualTitle: " + AcutualTitle);
		String ExpectedTitle = "Welcome to Green Mountain Outpost";
		Assert.assertEquals(AcutualTitle, ExpectedTitle);

	}

	@Test(priority = 1)
	public void ValidateEnterGMO_OnLineSuccessfully() {
		driver.findElement(By.xpath("//input[@name='bSubmit']")).click();
		String AcutualTitle = driver.findElement(By.xpath("(//*[contains(text(),'OnLine Catalog')])[2]")).getText();
		System.out.println("AcutualTitle: " + AcutualTitle);
		String ExpectedTitle = "OnLine Catalog";
		Assert.assertEquals(AcutualTitle, ExpectedTitle);
	}

	@Test(priority = 2)
	public void ValidateOrderSunGlasses() {
		driver.findElement(By.name("QTY_GLASSES")).sendKeys("3");
		driver.findElement(By.name("bSubmit")).click();
		String ActualTitle = driver.getTitle();
		System.out.println(ActualTitle);
		String ExpectedTitle = "Place Order";
		Assert.assertEquals(ActualTitle, ExpectedTitle);
		String unitPrice = driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]")).getText();
		System.out.println("unitPrice: " + unitPrice);
		String price = unitPrice.substring(2);
		System.out.println("price: " + price);
		Float PriceInIntegerFormat = Float.parseFloat(price);
		Float TotalPriceCalculated = PriceInIntegerFormat * 3;
		System.out.println("TotalPriceCalculated: " + TotalPriceCalculated);
		String ActualOrderQtyPrice = driver.findElement(By.xpath("//table/tbody/tr[2]/td[5]")).getText();
		System.out.println("ActualOrderQtyPrice: " + ActualOrderQtyPrice);
		String totalprice = ActualOrderQtyPrice.substring(2);
		System.out.println("totalprice: " + totalprice);
		Float ActuaPriceInFloatFormat = Float.parseFloat(totalprice);
		Assert.assertEquals(ActuaPriceInFloatFormat, TotalPriceCalculated);
	}

	@Test(priority = 3)
	public void ValidatingAlerts() {
		driver.navigate().to("https://demoqa.com/alerts");
		library.waitForPageToLoad();
		driver.findElement(By.id("alertButton")).click();
		Alert obj = driver.switchTo().alert();
		obj.accept();
		driver.findElement(By.id("confirmButton")).click();
		Alert obj1 = driver.switchTo().alert();
		String ActualStr = obj1.getText();
		System.out.println("Alert Text: " + ActualStr);
		String Expected = "Do confirm action?";
		// Assert.assertEquals(ActualStr, Expected);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(ActualStr, Expected);
		obj1.dismiss();
		driver.findElement(By.id("promtButton")).click();
		Alert obj3 = driver.switchTo().alert();
		obj3.sendKeys("raghu");
		obj3.accept();
		String ActualText = driver.findElement(By.xpath("//span[@id='promptResult']")).getText();
		String ExpectedText = "You entered raghu";
		Assert.assertEquals(ActualText, ExpectedText);
		softAssert.assertAll();
	}

	@Test(priority = 4)
	public void HandlingFrames() {
		System.out.println("inside HandlingFrames");
		driver.navigate().to("http://demo.automationtesting.in/Frames.html");
		waitForPageToLoad();
		driver.switchTo().frame("singleframe");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("hello");
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[contains(text(),'Iframe with in an Iframe')]")).click();

		WebElement ParentFrameElement = driver.findElement(By.xpath("//iframe[@src='MultipleFrames.html']"));
		driver.switchTo().frame(ParentFrameElement);

		WebElement ChildFrameElement = driver.findElement(By.xpath("//iframe[@src='SingleFrame.html']"));
		driver.switchTo().frame(ChildFrameElement);
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("inside second frame");
		driver.switchTo().defaultContent();

	}

	@Test(priority = 4)
	public void HandlingWindows() {
		System.out.println("HandlingWindows");
		driver.navigate().to(propObj.getProperty("WindowsURL"));
		waitForPageToLoad();
		Set<String> allWindows = driver.getWindowHandles();
		int CountOfWindows = allWindows.size();
		System.out.println(CountOfWindows);
		for (String window : allWindows) {
			driver.switchTo().window(window);
			String Title = driver.getTitle();
			System.out.println("Title: " + Title);
			if (Title.equals("Cognizant")) {
				driver.manage().window().maximize();
				driver.close();// this will close the current browser
			} else if (Title.equals("Tech Mahindra")) {
				driver.manage().window().maximize();
				driver.close();
			} else if (Title.equals("Jobs - Recruitment - Job Search - Employment -Job Vacancies - Naukri.com")) {
				driver.close();
			}
		}
		// driver.quit();//will close all windows (all instances of webdriver)
	}

	@Test(priority = 5)
	public void HandlingWebTable() {
		System.out.println("inside HandlingWebTable");
		driver.navigate().to(propObj.getProperty("WebTableURL"));
		waitForPageToLoad();
		String LastNames = propObj.getProperty("webtableLastNames");
		List<WebElement> AllItmes = driver.findElements(By.xpath("//table[@id='example']/tbody/tr/td[3]"));
		int Count = AllItmes.size();
		// System.out.println(LastNames);
		String AllLastNames[] = LastNames.split(",");
		for (String Name : AllLastNames) {
			for (int i = 1; i <= Count; i++) {
				String LastName = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[3]"))
						.getText();
				System.out.println(LastName);
				if (LastName.equals(Name)) {
					String Salary = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[7]"))
							.getText();
					System.out.println(Salary);
					break;
				}
			}
		}
	}

	@Test(priority = 6)
	public void HandlingMouseActionsRightClick() {
		System.out.println("inside HandlingMouseActions");
		driver.navigate().to(propObj.getProperty("mouseOpeartionRightClick"));
		waitForPageToLoad();
		Actions obj = new Actions(driver);
		WebElement target = driver.findElement(By.xpath("//span[contains(text(),'right click me')]"));
		obj.contextClick(target).build().perform();
		String action = driver
				.findElement(By.xpath("//li[@class='context-menu-item context-menu-icon context-menu-icon-copy']/span"))
				.getText();
		System.out.println("action: " + action);
		driver.findElement(By.xpath("//li[@class='context-menu-item context-menu-icon context-menu-icon-copy']/span"))
				.click();
		Alert alertobj = driver.switchTo().alert();
		String AlertText = alertobj.getText().substring(9);
		System.out.println("AlertText: " + AlertText);
		// String text=AlertText.substring(9);
		// AlertText.contains(action.toLowerCase())
		Assert.assertEquals(AlertText, action.toLowerCase());
		alertobj.accept();
	}

	@Test(priority = 7)
	public void HandlingMouseOpeartionDoubleClick() {
		System.out.println("inside HandlingMouseOpeartionDoubleClick");
		driver.navigate().to(propObj.getProperty("mouseOpeartionDoubleClick"));
		waitForPageToLoad();
		// js.executeScript("window.scrollBy(0,500)");//scroll 500 pixels
		// vertically downwards
		// js.executeScript("window.scrollBy(0,-500)");//scroll 500 pixels
		// vertically upwards
		// js.executeScript("window.scrollBy(1000,0)");//scroll 1000 pixels
		// horizontally rightwards
		// js.executeScript("window.scrollBy(-300,0)");//scroll 300 pixels
		// horizontally leftwards
		WebElement frameElement = driver.findElement(By.xpath("//iframe"));
		library.ScrollIntoView(driver, frameElement);
		driver.switchTo().frame(frameElement);
		WebElement target = driver
				.findElement(By.xpath("//*[contains(text(),'Double click the block')]/preceding-sibling::div"));
		library.DoubleClick(driver, target);
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("inside beforeMethod");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("inside afterMethod");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("inside beforeClass");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("inside afterClass");
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("inside beforeTest");
		library.luanchBrowser();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("inside afterTest");
	}

	@BeforeSuite
	public void beforeSuite() throws Exception {
		System.out.println("inside beforeSuite");
		library.readPropertyFIle();
	}
}