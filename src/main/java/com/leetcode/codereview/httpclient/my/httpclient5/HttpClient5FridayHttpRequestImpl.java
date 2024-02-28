package com.leetcode.codereview.httpclient.my.httpclient5;

import com.alibaba.fastjson.JSONObject;
import com.leetcode.codereview.httpclient.my.FridayHttpRequest;
import com.leetcode.codereview.httpclient.my.FridayHttpResponseUtil;
import com.leetcode.codereview.httpclient.my.bo.ConversationRequestBo;
import com.leetcode.codereview.httpclient.my.bo.ConversationResponseBo;
import com.leetcode.codereview.httpclient.my.enums.UserTypeEnum;
import com.linkedin.parseq.Task;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Future;
@Service
public class HttpClient5FridayHttpRequestImpl implements FridayHttpRequest {

    @Override
    @Nullable
    public String getAccessTokenSync() {
        final ClassicHttpRequest httpPost = ClassicRequestBuilder.post(TOKEN_URL)
                .setEntity(new UrlEncodedFormEntity(
                        Arrays.asList(
                                new BasicNameValuePair("grant_type", GRANT_TYPE),
                                new BasicNameValuePair("client_id", CLIENT_ID),
                                new BasicNameValuePair("client_secret", CLIENT_SECRET))))
                .build();

        return sendRequestSync(httpPost, response -> {
            String accessToken = null;
            try {
                final HttpEntity entity = response.getEntity();
                String entiryString = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(entiryString);
                accessToken = FridayHttpResponseUtil.parseToken(jsonNode);
            } catch (IOException e) {
                System.out.println("缓冲区读取异常");
            } catch (ParseException e) {
                System.out.println("EntityUtils.toString()方法调用异常");
            }
            return accessToken;
        });
    }

    @Override
    public Task<String> getAccessTokenAsync() {
        return Task.callable("异步获取accessToken", this::getAccessTokenSync);
    }

    @Override
    @Nullable
    public ConversationResponseBo getConversationMessageSync() {
        ClassicHttpRequest request = ClassicRequestBuilder.post(CONVERSATION_URL)
                .setEntity(new StringEntity(createConversationRequestBOJson(), ContentType.APPLICATION_JSON, UTF_8_CHARSET, false))
                .build();
        JsonNode jsonNode = sendRequestSync(request, response -> {
            final HttpEntity entity = response.getEntity();
            String entiryString = EntityUtils.toString(entity, UTF_8_CHARSET);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jn = objectMapper.readTree(entiryString);
            EntityUtils.consume(entity);
            return jn;
        });
        return FridayHttpResponseUtil.parseConversationMessage(jsonNode);
    }

    @Override
    public Task<ConversationResponseBo> getConversationMessageAsync() {
        return Task.callable("异步获取会话信息", this::getConversationMessageSync);
    }

    private <T> T sendRequestSync(ClassicHttpRequest request, HttpClientResponseHandler<? extends T> responseHandler) {
        CloseableHttpClient httpClient = (CloseableHttpClient) HttpClient5Factory.getHttpClient();
        T response = null;
        try {
            response = httpClient.execute(request, responseHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private Future<SimpleHttpResponse> sendRequestAsync(SimpleHttpRequest request, FutureCallback<SimpleHttpResponse> futureCallback) {
        CloseableHttpAsyncClient httpAsyncClient = (CloseableHttpAsyncClient) HttpClient5Factory.getHttpAsyncClient();
        httpAsyncClient.start();
        return httpAsyncClient.execute(request, futureCallback);
    }

    private String createConversationRequestBOJson() {
        ConversationRequestBo conversationRequestBo = new ConversationRequestBo();
        conversationRequestBo.setAppId("1752246885613350954");
        conversationRequestBo.setUserId("2878708992");
        conversationRequestBo.setUserType(UserTypeEnum.DX_UID.getValue());
        conversationRequestBo.setUtterances(Collections.singletonList("商家反馈自己购买面粉没有满减辛苦帮忙看一下"));
        conversationRequestBo.setStream(Boolean.FALSE);
        conversationRequestBo.setAccessToken(getAccessTokenSync());
        HashMap<String, String> businessInfo = new HashMap<>();
        businessInfo.put("yjm", "124");
        conversationRequestBo.setBusinessInfo(businessInfo);
        String jsonString = JSONObject.toJSONString(conversationRequestBo);
        return jsonString;
    }

    @Test
    public void test() {
        ConversationResponseBo conversationResponseBo = getConversationMessageSync();
        System.out.println(conversationResponseBo);
    }

}
