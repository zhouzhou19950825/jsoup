package com.upic;

import com.upic.jsoup.DefaultJsoupNewsMapping;
import com.upic.jsoup.po.News;
import com.upic.jsoup.po.Search;
import com.upic.jsoup.repository.SearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpicSpringJsoupApplicationTests {
    @Autowired
    private SearchRepository searchRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddSearch() {
        Search search = new Search();
        search.setClassNameSearchList("zt_list");
        search.setContentListClassName(Arrays.asList("zt_cenL"));
        search.setPubTime("pubTime");
        search.setSource("source");
        search.setUrl("https://www.ydylcn.com/skwx_ydyl/expertsList?SiteID=1");
        search.setWebName("一带一路数据库");
        search.setType("理论库");
        searchRepository.saveAndFlush(search);
    }
    
    public static void main(String[] args) throws IOException {
		// GuangMingNews g = new GuangMingNews();
		// List<News> listNews = g.getListNews("http://politics.gmw.cn/node_9840.htm");
		// listNews.forEach(System.out::println);
		// Search s=new Search();
		// s.setClassNameSearchList("media-list");
		// s.setContentListClassName(Arrays.asList("contentMain","ArticleContent"));
		// s.setPubTime("pubTime");
		// s.setSource("source");
		// s.se
		// s.setUrl("http://search.gmw.cn/search.do?c=n&cp=2&q=习近平&tt=false&to=true&adv=false");
		// DefaultJsoupNewsMapping search2 = DefaultJsoupNewsMapping.getSearch(s);
		// List<News> search3 = search2.search();
		// List<News> search = g.search(s);
		// search3.forEach(System.out::println);
		find1();
	}

	public static void find() {
		Search search = new Search();
		search.setClassNameSearchList("zt_list");
		search.setContentListClassName(Arrays.asList("zt_cenL"));
		search.setPubTime("pubTime");
		search.setSource("source");
		search.setUrl("https://www.yidaiyilu.gov.cn/info/iList.jsp?cat_id=10149");

		DefaultJsoupNewsMapping search2 = DefaultJsoupNewsMapping.getSearch(search);
		List<News> search3 = search2.search();
		search3.forEach(System.out::println);
	}
	public static void find1() {
        Search search = new Search();
        search.setClassNameSearchList("wtfz_list_right");
        search.setContentListClassName(Arrays.asList("content_left"));
        search.setPubTime("main_content_date");
        search.setSource("source");
        search.setUrl("https://www.yidaiyilu.gov.cn/info/iList.jsp?cat_id=10002");

        DefaultJsoupNewsMapping search2 = DefaultJsoupNewsMapping.getSearch(search);
        List<News> search3 = search2.search();
        search3.forEach(System.out::println);
    }
}
