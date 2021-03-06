package com.baublebar.testcases;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.baublebar.pages.BaublebarPage;
import com.saucelabs.saucerest.SauceREST;

public class LoadBaublebarTest extends TestBase{

	@BeforeSuite(enabled = ifLocal)	
	//@BeforeClass
	public void init() throws Exception{
		initConfigurations();
		initDriver();
	}

	@BeforeSuite(enabled = ifSauce)
	@Parameters({"browser","platform","version"})	
	//@BeforeClass
	public void setupDesireCapabilities(String browser,  Platform platform, String version) throws Exception {
	//public void setupDesireCapabilities() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setBrowserName(browser);
		caps.setPlatform(platform);
		caps.setVersion(version);
		/*DesiredCapabilities caps = DesiredCapabilities.iphone();
		caps.setCapability("platform", "OS X 10.10");
		caps.setCapability("version", "8.1");
		caps.setCapability("deviceName","iPhone Simulator");
		caps.setCapability("device-orientation", "portrait");*/
		initRemoteDriver(caps);
		initConfigurations();
	}
	
	@Test
	public void loadBaublebar() throws Exception{
		APPLICATION_LOGS.debug("Executing the LoadBaublebarTest");
		BaublebarPage landingPage = PageFactory.initElements(driver, BaublebarPage.class);
		landingPage.loadBaublebar();
		APPLICATION_LOGS.debug("Load Baublebar test completed");
		APPLICATION_LOGS.debug("************************************************");
	}
	
	@AfterMethod(enabled = ifSauce)
	public void updateSauceTestName(ITestResult result) throws Exception {  
		String jobID = ((RemoteWebDriver)driver).getSessionId().toString();
		SauceREST client = new SauceREST(username,key);
		Map<String, Object>saucejob = new HashMap<String,Object>();
		saucejob.put("name",result.getMethod().getMethodName());	
		Object a = saucejob.get("name");
		if (result.isSuccess()){
			client.jobPassed(jobID);
		} else{
			client.jobFailed(jobID);
		}
		client.updateJobInfo(jobID, saucejob);
	}
}