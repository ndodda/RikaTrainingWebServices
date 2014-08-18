package com.nlsinc.webservices.soap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StockQuoteTest {
	
	@Test(dataProvider="testData2")
	public void testService(String dataSymbol, String dataPrice) throws Exception{
		String soaprequest =
				"<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.webserviceX.NET/\">"+
						"\n<soap:Header/>"+
						"\n<soap:Body>"+
							"\n<web:GetQuote>"+
								"<web:symbol>"+dataSymbol+"</web:symbol>"+
							"</web:GetQuote>"+
						"</soap:Body>"+
				"</soap:Envelope>";
		URL url = new URL("http://www.webservicex.net/stockquote.asmx");
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("Content-Length", soaprequest.length()+"");
		conn.addRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"http://www.webserviceX.NET/GetQuote\"");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// Send soap request
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(soaprequest);
		writer.flush();
		// Read soap response
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			//System.out.println(line);
			sb.append(line);
		}
		writer.close();
		reader.close();
		String actualOutput = sb.toString();
		System.out.println("\n*** SOAP RESPONSE ***\n"+actualOutput);
		// Check the result
		System.out.println("\n*** ASSERTIONS ***\n");
		Assert.assertNotNull(actualOutput);
		Assert.assertEquals(actualOutput.contains(dataPrice), true);
  }

		@DataProvider
		public Object[][] testData2() {
			return new Object[][]{{"PEP", "91.79"},{"KO", "39.57"}};
		}
				  
}
