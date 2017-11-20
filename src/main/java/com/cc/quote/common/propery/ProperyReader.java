package com.cc.quote.common.propery;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ProperyReader {
	public String getValue(String propertyValue) {
		String proprtyVal = null;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			System.out.println("Read conf : "+propertyValue);
    		String filename = "config.properties";
    		input = ProperyReader.class.getClassLoader().getResourceAsStream(filename);

			// load a properties file
			prop.load(input);

			proprtyVal = prop.getProperty(propertyValue);
			// get the property value and print it out
			//System.out.println(prop.getProperty("database"));
			//System.out.println(prop.getProperty("dbuser"));
			//System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return proprtyVal;

	  }
	
	public static void main(String[] args) {
		ProperyReader properyReader = new ProperyReader();
		System.out.println("-------------------> "+properyReader.getValue("dbuser"));
	}
}
