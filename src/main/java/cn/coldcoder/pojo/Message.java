package cn.coldcoder.pojo;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer fromUid;

    private Integer toUid;

    private String content;

    private Boolean haveRead;

    private Date createTime;

    private Date updateTime;

    public Message(Integer id, Integer fromUid, Integer toUid, String content, Boolean haveRead, Date createTime, Date updateTime) {
        this.id = id;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.content = content;
        this.haveRead = haveRead;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Message() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
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