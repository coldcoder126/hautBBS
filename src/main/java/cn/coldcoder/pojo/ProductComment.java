package cn.coldcoder.pojo;

import java.util.Date;

public class ProductComment {
    private Integer id;

    private Integer productId;

    private Integer fromUid;

    private Integer toUid;

    private String content;

    private Boolean status;

    private Integer level;

    private Integer parentId;

    private Boolean haveRead;

    private Integer reportNum;

    private Date createTime;

    private Date updateTime;

    public ProductComment(Integer id, Integer productId, Integer fromUid, Integer toUid, String content, Boolean status, Integer level, Integer parentId, Boolean haveRead, Integer reportNum, Date createTime, Date updateTime) {
        this.id = id;
        this.productId = productId;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.content = content;
        this.status = status;
        this.level = level;
        this.parentId = parentId;
        this.haveRead = haveRead;
        this.reportNum = reportNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProductComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
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