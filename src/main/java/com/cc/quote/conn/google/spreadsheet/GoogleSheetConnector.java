package com.cc.quote.conn.google.spreadsheet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.cc.quote.conn.google.spreadsheet.conf.GoogleSheetConnectorConf;
import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.util.ServiceException;

public class GoogleSheetConnector extends GoogleSheetConnectorConf{

	public GoogleSheetConnector(String key) throws MalformedURLException {
		super(key);
	}
	
	public List<QuoteModel> getQuoteList(String sheetName) throws IOException, ServiceException {
		List<QuoteModel> quoteList = null;
		SpreadsheetService service = new SpreadsheetService(sheetName);
        // Get Feed of Spreadsheet url
        ListFeed lf = service.getFeed(url, ListFeed.class);
        //Iterate over feed to get cell value
        quoteList = new ArrayList<QuoteModel>();
        for (ListEntry le : lf.getEntries()) {
            CustomElementCollection cec = le.getCustomElements();
            QuoteModel quoteModel = new QuoteModel();
            //Pass column name to access it's cell values
            String quote = cec.getValue("quote");
            quoteModel.setQuote(quote);
            //System.out.println(quote);
            String auther = cec.getValue("auther");
            quoteModel.setAuther(auther);
           // System.out.println(auther);
            quoteList.add(quoteModel);
        }
		return quoteList;
	}

}
