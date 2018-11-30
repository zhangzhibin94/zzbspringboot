package com.xiniunet.controller;

import com.xiniunet.domain.Article;
import com.xiniunet.repository.ArticleRepository;
import com.xiniunet.request.ArticleFindRequest;
import com.xiniunet.response.ArticleFindResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/e_search")
public class ElasticSearchController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @PostMapping("/save")
    @ResponseBody
    public Object saveES(Article article){
        article.setId(System.currentTimeMillis());
        article.setCreatetime(new Date());
        Article save = articleRepository.save(article);
        return save;
    }
    @GetMapping("/search")
    @ResponseBody
    public Object search(String field,String message){
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title",message);
        Iterable<Article> search = articleRepository.search(queryBuilder);
        return search;
    }
    @GetMapping("/search_high")
    @ResponseBody
    public Object highLight(String field,String message){
        List<Article> articles = new ArrayList<>();
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery(field, message))
                .withHighlightFields(new HighlightBuilder.Field(field)).build();
        Page<Article> pageList = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        for(Article article:pageList){
            articles.add(article);
        }
        return articles;
    }
    @GetMapping("/search_by_title_content")
    @ResponseBody
    public Object findByTitleAndContent(String title,String content){
        List<Article> articleList = articleRepository.findByTitleAndContent(title, content);
        return articleList;
    }
    @GetMapping("/search_by_title_or_content")
    @ResponseBody
    public Object findByTitleOrContent(String title,String content){
        List<Article> articleList = articleRepository.findByTitleOrContent(title, content);
        return articleList;
    }
    @PostMapping("/search_by_title_asc_time")
    @ResponseBody
    public Object findByTitleOrContentPage(ArticleFindRequest article){
        ArticleFindResponse response = new ArticleFindResponse();
        Pageable pageable = PageRequest.of(article.getCurrentPageNumber(), article.getCurrentPageSize(), Sort.Direction.ASC,"createtime");
        List<Article> articles = articleRepository.findByContentOrderByCreatetimeAsc(article.getContent(), pageable);
        response.setArticleList(articles);
        return response;
    }
}
