package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseTestClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkRep;
	public ExtentReports rep;
	public ExtentTest test;
	String repName;
	
	
	public void onStart(ITestContext testContext) 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report"+ timeStamp + ".html";
		
		sparkRep = new ExtentSparkReporter(".\\reports\\" + repName);
		
		sparkRep.config().setDocumentTitle("OpenCart Testing");
		sparkRep.config().setReportName("OpenCart Functional Testing");
		sparkRep.config().setTheme(Theme.DARK);
		
		rep = new ExtentReports();
		
		rep.attachReporter(sparkRep);
		rep.setSystemInfo("Automation", "Open Cart");
		rep.setSystemInfo("Module", "Admin");
		rep.setSystemInfo("Sub Module", "Customers");
		rep.setSystemInfo("User Name: ", System.getProperty("user.name"));
		rep.setSystemInfo("Env", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		rep.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		rep.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			rep.setSystemInfo("Included Groups", includedGroups.toString());
		}
		
		
	}
	
	public void onTestSuccess(ITestResult result) 
	{
		test = rep.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+" got successfully executed");
	}
	
	public void onTestFailure(ITestResult result) 
	{
		test = rep.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+" got failed!");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			String imgPath = new BaseTestClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void onTestSkipped(ITestResult result) 
	{
		test = rep.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) 
	{
		rep.flush();
		
		String pathOfExtRep = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extRep = new File(pathOfExtRep);
		
		try {
			Desktop.getDesktop().browse(extRep.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
