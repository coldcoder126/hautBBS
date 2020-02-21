package cn.coldcoder.pojo;

import java.util.Date;

public class TopicComment {
    private Integer id;

    private Integer fromUid;

    private Integer toUid;

    private Integer topicId;

    private String content;

    private Boolean status;

    private Boolean haveRead;

    private Integer level;

    private Integer parentId;

    private Integer reportNum;

    private Date createTime;

    private Date updateTime;

    public TopicComment(Integer id, Integer fromUid, Integer toUid, Integer topicId, String content, Boolean status, Boolean haveRead, Integer level, Integer parentId, Integer reportNum, Date createTime, Date updateTime) {
        this.id = id;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.topicId = topicId;
        this.content = content;
        this.status = status;
        this.haveRead = haveRead;
        this.level = level;
        this.parentId = parentId;
        this.reportNum = reportNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TopicComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
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