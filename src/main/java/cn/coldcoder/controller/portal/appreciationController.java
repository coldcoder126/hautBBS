package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Appreciation;
import cn.coldcoder.service.IAppreciationService;
import cn.coldcoder.util.cacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("appreciate")
//点赞功能单独出来
public class appreciationController {
    @Autowired
    IAppreciationService iAppreciationService;

    @RequestMapping("appreciate.do")
    @ResponseBody
    //增加一条喜欢的记录
    public ServerResponse appreciateIt(@RequestHeader("Authorization")String token, Appreciation appreciation){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            int intUid = Integer.parseInt(uid);
            appreciation.setFromUid(intUid);
            return iAppreciationService.addAppreciation(appreciation);
        }
    }
}
