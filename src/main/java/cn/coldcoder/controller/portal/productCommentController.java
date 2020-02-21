package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.ProductComment;
import cn.coldcoder.service.IProductCommentService;
import cn.coldcoder.util.cacheUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/productComment/")
@Controller
public class productCommentController {
    @Autowired
    private IProductCommentService iProductCommentService;

    @RequestMapping("get_list.do")
    @ResponseBody
    public ServerResponse<PageInfo> getCommentList(@RequestHeader("Authorization")String token,
                                                   @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                   int productId){
        String uid = cacheUtil.getKey(token);
        return iProductCommentService.getProductComment(pageNum,pageSize,uid,productId);
    }

    @RequestMapping("publish.do")
    @ResponseBody
    public ServerResponse<String> publishComment(@RequestHeader("Authorization")String token, ProductComment productComment){
        String uid = cacheUtil.getKey(token);

        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            int intUid = Integer.parseInt(uid);
            productComment.setFromUid(intUid);
            return iProductCommentService.publishComment(productComment);
        }
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ServerResponse deleteComment(@RequestHeader("Authorization")String token,int commentId){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            int intUid = Integer.parseInt(uid);
            return iProductCommentService.deleteComment(commentId, intUid);
        }
    }
    @RequestMapping("get_notification.do")
    @ResponseBody
    public ServerResponse<PageInfo> getNotification(@RequestHeader("Authorization")String token,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iProductCommentService.getNotification(pageNum,pageSize,uid);
        }
    }
    @RequestMapping("readAll.do")
    @ResponseBody
    public ServerResponse readAll(@RequestHeader("Authorization")String token){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iProductCommentService.readAll(uid);
        }
    }
}
