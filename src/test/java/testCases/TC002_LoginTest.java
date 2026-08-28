package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTestClass;

public class TC002_LoginTest extends BaseTestClass {
	
	@Test(groups={"Regression", "Master"})
	public void verify_login()
	{
		logger.info("***Executing TC002_LoginTest***");
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(prop.getProperty("email"));
		lp.setPassword(prop.getProperty("password"));
		lp.clickLogin();
		
		MyAccountPage ap = new MyAccountPage(driver);
		boolean targetPage=ap.isMyAccountPageExist();
		Assert.assertEquals(targetPage, true);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("*** Finishing TC002_LoginTest***");
		
	}
	

}
