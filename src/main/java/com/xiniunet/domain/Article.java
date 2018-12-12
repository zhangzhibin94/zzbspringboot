package com.xiniunet.domain;


import java.util.Date;

//@Document表示一条记录，对应索引名称为blog，索引类型为article
//@Document(indexName = "blog",type = "article")
public class Article extends BaseDomain {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
