package cn.coldcoder.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
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

public class MsgSecCheckUtil {
//文字有风险返回87014，无风险返回0，出错返回-1
    public static int MsgSecCheck(String textContent) {
        if(textContent==null||textContent.trim().length()<2){
            return 0;
        }
        String access_token = getAccessToken();
        if(!access_token.equals("false")){
            URI uri = null;
            try {
                uri = new URIBuilder("https://api.weixin.qq.com/wxa/msg_sec_check").
                        setParameter("access_token",access_token).build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = null;
            ObjectMapper objectMapper = new ObjectMapper();
            CloseableHttpResponse response = null;
            Map<String, String> paramMap = new HashMap();

            paramMap.put("content",textContent);
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(paramMap);
            StringEntity entity = new StringEntity(jsonString,"utf-8");//解决中文乱码问题

            entity.setContentEncoding("UTF-8");
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
            httpPost.addHeader("accept","application/json");
            httpPost.setEntity(entity);

            response = httpclient.execute(httpPost);
            int responseCode = response.getStatusLine().getStatusCode();
            responseEntity = response.getEntity();   //获取返回的实体
            System.out.println(responseEntity.toString());
            if(responseCode==200&&entity!=null) {
                String entity2String = EntityUtils.toString(responseEntity, "utf-8");
                EntityUtils.consume(entity);
                JsonNode node = objectMapper.readTree(entity2String);
                if (node.has("errcode")) {
                    int errcode = Integer.parseInt(StringUtils.strip(node.findValue("errcode").toString(), "\"\""));
                    return errcode;
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
            return -1;
        }else{
            System.out.println("出错");
            return -1;
        }

    }

    private static String  getAccessToken(){
        AccessTokenUtil accessTokenUtil = AccessTokenUtil.getAccessTokenUtil();
        try {
            return(accessTokenUtil.getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
}
