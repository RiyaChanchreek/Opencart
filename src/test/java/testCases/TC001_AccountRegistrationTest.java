package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTestClass;

public class TC001_AccountRegistrationTest extends BaseTestClass{
	
	
	@Test(groups={"Sanity", "Master"})
	public void verify_account_reg()
	{
		logger.info("***Executing TC001_AccountRegistrationTest***");
		
		try
		{
		HomePage hp = new HomePage(driver);
		logger.info("***Clicked My Account***");
		hp.clickMyaccount();
		logger.info("***Clicked Register***");
		hp.clickRegister();
		
		AccountRegistrationPage arp = new AccountRegistrationPage(driver);
		logger.info("***Enter User Details***");
		arp.setFirstName(randomString().toUpperCase());
		arp.setLastName(randomString().toUpperCase());
		arp.setEmail(randomString()+"@test.com");
		arp.setTelephone(randomNumber());
		
		String pass = randomAlphaNumeric();
		
		arp.setPassword(pass);
		arp.setconfirmPassword(pass);
		
		arp.setPrivacyPolicy();
		arp.clickContinue();
		
		logger.info("***Verify Confirmation***");
		String cnfmMsg=arp.getConfirmationMsg();
		if(cnfmMsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("***Test failed***");
			logger.debug("***Debug logs***");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(cnfmMsg, "Your Account Has Been Created!***");
		}
		catch(Exception e)
		{
			
			Assert.fail();
		}
	
		logger.info("***Finished TC001_AccountRegistrationTest***");
	}


}
