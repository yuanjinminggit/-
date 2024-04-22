package com.leetcode.codereview.httpclient.my.bo;

public class HumanOperatedTTRequestDto {

    // 应用id
    private String appId;

    // 会话id
    private String conversationId;

    // 消息id
    private String messageId;

    // 用户id
    private String userId;

    // 平台鉴权token
    private String token;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
