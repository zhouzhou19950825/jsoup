package com.upic.jsoup.https;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author dongtengzhou
 */
public class JsoupTest {

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

	public static void test() {
//		String url = "https://www.yidaiyilu.gov.cn/info/iList.jsp?cat_id=10149";
//		String content = JsoupHelper.get(url, null, "utf-8", headers);
//		System.out.println("返回的json字符串：" + content);
//		Document parse = Jsoup.parse(content);
		try {
			System.setProperty("javax.net.ssl.trustStore", "/Users/dongtengzhou/j2eeworkspace/javabaseworkspace/jsoup/upic-spring-jsoup/certfile/www.yidaiyilu.gov.cn");
			Document document = Jsoup.connect("https://www.yidaiyilu.gov.cn/info/iList.jsp?cat_id=10149").get();
			System.out.println(document.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		Authenticator.setDefault(new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("", "".toCharArray());
//            }
//        });
		test();
	}
}