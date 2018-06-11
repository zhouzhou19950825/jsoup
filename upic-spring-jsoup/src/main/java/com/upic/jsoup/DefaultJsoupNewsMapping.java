package com.upic.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;

/**
 * 光明网是需要爬取页面解析list 时政：http://politics.gmw.cn/node_9840.htm
 * 教育：http://edu.gmw.cn/node_10810.htm
 * 搜索：http://search.gmw.cn/search.do?c=n&cp=1&q=[content]&tt=false&to=true&adv=false
 * adv false新闻全文 true新闻标题
 * 
 * @author dtz
 *
 */
//@Component
public class DefaultJsoupNewsMapping extends AbstractJsoupNewsMapping {

//	private final static String CLASS_NAME="channel-newsGroup";
	 private DefaultJsoupNewsMapping(Search search) {
		 super(search);
	}
	public static DefaultJsoupNewsMapping getSearch(Search search) {
		return new DefaultJsoupNewsMapping(search);
	} 
	public static DefaultJsoupNewsMapping getSearch() {
		return new DefaultJsoupNewsMapping(null);
	} 
	
	@Override
	protected Element getNewtext(Document doc) {
		Element elementById =null;
		for(String className:search.getContentListClassName()) {
			if(doc.getElementById(className)==null) {
				continue;
			}
			elementById = doc.getElementById(className);
		}
		// 获取新闻内容
//		Element elementById = doc.getElementById("contentMain") == null ? doc.getElementById("ArticleContent")
//				: doc.getElementById("contentMain");
		return elementById;
	}

	@Override
	protected String getPubTime(Document doc) {
		// 获取新闻发布的时间
		Element elementsByClass = doc.getElementById(search.getPubTime()); //pubTime
		if(elementsByClass==null) {
			return null;
		}
		String text = elementsByClass.text();
		return text;
	}

	@Override
	protected String getSource(Document doc) {
		Element elementsByClass = doc.getElementById(search.getSource());//source
		if(elementsByClass==null) {
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

	private List<News> getListNewsMethod(Document document,String className){
		String url = document.baseUri() + "/";
		if (document.baseUri().contains("htm")) {
			int lastIndexOf = document.baseUri().lastIndexOf("/");
			url = document.baseUri().substring(0, lastIndexOf) + "/";
		}
		document.setBaseUri(url);

		Elements elementsByClass = document.getElementsByClass(className);
		List<News> listNews = new ArrayList<News>();
		elementsByClass.forEach(x -> {
			Elements select = x.select("a");
			select.forEach(el -> {
				String select2 = el.attr("href");
				try {
					String urls = select2.startsWith("http") ? select2 : document.baseUri() + select2;

					listNews.add(selectAllNews(urls));
				} catch (IOException e) {
					// e.printStackTrace();
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
//		return getListNewsMethod(doc, "media-list");
		return getListNewsMethod(doc, search.getClassNameSearchList());
	}

	/**
	 * 拼接url
	 * @param baseUrl
	 * @param values
	 * @return
	 */
	private String splicingUrl(String baseUrl,Map<String,String> values) {
		StringBuffer sb=new StringBuffer();
		if(values==null) {
			return baseUrl; 
		}
		sb.append(baseUrl).append("?");
		values.forEach((key,value)->{
			sb.append(key).append("=").append(value).append("&");
		});
		return sb.toString();
	}
	public static void main(String[] args) throws IOException {
//		GuangMingNews g = new GuangMingNews();
//		List<News> listNews = g.getListNews("http://politics.gmw.cn/node_9840.htm");
//		listNews.forEach(System.out::println);
//		Search s=new Search();
//		s.setClassNameSearchList("media-list");
//		s.setContentListClassName(Arrays.asList("contentMain","ArticleContent"));
//		s.setPubTime("pubTime");
//		s.setSource("source");
//		s.se
//		s.setUrl("http://search.gmw.cn/search.do?c=n&cp=2&q=习近平&tt=false&to=true&adv=false");
//		DefaultJsoupNewsMapping search2 = DefaultJsoupNewsMapping.getSearch(s);
//		List<News> search3 = search2.search();
//		List<News> search = g.search(s);
//		search3.forEach(System.out::println);
		find();
	}
	
	public static void find() {
		
		Search s=new Search();
		s.setClassNameSearchList("texie");
		s.setContentListClassName(Arrays.asList("articlemain"));
		s.setPubTime("timespan");
		s.setSource("source");
//		s.se
		s.setUrl("http://www.fortunechina.com/business/");
		DefaultJsoupNewsMapping search2 = DefaultJsoupNewsMapping.getSearch(s);
		List<News> search3 = search2.search();
//		List<News> search = g.search(s);
		search3.forEach(System.out::println);
		
	}
}