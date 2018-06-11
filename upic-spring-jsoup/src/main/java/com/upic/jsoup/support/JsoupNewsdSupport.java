package com.upic.jsoup.support;

import java.util.Collections;
import java.util.List;

import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;

public interface JsoupNewsdSupport {

	/**
	 * 如果有搜索的就用此接口
	 * @return
	 */
	default List<News> searchAll(Search search){
		return Collections.emptyList();
	}
}
