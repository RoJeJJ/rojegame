package com.roje.game.login.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class WeChatUtil {
    /**
     * 通过oauth获取用户详细信息
     */
    private static final String USER_INFO_OAUTH = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    public static JsonObject userInfo(String openid, String token){
        String url = String.format(USER_INFO_OAUTH,token,openid);
        HttpPost post = new HttpPost(url);
        try {
            HttpResponse httpResponse = HttpClientUtil.INSTANCE.getHttpClient().execute(post);
            InputStreamReader reader = new InputStreamReader(httpResponse.getEntity().getContent());
            return new JsonParser().parse(reader).getAsJsonObject();
        } catch (IOException e) {
            log.info("请求用户微信信息异常",e);
            return null;
        }
    }
}
