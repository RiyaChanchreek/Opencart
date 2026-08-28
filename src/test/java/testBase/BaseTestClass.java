package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTestClass {
	

	public static WebDriver driver;
	public Logger logger;
	public Properties prop;
	
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException, URISyntaxException
	
	{
		
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop=new Properties();
		prop.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		
		
		
		//If execution env is remote 
		
			if(prop.getProperty("execution_env").equalsIgnoreCase("remote"))
			{
				DesiredCapabilities cap = new DesiredCapabilities();
				//OS
				switch(os.toLowerCase())
				{
				case "windows": cap.setPlatform(Platform.WIN11); break;
				case "mac": cap.setPlatform(Platform.MAC); break;
				case "linux": cap.setPlatform(Platform.LINUX); break;
				default: System.out.println("Wrong OS information"); return;
				}
				//Browser
				switch(br.toLowerCase())
				{
				case "chrome": cap.setBrowserName("chrome"); break;
				case "edge": cap.setBrowserName("MicrosoftEdge"); break;
				case "firefox": cap.setBrowserName("firefox"); break;
				
				default: System.out.println("Invalid browser name"); return;
				}
				
				driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), cap);
				
			}
		
		//If execution env is local
			if(prop.getProperty("execution_env").equalsIgnoreCase("local"))
			{
				switch(br.toLowerCase()) 
				{
				case "chrome":driver=new ChromeDriver(); break;
				case "edge":driver=new EdgeDriver(); break;
				case "firefox":driver=new FirefoxDriver(); break;
				default: System.out.println("Invalid browser name"); return;
				}
			}
		
		
		//driver= new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void teardown()
	{
		driver.quit();
	}
	
	
	
	public String randomString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String generatedNumber=RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedSt=RandomStringUtils.randomAlphabetic(5);
		String generatedNum=RandomStringUtils.randomNumeric(3);
		return generatedSt+generatedNum+"@";
	}

	public String captureScreen(String tname) 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenshot= (TakesScreenshot)driver;
		File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+ tname+"_"+timeStamp+".png";
		
		File targetFile = new File(targetFilePath);
		
		srcFile.renameTo(targetFile);
		
		return targetFilePath;
	}
}
