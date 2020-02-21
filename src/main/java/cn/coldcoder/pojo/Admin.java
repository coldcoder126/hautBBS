package cn.coldcoder.pojo;

import java.util.Date;

public class Admin {
    private Integer id;

    private String account;

    private String password;

    private String nickName;

    private Integer level;

    private Date createTime;

    private Date updateTime;

    public Admin(Integer id, String account, String password, String nickName, Integer level, Date createTime, Date updateTime) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickName = nickName;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Admin() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

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