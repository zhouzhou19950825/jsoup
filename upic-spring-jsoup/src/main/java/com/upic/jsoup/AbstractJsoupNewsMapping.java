package com.upic.jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.tomcat.util.net.SSLSupport;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upic.jsoup.controller.JsoupController;
import com.upic.jsoup.https.JsoupHelper;
import com.upic.jsoup.https.SslTest;
import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;
import com.upic.jsoup.sslsupport.SSLSupportInitCert;
import com.upic.jsoup.support.JsoupNewsdSupport;

/**
 * 
 * @author dtz
 *
 */
@SuppressWarnings("all")
public abstract class AbstractJsoupNewsMapping implements JsoupNewsdSupport {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsoupNewsMapping.class);
	private static final String DEFAULT_CHARSET = "utf-8";

	private LinkedList ImgUrls = new LinkedList();// 用于存放图片URL

	protected Search search;

	protected AbstractJsoupNewsMapping(Search search) {
		this.search = search;
	}

	public static Map<String, String> headers = null;

	static {
		headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("Connection", "Keep-Alive");
		headers.put("Content-Type", "application/json;charset=UTF-8");
	}

	protected Document getDocByUrl(String url) {
		try {
//			System.setProperty("javax.net.ssl.trustStore", "/Users/dongtengzhou/j2eeworkspace/javabaseworkspace/jsoup/upic-spring-jsoup/certfile/www.yidaiyilu.gov.cn");
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			// 需要ssl访问
			if (e instanceof SSLException) {
				if (e instanceof SSLHandshakeException) {
					// getBaseUrl
					String baseUrl = getBaseUrl(url);
					try {
						// 获取生成ssl文件证书
//						String generateSupportFile = SSLSupportInitCert.generateSupportFile(baseUrl);
//						System.setProperty("javax.net.ssl.trustStore", "/Users/dongtengzhou/j2eeworkspace/javabaseworkspace/jsoup/upic-spring-jsoup/certfile/www.yidaiyilu.gov.cn");
//						Document document = Jsoup.connect("https://www.yidaiyilu.gov.cn/info/iList.jsp?cat_id=10149").get();
//						System.out.println(document.html());
						String request = SslTest.getRequest(url, 3000);
						return Jsoup.parse(request, url);
//						return Jsoup.connect(url).get();
					} catch (Exception e1) {
						LOGGER.error("e1:"+e1.getMessage());
						return null;
					}
				}
			}
			return null;
		}
	}

	public News getNewByurl(String url) {
		Document doc = getDocByUrl(url);
		if (doc == null) {
			return null;
		}
		return getNew(doc);
	}

	private News getNew(Document doc) {
		return new News(doc.baseUri(), doc.title(), getPubTime(doc), getText(getNewtext(doc)), getSource(doc),
				getImgurl(doc));
	}

	private String getText(Element e) {
		// StringBuffer sb=new StringBuffer();
		// Elements elementsByTag = e.getElementsByTag("p");
		// elementsByTag.forEach(x->{
		// sb.append(x.text()).append("\r\rn");
		// });
		return e.html();
	}

	/**
	 * 正文
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract Element getNewtext(Document doc);

	/**
	 * 时间
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract String getPubTime(Document doc);

	/**
	 * 图片
	 * 
	 * @param doc
	 * @return
	 */
	protected List<String> getImgurl(Document doc) {
		// 获取图片地址，并保存在ImgUrls链表中
		int count = 0;
		Elements pngs = doc.select("img[src]");
		for (Element img : pngs) {
			count++;
			String imgUrl = img.attr("abs:src");// 获取图片的绝对路径
			// imgUrl = img.absUrl("src");
			ImgUrls.add(imgUrl);
			// System.out.println(imgUrl);
			// downloadImg(imgUrl, count);
		}
		return ImgUrls;
	}

	/**
	 * 获得来源
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract String getSource(Document doc);

	/**
	 * 是否为页面获取
	 */
	protected abstract boolean isWebGet();

	/**
	 * 如果需要根据页面爬取List<News>
	 * 
	 * @throws IOException
	 */
	public List<News> getListNews(String url) throws IOException {
		if (!isWebGet()) {
			return null;
		}
		Document document = Jsoup.connect(url).get();
		return getListNews0(document);
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<News> search() {
		return searchAll(search);
	}

	protected abstract List<News> getListNews0(Document document);

	protected abstract String getBaseUrl(String url);
}