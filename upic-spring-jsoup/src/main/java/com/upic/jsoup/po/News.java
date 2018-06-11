package com.upic.jsoup.po;

import java.util.List;

public class News {

	private String newsUrl;
	
	private String title;
	
	private String pubTime;
	
	private String text;
	
	private String source;  //来源
	private List<String> picUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(List<String> picUrl) {
		this.picUrl = picUrl;
	}

	public News(String newsUrl,String title, String pubTime, String text,String source, List<String> picUrl) {
		super();
		this.newsUrl=newsUrl;
		this.source=source;
		this.title = title;
		this.pubTime = pubTime;
		this.text = text;
		this.picUrl = picUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	@Override
	public String toString() {
		return "News [newsUrl=" + newsUrl + ", title=" + title + ", pubTime=" + pubTime + ", text=" + text + ", source="
				+ source +"]";
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	
}