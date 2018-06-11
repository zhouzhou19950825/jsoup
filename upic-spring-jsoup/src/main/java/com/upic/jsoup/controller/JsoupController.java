package com.upic.jsoup.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upic.jsoup.DefaultJsoupNewsMapping;
import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;
import com.upic.jsoup.repository.SearchRepository;

@RestController
public class JsoupController {
	@Autowired
	private SearchRepository searchR;
	protected static final Logger LOGGER = LoggerFactory.getLogger(JsoupController.class);

	@RequestMapping("/searchAll")
	public List<News> getNewsById(long id, Map<String, String> values) {
		try {
			Optional<Search> findById = searchR.findById(id);
			if (!findById.isPresent()) {
				return null;
			}
			Search search2 = findById.get();
			search2.setValues(values);
			DefaultJsoupNewsMapping search = DefaultJsoupNewsMapping.getSearch(search2);
			return search.search();
		} catch (Exception e) {
			LOGGER.error("getNewsById:"+e.getMessage());
		}
		return null;
	}
	
	@RequestMapping("/getNews")
	public News getNews(String url) {
		try {
			DefaultJsoupNewsMapping search = DefaultJsoupNewsMapping.getSearch();
			return search.getNewByurl(url);
		} catch (Exception e) {
			LOGGER.error("getNews:"+e.getMessage());
		}
		return null;
	}
}
