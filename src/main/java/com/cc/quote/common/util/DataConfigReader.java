package com.cc.quote.common.util;

import java.util.ArrayList;
import java.util.List;

import com.cc.quote.common.propery.ProperyReader;

public class DataConfigReader {
	// static variable single_instance of type Singleton
    private static DataConfigReader configDataReader = null;
	private List<String> replyListString;
	private DataConfigReader() {
		
	}

    // static method to create instance of Singleton class
    public static DataConfigReader getInstance() {
        if (configDataReader == null) {
        	configDataReader = new DataConfigReader();
        	configDataReader.initData();
        }
        return configDataReader;
    }
    
    private void initData() {
    	ProperyReader properyReader = new ProperyReader();
    	this.replyListString = new ArrayList<String>();
    	this.processReply(properyReader.getValue("reply.replyTextList"));

    }
    
    private void processReply(String replyList) {
    	for (String replyText : replyList.split(",")) {
    		this.replyListString.add(replyText);
    	}
    }
	
    public List<String> getReplyList() {
    	return this.replyListString;
    }
    
    
    public static void main(String[] args){
    	System.out.println(DataConfigReader.getInstance().getReplyList());
    }
    
}
