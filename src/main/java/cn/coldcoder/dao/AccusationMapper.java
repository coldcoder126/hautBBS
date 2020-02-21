package cn.coldcoder.dao;

import cn.coldcoder.pojo.Accusation;
import org.apache.ibatis.annotations.Param;

public interface AccusationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Accusation record);

    int insertSelective(Accusation record);

    Accusation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Accusation record);

    int updateByPrimaryKey(Accusation record);

    int checkByUidAndTid(@Param("tid")Integer tid,@Param("from_uid") Integer uid,@Param("category")Integer category);

    int insertByUidAndTid(@Param("tid")Integer tid, @Param("from_uid") Integer uid,
                          @Param("category")Integer category, @Param("reason")String reason);
}