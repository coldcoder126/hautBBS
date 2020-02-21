package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Appreciation;

public interface IAppreciationService {
    ServerResponse addAppreciation(Appreciation appreciation);
}
