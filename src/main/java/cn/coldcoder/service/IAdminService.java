package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Admin;

public interface IAdminService {
    ServerResponse<Admin> login(String account, String password);
}
