package cn.coldcoder.dao;

import cn.coldcoder.pojo.Topic;
import cn.coldcoder.vo.BasicTopicVo;
import cn.coldcoder.vo.TopicListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    int updateStatusByKey(@Param("id") Integer id, @Param("uid") Integer uid);

    //按照类型，以更新时间的倒序来查询帖子
    List<TopicListVo> selectTopicListByTypeOrderByUpdateTime(Integer topicType);

    List<TopicListVo> selectTopicListByStatus(Integer status);
    //按照状态查询帖子

    //根据帖子的编号获取一条TopicVo
    TopicListVo selectTopicVoById(Integer id);

    //按照用户id查出该用户帖子的基本信息（用于显示个人管理页面）
    List<BasicTopicVo> selectBasicTopicByUid(Integer uid);

    //按关键字模糊查询帖子标题或内容并返回帖子的基本信息
    List<BasicTopicVo> searchTopicByKey(@Param("key")String key,@Param("type")Integer type);

    //按照用户id获取该用户发过的所有的状态的帖子
    List<Topic> selectAllTopicByuid(int uid);
}