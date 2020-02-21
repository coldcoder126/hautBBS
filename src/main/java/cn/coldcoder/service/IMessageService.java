package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Message;

public interface IMessageService {
    ServerResponse<String > sendMessage(String fromUid, Message message);
    ServerResponse readAll(int fromUid,String toUid);
    public ServerResponse getHistory(String fromUid,int toUid,int pageNum,int pageSize);
    ServerResponse getNotification(String uid);
}
