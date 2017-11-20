package com.cc.quote.service.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestClass {
 public static void main(String[] args) {
	 //https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
	 String date = "2018-01-31T08:30:00.000Z";
	 SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	 SimpleDateFormat format2 = new SimpleDateFormat("hh:mm aa");
	 Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(date);
	 String formatted = format1.format(calendar.getTime());
	 String formatted2 = format2.format(calendar.getTime());
	 System.out.println(" Date : "+formatted);
	 System.out.println(" Date : "+formatted2);
	// System.out.println(" Date : "+calendar.get);
 }
}
