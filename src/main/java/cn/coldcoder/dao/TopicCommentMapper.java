package cn.coldcoder.dao;

import cn.coldcoder.pojo.TopicComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByTopicId(Integer topicId);
    //删除该贴下所有的评论

    int insert(TopicComment record);

    int insertSelective(TopicComment record);

    TopicComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicComment record);

    int updateByPrimaryKey(TopicComment record);

    //查出第一级的评论放在list中
    List<TopicComment> selectByTopicId(Integer topicId);

    //以第一级评论查出第二级评论
    List<TopicComment> selectChildByParentId(Integer parentId);

    int updateStatus(@Param("id") Integer id,@Param("from_uid") Integer from_uid);

    int updateStatusByTopicId(@Param("topicId") Integer topicId,@Param("status") Integer status);

    //获取像我回复的未读的通知
    List<TopicComment> selectByToUid(int toUid);

    //将现在之前所有的未读 置为已读
    int readAll(int toUid);

    List<TopicComment> selectByFromUid(int fromUid);


}