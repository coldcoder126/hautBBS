package cn.coldcoder.dao;

import cn.coldcoder.pojo.Product;
import cn.coldcoder.pojo.ProductComment;
import cn.coldcoder.pojo.TopicComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByProductId(Integer ProductId);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    ProductComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);

    //查出第一级的评论放在list中
    List<ProductComment> selectByProductId(Integer productId);

    List<ProductComment> selectChildByParentId(Integer parentId);

    int updateStatus(@Param("uid") Integer uid, @Param("from_uid") Integer from_uid);

    int updateStatusByProductId(@Param("productId") Integer productId,@Param("status") Integer status);

    List<ProductComment> selectByToUid(int toUid);

    int readAll(int toUid);


}