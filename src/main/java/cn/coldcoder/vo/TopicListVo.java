package cn.coldcoder.vo;

import cn.coldcoder.pojo.User;

import java.util.Date;

//用于显示帖子的大概信息
public class TopicListVo {
    private Integer id;             //帖子的id
    private String title;           //帖子标题
    private String content;         //帖子内容
    private String images;          //帖子的图片
    private Boolean isAnon;         //是否匿名
    private Boolean isCommentable;  //是否可评论
    private Integer commentNum;     //帖子的评论数
    private Date createTime;        //帖子的创建时间
    private Date updateTime;        //帖子的更新时间
    private BasicUserInfoVo basicUser;              //帖子中的user信息

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Boolean getAnon() { return isAnon; }

    public void setAnon(Boolean anon) { isAnon = anon; }

    public Boolean getCommentable() { return isCommentable; }

    public void setCommentable(Boolean commentable) { isCommentable = commentable; }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public BasicUserInfoVo getBasicUser() { return basicUser; }

    public void setBasicUser(BasicUserInfoVo basicUser) { this.basicUser = basicUser; }
}
