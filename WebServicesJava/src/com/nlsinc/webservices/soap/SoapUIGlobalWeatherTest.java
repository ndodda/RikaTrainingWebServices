package com.nlsinc.webservices.soap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.eviware.soapui.SoapUIProTestCaseRunner;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestRunner.Status;

public class SoapUIGlobalWeatherTest {
	
	String projectDir = null;
	  @BeforeClass
	  public void beforeClass() {
		  projectDir = System.getProperty("project.dir", "C:\\Users\\ndodda\\Desktop\\Training\\SoapUI\\Soap\\GlobalWeather");
	  }

	@Test(dataProvider = "testData")
	public void testRunner(String projectFile) throws Exception{
		SoapUIProTestCaseRunner runner = new SoapUIProTestCaseRunner();
		  runner.setProjectFile(projectDir+File.separator+projectFile);
		  assertTrue(runner.run());
	}
	
	@Test(dataProvider="testData", enabled = false)
	  public void testRunner2(String projectFile) throws Exception {
		  WsdlProject project = new WsdlProject(projectDir+File.separator+projectFile); 
		  List<TestSuite> suites = project.getTestSuiteList();
		  for (TestSuite suite : suites) {
			  System.out.println("Running TestSuite: "+suite.getName());
			  List<TestCase> cases = suite.getTestCaseList();
			  for (TestCase tcase : cases) {
				  System.out.println("Running TestCase: " + tcase.getName());
				  TestRunner runner = tcase.run(new PropertiesMap(), false);
				  assertEquals(Status.FINISHED, runner.getStatus() ); 	
			  }
		  }  
	  }
	  
	
	@DataProvider
	public Object[][] testData() {
		return new Object[][]{{"SOAPGlobalWeather-soapui-project.xml"}};
	}

}
