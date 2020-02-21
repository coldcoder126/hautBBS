package cn.coldcoder.service.Impl;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.UserMapper;
import cn.coldcoder.pojo.User;
import cn.coldcoder.service.IUserService;
import cn.coldcoder.util.*;
import cn.coldcoder.vo.TokenInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param  code，获取其openid和session_key，并返回token给客户端
     * @return
     * @throws IOException
     */
//  先查询是否有此openid,有？则返回该用户信息；没有？新注册一个用户
    public ServerResponse getToken(String code) {

        Map wxMap = null;
        try {
            wxMap = httpClientUtil.getHttp(code);
        } catch (IOException e) {
            System.out.println("请求微信服务器失败");
            e.printStackTrace();
        }
        String sessionkey = (String) wxMap.get("session_key");
        String openid = (String) wxMap.get("openid");

        String token = StringUtils.left(sessionkey,10);
        //截取sessionkey的前10位作为token
        int resultCount = userMapper.checkOpenid(openid);
        // 去数据库查看openid是否存在，如果存在，更新token,如果不存在，新增一条记录,并将token:id存入cache

        if(resultCount >0){
            // 数据库中有此用户，更新cache   token:userid
            int intUid = userMapper.selectIdByOpenId(openid);
            String useridStr =String.valueOf(intUid);
            cacheUtil.setKey(Const.TOKEN_PREFIX +token,useridStr);
            TokenInfo tokenInfo = new TokenInfo(Const.TOKEN_PREFIX+token,sessionkey,false);
            return ServerResponse.createBySuccess(tokenInfo);
        }else {     //数据库中无此用户
            User user = new User();
            user.setOpenid(openid);
            int insertCount = userMapper.insertSelective(user);
            if(insertCount != 1){
                return ServerResponse.createByErrorMessage("插入用户失败");
            }else{  //新增用户成功
                int intUid = userMapper.selectIdByOpenId(openid);
                String strUid =String.valueOf(intUid);
                cacheUtil.setKey(Const.TOKEN_PREFIX +token,strUid);
                TokenInfo tokenInfo = new TokenInfo(Const.TOKEN_PREFIX+token,sessionkey,true);
                return ServerResponse.createBySuccess(tokenInfo);
            }
        }
    }

    //第一次从微信客户端获取用户信息，
        // 如果该用户存在，直接获取用户的信息，
        // 如果用户不存在，注册新用户，如果昵称相同则随机生成
    public ServerResponse firstRegister(String uid,User updateUser){
        int intUid = Integer.parseInt(uid);
        updateUser.setId(intUid);
        int resultCount = userMapper.checkNickName(updateUser.getNickName());
        if(resultCount>0){
            String userName = "用户"+UUID.randomUUID().toString().substring(0,8);
            updateUser.setNickName(userName);
        }
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByError();
        }
    }

    public ServerResponse<String> updatePhone(String uid,String encryptedData, String KeyorCode,String iv){
        String phone ="";
        int intUid = Integer.parseInt(uid);
        if(KeyorCode.contains("==")){
            //如果包含“==”说明传来的是session,可直接解密，否则传来的是code,去换取session后再用sessionkey解密
            try {
                String phoneStr = AESUtil.wxDecrypt(encryptedData,KeyorCode,iv);
                phone = phoneStr.split(",")[1].split(":")[1].substring(1,12);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //调用httpclient
            Map wxMap = null;
            try {
                wxMap = httpClientUtil.getHttp(KeyorCode);
            } catch (IOException e) {
                System.out.println("请求微信服务器失败");
                e.printStackTrace();
            }
            String sessionkey = (String) wxMap.get("session_key");
            try {
                String phoneStr = AESUtil.wxDecrypt(encryptedData,sessionkey,iv);
                phone = phoneStr.split(",")[1].split(":")[1].substring(1,12);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        User updateUser = new User();
        updateUser.setId(intUid);
        updateUser.setPhone(phone);
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByError();
        }
    }

    //更新用户信息（文字信息）
    public ServerResponse<String> updateBasicUserInfo(String uid,User updateUser){
        //更新昵称，个人描述，学号，三种场景
        int intUid = Integer.parseInt(uid);
        updateUser.setId(intUid);
        if(StringUtils.isNotEmpty(updateUser.getNickName())){
            //首先判断名字是否为空，不为空则检查昵称是否重复
            int resultCount = userMapper.checkNickName(updateUser.getNickName());
            if (resultCount > 0)
                return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"昵称已被占用");
            if(StringUtils.isNotEmpty(updateUser.getDescription())){
                //昵称不为空且有描述，检查描述是否有风险
                int reskStatus = MsgSecCheckUtil.MsgSecCheck(updateUser.getDescription());
                if(reskStatus>1){
                    return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
                }
            }
            int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("成功更新用户基本信息");
            }else {
                return ServerResponse.createByErrorMessage("更新用户基本信息失败");
            }
        }else if(StringUtils.isNotEmpty(updateUser.getDescription())){
            //如果只更新描述
            int reskStatus = MsgSecCheckUtil.MsgSecCheck(updateUser.getDescription());
            if(reskStatus>1){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
            }
            int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("成功更新用户基本信息");
            }else {
                return ServerResponse.createByErrorMessage("更新用户基本信息失败");
            }
        }else if(StringUtils.isNotEmpty(updateUser.getStuid())){
            //如果是更新学号，先检查学号是否重复
            int resultCount = userMapper.checkStuid(updateUser.getStuid());
            if (resultCount > 0)
                return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"已绑定其他用户");
            else {
                int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
                if(updateCount>0){
                    return ServerResponse.createBySuccessMessage("成功更新用户基本信息");
                }else {
                    return ServerResponse.createByErrorMessage("更新用户基本信息失败");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("更新用户基本信息失败");
        }

    }

    //更新用户头像或背景图，成功则返回成功图像url，使用私有方法组装一下
    public ServerResponse updateUserImage(String uid,String avatarUrl,String backgroundUrl){
        int intUid = Integer.parseInt(uid);
        User updateUser = assembleUser(intUid,avatarUrl,backgroundUrl);
        //组装成user对象，只更新头像头像则背景图传null，反之亦然。
        int resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("更新成功");
        }else {
            return ServerResponse.createByErrorMessage("更新失败");
        }
    }

    public ServerResponse<User> getUserInfo(String uid){
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
        user.setOpenid(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    public ServerResponse<User> getPublicUserInfo(int uid){
        User user = userMapper.selectPublicUserInfoById(uid);
        user.setOpenid(StringUtils.EMPTY);
        user.setId(0);
        user.setPhone(StringUtils.EMPTY);
        user.setRealName(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    public ServerResponse<PageInfo> getPublicInfoByNickName(String nickName,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(nickName)){
            nickName = new StringBuilder().append("%").append(nickName).append("%").toString();
        }
        List<User> userList = userMapper.selectByNickName(nickName);
        for(User user:userList){
            user.setOpenid(StringUtils.EMPTY);
        }
        PageInfo PageResult = new PageInfo(userList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public boolean checkScore(String  uid, int score){
        int intUid = Integer.parseInt(uid);
        return userMapper.checkScore(intUid,score);
    }

    private User assembleUser(int uid,String avatarUrl,String backgroundUrl){
        User user = new User();
        user.setId(uid);
        if(StringUtils.isBlank(avatarUrl)&&StringUtils.isNotBlank(backgroundUrl)){
           user.setBackgroundUrl(backgroundUrl);
           return user;
        }
        if(StringUtils.isBlank(backgroundUrl)&&StringUtils.isNotBlank(avatarUrl)){
            user.setAvatarUrl(avatarUrl);
            return user;
        }
        return null;
    }



    public ServerResponse addScoreByUid( String uid,int score){
        int intUid = Integer.parseInt(uid);
        User user = userMapper.selectByPrimaryKey(intUid);
        if(score>30)
            return ServerResponse.createByErrorMessage("签到失败");
        if(isToday(user.getSignTime())){
            return ServerResponse.createByErrorMessage("今天已签");
        }else {
            int resultCount = userMapper.addScore(intUid,score);
            if(resultCount>0){
                return ServerResponse.createBySuccess(score);
            }else {
                return ServerResponse.createByErrorMessage("签到失败");
            }
        }

    }

    private static boolean isToday(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);

    }


}
