package cn.coldcoder.controller.backend;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Admin;
import cn.coldcoder.pojo.Message;
import cn.coldcoder.service.IMessageService;
import cn.coldcoder.service.IProductService;
import cn.coldcoder.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/topic_product/")
//在这里管理已发的帖子（包括商品贴）
public class TopicManagerController {
    @Autowired
    private ITopicService iTopicService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IMessageService iMessageService;

    @RequestMapping("get_topicList.do")
    @ResponseBody
    public ServerResponse getTopicList(HttpSession session,
                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
                                       @RequestParam(required = true) int topicType){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.getTopicListOrderByUpdateTime(pageNum,pageSize,topicType);
        }
    }
    @RequestMapping("get_deletedTopic.do")
    @ResponseBody
    //获取已删除的帖子
    public ServerResponse getDeletedTopic(HttpSession session,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "15") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.getDeletedTopicList(pageNum,pageSize,3);
        }
    }


    @RequestMapping("delete_topic.do")
    @ResponseBody
    //管理员根据帖子的id删除该贴,管理员删除为把状态置为3
    public ServerResponse deleteTopic(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.updateById(id, 3);
        }
    }

    @RequestMapping("delete_topic_from_wx.do")
    @ResponseBody
    public ServerResponse deleteTopicFromWeChat(int id){
        return iTopicService.updateById(id, 3);
    }

    @RequestMapping("recover_topic.do")
    @ResponseBody
    //恢复已删帖子
    public ServerResponse recoverTopic(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.updateById(id, 0);
        }
    }

    @RequestMapping("get_topicbyid.do")
    @ResponseBody
    //按照帖子的id来搜索帖子
    public ServerResponse getTopicById(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.getTopicVoById(id);
        }
    }


    @RequestMapping("get_allTopicByUid.do")
    @ResponseBody
    //根据用户id查找他所发的所有帖子，所有状态，按时间排序
    public ServerResponse selectAllTopicByUid(HttpSession session,int uid,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.selectAllTopicByUid(uid, pageNum, pageSize);
        }
    }

    @RequestMapping("notice_owner.do")
    @ResponseBody
    //通知用户一下
    public ServerResponse noticeOwner(HttpSession session,int toUid,String msg){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            Message message = new Message();
            message.setToUid(toUid);
            message.setContent(msg);
            return iMessageService.sendMessage("1001",message);
        }
    }

    @RequestMapping("notice_from_wx.do")
    @ResponseBody
    public ServerResponse noticeOwnerFromWeChat(int toUid,String msg){
        Message message = new Message();
        message.setToUid(toUid);
        message.setContent(msg);
        return iMessageService.sendMessage("1001",message);
    }

//    -------------------------------------------------商品-----------------------------------------------

    @RequestMapping("get_productList.do")
    @ResponseBody
    public ServerResponse getProductList(HttpSession session,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iProductService.getlist(pageNum,pageSize);
        }
    }

    public ServerResponse getDeletedProduct(HttpSession session,
                                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iTopicService.getDeletedTopicList(pageNum,pageSize,3);
        }
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse deleteProduct(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iProductService.updateStatusById(id, 3);
        }
    }

    @RequestMapping("delete_product_from_wx.do")
    @ResponseBody
    public ServerResponse deleteProductFromWeChat(int id){
        return iProductService.updateStatusById(id, 3);
    }

    @RequestMapping("recover_product.do")
    @ResponseBody
    public ServerResponse recoverProduct(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iProductService.updateStatusById(id, 0);
        }
    }

    public ServerResponse selectAllProductByUid(HttpSession session,int uid,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iProductService.selectAllProductByUid(uid, pageNum, pageSize);
        }
    }



}
