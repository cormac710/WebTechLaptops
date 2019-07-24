package com.cormacLaptops.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cormacLaptops.test.util.UtilsDAOExt;

public class LaptopSeleniumTest {
	WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/cormacLaptops/");

		final UtilsDAOExt utilsDAO = new UtilsDAOExt();
		utilsDAO.deleteTable();
		utilsDAO.addThreeRows();
	}

	@Test
	public void testDelete() {
		final WebElement laptops = driver.findElement(By.xpath("//a[@href='#laptops']"));
		laptops.click();

		final WebElement modal = driver.findElement(By.id("aDelete"));
		modal.click();
	}

	@Test
	public void testModal() {
		final WebElement laptops = driver.findElement(By.xpath("//a[@href='#laptops']"));
		laptops.click();

		final WebElement modal = driver.findElement(By.id("aModal"));
		modal.click();
		
		try {
			// give modal a second to pop
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		final WebElement modalMake = driver.findElement(By.id("modalMake"));
		assertEquals("Dell", modalMake.getAttribute("value"));

		final WebElement modalModal = driver.findElement(By.id("modalModel"));
		assertEquals("Inspiron", modalModal.getAttribute("value"));
	}

	@Test
	public void testLaptopsDataTable() {
		final WebElement laptops = driver.findElement(By.xpath("//a[@href='#laptops']"));
		laptops.click();

		final int rowCount = driver.findElements(By.xpath("//table[@id='laptopsTbl']/tbody/tr")).size();
		final int columnCount = driver.findElements(By.xpath("//table[@id='laptopsTbl']/thead/tr/th")).size();

		assertEquals(3, rowCount);
		assertEquals(6, columnCount);
	}

	@Test
	public void testAddLaptop() {
		final WebElement tab = driver.findElement(By.xpath("//a[@href='#addNew']"));
		tab.click();

		final WebElement make = driver.findElement(By.id("make"));
		make.sendKeys("HP");
		final WebElement model = driver.findElement(By.id("model"));
		model.sendKeys("X10");
		final WebElement price = driver.findElement(By.id("price"));
		price.sendKeys("500");
		final WebElement ram = driver.findElement(By.id("ram"));
		ram.sendKeys("6");
		final WebElement hardDrive = driver.findElement(By.id("hardDrive"));
		hardDrive.sendKeys("500");
		final WebElement processor = driver.findElement(By.id("processor"));
		processor.sendKeys("i7");
		final WebElement description = driver.findElement(By.id("description"));
		description.sendKeys("description for HP");

		assertEquals("HP", make.getAttribute("value"));
		assertEquals("X10", model.getAttribute("value"));
		assertEquals("500", price.getAttribute("value"));
		assertEquals("6", ram.getAttribute("value"));
		assertEquals("i7", processor.getAttribute("value"));
		assertEquals("description for HP", description.getAttribute("value"));

		final WebElement btnAddLaptop = driver.findElement(By.id("btnAddLaptop"));
		btnAddLaptop.click();
	}

	@Test
	public void testSearch() {
		final WebElement tab = driver.findElement(By.xpath("//a[@href='#RAM']"));
		tab.click();

		final WebElement makeSearch = driver.findElement(By.id("makeSearch"));
		makeSearch.sendKeys("Lenovo");

		final WebElement btnSearchMake = driver.findElement(By.id("btnSearchMake"));
		btnSearchMake.click();

		assertEquals("Lenovo", makeSearch.getAttribute("value"));
		final int columnCount = driver.findElements(By.xpath("//table[@id='laptopList']/thead/tr/th")).size();

		assertEquals(6, columnCount);
	}

}
