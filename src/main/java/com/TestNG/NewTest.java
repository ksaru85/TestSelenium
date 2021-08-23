package com.TestNG;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class NewTest {

	private static final String TimeUint = null;
	WebDriver driver = null;

	@Test(priority = 0)
	public void ValidateGMOONlineLoadedSuccessfully() {
		System.out.println("inside ValidateGMOONlineLoadedSuccessfully");
		String ActualTitle = driver.getTitle();
		System.out.println("ActualTitle: " + ActualTitle);

		String ExpectedTitle = "Welcome to Green Mountain Outpost";
		Assert.assertEquals(ActualTitle, ExpectedTitle);

		// driver.findElement(By.xpath("//input[@name='bSubmit']")).click();
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
		String AcutualTitle = driver.getTitle();
		System.out.println(AcutualTitle);

		String ExpectedTitle = "Place Order";
		Assert.assertEquals(AcutualTitle, ExpectedTitle);

		String unitPrice = driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]")).getText();
		System.out.println("unitPrice: " + unitPrice);

		String price = unitPrice.substring(2);
		System.out.println("price: " + price);
		float PriceIntegerFormat = Float.parseFloat(price);
		float TotalPriceCalculated = PriceIntegerFormat * 3;
		System.out.println("TotalPriceCalculated: " + TotalPriceCalculated);

		String ActualOrderQtyPrice = driver.findElement(By.xpath("//table/tbody/tr[2]/td[5]")).getText();
		System.out.println("ActualOrderQtyPrice: " + ActualOrderQtyPrice);

		String totalprice = ActualOrderQtyPrice.substring(2);
		System.out.println("totalprice: " + totalprice);
		float ActualPriceFloatFormat = Float.parseFloat(totalprice);
		Assert.assertEquals(ActualPriceFloatFormat, TotalPriceCalculated);

		/*
		 * String
		 * salesTax=driver.findElement(By.xpath("//td[contains(text(),'$ 10.20')]")).
		 * getText(); System.out.println("salesTax: "+salesTax); float
		 * PlusTaxFloatFormat=Float.parseFloat(salesTax);
		 * Assert.assertEquals(PlusTaxFloatFormat, ActualPriceFloatFormat);
		 */

		String SalesTax = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText();
		System.out.println("SaleTax: " + SalesTax);

		String ShippingandHandling = driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]")).getText();
		System.out.println("Shipping & Handling: " + ShippingandHandling);

		String tt_price = SalesTax.substring(2);
		System.out.println("tt_price " + tt_price);
		float priceIn_Integer_Format = Float.parseFloat(tt_price);

		String tts_price = ShippingandHandling.substring(2);
		System.out.println("tts_price " + tts_price);

		float priceIn_Int_Format = Float.parseFloat(tts_price);

		float Total_value = priceIn_Integer_Format + priceIn_Int_Format;

		System.out.println("Total_value: " + Total_value);

		float GrandTotal = Total_value + TotalPriceCalculated;

		System.out.println("GrandTotal " + GrandTotal);
		String ActualGrand_total = driver.findElement(By.xpath("//table/tbody/tr[6]/td[2]")).getText();
		System.out.println("Grand_total " + ActualGrand_total);

		String total_price_Grand_total = ActualGrand_total.substring(2);
		System.out.println("total_price  :" + total_price_Grand_total);
		float priceinfloatFormat = Float.parseFloat(total_price_Grand_total);

		Assert.assertEquals(GrandTotal, priceinfloatFormat);

	}

	@Test(priority = 3)
	public void ValidateAlerts() {

		driver.navigate().to("https://demoqa.com/alerts");
		driver.findElement(By.id("alertButton")).click();
		Alert obj = driver.switchTo().alert();
		obj.accept();

		driver.findElement(By.id("confirmButton")).click();
		Alert obj1 = driver.switchTo().alert();
		String Actualstr = obj1.getText();
		System.out.println("Alert Text:" + Actualstr);
		String Expected = "Do you confirm action?";
		// Assert.assertEquals(Actualstr, Expected); //hard assert
		SoftAssert softAssert = new SoftAssert();  //soft assert
		softAssert.assertEquals(Actualstr, Expected);
		obj1.dismiss();

		driver.findElement(By.id("promtButton")).click();
		Alert obj2 = driver.switchTo().alert();
		obj2.sendKeys("Arum");
		obj2.accept();

		String ActualText = driver.findElement(By.xpath("//span[@id='promptResult']")).getText();
		String ExpectedText = "You entered arum";
		Assert.assertEquals(ActualText, ExpectedText);
		softAssert.assertAll();

	}
	
	@Test(priority=4)
	public void HandlingFrames() {
		System.out.println("inside HandlingFrames");
		driver.navigate().to("http://demo.automationtesting.in/Frames.html");
		driver.switchTo().frame("singleframe");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("hello");
		
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[contains(text(),'Iframe with in an Iframe')]")).click();
		
		WebElement ParentFrameElement=driver.findElement(By.xpath("//iframe[@src='MultipleFrames.html']"));
		driver.switchTo().frame(ParentFrameElement);
		
		WebElement ChildFrameElement=driver.findElement(By.xpath("//iframe[@src='SingleFrame.html']"));
		driver.switchTo().frame(ChildFrameElement);
		
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("inside second frame");
		driver.switchTo().defaultContent();
		
		
		
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
	}

	@AfterTest
	public void afterTest() {
		System.out.println("inside afterTest");
	}

	@BeforeSuite
	public void beforeSuite() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://demo.borland.com/gmopost/");
		driver.manage().window().maximize();

		// implicit wait - applicable for all webelements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		System.out.println("inside beforeSuite");

	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("inside afterSuite");
	}

}
