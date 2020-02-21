package cn.coldcoder.common;

//常用的一些常量
public class Const {

    public static final String CURRENT_ADMIN = "current_admin";
    public static final String TOKEN_PREFIX = "token_";
    public static final Integer SCORE_TOPIC = 20;
    public static final Integer SCORE_COMMENT = 1;
    public static final Integer SCORE_APPRECIATION = 3;

    //喜欢或举报表里的类型
    public static final Integer TYPE_TOPIC = 1;
    public static final Integer TYPE_TOPIC_COMMENT = 2;
    public static final Integer TYPE_PRODUCT = 3;
    public static final Integer TYPE_PRODUCT_COMMENT = 4;

    private  String access_token ="token";
    public void setAccess_token(String accessToken){
        this.access_token=accessToken;
    }

}
