package com.baublebar.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.baublebar.util.Constants;
import com.baublebar.testcases.TestBase;
import com.thoughtworks.selenium.Wait;

public class BaublebarPage {
	
	public WebDriver driver;
	
	@FindBy(xpath=Constants.discountLink)
	public WebElement discountLink;
	

	@FindBy(xpath=Constants.discountEmail)
	public WebElement discountEmail;
	
	@FindBy(xpath=Constants.discountSubmit)
	public WebElement discountSubmit;	
	
	@FindBy(name=Constants.email)
	public WebElement email;
	
	@FindBy(css=Constants.getStarted)
	public WebElement getStarted;
	
	@FindBy(css=Constants.loginLink)
	public WebElement loginLink;
	
	@FindBy(css=Constants.haveAnAcc)
	public WebElement haveAnAcc;
	
	@FindBy(xpath=Constants.accountEmail)
	public WebElement accountEmail;
	
	@FindBy(xpath=Constants.accountPassword)
	public WebElement accountPassword;
	
	@FindBy(xpath=Constants.confirmPassword)
	public WebElement confirmPassword;
	
	@FindBy(css=Constants.submit)
	public WebElement submit;
		
	@FindBy(xpath=Constants.addToWish)
	public WebElement addToWish;
	
	@FindBy(css=Constants.loginEmail)
	public WebElement loginEmail;
	
	@FindBy(css=Constants.loginPassword)
	public WebElement loginPassword;
	
	@FindBy(css=Constants.loginButton)
	public WebElement loginButton;
	
	@FindBy(xpath=Constants.myAccount)
	public WebElement myAccount;
	
	@FindBy(xpath=Constants.myWishList)
	public WebElement myWishList;
	
	@FindBy(css=Constants.myItem)
	public WebElement myItem;
	
	WebDriverWait wait;
	
	public BaublebarPage(WebDriver dr){
		driver = dr;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//getTopNavBar();
		//getMainNavBar();
	}
	
	public void loadBaublebar(){
		driver.manage().deleteAllCookies();
		String applicationURL = (TestBase.CONFIG.getProperty("applicationURL"));
		try {
			driver.get(applicationURL);
			Thread.sleep(2000);
			Cookie ck = new Cookie("firstVisit", "1","staging.baublebar.com", "/", null,true);
			driver.manage().addCookie(ck);
			driver.get(applicationURL);
			Assert.assertEquals("The Final Say in Fashion Jewelry | BaubleBar", driver.getTitle());
		} catch (Exception e ){
			e.printStackTrace();
		}
		
		//driver.get("https://www.baublebar.com");
		//driver.get(CONFIG.getProperty("applicationURL"));
	}
	
	public void signupForDiscount(String discounEmail) throws Throwable{
		//driver.manage().deleteAllCookies();
	//	driver.get(CONFIG.getProperty("applicationURL"));
		discountLink.click();
		wait.until(ExpectedConditions.elementToBeClickable(discountEmail));
		
		
		//this.switchToFrameByIndex(6);
		//email.clear();
		discountEmail.clear();
		discountEmail.sendKeys(discounEmail);
		//email.sendKeys();
		discountSubmit.click();
		//getStarted.click();
		//System.out.println("Code is " + driver.findElement(By.id("bouncex_el_18")).getText());
		//System.out.println("Code is " + driver.findElement(By.xpath("//*[@id=\"bouncex_el_18\"]/div/div")).getText());
		
	}
	
	public void createAccount(String accEmail, String accPassword) throws Throwable{
		driver.get(TestBase.CONFIG.getProperty("applicationURL"));
		loginLink.click();
		haveAnAcc.click();
		accountEmail.clear();
		accountEmail.sendKeys(accEmail);
		accountPassword.clear();
		accountPassword.sendKeys(accPassword);
		confirmPassword.clear();
		confirmPassword.sendKeys(accPassword);
		submit.click();
	}
	
	
	public void verifyAnItemToWishList(String accEmail, String accPassword) throws InterruptedException{
		driver.get("http://www.baublebar.com/wishbone-bracelet.html");
		addToWish.click();
		String mainWindow =driver.getWindowHandle();
		Set windows=driver.getWindowHandles();
		//this method will you handle of all opened windows
		Iterator iter = windows.iterator();
		while(iter.hasNext()){
			String popupHandle = iter.next().toString();
		    if(!popupHandle.contains(mainWindow)){
		    	driver.switchTo().window(popupHandle);
		    }
			loginEmail.clear();
	    	loginEmail.sendKeys(accEmail);
	    	
	    	loginPassword.clear();
		    loginPassword.sendKeys(accPassword);
		    
		    loginButton.click();
			myAccount.click();
			
			myWishList.click();
			System.out.println("test completed");
		
			String actual = myItem.getText();
			String expected = "WISHBONE BRACELET";
			Assert.assertEquals(expected, actual);	
		}
	}
	


	public void switchToFrameByIndex(int index){
       WebElement frame;
       List<WebElement> frameset=driver.findElements(By.tagName("iframe"));
       if (frameset.size() > index) {
           frame=frameset.get(index);
           driver.switchTo().frame(frame);
       }else{
           System.out.println("Number of Frames present are:"+frameset.size());
           System.out.println("Index is greater than the number of frames present.");
       }

   }

}
