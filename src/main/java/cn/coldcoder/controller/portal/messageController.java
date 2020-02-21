package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Message;
import cn.coldcoder.service.IMessageService;
import cn.coldcoder.util.cacheUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//发送私信的controller
@Controller
@RequestMapping("message/")
public class messageController {
    @Autowired
    private IMessageService iMessageService;

    @RequestMapping("send.do")
    @ResponseBody
    public ServerResponse sendMessage(@RequestHeader("Authorization")String token, Message message){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iMessageService.sendMessage(uid,message);
        }
    }


    @RequestMapping("read_all.do")
    @ResponseBody
    //一键已读
    public ServerResponse readAll(@RequestHeader("Authorization")String token,int fromUid){
        String toUid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(toUid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iMessageService.readAll(fromUid,toUid);
        }
    }

    @RequestMapping("get_history.do")
    @ResponseBody
    //接收消息
    public ServerResponse<PageInfo> getHistory(@RequestHeader("Authorization")String token,int toUid,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iMessageService.getHistory(uid,toUid,pageNum,pageSize);
        }
    }

    //获取所有to_uid为我且未读的消息
    @RequestMapping("get_notification.do")
    @ResponseBody
    public ServerResponse getNotification(@RequestHeader("Authorization")String token){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iMessageService.getNotification(uid);
        }
    }

}
