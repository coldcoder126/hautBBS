package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Product;
import com.github.pagehelper.PageInfo;

public interface IProductService {
    ServerResponse publishProduct(String uid, Product product);
    ServerResponse<PageInfo> getlist(int pageNum, int pageSize);
    ServerResponse<PageInfo> getDeletedList(int pageNum, int pageSize,int status);
    ServerResponse setSaleStatus(String uid,Integer productId);
    ServerResponse getMyProductList(String uid,int pageNum, int pageSize);
    ServerResponse<PageInfo> searchByKey(String key,int pageNum,int pageSize );
    ServerResponse updateStatusById(int id,int status);
    ServerResponse deleteProduct(int ProductId);
    ServerResponse<PageInfo> selectAllProductByUid(int uid,int pageNum,int pageSize);
    ServerResponse getProductByid(int id);
}
