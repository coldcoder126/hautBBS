package cn.coldcoder.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class Topic {
    @Field("id")
    private Integer id;

    private Integer uid;
    @Field("title")
    private String title;
    @Field("content")
    private String content;

    private String images;

    private Integer commentNum;

    private Integer reportNum;
    @Field("topicType")
    private Integer topicType;

    private Integer status;

    private Boolean isAnon;

    private Boolean isCommentable;
    @Field("createTime")
    private Date createTime;

    private Date updateTime;

    public Topic(Integer id, Integer uid, String title, String content, String images, Integer commentNum, Integer reportNum, Integer topicType, Integer status, Boolean isAnon, Boolean isCommentable, Date createTime, Date updateTime) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.images = images;
        this.commentNum = commentNum;
        this.reportNum = reportNum;
        this.topicType = topicType;
        this.status = status;
        this.isAnon = isAnon;
        this.isCommentable = isCommentable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Topic() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsAnon() {
        return isAnon;
    }

    public void setIsAnon(Boolean isAnon) {
        this.isAnon = isAnon;
    }

    public Boolean getIsCommentable() {
        return isCommentable;
    }

    public void setIsCommentable(Boolean isCommentable) {
        this.isCommentable = isCommentable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}