package cn.coldcoder.vo;

import java.util.Date;

/**
 * 帖子的基础信息
 * 使用场景：1.用户管理已发帖处 2.按关键字搜索帖子时显示结果
 */
public class BasicTopicVo {
    private Integer id;             //帖子的id
    private String title;           //帖子标题
    private String content;         //帖子内容
    private Integer topicType;      //帖子类型
    private String images;          //帖子的图片
    private Date createTime;        //帖子的创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTopicType() { return topicType; }

    public void setTopicType(Integer topicType) { this.topicType = topicType; }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
