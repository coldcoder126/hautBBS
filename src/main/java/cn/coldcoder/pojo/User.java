package cn.coldcoder.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private String openid;

    private String nickName;

    private String email;

    private String phone;

    private Integer score;

    private Integer gender;

    private Integer status;

    private String stuid;

    private String realName;

    private Integer grade;

    private String authenName;

    private String country;

    private String province;

    private String city;

    private String description;

    private String backgroundUrl;

    private String avatarUrl;

    private Date signTime;

    private Date createTime;

    private Date updateTime;

    public User(Integer id, String openid, String nickName, String email, String phone, Integer score, Integer gender, Integer status, String stuid, String realName, Integer grade, String authenName, String country, String province, String city, String description, String backgroundUrl, String avatarUrl, Date signTime, Date createTime, Date updateTime) {
        this.id = id;
        this.openid = openid;
        this.nickName = nickName;
        this.email = email;
        this.phone = phone;
        this.score = score;
        this.gender = gender;
        this.status = status;
        this.stuid = stuid;
        this.realName = realName;
        this.grade = grade;
        this.authenName = authenName;
        this.country = country;
        this.province = province;
        this.city = city;
        this.description = description;
        this.backgroundUrl = backgroundUrl;
        this.avatarUrl = avatarUrl;
        this.signTime = signTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid == null ? null : stuid.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAuthenName() {
        return authenName;
    }

    public void setAuthenName(String authenName) {
        this.authenName = authenName == null ? null : authenName.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl == null ? null : backgroundUrl.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Date getSignTime() { return signTime; }

    public void setSignTime(Date signTime) { this.signTime = signTime; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}