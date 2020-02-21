package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Topic;
import cn.coldcoder.vo.TopicListVo;
import com.github.pagehelper.PageInfo;

public interface ITopicService {
    ServerResponse<String> publishTopic(String uid, Topic topic);
    ServerResponse<String> deleteTopic(int topicId);
    ServerResponse<PageInfo> getTopicListOrderByUpdateTime(int pageNum, int pageSize,int topicType);
    ServerResponse<PageInfo> getDeletedTopicList(int pageNum, int pageSize,int status);
    ServerResponse<TopicListVo> getTopicVoById(int id);
    ServerResponse<String> reportTopic(Integer tid,String uid,String reason);
    ServerResponse<PageInfo> getMyTopicList(String uid,int pageNum,int pageSize);
    ServerResponse<PageInfo> searchByKey(String key,int type,int pageNum,int pageSize );
    ServerResponse<String> updateById(int id,int status);
    ServerResponse<PageInfo> selectAllTopicByUid(int uid,int pageNum,int pageSize);
}
