package cn.coldcoder.service;

import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    ServerResponse getToken(String code);
    ServerResponse<String> updateBasicUserInfo(String uid,User user);
    ServerResponse updateUserImage(String uid,String avatarUrl,String backgroundUrl);
    ServerResponse<String> updatePhone(String uid,String encryptedData, String KeyorCode,String iv);
    ServerResponse<User> getUserInfo(String uid);
    ServerResponse<User> getPublicUserInfo(int uid);
    ServerResponse<PageInfo> getPublicInfoByNickName(String nickName, int pageNum, int pageSize);
    boolean checkScore(String uid, int score);
    ServerResponse addScoreByUid(String uid,int score);
    ServerResponse firstRegister(String uid,User updateUser);

}
