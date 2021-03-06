package com.baublebar.testcases;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.baublebar.pages.BaublebarPage;
import com.baublebar.util.TestUtil;
import com.saucelabs.saucerest.SauceREST;


public class ShoppingCartTest extends TestBase {
	
	@Test(dataProvider = "getShoppingCartData")
	public void ShoppingCartTest(Hashtable<String, String> data) throws Throwable {
		APPLICATION_LOGS.debug("Executing the Shopping Cart	Test");
		if (!TestUtil.isExecutable("ShoppingCartTest", xls)|| data.get("Runmode").equals("N")) throw new SkipException("Skipping the test");
		topMenuBar = getTopMenuBar();		
		topMenuBar.clickVisitUs();
		APPLICATION_LOGS.debug("Shopping Cart Test Completed");
		APPLICATION_LOGS.debug("************************************************");
	}
	
	@DataProvider
	public Object[][] getShoppingCartData() {
		return TestUtil.getData("ShoppingCartTest", xls);
	}
	
	@AfterMethod(enabled = ifSauce)
	public void updateSauceTestName(ITestResult result) throws Exception {
		String jobID = ((RemoteWebDriver) driver).getSessionId().toString();
		SauceREST client = new SauceREST(username, key);
		Map<String, Object> saucejob = new HashMap<String, Object>();
		saucejob.put("name", result.getMethod().getMethodName());
		Object a = saucejob.get("name");
		System.out.println(a);
		if (result.isSuccess()) {
			client.jobPassed(jobID);
		}
		else {
			client.jobFailed(jobID);
		}
		client.updateJobInfo(jobID, saucejob);
	}

}