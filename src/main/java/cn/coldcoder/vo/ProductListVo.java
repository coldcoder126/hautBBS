package cn.coldcoder.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ProductListVo {

    private Integer id;             //该条商品信息的id
    private Double price;       //该商品的价格
    private String title;           //该商品的主要内容
    private String description;     //该商品的描述
    private String images;          //该商品的图片
    private Integer commentNum;     //评论的数量
    private Integer category;       //出售还是求购
    private Integer status;         //商品状态（用于此Vo的复用）
    private Date createTime;        //该商品发布的时间

    BasicUserInfoVo basicUserInfoVo;    //发布人的基本信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCategory() { return category; }

    public void setCategory(Integer category) { this.category = category; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BasicUserInfoVo getBasicUserInfoVo() {
        return basicUserInfoVo;
    }

    public void setBasicUserInfoVo(BasicUserInfoVo basicUserInfoVo) {
        this.basicUserInfoVo = basicUserInfoVo;
    }
}
