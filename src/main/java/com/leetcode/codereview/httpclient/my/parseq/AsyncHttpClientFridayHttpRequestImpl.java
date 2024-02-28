package com.leetcode.codereview.httpclient.my.parseq;

import com.alibaba.fastjson.JSONObject;
import com.leetcode.codereview.httpclient.my.FridayHttpRequest;
import com.leetcode.codereview.httpclient.my.FridayHttpResponseUtil;
import com.leetcode.codereview.httpclient.my.bo.ConversationRequestBo;
import com.leetcode.codereview.httpclient.my.bo.ConversationResponseBo;
import com.leetcode.codereview.httpclient.my.enums.UserTypeEnum;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.ning.http.client.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Executors;
@Service
public class AsyncHttpClientFridayHttpRequestImpl implements FridayHttpRequest {

    private static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";

    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public String getAccessTokenSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Task<String> getAccessTokenAsync() {
        Task<Response> responseTask = WrappedAsyncHttpClient.post(TOKEN_URL)
                .addHeader("Content-Type", CONTENT_TYPE_URLENCODED)
                .addFormParam("client_id", CLIENT_ID)
                .addFormParam("client_secret", CLIENT_SECRET)
                .addFormParam("grant_type", GRANT_TYPE)
                .task("异步获取access_token");

        return responseTask.map(response -> {
            String body = response.getResponseBody(UTF_8_CHARSET);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            return FridayHttpResponseUtil.parseToken(jsonNode);
        });
    }

    @Override
    public ConversationResponseBo getConversationMessageSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Task<ConversationResponseBo> getConversationMessageAsync() {
        Task<Response> messageTask = getAccessTokenAsync().flatMap(token ->
                WrappedAsyncHttpClient.post(CONVERSATION_URL)
                        .setHeader("Content-Type", CONTENT_TYPE_JSON)
                        .setBodyEncoding(UTF_8_CHARSET)
                        .setBody(createConversationRequestBOJson(token)).task("异步发送会话消息"));
        return messageTask.map(response -> {
            String body = response.getResponseBody(UTF_8_CHARSET);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            return FridayHttpResponseUtil.parseConversationMessage(jsonNode);
        });
    }

    private String createConversationRequestBOJson(String accessToken) {
        ConversationRequestBo conversationRequestBo = new ConversationRequestBo();
        conversationRequestBo.setAppId("1752246885613350954");
        conversationRequestBo.setUserId("2878708992");
        conversationRequestBo.setUserType(UserTypeEnum.DX_UID.getValue());
        conversationRequestBo.setUtterances(Collections.singletonList("商家反馈自己购买面粉没有满减辛苦帮忙看一下"));
        conversationRequestBo.setStream(Boolean.FALSE);
        conversationRequestBo.setAccessToken(accessToken);
        HashMap<String, String> businessInfo = new HashMap<>();
        businessInfo.put("yjm", "124");
        conversationRequestBo.setBusinessInfo(businessInfo);
        String jsonString = JSONObject.toJSONString(conversationRequestBo);
        return jsonString;
    }

    private Engine engine;

    @BeforeTest
    public void init() {
        engine = new EngineBuilder()
                .setTaskExecutor(Executors.newScheduledThreadPool(10))
                .setTimerScheduler(Executors.newSingleThreadScheduledExecutor())
                .build();

    }

    @Test
    public void testMessage() {
        Task<ConversationResponseBo> task = getConversationMessageAsync();
        engine.run(task);
        try {
            if (task.isDone()) {
                ConversationResponseBo s = task.get();
                System.out.println(s);
            }
            task.await();
            ConversationResponseBo s = task.get();
            System.out.println(s);
        } catch (Exception e) {

        }
    }

    @Test
    public void testToken() {
        Task<String> task = getAccessTokenAsync();
        engine.run(task);
        try {
            if (task.isDone()) {
                String s = task.get();
                System.out.println("token1:" + s);
            }
            task.await();
            String s = task.get();
            System.out.println("token2:" + s);
        } catch (Exception e) {

        }
    }
}
