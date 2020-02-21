package cn.coldcoder.vo;

import java.util.Date;

//用于通知，是通用的vo
public class CommentVo {
    private Integer id;
    private String content;
    private Integer topicId;
    private Boolean haveRead;
    private Boolean status;
    private Date createTime;
    private Date updateTime;
    private BasicUserInfoVo fromUser;   //评论者信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public BasicUserInfoVo getFromUser() {
        return fromUser;
    }

    public void setFromUser(BasicUserInfoVo fromUser) {
        this.fromUser = fromUser;
    }
}
