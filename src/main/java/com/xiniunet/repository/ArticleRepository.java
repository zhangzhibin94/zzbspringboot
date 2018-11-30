package com.xiniunet.repository;

import com.xiniunet.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 方法名称拥有特定的书写规则，不需要去考虑实现，由springData实现接口
 */
@Component
@Document(indexName = "blog",type = "article",shards = 1,replicas = 0)
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {
    /**
     * 根据title和content匹配，若两者都存在，则必须满足两个条件
     * @param title
     * @param content
     * @return
     */
    List<Article> findByTitleAndContent(String title, String content);

    /**
     * 根据title和content匹配，若两者都存在，则满足其中一个条件就可以
     * @param title
     * @param content
     * @return
     */
    List<Article> findByTitleOrContent(String title,String content);
    /**
     * 根据title和content匹配，若两者都存在，则满足其中一个条件就可以并且忽略大小写
     * @param title
     * @param content
     * @return
     */
    List<Article> findByTitleOrContentAllIgnoreCase(String title,String content);

    /**
     * 根据title升序排序
     * @param title
     * @return
     */
    List<Article> findByCreatetimeOrderByTitleAsc(String title);

    /**
     * 分页查询
     * @param content
     * @param pageable
     * @return
     */
    List<Article> findByContentOrderByCreatetimeAsc(String content, Pageable pageable);
}
