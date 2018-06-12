package com.upic.jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;
import com.upic.jsoup.sslsupport.SSLSupportInitCert;

/**
 * 光明网是需要爬取页面解析list 时政：http://politics.gmw.cn/node_9840.htm
 * 教育：http://edu.gmw.cn/node_10810.htm
 * 搜索：http://search.gmw.cn/search.do?c=n&cp=1&q=[content]&tt=false&to=true&adv=false
 * adv false新闻全文 true新闻标题
 *
 * @author dtz
 */
// @Component
public class DefaultJsoupNewsMapping extends AbstractJsoupNewsMapping {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultJsoupNewsMapping.class);
	private DefaultJsoupNewsMapping(Search search) {
		super(search);
	}

	public static DefaultJsoupNewsMapping getSearch(Search search) {
		return new DefaultJsoupNewsMapping(search);
	}

	@Override
	protected Element getNewtext(Document doc) {
		Element elementById = null;
		for (String className : search.getContentListClassName()) {
			if (doc.getElementById(className) != null) {
				elementById = doc.getElementById(className);
				continue;
			}
			if (doc.getElementsByClass(className) != null) {
				elementById = doc.getElementsByClass(className).first();
				continue;
			}
		}
		return elementById;
	}

	@Override
	protected String getPubTime(Document doc) {
		// 获取新闻发布的时间
		Element elementsByClass = doc.getElementById(search.getPubTime()); // pubTime
		if (elementsByClass == null) {
			return null;
		}
		String text = elementsByClass.text();
		return text;
	}

	@Override
	protected String getSource(Document doc) {
		Element elementsByClass = doc.getElementById(search.getSource());// source
		if (elementsByClass == null) {
			System.out.println(doc.title());
			return null;
		}
		String text = elementsByClass.text();
		return text;
	}

	@Override
	protected boolean isWebGet() {
		return true;
	}

	/**
	 * 需要网上爬取
	 */
	@Override
	protected List<News> getListNews0(Document document) {
		return getListNewsMethod(document, search.getClassNameSearchList());
	}

	private List<News> getListNewsMethod(Document document, String className) {
//		String url = document.baseUri() + "/";
		String url="http://"+getBaseUrl(document.baseUri());
		if (document.baseUri().contains("htm")) {
			int lastIndexOf = document.baseUri().lastIndexOf("/");
			url = document.baseUri().substring(0, lastIndexOf) + "/";
		}
		document.setBaseUri(url);

		Elements elementsByClass = document.getElementsByClass(className);
		List<News> listNews = new ArrayList<News>();
		elementsByClass.parallelStream().forEach(x -> {
			Elements select = x.select("a");
			select.parallelStream().forEach(el -> {
				String select2 = el.attr("href");
				try {
					String urls = select2.startsWith("http") ? select2 : document.baseUri() + select2;

					listNews.add(selectAllNews(urls));
				} catch (IOException e) {
				}
			});
		});
		return listNews;
	}

	/**
	 * 搜索新闻
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private News selectAllNews(String url) throws IOException {
		Document doc = getDocByUrl(url);
		// 由于内容庞大 先不抓取
		return new News(url, doc.title(), getPubTime(doc), null, getSource(doc), getImgurl(doc));
	}

	/**
	 * 关键字搜索
	 */
	@Override
	public List<News> searchAll(Search search) {
		if (search == null) {
			return super.searchAll(search);
		}
		Document doc = getDocByUrl(splicingUrl(search.getUrl(), search.getValues()));
		if (doc == null) {
			return super.searchAll(search);
		}
		return getListNewsMethod(doc, search.getClassNameSearchList());
	}

	/**
	 * 拼接url
	 *
	 * @param baseUrl
	 * @param values
	 * @return
	 */
	private String splicingUrl(String baseUrl, Map<String, String> values) {
		StringBuffer sb = new StringBuffer();
		if (values == null || values.isEmpty()) {
			return baseUrl;
		}
		sb.append(baseUrl).append("?");
		values.forEach((key, value) -> {
			sb.append(key).append("=").append(value).append("&");
		});
		return sb.toString();
	}
	@Override
	protected String getBaseUrl(String url) {
		URL urls=null;
		try {
			urls = new  URL(url);
		} catch (MalformedURLException e) {
			LOGGER.error("getBaseUrl:"+e.getMessage());
		}
		String host = urls.getHost();// 获取主机名 
		return host;
	}
	
	
}