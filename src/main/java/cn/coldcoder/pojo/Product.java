package cn.coldcoder.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class Product {
    @Field("id")
    private Integer id;

    private Integer uid;
    @Field("price")
    private Double price;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("images")
    private String images;

    private Integer commentNum;

    private Integer reportNum;

    private Integer status;
    @Field("category")
    private Integer category;
    @Field("createTime")
    private Date createTime;

    private Date updateTime;

    public Product(Integer id, Integer uid, Double price, String title, String description, String images, Integer commentNum, Integer reportNum, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.uid = uid;
        this.price = price;
        this.title = title;
        this.description = description;
        this.images = images;
        this.commentNum = commentNum;
        this.reportNum = reportNum;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Product() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        DecimalFormat df=new DecimalFormat("0.00");
        this.price = new Double(df.format(price).toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategory() { return category; }

    public void setCategory(Integer category) { this.category = category; }

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