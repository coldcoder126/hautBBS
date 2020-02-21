package cn.coldcoder.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenUtil {
    //单例模式
    private String appid = propertiesUtil.getProperty("appid");
    private String secretkey = propertiesUtil.getProperty("secretkey");
    private String accessToke = "token";
    private DateTime dateTime = new DateTime(2019,9,1,0,0,0);
    private static AccessTokenUtil accessTokenUtil = new AccessTokenUtil();

    public static AccessTokenUtil getAccessTokenUtil(){
        return accessTokenUtil;
    }
    private AccessTokenUtil(){}

    public String getAccessToken() throws IOException {
        DateTime now = new DateTime();
        System.out.println(Minutes.minutesBetween(dateTime,now).getMinutes());
        if(Minutes.minutesBetween(dateTime,now).getMinutes()>90){
            //微信规定access_token有效时长2小时，每次使用时候和上次相隔90分钟以上就重新获得一次access_token

            URI uri = null;
            try {
                uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token").
                        setParameter("grant_type","client_credential").
                        setParameter("appid", appid).
                        setParameter("secret",secretkey).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpGet.setConfig(requestConfig);
            //1.生成httpGet对象，2.设置超时时间等

            HttpEntity entity = null;
            ObjectMapper objectMapper = new ObjectMapper();
            CloseableHttpResponse response = null;

            try{
                response = httpclient.execute(httpGet);
                int responseCode = response.getStatusLine().getStatusCode();
                entity =response.getEntity();   //获取返回的实体
                System.out.println(entity.toString());
                if(responseCode==200&&entity!=null) {
                    String entity2String = EntityUtils.toString(entity, "utf-8");
                    EntityUtils.consume(entity);
                    JsonNode node = objectMapper.readTree(entity2String);
                    //去掉字符串两端的引号

                    if(node.has("access_token")) {
                        String token = StringUtils.strip(node.findValue("access_token").toString(), "\"\"");
                        setAccessToke(token);
                        setDateTime();
                }else {
                        return "false";
                }}else {
                    return "false";
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpclient.close();
                response.close();
            }
            return accessToke;
        }else{
            return accessToke;
        }
    }

    private void setAccessToke(String token){
        this.accessToke = token;
    }
    private void setDateTime(){
        this.dateTime = new DateTime();
    }

}
