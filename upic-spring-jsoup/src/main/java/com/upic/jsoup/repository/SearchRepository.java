package com.upic.jsoup.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.upic.jsoup.po.Search;

/**
 * Created by dtz
 */
public interface SearchRepository extends JpaRepository<Search, Long>, JpaSpecificationExecutor<Search> {

}