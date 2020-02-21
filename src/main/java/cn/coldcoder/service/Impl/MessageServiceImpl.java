package cn.coldcoder.service.Impl;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.MessageMapper;
import cn.coldcoder.pojo.Message;
import cn.coldcoder.service.IMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iMessageService")
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    public ServerResponse<String > sendMessage(String fromUid,Message message){
        int intFromUid = Integer.parseInt(fromUid);
        message.setFromUid(intFromUid);
        int resultCount = messageMapper.insertSelective(message);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("发送成功");
        }else {
            return ServerResponse.createByErrorMessage("发送失败");
        }
    }

    //一键已读
    public ServerResponse readAll(int fromUid,String toUid){
        int intToUid = Integer.parseInt(toUid);
        int resultCount = messageMapper.readAll(fromUid,intToUid);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse getHistory(String fromUid,int toUid,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        int intFromUid = Integer.parseInt(fromUid);
        List<Message> messageList = messageMapper.selectHistory(intFromUid,toUid);
        for (Message message:messageList){
            if(message.getToUid()==intFromUid){
                message.setToUid(0);
            }else {
                message.setFromUid(0);
            }
        }
        PageInfo PageResult = new PageInfo(messageList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public ServerResponse getNotification(String uid){
        int intUid = Integer.parseInt(uid);
        List<Message> messageList = messageMapper.selectUnread(intUid);
        return ServerResponse.createBySuccess(messageList);
    }
}
