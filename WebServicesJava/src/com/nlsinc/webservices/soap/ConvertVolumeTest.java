package com.nlsinc.webservices.soap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ConvertVolumeTest {


	@Test(dataProvider="testData2")
	public void testService(String quantity, String fromUnit, String toUnit, String quantity2) throws Exception{
		String soaprequest =
				"<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.webserviceX.NET/\">"+
						"\n<soap:Header/>"+
						"\n<soap:Body>"+
						"<web:ChangeVolumeUnit>"+
				        	"<web:VolumeValue>"+quantity+"</web:VolumeValue>"+
				        	"<web:fromVolumeUnit>"+fromUnit+"</web:fromVolumeUnit>"+
				        	"<web:toVolumeUnit>"+toUnit+"</web:toVolumeUnit>"+
				        "</web:ChangeVolumeUnit>"+
						"</soap:Body>"+
				"</soap:Envelope>";
	
			URL url = new URL("http://www.webservicex.net/convertVolume.asmx");
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("Content-Length", soaprequest.length()+"");
			conn.addRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"http://www.webserviceX.NET/ChangeVolumeUnit\"");

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
			Assert.assertEquals(actualOutput.contains(quantity2), true);
		
		
		
  }

		@DataProvider
		public Object[][] testData2() {
			return new Object[][]{{"5","liter","gallonUSLiquid","1.32086"},{"5","liter","cupUS","21.1337"},{"5","liter","cubicMeter","0.005"}};
		}

}
