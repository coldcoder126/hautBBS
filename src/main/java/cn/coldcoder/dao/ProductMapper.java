package cn.coldcoder.dao;

import cn.coldcoder.pojo.Product;
import cn.coldcoder.vo.ProductListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //将商品的状态置为已售出
    int updateSaledStatus(@Param("id") Integer id,@Param("uid") Integer uid);

    //列出所有正常状态的商品
    List<ProductListVo> selectOrderByCreateTime();

    List<ProductListVo> selectDeletedList(Integer status);

    //查某一个用户发布的所有商品列表,包括已售和未售
    List<ProductListVo> selectProductListByUid(Integer uid);

    //按关键字模糊查询商品
    List<ProductListVo> searchProductByKey(String key);

    List<Product> selectAllProductByuid(int uid);

    //按主键查，但是状态要正常
    ProductListVo selectByKeyAndStatus(Integer id);

}