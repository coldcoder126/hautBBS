package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.TopicComment;
import com.github.pagehelper.PageInfo;

public interface ITopicCommentService {
    ServerResponse<PageInfo> getTopicComment(int pageNum, int pageSize, String uid, int topicId);
    ServerResponse<String> publishComment(TopicComment topicComment);
    ServerResponse deleteComment(int commentId,int from_uid);
    ServerResponse<PageInfo> getNotification(int pageNum,int pageSize,String uid);
    ServerResponse readAll(String uid);
    ServerResponse<PageInfo> getMyComments(int pageNum,int pageSize,String uid);

}
