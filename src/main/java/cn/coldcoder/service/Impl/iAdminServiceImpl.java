package cn.coldcoder.service.Impl;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.AdminMapper;
import cn.coldcoder.pojo.Admin;
import cn.coldcoder.service.IAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iAdminService")
public class iAdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    public ServerResponse<Admin> login(String account,String password){
        Admin admin = adminMapper.selectLogin(account,password);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"登录失败");
        }else {
            admin.setPassword(StringUtils.EMPTY);
            return ServerResponse.createBySuccess(admin);
        }
    }

}
