package cn.coldcoder.dao;

import cn.coldcoder.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int readAll(@Param("fromUid") int fromUid, @Param("toUid") int toUid);

    //查找历史记录,不论是A发给B的还是B发给A的，并按时间排序
    List<Message> selectHistory(@Param("fromUid") int fromUid, @Param("toUid") int toUid);

    //查找所有to_uid为我且未读的消息
    List<Message> selectUnread(int toUid);
}