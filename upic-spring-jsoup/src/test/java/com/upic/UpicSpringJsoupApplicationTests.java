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
    public void addSearch() {
        Search search = new Search();
        search.setClassNameSearchList("texie");
        search.setContentListClassName(Arrays.asList("articlemain"));
        search.setPubTime("timespan");
        search.setSource("source");
        search.setUrl("http://www.fortunechina.com/business/");
        search.setWebName("财富中文网");

        searchRepository.saveAndFlush(search);
    }
}
