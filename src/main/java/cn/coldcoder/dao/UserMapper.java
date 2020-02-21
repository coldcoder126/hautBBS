package cn.coldcoder.dao;

import cn.coldcoder.pojo.User;
import cn.coldcoder.vo.BasicUserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectPublicUserInfoById(Integer id);
    //查看其他用户的信息，只返回非隐私信息

    List<User> selectByNickName(String nickName);
    //根据用户昵称模糊查询用户信息

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkOpenid(String openid);
    //检查openid 是否存在，存在则返回1,不存在返回0

    int selectIdByOpenId(String openid);
    //根据openId返回用户id

    int insertOpenid(String openid);
    //插入一个openid并返回主键

    int updateScore(@Param("id") Integer id, @Param("score") Integer score);
    //减分

    int addScore(@Param("id") Integer id, @Param("score") Integer score);

    Boolean checkScore(@Param("id") Integer id,@Param("score") Integer score);

    //查询出必要的公开信息
    BasicUserInfoVo selectBasicByKey(Integer id);

    int checkNickName(String nickName);

    int checkStuid(String stuid);

}