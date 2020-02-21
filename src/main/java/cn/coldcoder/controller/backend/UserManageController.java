package cn.coldcoder.controller.backend;


import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Admin;
import cn.coldcoder.pojo.User;
import cn.coldcoder.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping("search_byNickName.do")
    @ResponseBody
    //按照用户昵称模糊查询用户
    public ServerResponse<PageInfo> getUserInfoByNickName(HttpSession session,String nickName,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iUserService.getPublicInfoByNickName(nickName, pageNum, pageSize);
        }
    }
    @RequestMapping("get_infoById.do")
    @ResponseBody
    //通过查询用户id获取用户公开信息，管理员使用
    public ServerResponse<User> getUserInfoById(HttpSession session,int id){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            return iUserService.getPublicUserInfo(id);
        }
    }

    @RequestMapping("update_userInfo.do")
    @ResponseBody
    //通过用户id更改用户信息
    public ServerResponse<String> updateUserInfo(HttpSession session,int id,User user){
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }else {
            String StrUid = new Integer(id).toString();
            return iUserService.updateBasicUserInfo(StrUid, user);
        }
    }



}
