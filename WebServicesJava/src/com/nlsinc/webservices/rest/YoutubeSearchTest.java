package com.nlsinc.webservices.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class YoutubeSearchTest {
	
	@Test(dataProvider="testData2")
	public void testService(String search, String header) throws Exception{
		String[] searchArray = search.split(" ");
		URL url = new URL("https://www.youtube.com/results?search_query="+searchArray[0]+"+"+searchArray[1]);
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
		Assert.assertEquals(actualOutput.contains(header), true);
  }

		@DataProvider
		public Object[][] testData2() {
			return new Object[][]{{"harlem shake", "YouTube Video Search"},{"workout playlist", "YouTube Video Search"}};
		}


}
