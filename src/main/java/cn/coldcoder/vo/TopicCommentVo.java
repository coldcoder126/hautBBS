package cn.coldcoder.vo;

import cn.coldcoder.pojo.User;
import java.util.Date;
import java.util.List;
//一级评论，下面可能还有二级评论
//可以在查找productCommentList复用此Vo(因为两个表差的只有topic_id和product_id 名字不同，而查询显示，用不到此属性)

public class TopicCommentVo {
    private Integer id;
    private String content;
    private Date createTime;
    private Integer level;
    private Integer parentId;
    private Boolean isLike;     //是否已经感谢此条评论的作者
    private BasicUserInfoVo fromUser;   //评论者信息
    private BasicUserInfoVo toUser;     //被评论者信息（二级评论中会用到）

    private List<TopicCommentVo> childTopicCommentVoList;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() { return parentId; }

    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) { isLike = like; }

    public BasicUserInfoVo getFromUser() {
        return fromUser;
    }

    public void setFromUser(BasicUserInfoVo fromUser) {
        this.fromUser = fromUser;
    }

    public BasicUserInfoVo getToUser() {
        return toUser;
    }

    public void setToUser(BasicUserInfoVo toUser) {
        this.toUser = toUser;
    }

    public List<TopicCommentVo> getChildTopicCommentVoList() {
        return childTopicCommentVoList;
    }

    public void setChildTopicCommentVoList(List<TopicCommentVo> childTopicCommentVoList) {
        this.childTopicCommentVoList = childTopicCommentVoList;
    }
}
