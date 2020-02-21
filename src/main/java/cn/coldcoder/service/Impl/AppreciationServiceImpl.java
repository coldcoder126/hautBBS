package cn.coldcoder.service.Impl;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.AppreciationMapper;
import cn.coldcoder.pojo.Appreciation;
import cn.coldcoder.service.IAppreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iAppreciationService")
public class AppreciationServiceImpl implements IAppreciationService {
    @Autowired
    AppreciationMapper appreciationMapper;

    public ServerResponse addAppreciation(Appreciation appreciation){
        int resultCount = appreciationMapper.insertSelective(appreciation);
        if(resultCount>0){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByError();
        }
    }
}
