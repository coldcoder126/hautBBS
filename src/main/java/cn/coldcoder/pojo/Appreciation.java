package cn.coldcoder.pojo;

import java.util.Date;

public class Appreciation {
    private Integer id;

    private Integer tid;

    private Integer fromUid;

    private Integer category;

    private Date createTime;

    private Date updateTime;

    public Appreciation(Integer id, Integer tid, Integer fromUid, Integer category, Date createTime, Date updateTime) {
        this.id = id;
        this.tid = tid;
        this.fromUid = fromUid;
        this.category = category;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Appreciation() {
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

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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