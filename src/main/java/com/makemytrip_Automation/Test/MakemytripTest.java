package com.makemytrip_Automation.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.xmlbeans.impl.regex.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.makemytrip_Automation.BaseUI.BaseUI;
import com.makemytrip_Automation.utils.ReadExcelDataFile;

public class MakemytripTest extends BaseUI {
	public static ReadExcelDataFile file;

	@Test
	public void login() throws InterruptedException {
		logger = report.createTest("LoginTest");
		invokeBrowser("Chrome");
		openURL(prop.getProperty("websiteURL"));
		file = new ReadExcelDataFile(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\loginData.xlsx");
		String username = file.getCellData("loginInfo", 0, 2);
		String password = file.getCellData("loginInfo", 1, 2);
		elementClick("signinBtn_xpath");
		elementClick("signinBtn_xpath");
		enterText("usernameTextbox_xpath", username);
		elementDoubleClick("continueBtn_xpath");
		Thread.sleep(3000);
		enterText("passwordTextbox_xpath", password);
		elementClick("loginBtn_xpath");
		elementClick("crossBarBtn_xpath");

	}

	@Test(dependsOnMethods = "login")
	public void selectCity() throws InterruptedException {
		logger = report.createTest("selectCityTest");

		elementClick("fromCityBtn_xpath");
		String from = file.getCellData("travelInfo", 0, 2);
		String to = file.getCellData("travelInfo", 1, 2);
		enterText("fromCityTextbox_xpath", from);
		Thread.sleep(1000);
		elementClick("fromCityFirstOption_xpath");
		elementDoubleClick("toCityBtn_xpath");
		enterText("toCityTextbox_xpath", to);
		Thread.sleep(3000);
		elementClick("toCityFirstOption_xpath");

	}

	@Test(dependsOnMethods = "selectCity")
	public void selectDate() throws java.text.ParseException, InterruptedException {
		logger = report.createTest("selectDateTest");

		String date = "12/12/2021";
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date expectedDate = dateFormat.parse(date);
			String day = new SimpleDateFormat("dd").format(expectedDate);
			String month = new SimpleDateFormat("MMMM").format(expectedDate);
			String year = new SimpleDateFormat("yyyy").format(expectedDate);
			String expectedMonthYear = month + " " + year;

			try {
				elementDoubleClick("departureBtn_xpath");
			} catch (InterruptedException e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
			while (true) {
				String displayDate = getDisplayDate();
				if (expectedMonthYear.equals(displayDate)) {
					selectDay(13);
					mouseClick("searchBtn_xpath");
					Thread.sleep(10000);
					break;
				} else if (expectedDate.compareTo(currentDate) > 0) {
					elementClick("forwardArrowBtn_xpath");
				} else {
					elementClick("backwardArrowBtn_xpath");
				}

			}

		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(dependsOnMethods = "selectDate")
	public void flightDetails()// throws InterruptedException
	{
		try {
			String[] str = new String[10];
			elementClick("sortBtn_xpath");
			Thread.sleep(2000);
			for (int i = 1; i < 6; i++) {
				str[i - 1] = driver.findElement(By.xpath("(//div[@class='priceSection']/div/div/p)["+i+"]")).getText();// .click();
			}
			for (int i = 0; i < 5; i++) {
				System.out.println(str[i]);
			}

		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterTest
	public void reportEnd() {
		report.flush();
	}
}
