package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Topic;
import cn.coldcoder.service.ITopicService;
import cn.coldcoder.service.IUserService;
import cn.coldcoder.util.cacheUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/topic/")
public class topicController {
    @Autowired
    private ITopicService iTopicService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping("publish_topic.do")
    @ResponseBody
    //发布topic，先在客户端上传图片，只需存入上传成功返回的url值
    //ps.发帖要扣积分，前端在缓存里检查积分，后台负责更新积分
    public ServerResponse publishTopic(@RequestHeader("Authorization") String token, Topic topic) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicService.publishTopic(uid, topic);
        }
    }

    @RequestMapping("delete_topic.do")
    @ResponseBody
    //用户删帖的时候是真删除并删除帖子下相应评论
    public ServerResponse deleteTopic(@RequestHeader("Authorization") String token, int topicId) {
        String uid = cacheUtil.getKey(token);
        int intUid = Integer.parseInt(uid);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicService.deleteTopic(topicId);
        }
    }

    @RequestMapping("get_topicbyid.do")
    @ResponseBody
    //按照帖子的id来搜索帖子
    public ServerResponse getTopicById(@RequestHeader("Authorization") String token,int id){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicService.getTopicVoById(id);
        }
    }

    @RequestMapping("get_topiclist.do")
    @ResponseBody
    //浏览帖子，每次10条
    public ServerResponse<PageInfo> getTopicList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestParam(required = true) int topicType) {
        return iTopicService.getTopicListOrderByUpdateTime(pageNum, pageSize,topicType);
    }



    @RequestMapping("report_topic.do")
    @ResponseBody
    public ServerResponse<String> repoortTopic(@RequestHeader("Authorization") String token, Integer topicId, String reason) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iTopicService.reportTopic(topicId, uid, reason);
        }
    }

    //按照用户的id获取该用户发过的帖子，不传UID则为“我”发的帖子
    @RequestMapping("get_topicListByUid.do")
    @ResponseBody
    public ServerResponse getMyTopicList(@RequestHeader("Authorization") String token,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "UID", defaultValue = "0") int UID) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            if(UID==0){
                return iTopicService.getMyTopicList(uid,pageNum,pageSize);
            }else{
                uid = String.valueOf(UID);
                return iTopicService.getMyTopicList(uid,pageNum,pageSize);
            }

        }
    }


    //按关键字搜索帖子，并按发布时间返回
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> searchByKey(String key,int type,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return iTopicService.searchByKey(key,type,pageNum,pageSize);
    }

}
