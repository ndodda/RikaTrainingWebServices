package com.nlsinc.webservices.rest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class GoogleSearchTest {
	
	@Test(dataProvider="testData2")
	public void testService(String dataSearch, String dataResultCount) throws Exception{
		URL url = new URL("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="+dataSearch);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		// Read soap response
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			//System.out.println(line);
			sb.append(line);
		}
		reader.close();
		String actualOutput = sb.toString();
		System.out.println("\n*** REST RESPONSE ***\n"+actualOutput);
		// Check the result
		System.out.println("\n*** ASSERTIONS ***\n");
		Assert.assertNotNull(actualOutput);
		Assert.assertEquals(actualOutput.contains(dataResultCount), true);
  }

		@DataProvider
		public Object[][] testData2() {
			return new Object[][]{{"scarf", "8770000"},{"car", "313000000"}};
		}

}
