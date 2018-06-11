package com.upic.jsoup.po;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;
@Data
@Entity
public class Search {
	@Id
    @GeneratedValue
	private Long id;
	private String url;
	private String keyWord;//不管
	@ElementCollection
	private List<String> contentListClassName; //内容样式名称
	private String pubTime;//发布时间
	private String classNameSearchList; //样式名
	private String source;  //来源
	@Transient
	private Map<String,String> values; //参数装配
	
	private String type; //类型
	
	private String webName;//哪个网站的
}