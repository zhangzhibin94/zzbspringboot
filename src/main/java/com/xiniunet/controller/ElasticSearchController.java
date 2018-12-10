package com.xiniunet.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/e_search")
public class ElasticSearchController {
    /*@Autowired
    private ArticleRepository articleRepository;


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
    }*/
}
