package cn.coldcoder.common;
public enum ResponseCode {
    SUCCESS(200,"SUCCESS"),                 //成功统一返回码
    FAIL(400,"FAIL"),                     //服务器统一失败返回码
    NEED_LOGIN(401,"NEED_LOGIN"),           //用户需要先登录
    FORBIDDEN(403,"FORBIDDEN"),             //用户没有权限
    ERROR(500,"ERROR");                     //服务器统一错误返回码

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
