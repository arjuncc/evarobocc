package com.cc.quote.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cc.quote.conn.google.spreadsheet.model.QuoteModel;
import com.cc.quote.service.Track;
import com.cc.quote.service.rest.helper.QuoteServiceHelper;

@Path("/json")
public class QuoteServiceController {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {

		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");

		return track;

	}
	
	@GET
	@Path("/quote/_get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuotes() {
		return getQuoteFromList();
	}
	
	@POST
	@Path("/quote/post/_get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuotesPost() {
		return getQuoteFromList();
	}
	
	private String getQuoteFromList() {
		String quote = "{\"quote\":\"hello world\",\"auther\":\"arjuncc\"}";
		try {
			QuoteServiceHelper quoteServiceHelper = new QuoteServiceHelper();
			QuoteModel quoteModel = quoteServiceHelper.getRandomQuote("QuoteSheet");
			quote = quoteModel.getQuoteWithCharLimit(140);
		} catch (Exception exp) {
			
		}
		return quote;
	}
	

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
		
	}
	
}