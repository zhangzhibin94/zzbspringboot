package com.xiniunet.response;

import com.xiniunet.domain.Article;

import java.util.List;

public class ArticleFindResponse {
    private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
