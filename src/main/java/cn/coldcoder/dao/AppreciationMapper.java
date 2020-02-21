package cn.coldcoder.dao;

import cn.coldcoder.pojo.Appreciation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface  AppreciationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appreciation record);

    int insertSelective(Appreciation record);

    Appreciation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appreciation record);

    int updateByPrimaryKey(Appreciation record);

    //查询用户的喜欢状态
    int selectByTIdAndUid(@Param("tid")Integer tid, @Param("from_uid") Integer uid,@Param("category")Integer category);

    List<Integer> selectAllTiddByUid( @Param("from_uid") Integer uid,@Param("category")Integer category);
}