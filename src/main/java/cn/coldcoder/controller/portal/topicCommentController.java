package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.TopicComment;
import cn.coldcoder.service.ITopicCommentService;
import cn.coldcoder.util.cacheUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/topicComment/")
@Controller
public class topicCommentController {
    @Autowired
    private ITopicCommentService iTopicCommentService;

    //获取一个帖子下的所有评论
    @RequestMapping("get_comment.do")
    @ResponseBody
    public ServerResponse<PageInfo> getTopicComment(@RequestHeader("Authorization")String token,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                    int topicId) {
        String uid = cacheUtil.getKey(token);
        return iTopicCommentService.getTopicComment(pageNum,pageSize,uid,topicId);
    }

    @RequestMapping("publish_comment.do")
    @ResponseBody
    //前端传入的必须有from_uid,to_uid,topic_id,content,level,parent_id
    public ServerResponse<String> publishComment(@RequestHeader("Authorization")String token, TopicComment topicComment){
        String uid = cacheUtil.getKey(token);

        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            int intUid = Integer.parseInt(uid);
            topicComment.setFromUid(intUid);
            return iTopicCommentService.publishComment(topicComment);
        }
    }

    @RequestMapping("delete_comment.do")
    @ResponseBody
    //假删除，
    public ServerResponse deleteComment(@RequestHeader("Authorization")String token,int commentId){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            int intUid = Integer.parseInt(uid);
            return iTopicCommentService.deleteComment(commentId, intUid);
        }
    }

    @RequestMapping("get_notification.do")
    @ResponseBody
    public ServerResponse getNotification(@RequestHeader("Authorization")String token,
                                             @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicCommentService.getNotification(pageNum,pageSize,uid);
        }
    }

    @RequestMapping("readAll.do")
    @ResponseBody
    public ServerResponse readAll(@RequestHeader("Authorization")String token){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicCommentService.readAll(uid);
        }
    }


    @RequestMapping("get_CommentsFromMe.do")
    @ResponseBody
    public ServerResponse<PageInfo> getMyComments(@RequestHeader("Authorization")String token,
                                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicCommentService.getMyComments(pageNum,pageSize,uid);
        }
    }


    //todo 举报评论
}
