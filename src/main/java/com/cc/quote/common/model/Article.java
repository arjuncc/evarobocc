package com.cc.quote.common.model;

public class Article {
	private String auther;
	private String heading;
	private String link;
	private String image;
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Article [auther=" + auther + ", heading=" + heading + ", link=" + link + ", image=" + image + "]";
	}
	
}
