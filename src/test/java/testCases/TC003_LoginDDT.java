package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTestClass;
import utilities.DataProviders;

/*data TC:valid  - 	login success - test pass  - logout
					login failed - test fail

data TC:invalid - 	login success - test fail  - logout
					login failed - test pass
*/

public class TC003_LoginDDT extends BaseTestClass
{
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")
	public void verify_loginDDT(String email, String pwd, String res)
	{
		logger.info("***Executing TC003_LoginDDT***");
		
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage ap = new MyAccountPage(driver);
		boolean targetPage=ap.isMyAccountPageExist();
	
		/*data TC:valid  - 	login success - test pass  - logout
		login failed - test fail

		data TC:invalid - 	login success - test fail  - logout
				login failed - test pass
		*/
		
		
		if(res.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				ap.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(res.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				ap.clickLogout();
				Assert.assertTrue(false);	
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}		
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Finishing TC003_LoginDDT***");
		
	}
	
	

}
