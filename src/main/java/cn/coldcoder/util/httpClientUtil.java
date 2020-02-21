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
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收一个微信code，成功则返回一个map,里面session_key和openid
 * 失败返回空值map
 */

public class httpClientUtil {

    public static Map getHttp(String wxcode) throws IOException {

        String appid = propertiesUtil.getProperty("appid");
        String secretkey = propertiesUtil.getProperty("secretkey");
        URI uri = null;
        try {
            uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session").
                    setParameter("appid", appid).
                    setParameter("secret",secretkey).
                    setParameter("js_code",wxcode).setParameter("grant_type","authorization_code").build();
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
        Map map = new HashMap();
        CloseableHttpResponse response = null;

        try{
            response = httpclient.execute(httpGet);
            int responseCode = response.getStatusLine().getStatusCode();
            entity =response.getEntity();   //获取返回的实体

            if(responseCode==200&&entity!=null) {
                String entity2String = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);

                JsonNode node = objectMapper.readTree(entity2String);
                //去掉字符串两端的引号
                if(node.has("session_key")) {
                    map.put("openid", StringUtils.strip(node.findValue("openid").toString(), "\"\""));
                    map.put("session_key", StringUtils.strip(node.findValue("session_key").toString(), "\"\""));
                    return map;
                }else {
                    return map;
                }
            }else {
                return map;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return map;
        }finally {
            httpclient.close();
            response.close();
        }
    }

}
