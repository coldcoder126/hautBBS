package cn.coldcoder.pojo;

import java.util.Date;

public class Accusation {
    private Integer id;

    private Integer tid;

    private Integer category;

    private Integer fromUid;

    private String reason;

    private Date createTime;

    private Date updateTime;

    public Accusation(Integer id, Integer tid, Integer category, Integer fromUid, String reason, Date createTime, Date updateTime) {
        this.id = id;
        this.tid = tid;
        this.category = category;
        this.fromUid = fromUid;
        this.reason = reason;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Accusation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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