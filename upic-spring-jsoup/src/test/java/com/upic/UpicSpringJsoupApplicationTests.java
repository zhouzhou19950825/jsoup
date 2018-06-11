package com.upic;

import com.upic.jsoup.po.Search;
import com.upic.jsoup.repository.SearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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
}
