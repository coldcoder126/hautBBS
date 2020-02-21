package cn.coldcoder.controller.portal;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.User;
import cn.coldcoder.service.IUserService;
import cn.coldcoder.util.cacheUtil;
import cn.coldcoder.util.httpClientUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class userController {
    @Autowired
    private IUserService iUserService;



    @RequestMapping("get_token.do")
    @ResponseBody
    public ServerResponse getToken(String wxcode){
        return iUserService.getToken(wxcode);
    }

    @RequestMapping("first_update.do")
    @ResponseBody
    //第一次登陆从客户端获取用户昵称，如果昵称不可用，随机生成昵称。
    //其他情况下更新信息如有重复则只是提醒
    public ServerResponse firstUpdate(@RequestHeader("Authorization") String token, User user){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iUserService.firstRegister(uid,user);
        }
    }


    @RequestMapping("update_userinfo.do")
    @ResponseBody
    //更新用户基本信息
    public ServerResponse updateInfo(@RequestHeader("Authorization") String token, User user) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iUserService.updateBasicUserInfo(uid, user);
        }
    }

    @RequestMapping("update_phone.do")
    @ResponseBody
    //更新用户手机号
    public ServerResponse updatePhone(@RequestHeader("Authorization") String token, String encryptedData, String KeyorCode,String iv){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iUserService.updatePhone(uid,encryptedData,KeyorCode,iv);
        }
    }

    @RequestMapping("update_avatar.do")
    @ResponseBody
    //修改头像，修改成功返回成功状态
    public ServerResponse updateAvatar(@RequestHeader("Authorization")String token,String avatarUrl){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iUserService.updateUserImage(uid,avatarUrl,null);
        }
    }

    @RequestMapping("update_background.do")
    @ResponseBody
    //修改背景图，传入新背景图的相对url,修改成功返回状态值
    public ServerResponse updateBackground(@RequestHeader("Authorization")String token,String backgroundUrl){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iUserService.updateUserImage(uid,null,backgroundUrl);
        }
    }


    @RequestMapping("get_info.do")
    @ResponseBody
    //获取用户的全部信息,显示给前台个人信息管理用
    public ServerResponse<User> getInfo(@RequestHeader("Authorization")String token){
        String uid = cacheUtil.getKey(token);
        if(StringUtils.isBlank(uid)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请先登录");
        }else {
            return iUserService.getUserInfo(uid);
        }
    }


    @RequestMapping("sign.do")
    @ResponseBody
    //签到，由前台生成随机分数，服务器只负责增加
    public ServerResponse Sign(@RequestHeader("Authorization")String token,int score) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iUserService.addScoreByUid(uid,score);
        }
    }

    @RequestMapping("get_userById.do")
    @ResponseBody
    //查看其他用户信息
    public ServerResponse getUserInfoById(@RequestHeader("Authorization")String token,int id){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        } else {
            return iUserService.getPublicUserInfo(id);
        }
    }

}
