package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.ProductComment;
import com.github.pagehelper.PageInfo;

public interface IProductCommentService {
    ServerResponse<PageInfo> getProductComment(int pageNum, int pageSize, String uid, int productId);
    ServerResponse publishComment(ProductComment productComment);
    ServerResponse<String> deleteComment(int commentId,int from_uid);
    ServerResponse<PageInfo> getNotification(int pageNum,int pageSize,String uid);
    ServerResponse readAll(String uid);
}
