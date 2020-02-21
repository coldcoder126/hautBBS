package cn.coldcoder.vo;

public class TokenInfo {
    private String token;
    private String sessionKey;
    private Boolean isFirst;

    public TokenInfo(String token, String sessionKey, Boolean isFirst) {
        this.token = token;
        this.sessionKey = sessionKey;
        this.isFirst = isFirst;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }
}
