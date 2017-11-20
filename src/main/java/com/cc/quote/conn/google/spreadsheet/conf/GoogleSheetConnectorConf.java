package com.cc.quote.conn.google.spreadsheet.conf;
import java.net.MalformedURLException;
import java.net.URL;

import com.cc.quote.common.propery.ProperyReader;


public class GoogleSheetConnectorConf {
	private static String SHEET_URL = null;
	protected URL url = null;
	public GoogleSheetConnectorConf(String keyId) throws MalformedURLException{
		ProperyReader properyReader = new ProperyReader();
		System.out.println("--------->");
		GoogleSheetConnectorConf.SHEET_URL = properyReader.getValue("googlesheet.url.key."+keyId);
        // Use this String as url
        url = new URL(GoogleSheetConnectorConf.SHEET_URL);
	}
		
}
